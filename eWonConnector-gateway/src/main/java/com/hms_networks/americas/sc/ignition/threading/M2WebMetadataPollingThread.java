package com.hms_networks.americas.sc.ignition.threading;

import com.hms_networks.americas.sc.ignition.IgnitionEwonConnectorHook;
import com.hms_networks.americas.sc.ignition.comm.M2WebCommunicationManager;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.*;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import com.hms_networks.americas.sc.ignition.config.EwonSyncDataState;
import com.hms_networks.americas.sc.ignition.data.CacheManager;
import com.hms_networks.americas.sc.ignition.data.SyncDataStateManager;
import com.hms_networks.americas.sc.ignition.data.TagManager;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A thread that polls for M2Web metadata at a specified interval. The polling logic is defined in
 * the {@link M2WebMetadataPollingThread#run()} method. The polling interval can be changed at
 * runtime by calling the {@link M2WebMetadataPollingThread#setPollingInterval(long, TimeUnit)}
 * method.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebMetadataPollingThread extends PollingThread {

  /**
   * The thread name for {@link M2WebMetadataPollingThread}.
   *
   * @since 1.0.0
   */
  private static final String THREAD_NAME = "M2Web Metadata Polling Thread";

  /**
   * Log handler for {@link M2WebMetadataPollingThread}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(M2WebMetadataPollingThread.class);

  /**
   * Interval for updating the M2Web metadata cache. This is used to ensure that the M2Web metadata
   * cache is up-to-date with the M2Web API.
   *
   * @since 1.0.0
   */
  private static final long METADATA_CACHE_UPDATE_INTERVAL_MS = TimeUnit.MINUTES.toMillis(60);

  /**
   * Base for the backoff time when retrying the M2Web metadata cache sync.
   *
   * @since 1.0.0
   */
  private static final int METADATA_CACHE_UPDATE_RETRY_BACKOFF_BASE = 2;

  /**
   * Maximum backoff time when retrying the M2Web metadata cache sync.
   *
   * @since 1.0.0
   */
  private static final long METADATA_CACHE_UPDATE_RETRY_MAX_BACKOFF_MS =
      TimeUnit.MINUTES.toMillis(5);

  /**
   * Maximum number of retries when updating the M2Web metadata cache.
   *
   * <p>The maximum retry count is not applied to the initial metadata cache sync.
   *
   * @since 1.0.0
   */
  private static final int METADATA_CACHE_UPDATE_MAX_RETRIES = 5;

  /**
   * Boolean indicating if the metadata cache is currently being updated. This is used to prevent
   * multiple threads from updating the metadata cache at the same time.
   *
   * @since 1.0.0
   */
  private final AtomicBoolean isMetadataCacheUpdating = new AtomicBoolean(false);

  /**
   * Last date/time (in milliseconds) that the M2Web metadata cache was updated from the M2Web API.
   *
   * @since 1.0.0
   */
  private long lastMetadataCacheSyncDateTimeMs = 0;

  /**
   * Creates a new polling thread with the specified thread polling interval.
   *
   * @param pollingInterval The thread polling interval.
   * @param pollingIntervalTimeUnit The thread polling interval time unit.
   * @param gatewayHook The gateway module hook.
   * @param connectorSettings The Ewon connector settings.
   * @since 1.0.0
   */
  public M2WebMetadataPollingThread(
      long pollingInterval,
      TimeUnit pollingIntervalTimeUnit,
      IgnitionEwonConnectorHook gatewayHook,
      EwonConnectorSettings connectorSettings) {
    super(THREAD_NAME, pollingInterval, pollingIntervalTimeUnit, gatewayHook, connectorSettings);
  }

  /**
   * Gets a boolean indicating whether the M2Web metadata cache needs to be updated. The {@link
   * #METADATA_CACHE_UPDATE_INTERVAL_MS} is used to determine if the cache needs to be updated.
   *
   * <p>This is independent of the last metadata cache update time stored in {@link
   * EwonSyncDataState#LAST_M2WEB_METADATA_SYNC_TIME}, which is used only for display to the user.
   *
   * @return {@code true} if the M2Web metadata cache needs to be updated, {@code false} otherwise.
   * @since 1.0.0
   */
  private boolean doesMetadataCacheNeedUpdate() {
    long timeSinceLastMetadataCacheSyncMs =
        System.currentTimeMillis() - lastMetadataCacheSyncDateTimeMs;
    return timeSinceLastMetadataCacheSyncMs >= METADATA_CACHE_UPDATE_INTERVAL_MS;
  }

  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's <code>run</code> method to be called in that separately
   * executing thread.
   *
   * <p>The general contract of the method <code>run</code> is that it may take any action
   * whatsoever.
   *
   * @see Thread#run()
   * @since 1.0.0
   */
  @Override
  public void run() {
    try {
      // Check if metadata cache needs to be updated
      boolean metadataCacheNeedsUpdate = doesMetadataCacheNeedUpdate();

      // If metadata cache needs to be updated and not already in progress, update it
      final boolean expectedValueMetadataCacheUpdateNotInProgress = false;
      final boolean newValueMetadataCacheUpdateInProgress = true;
      if (metadataCacheNeedsUpdate
          && !isMetadataCacheUpdating.compareAndExchange(
              expectedValueMetadataCacheUpdateNotInProgress,
              newValueMetadataCacheUpdateInProgress)) {

        // Create boolean to track if an error/exception occurred
        boolean errorOccurred;

        // Create gateway list
        List<M2WebEwon> getEwonsResponseEwons = null;

        // Create boolean flag, counter, and lists
        boolean retry = true; // Default to true to ensure initial cache update is attempted
        int retryCount = 0;
        boolean retryGatewayList = false;
        List<M2WebEwon> retryGatewayTagList = new ArrayList<>();

        // Loop while retry flag is true
        while (retry) {
          // Reset error flag and retry flag, then set isRetry flag
          errorOccurred = false;
          retry = false;
          boolean isRetry = retryCount > 0;

          // Calculate backoff time
          double backoffTimeMsUnbounded =
              Math.pow(METADATA_CACHE_UPDATE_RETRY_BACKOFF_BASE, retryCount)
                  * (double) DateTimeConstants.MILLIS_PER_SECOND;
          long backoffTimeMs =
              Math.min((long) backoffTimeMsUnbounded, METADATA_CACHE_UPDATE_RETRY_MAX_BACKOFF_MS);

          // Log that metadata cache update is starting
          if (!SyncDataStateManager.getStartupM2WebMetadataSyncCompleted()) {
            LOGGER.info("Building M2Web metadata cache...");
          } else if (retryCount >= 1) {
            LOGGER.info("Updating M2Web metadata cache (Retry #{})...", retryCount);
          } else {
            LOGGER.info("Updating M2Web metadata cache...");
          }

          // Update Ewon gateway list (if first attempt or retry flag is true)
          if (!isRetry || retryGatewayList) {
            Future<M2WebGetEwonsResponse> ewonGatewayList =
                M2WebCommunicationManager.getEwonGatewayList(connectorSettings.getAuthInfo());
            if (connectorSettings.isDebugEnabled() && retryGatewayList) {
              LOGGER.debug(
                  "Retrying M2Web metadata cache gateway list update due to a previous error...");
            }
            try {
              M2WebGetEwonsResponse getEwonsResponse = ewonGatewayList.get();
              if (getEwonsResponse == null) {
                LOGGER.error(
                    "Failed to update M2Web metadata cache gateway list due to a null API"
                        + " response.");
                errorOccurred = true;
                retryGatewayList = true;
              } else if (!getEwonsResponse.getSuccess()) {
                LOGGER.error(
                    "Failed to update M2Web metadata cache gateway list because the API returned an"
                        + " unsuccessful result.");
                errorOccurred = true;
                retryGatewayList = true;
              } else {
                getEwonsResponseEwons = getEwonsResponse.getEwons();
                // Shuffle list to avoid groups of Ewons that are offline, etc. when polling
                Collections.shuffle(getEwonsResponseEwons);
                CacheManager.updateCachedM2WebEwonSet(getEwonsResponseEwons);
                if (connectorSettings.isDebugEnabled()) {
                  LOGGER.debug("Updated M2Web metadata cache gateway list successfully.");
                }
                retryGatewayList = false;
              }
            } catch (InterruptedException e) {
              LOGGER.error(
                  "Failed to update M2Web metadata cache gateway list due to an interruption.", e);
              errorOccurred = true;
              retryGatewayList = true;
            } catch (ExecutionException e) {
              LOGGER.error(
                  "Failed to update M2Web metadata cache gateway list due to an exception during"
                      + " execution.",
                  e);
              if (connectorSettings.isDebugEnabled()) {
                ExceptionUtilities.printExceptionTraceMessages(LOGGER, e);
              }
              errorOccurred = true;
              retryGatewayList = true;
            }
          }

          // Update tag list for each Ewon
          if (getEwonsResponseEwons != null) {
            Map<M2WebEwon, Future<M2WebEwonEBDTagListResponse>> ewonTagListFutures =
                new HashMap<>();
            List<M2WebEwon> ewonsToUpdate = isRetry ? retryGatewayTagList : getEwonsResponseEwons;
            for (M2WebEwon cachedEwon : ewonsToUpdate) {

              if (connectorSettings.isDebugEnabled() && retryGatewayList) {
                LOGGER.debug(
                    "Retrying M2Web metadata cache tag list update for gateway ["
                        + cachedEwon.getName()
                        + "] due to a previous error...");
              }

              // Create callback for updating cached tag list
              FutureCallback<M2WebEwonEBDTagListResponse>
                  m2WebEwonEBDTagListResponseFutureCallback =
                      new FutureCallback<>() {
                        @Override
                        public void completed(
                            M2WebEwonEBDTagListResponse m2WebEwonEBDTagListResponse) {
                          if (m2WebEwonEBDTagListResponse == null) {
                            String errorMessage =
                                String.format(
                                    "Failed to update M2Web metadata cache tag list for gateway"
                                        + " [%s] due to a null API response.",
                                    cachedEwon.getName());
                            LOGGER.error(errorMessage);
                            throw new RuntimeException(errorMessage);
                          } else if (!m2WebEwonEBDTagListResponse.getSuccess()) {
                            if (m2WebEwonEBDTagListResponse.isUnavailable()) {
                              // Get cached unavailable status
                              boolean cachedUnavailableStatus =
                                  CacheManager.getCachedM2WebEwonUnavailableStatus(
                                      cachedEwon.getName());

                              // If cached unavailable status is false, update it
                              if (!cachedUnavailableStatus) {
                                final boolean unavailableStatus = true;
                                CacheManager.updateCachedM2WebEwonUnavailableStatus(
                                    cachedEwon.getName(), unavailableStatus);
                              }

                              // Log error if debug enabled or cached unavailable status was false
                              if (connectorSettings.isDebugEnabled() || !cachedUnavailableStatus) {
                                String warnMessage =
                                    "Unable to update M2Web metadata cache tag list for gateway ["
                                        + cachedEwon.getName()
                                        + "] because the gateway is unavailable/offline.";
                                if (!connectorSettings.isDebugEnabled()) {
                                  warnMessage +=
                                      " (Subsequent warnings will not be logged until the gateway"
                                          + " becomes available again)";
                                }
                                LOGGER.warn(warnMessage);
                              }
                            } else if (m2WebEwonEBDTagListResponse.areEwonCredentialsIncorrect()) {
                              LOGGER.error(
                                  "Failed to update M2Web metadata cache tag list for gateway [{}]"
                                      + " because the configured credentials are incorrect or not"
                                      + " configured on the device.",
                                  cachedEwon.getName());
                            } else if (m2WebEwonEBDTagListResponse.didTimeoutReachingDevice()) {
                              LOGGER.error(
                                  "Failed to update M2Web metadata cache tag list for gateway [{}]"
                                      + " because the device did not respond in time.",
                                  cachedEwon.getName());
                            } else if (m2WebEwonEBDTagListResponse.wasUnableToReachDevice()
                                != M2WebEwonEBDResponse.ERROR_CODE_NONE) {
                              int errorCode = m2WebEwonEBDTagListResponse.wasUnableToReachDevice();
                              LOGGER.error(
                                  "Failed to update M2Web metadata cache tag list for gateway [{}]"
                                      + " because the API could not reach the device. Error code:"
                                      + " {}",
                                  cachedEwon.getName(),
                                  errorCode);
                            } else {
                              String errorMessage =
                                  String.format(
                                      "Failed to update M2Web metadata cache tag list for gateway"
                                          + " [%s] due to an error (%d): %s",
                                      cachedEwon.getName(),
                                      m2WebEwonEBDTagListResponse.getCode(),
                                      m2WebEwonEBDTagListResponse.getMessage());
                              LOGGER.error(errorMessage);
                              throw new RuntimeException(errorMessage);
                            }
                          } else {
                            // Get cached unavailable status
                            boolean cachedUnavailableStatus =
                                CacheManager.getCachedM2WebEwonUnavailableStatus(
                                    cachedEwon.getName());
                            if (cachedUnavailableStatus) {
                              // Update cached unavailable status
                              final boolean unavailableStatus = false;
                              CacheManager.updateCachedM2WebEwonUnavailableStatus(
                                  cachedEwon.getName(), unavailableStatus);

                              // Log info
                              LOGGER.info(
                                  "Gateway ["
                                      + cachedEwon.getName()
                                      + "] is now available. Updating metadata.");
                            }

                            List<M2WebEwonEBDTag> getEwonTagListResponseTags =
                                m2WebEwonEBDTagListResponse.getTags();
                            CacheManager.updateCachedM2WebEwonTagSet(
                                cachedEwon.getName(), getEwonTagListResponseTags);
                            TagManager.applyM2WebEwonTagConfigurations(
                                cachedEwon, getEwonTagListResponseTags);
                            if (connectorSettings.isDebugEnabled()) {
                              LOGGER.debug(
                                  "Updated M2Web metadata cache tag list for gateway ["
                                      + cachedEwon.getName()
                                      + "] successfully.");
                            }
                          }
                        }

                        @Override
                        public void failed(Exception e) {
                          LOGGER.error(
                              "Failed to update M2Web metadata cache tag list for gateway ["
                                  + cachedEwon.getName()
                                  + "] due to an exception. "
                                  + e,
                              e);
                          if (connectorSettings.isDebugEnabled()) {
                            ExceptionUtilities.printExceptionTraceMessages(LOGGER, e);
                          }
                        }

                        @Override
                        public void cancelled() {
                          LOGGER.error(
                              "Failed to update M2Web metadata cache tag list for gateway ["
                                  + cachedEwon.getName()
                                  + "] because the request was cancelled.");
                        }
                      };

              // Add future to map
              Future<M2WebEwonEBDTagListResponse> m2WebEwonEBDTagListResponseFuture =
                  M2WebCommunicationManager.getEwonTagList(
                      connectorSettings.getAuthInfo(),
                      cachedEwon.getEncodedName(),
                      m2WebEwonEBDTagListResponseFutureCallback);
              ewonTagListFutures.put(cachedEwon, m2WebEwonEBDTagListResponseFuture);

              // Add sleep to avoid overloading M2Web API
              try {
                Thread.sleep(100);
              } catch (InterruptedException e) {
                LOGGER.warn(
                    "M2Web metadata polling thread was interrupted while sleeping. HTTP(s) requests"
                        + " may be sent more frequently than expected.");
              }
            }

            // Wait for all tag list updates to complete
            for (Map.Entry<M2WebEwon, Future<M2WebEwonEBDTagListResponse>> ewonTagListFutureEntry :
                ewonTagListFutures.entrySet()) {
              try {
                ewonTagListFutureEntry.getValue().get();

                // Remove Ewon from retry list if it was successful
                retryGatewayTagList.remove(ewonTagListFutureEntry.getKey());
              } catch (Exception e) {
                // No need to log error here since it will be logged in the callback
                errorOccurred = true;

                // Add Ewon to retry list
                retryGatewayTagList.add(ewonTagListFutureEntry.getKey());
              }
            }
          } else {
            // Only log in debug mode because an error will already be logged regarding
            if (connectorSettings.isDebugEnabled()) {
              LOGGER.debug(
                  "Unable to update M2Web metadata cache tag list because the gateway list API"
                      + " request failed, or the response was otherwise null, invalid, or empty.");
            }
          }

          // If no errors, update last metadata cache sync date/time
          if (!errorOccurred) {
            lastMetadataCacheSyncDateTimeMs = System.currentTimeMillis();
            SyncDataStateManager.setLastM2WebMetadataSyncDateTime(lastMetadataCacheSyncDateTimeMs);

            // Update inital sync flag if needed and output log message
            if (!SyncDataStateManager.getStartupM2WebMetadataSyncCompleted()) {
              // Mark initial M2Web metadata cache sync as complete if it hasn't been already
              SyncDataStateManager.setStartupM2WebMetadataSyncCompleted();
              LOGGER.info("M2Web metadata cache built successfully.");
            } else {
              LOGGER.info("M2Web metadata cache update complete.");
            }

            // Update successful metadata sync execution counter
            SyncDataStateManager.incrementSuccessfulM2WebMetadataExecutionCount();
          } else {
            // Update failed metadata sync execution counter
            SyncDataStateManager.incrementFailedM2WebMetadataExecutionCount();

            // Check if retry is needed and output log message
            if (!SyncDataStateManager.getStartupM2WebMetadataSyncCompleted()
                || retryCount < METADATA_CACHE_UPDATE_MAX_RETRIES) {
              retry = true;
              LOGGER.warn(
                  "M2Web metadata cache update failed. Retrying in ["
                      + backoffTimeMs
                      + "] milliseconds.");
            } else if (retryCount >= METADATA_CACHE_UPDATE_MAX_RETRIES
                && METADATA_CACHE_UPDATE_MAX_RETRIES > 0) {
              LOGGER.error(
                  "M2Web metadata cache update failed after ["
                      + retryCount
                      + "] retries (maximum). Giving up.");
            } else {
              LOGGER.error("M2Web metadata cache update failed.");
            }
          }

          // Update sync data status tags
          TagManager.updateSyncDataStatusTags();

          // Handle if retry flag is true
          if (retry) {
            // Increment retry count
            retryCount++;

            // Sleep for calculated delay time before retrying
            try {
              Thread.sleep(backoffTimeMs);
            } catch (InterruptedException e) {
              LOGGER.warn(
                  "M2Web metadata polling thread was interrupted while sleeping before a retry. The"
                      + " retry may occur sooner than expected.");
            }
          }
        }

        // Set metadata cache update in progress flag
        final boolean metadataCacheNotInProgress = false;
        isMetadataCacheUpdating.set(metadataCacheNotInProgress);
      }
    } catch (Exception e) {
      LOGGER.error(
          "A critical error has occurred in the M2Web metadata synchronization thread! THIS IS A"
              + " BUG -- PLEASE REPORT IT!",
          e);
      ExceptionUtilities.printExceptionTraceMessages(LOGGER, e);
      gatewayHook.shutdown();
    }
  }
}

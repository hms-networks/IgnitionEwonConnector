package com.hms_networks.americas.sc.ignition.threading;

import com.hms_networks.americas.sc.ignition.IgnitionEwonConnectorHook;
import com.hms_networks.americas.sc.ignition.comm.M2WebCommunicationManager;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.*;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import com.hms_networks.americas.sc.ignition.data.CacheManager;
import com.hms_networks.americas.sc.ignition.data.SyncDataStateManager;
import com.hms_networks.americas.sc.ignition.data.TagManager;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A thread that polls for M2Web data at a specified interval. The polling logic is defined in the
 * {@link M2WebPollingThread#run()} method. The polling interval can be changed at runtime by
 * calling the {@link M2WebPollingThread#setPollingInterval(long, TimeUnit)} method.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebPollingThread extends PollingThread {

  /**
   * The thread name for {@link M2WebPollingThread}.
   *
   * @since 1.0.0
   */
  private static final String THREAD_NAME = "M2Web Polling Thread";

  /**
   * Log handler for {@link M2WebPollingThread}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(M2WebPollingThread.class);

  /**
   * Creates a new M2Web polling thread with the specified thread polling interval.
   *
   * @param pollingInterval The thread polling interval.
   * @param pollingIntervalTimeUnit The thread polling interval time unit.
   * @param gatewayHook The gateway module hook.
   * @param connectorSettings The Ewon connector settings.
   * @since 1.0.0
   */
  public M2WebPollingThread(
      long pollingInterval,
      TimeUnit pollingIntervalTimeUnit,
      IgnitionEwonConnectorHook gatewayHook,
      EwonConnectorSettings connectorSettings) {
    super(THREAD_NAME, pollingInterval, pollingIntervalTimeUnit, gatewayHook, connectorSettings);
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
      // Create a boolean to track if an error/exception occurred
      boolean errorOccurred = false;

      if (!SyncDataStateManager.getStartupM2WebMetadataSyncCompleted()) {
        if (connectorSettings.isDebugEnabled()) {
          LOGGER.debug(
              "Skipping M2Web tag data synchronization because the initial/startup metadata cache"
                  + " sync has not completed!");
        }
      } else {
        List<Future<M2WebEwonEBDInstantValuesResponse>> ewonInstantValuesFutures;
        if (connectorSettings.isForceLive()) {
          // Update tag data for all tags
          ewonInstantValuesFutures = updateM2WebTagDataForAllEwons();
        } else {
          // Create list of futures for tag data updates
          ewonInstantValuesFutures = new ArrayList<>();

          // Update tag data for forced Ewons
          for (M2WebEwon forcedRealtimeEwon : TagManager.getForcedM2WebEwons()) {
            ewonInstantValuesFutures.add(updateM2WebTagDataForEwon(forcedRealtimeEwon));
          }

          // Update tag data for forced tags (if parent Ewon not already forced realtime)
          CacheManager.getCachedM2WebEwons()
              .forEach(
                  m2WebEwon -> {
                    // Get parent Ewon name
                    String ewonName = m2WebEwon.getName();

                    // If parent Ewon not already forced realtime, update tag data for forced tags
                    if (!TagManager.getForcedM2WebEwons().contains(ewonName)) {
                      // Build list of forced tags for Ewon
                      List<String> forcedRealtimeEwonTags = new ArrayList<>();
                      for (M2WebEwonEBDTag m2WebEwonEBDTag :
                          CacheManager.getCachedM2WebEwonTagMap(ewonName).values()) {
                        if (TagManager.isEwonTagForcedM2Web(m2WebEwon, m2WebEwonEBDTag)) {
                          forcedRealtimeEwonTags.add(m2WebEwonEBDTag.getName());
                        }
                      }

                      // Update tag data for forced tags (if any)
                      if (!forcedRealtimeEwonTags.isEmpty()) {
                        ewonInstantValuesFutures.add(
                            updateM2WebTagDataForEwonTags(m2WebEwon, forcedRealtimeEwonTags));
                      }
                    }
                  });
        }

        // Update last M2Web tag data sync date/time
        SyncDataStateManager.setLastM2WebSyncDateTime(System.currentTimeMillis());

        // Wait for all Ewon instant values futures to complete to check for errors
        for (Future<M2WebEwonEBDInstantValuesResponse> ewonInstantValuesFuture :
            ewonInstantValuesFutures) {
          try {
            ewonInstantValuesFuture.get();
          } catch (Exception e) {
            // No need to log error here since it will be logged in the callback
            errorOccurred = true;

            // Break out of loop since we know an error occurred already. No need to wait for rest
            break;
          }
        }

        // Update success/failure counters
        if (errorOccurred) {
          SyncDataStateManager.incrementFailedM2WebExecutionCount();
        } else {
          SyncDataStateManager.incrementSuccessfulM2WebExecutionCount();
        }

        // Update sync data status tags
        TagManager.updateSyncDataStatusTags();
      }
    } catch (Exception e) {
      LOGGER.error(
          "A critical error has occurred in the M2Web data synchronization thread! THIS IS A BUG --"
              + " PLEASE REPORT IT!",
          e);
      ExceptionUtilities.printExceptionTraceMessages(LOGGER, e);
      gatewayHook.shutdown();
    }
  }

  /**
   * Updates tag data for all Ewons. The list of Ewons is retrieved from the metadata cache gateway
   * list.
   *
   * @since 1.0.0
   */
  public List<Future<M2WebEwonEBDInstantValuesResponse>> updateM2WebTagDataForAllEwons() {
    // Update tag data for all Ewons in cache
    List<Future<M2WebEwonEBDInstantValuesResponse>> ewonInstantValuesFutures = new ArrayList<>();
    for (M2WebEwon cachedEwon : CacheManager.getCachedM2WebEwons()) {
      ewonInstantValuesFutures.add(updateM2WebTagDataForEwon(cachedEwon));
    }
    return ewonInstantValuesFutures;
  }

  /**
   * Updates tag data for the specified {@link M2WebEwon}.
   *
   * @param ewon The {@link M2WebEwon} for which to update tag data. The value returned from {@link
   *     M2WebEwon#getName()} is used to identify the Ewon.
   * @return A {@link Future} for the M2Web EBD instant values response.
   * @since 1.0.0
   */
  public Future<M2WebEwonEBDInstantValuesResponse> updateM2WebTagDataForEwon(M2WebEwon ewon) {
    Collection<String> tagNames = null;
    return updateM2WebTagDataForEwonTags(ewon, tagNames);
  }

  /**
   * Updates tag data for the specified Ewon tags on the specified {@link M2WebEwon}.
   *
   * @param ewon The {@link M2WebEwon} for which to update tag data. The value returned from {@link
   *     M2WebEwon#getName()} is used to identify the Ewon.
   * @param tagNames The names of the tags for which to update tag data.
   * @return A {@link Future} for the M2Web EBD instant values response.
   * @since 1.0.0
   */
  public Future<M2WebEwonEBDInstantValuesResponse> updateM2WebTagDataForEwonTags(
      M2WebEwon ewon, Collection<String> tagNames) {
    // Build future callback for M2Web EBD instant values request
    FutureCallback<M2WebEwonEBDInstantValuesResponse> ewonEBDInstantValuesResponseFutureCallback =
        new FutureCallback<>() {
          @Override
          public void completed(
              M2WebEwonEBDInstantValuesResponse m2WebEwonEBDInstantValuesResponse) {
            // Check success status
            if (m2WebEwonEBDInstantValuesResponse.getSuccess()) {
              // Get cached unavailable status
              boolean cachedUnavailableStatus =
                  CacheManager.getCachedM2WebEwonUnavailableStatus(ewon.getName());
              if (cachedUnavailableStatus) {
                // Update cached unavailable status
                final boolean unavailableStatus = false;
                CacheManager.updateCachedM2WebEwonUnavailableStatus(
                    ewon.getName(), unavailableStatus);

                // Log info
                LOGGER.info(
                    "Gateway [" + ewon.getName() + "] is now available. Updating tag data.");
              }

              // Loop through Ewon tag values
              for (M2WebEwonEBDInstantValue instantValue :
                  m2WebEwonEBDInstantValuesResponse.getInstantValues()) {
                // Get tag name
                String tagName = instantValue.getTagName();

                // If tag name is in tagNames collection (or collection null/empty), update tag data
                if (tagNames == null || tagNames.isEmpty() || tagNames.contains(tagName)) {
                  TagManager.updateM2WebEwonTagInstantValue(ewon, instantValue);
                }
              }

              if (connectorSettings.isDebugEnabled()) {
                LOGGER.debug(
                    "Updated M2Web instant tag values for gateway ["
                        + ewon.getName()
                        + "] successfully.");
              }
            } else {
              if (m2WebEwonEBDInstantValuesResponse.isUnavailable()) {
                // Get cached unavailable status
                boolean cachedUnavailableStatus =
                    CacheManager.getCachedM2WebEwonUnavailableStatus(ewon.getName());

                // If cached unavailable status is false, update it
                if (!cachedUnavailableStatus) {
                  final boolean unavailableStatus = true;
                  CacheManager.updateCachedM2WebEwonUnavailableStatus(
                      ewon.getName(), unavailableStatus);
                }

                // Log error if debug enabled or cached unavailable status was false
                if (connectorSettings.isDebugEnabled() || !cachedUnavailableStatus) {
                  String message =
                      "Unable to update M2Web instant tag values for gateway ["
                          + ewon.getName()
                          + "] because the gateway is unavailable.";
                  if (!connectorSettings.isDebugEnabled()) {
                    message +=
                        " (Subsequent errors will not be logged until the gateway becomes available"
                            + " again)";
                  }
                  LOGGER.error(message);
                }
              } else if (m2WebEwonEBDInstantValuesResponse.areEwonCredentialsIncorrect()) {
                LOGGER.error(
                    "Failed to update M2Web instant tag values for gateway [{}] because the "
                        + "configured credentials are incorrect or not configured on the device.",
                    ewon.getName());
              } else if (m2WebEwonEBDInstantValuesResponse.didTimeoutReachingDevice()) {
                LOGGER.error(
                    "Failed to update M2Web instant tag values for gateway [{}] because the "
                        + "device did not respond in time.",
                    ewon.getName());
              } else if (m2WebEwonEBDInstantValuesResponse.wasUnableToReachDevice()
                  != M2WebEwonEBDResponse.ERROR_CODE_NONE) {
                int errorCode = m2WebEwonEBDInstantValuesResponse.wasUnableToReachDevice();
                LOGGER.error(
                    "Failed to update M2Web instant tag values for gateway [{}] because the "
                        + "API could not reach the device. Error code: {}",
                    ewon.getName(),
                    errorCode);
              } else {
                String errorMessage =
                    String.format(
                        "Failed to update M2Web instant tag values for gateway [%s] due to an"
                            + " error (%d): %s",
                        ewon.getName(),
                        m2WebEwonEBDInstantValuesResponse.getCode(),
                        m2WebEwonEBDInstantValuesResponse.getMessage());
                LOGGER.error(errorMessage);
                throw new RuntimeException(errorMessage);
              }
            }
          }

          @Override
          public void failed(Exception e) {
            LOGGER.error(
                "Failed to update M2Web instant tag values for gateway ["
                    + ewon.getName()
                    + "] due to an exception.",
                e);
            if (connectorSettings.isDebugEnabled()) {
              ExceptionUtilities.printExceptionTraceMessages(LOGGER, e);
            }
          }

          @Override
          public void cancelled() {
            LOGGER.error(
                "Failed to update M2Web instant tag values for gateway ["
                    + ewon.getName()
                    + "] because the request was cancelled.");
          }
        };

    // Perform M2Web EBD instant values request
    return M2WebCommunicationManager.getEwonInstantValues(
        connectorSettings.getAuthInfo(),
        ewon.getEncodedName(),
        ewonEBDInstantValuesResponseFutureCallback);
  }
}

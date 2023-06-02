package com.hms_networks.americas.sc.ignition.threading;

import com.hms_networks.americas.sc.ignition.IgnitionEwonConnectorHook;
import com.hms_networks.americas.sc.ignition.comm.DMWebCommunicationManager;
import com.hms_networks.americas.sc.ignition.comm.responses.dmw.*;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import com.hms_networks.americas.sc.ignition.config.EwonSyncDataState;
import com.hms_networks.americas.sc.ignition.data.CacheManager;
import com.hms_networks.americas.sc.ignition.data.SyncDataStateManager;
import com.hms_networks.americas.sc.ignition.data.TagManager;
import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A thread that polls for DMWeb data at a specified interval. The polling logic is defined in the
 * {@link DMWebPollingThread#run()} method. The polling interval can be changed at runtime by
 * calling the {@link DMWebPollingThread#setPollingInterval(long, TimeUnit)} method.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebPollingThread extends PollingThread {

  /**
   * The thread name for {@link DMWebPollingThread}.
   *
   * @since 1.0.0
   */
  private static final String THREAD_NAME = "DMWeb Polling Thread";

  /**
   * Log handler for {@link DMWebPollingThread}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DMWebPollingThread.class);

  /**
   * Creates a new DMWeb polling thread with the specified thread polling interval.
   *
   * @param pollingInterval The thread polling interval.
   * @param pollingIntervalTimeUnit The thread polling interval time unit.
   * @param gatewayHook The gateway module hook.
   * @param connectorSettings The Ewon connector settings.
   * @since 1.0.0
   */
  public DMWebPollingThread(
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
      // Only execute DMWeb polling thread tasks if M2Web is not globally enabled
      if (!connectorSettings.isForceLive()) {
        // Create a boolean to track if an error/exception occurred
        boolean errorOccurred = false;

        // Update tag data via DMWeb for eligible Ewons + tags
        if (!SyncDataStateManager.getStartupM2WebMetadataSyncCompleted()) {
          if (connectorSettings.isDebugEnabled()) {
            LOGGER.debug(
                "Skipped DMWeb tag data update because the initial/startup M2Web metadata sync has"
                    + " not completed.");
          }
        } else if (CacheManager.getCachedM2WebEwons().isEmpty()) {
          LOGGER.error(
              "Skipped DMWeb tag data update because the expected metadata cache(s) were empty.");
        } else {
          // Create callback for Ewon sync data update
          FutureCallback<SimpleHttpResponse> ewonSyncDataUpdateCallback =
              new FutureCallback<>() {
                @Override
                public void completed(SimpleHttpResponse simpleHttpResponse) {
                  try {
                    DMWebSyncDataResponse response =
                        DMWebSyncDataResponse.getFromJson(simpleHttpResponse.getBodyText());

                    // Store new sync data state last transaction ID
                    SyncDataStateManager.setLastDMWebTransactionId(response.getTransactionId());

                    // Loop through Ewon sync data and update data unless realtime data is forced
                    for (DMWebEwon syncDataEwon : response.getEwons()) {

                      // Check that Ewon is not forced to use M2Web
                      if (!TagManager.isEwonForcedM2Web(syncDataEwon.getName())
                          || connectorSettings.isCombineLiveData()) {

                        // Loop through tags and update data
                        for (DMWebEwonTag syncDataTag : syncDataEwon.getTags()) {

                          // Check that tag is not forced to use M2Web
                          if (!TagManager.isEwonTagForcedM2Web(syncDataEwon, syncDataTag)
                              || connectorSettings.isCombineLiveData()) {
                            // Update tag data for applicable tags
                            TagManager.updateDMWebEwonTag(syncDataEwon, syncDataTag);
                          }
                        }
                      }
                    }
                  } catch (Exception e) {
                    LOGGER.error(
                        "Failed to get updated Ewon tag data from DMWeb API due to an exception"
                            + " during parsing.");
                    LOGGER.error(
                        Arrays.stream(ExceptionUtils.getThrowables(e))
                            .map(Throwable::toString)
                            .collect(Collectors.joining(" -> ")));
                  }
                }

                @Override
                public void failed(Exception e) {
                  LOGGER.error(
                      "Failed to get updated Ewon tag data from DMWeb API due to an exception"
                          + " during execution.",
                      e);
                  if (connectorSettings.isDebugEnabled()) {
                    ExceptionUtilities.printExceptionTraceMessages(LOGGER, e);
                  }
                }

                @Override
                public void cancelled() {
                  LOGGER.error(
                      "Failed to get updated Ewon tag data from DMWeb API due to an interruption.");
                }
              };

          // Perform async request to get Ewon sync data
          final boolean createTransaction = true;
          long syncDataStateLastTransactionId = SyncDataStateManager.getLastDMWebTransactionId();
          Future<DMWebSyncDataResponse> dmWebSyncDataResponseFuture;
          if (syncDataStateLastTransactionId
              == EwonSyncDataState.LAST_DMWEB_TRANSACTION_ID_DEFAULT) {
            // If no last transaction ID is set (set to default), create a new transaction
            dmWebSyncDataResponseFuture =
                DMWebCommunicationManager.syncData(
                    connectorSettings.getAuthInfo(), createTransaction, ewonSyncDataUpdateCallback);
          } else {
            // If last transaction ID is set, use it to get updated data
            dmWebSyncDataResponseFuture =
                DMWebCommunicationManager.syncData(
                    connectorSettings.getAuthInfo(),
                    syncDataStateLastTransactionId,
                    createTransaction,
                    ewonSyncDataUpdateCallback);
          }

          // Wait for sync data response
          try {
            dmWebSyncDataResponseFuture.get();
          } catch (Exception e) {
            // No need to log error here since it will be logged in the callback
            errorOccurred = true;
          }

          // Update success/failure counters
          if (errorOccurred) {
            SyncDataStateManager.incrementFailedDMWebExecutionCount();
          } else {
            SyncDataStateManager.incrementSuccessfulDMWebExecutionCount();

            // Update last DMWeb tag data sync date/time
            SyncDataStateManager.setLastDMWebSyncDateTime(System.currentTimeMillis());
          }

          // Update sync data status tags
          TagManager.updateSyncDataStatusTags();
        }
      }
    } catch (Exception e) {
      LOGGER.error(
          "A critical error has occurred in the DMWeb data synchronization thread! THIS IS A BUG --"
              + " PLEASE REPORT IT!",
          e);
      ExceptionUtilities.printExceptionTraceMessages(LOGGER, e);
      gatewayHook.shutdown();
    }
  }
}

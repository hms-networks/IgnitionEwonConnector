package com.hms_networks.americas.sc.ignition.data;

import com.hms_networks.americas.sc.ignition.config.EwonSyncDataState;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simpleorm.utils.SException;

/**
 * Class for managing Ignition Ewon Connector sync data state using {@link EwonSyncDataState}.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class SyncDataStateManager {

  /**
   * Log handler for {@link SyncDataStateManager}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SyncDataStateManager.class);

  /**
   * Exception message for when the Ewon synchronization data state has not been initialized.
   *
   * @since 1.0.0
   */
  private static final String SYNC_DATA_NOT_INITIALIZED_EXCEPTION_MSG =
      "Ewon synchronization data state has not been initialized.";

  /**
   * The Ewon connector gateway context.
   *
   * @since 1.0.0
   */
  private static GatewayContext gatewayContext;

  /**
   * The stored Ewon synchronization data state information.
   *
   * @since 1.0.0
   */
  private static EwonSyncDataState syncDataState;

  /**
   * The number of successful M2Web thread executions that have occurred since the thread was
   * started. This value is non-persistent and is reset to zero (0) when the connector is
   * started/restarted.
   *
   * @since 1.0.0
   */
  private static int successfulM2WebExecutionCount = 0;

  /**
   * The number of failed M2Web thread executions that have occurred since the thread was started.
   * This value is non-persistent and is reset to zero (0) when the connector is started/restarted.
   *
   * @since 1.0.0
   */
  private static int failedM2WebExecutionCount = 0;

  /**
   * The number of successful M2Web metadata thread executions that have occurred since the thread
   * was started. This value is non-persistent and is reset to zero (0) when the connector is
   * started/restarted.
   *
   * @since 1.0.0
   */
  private static int successfulM2WebMetadataExecutionCount = 0;

  /**
   * The number of failed M2Web metadata thread executions that have occurred since the thread was
   * started. This value is non-persistent and is reset to zero (0) when the connector is
   * started/restarted.
   *
   * @since 1.0.0
   */
  private static int failedM2WebMetadataExecutionCount = 0;

  /**
   * The number of successful DMWeb thread executions that have occurred since the thread was
   * started. This value is non-persistent and is reset to zero (0) when the connector is
   * started/restarted.
   *
   * @since 1.0.0
   */
  private static int successfulDMWebExecutionCount = 0;

  /**
   * The number of failed DMWeb thread executions that have occurred since the thread was started.
   * This value is non-persistent and is reset to zero (0) when the connector is started/restarted.
   *
   * @since 1.0.0
   */
  private static int failedDMWebExecutionCount = 0;

  /**
   * Flag indicating if the startup M2Web metadata synchronization has been completed. This value is
   * non-persistent and is reset to false (false) when the connector is started/restarted.
   *
   * @since 1.0.0
   */
  private static boolean startupM2WebMetadataSyncCompleted = false;

  /**
   * Initializes the synchronization data state manager. This method will load existing
   * synchronization data, or create it if necessary.
   *
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void initialize(GatewayContext gatewayContext) {
    // Store gateway context
    SyncDataStateManager.gatewayContext = gatewayContext;

    // Reset non-persistent values
    successfulM2WebExecutionCount = 0;
    failedM2WebExecutionCount = 0;
    successfulM2WebMetadataExecutionCount = 0;
    failedM2WebMetadataExecutionCount = 0;
    successfulDMWebExecutionCount = 0;
    failedDMWebExecutionCount = 0;
    startupM2WebMetadataSyncCompleted = false;

    // Load and store synchronization data, and create it if necessary
    final long syncDataId = 1L;
    syncDataState =
        gatewayContext.getPersistenceInterface().find(EwonSyncDataState.META, syncDataId);
    if (syncDataState == null) {
      LOGGER.info("Existing Ewon synchronization data information not found, creating new.");
      syncDataState =
          gatewayContext.getLocalPersistenceInterface().createNew(EwonSyncDataState.META);
      syncDataState.setLong(EwonSyncDataState.ID, syncDataId);
      gatewayContext.getLocalPersistenceInterface().save(syncDataState);
    }
  }

  /**
   * Shuts down the synchronization data state manager. This method will remove stored gateway
   * context and synchronization data state.
   *
   * @since 1.0.0
   */
  public static void shutdown() {
    // Remove stored gateway context
    gatewayContext = null;

    // Remove stored synchronization data state
    syncDataState = null;
  }

  /**
   * Checks if the synchronization data state has been initialized, and throws an exception if it
   * has not.
   *
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @since 1.0.0
   */
  private static void checkSyncDataStateInitialized() {
    // Throw exception if sync data state has not been initialized
    if (syncDataState == null) {
      throw new IllegalStateException(SYNC_DATA_NOT_INITIALIZED_EXCEPTION_MSG);
    }
  }

  /**
   * Gets the synchronization data state.
   *
   * @return the synchronization data state
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @since 1.0.0
   */
  public static EwonSyncDataState getSyncDataState() {
    checkSyncDataStateInitialized();

    return syncDataState;
  }

  /**
   * Sets the synchronization data state latest DMWeb data point time stamp.
   *
   * @param lastM2WebSyncDateTime the latest DMWeb data point time stamp.
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void setLatestDMWebDataPointTimeStamp(Date lastM2WebSyncDateTime) {
    checkSyncDataStateInitialized();

    // Update latest DMWeb data point time stamp
    syncDataState.setLatestDMWebDataPointTimeStamp(lastM2WebSyncDateTime);
    gatewayContext.getLocalPersistenceInterface().save(syncDataState);
  }

  /**
   * Gets the synchronization data state latest DMWeb data point time stamp.
   *
   * @return the synchronization data state latest DMWeb data point time stamp
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @since 1.0.0
   */
  public static Date getLatestDMWebDataPointTimeStamp() {
    checkSyncDataStateInitialized();

    // Get latest DMWeb data point time stamp
    return syncDataState.getLatestDMWebDataPointTimeStamp();
  }

  /**
   * Sets the synchronization data state last M2Web sync date/time (in milliseconds).
   *
   * @param lastM2WebSyncDateTimeMs the last M2Web sync date/time (in milliseconds)
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void setLastM2WebSyncDateTime(long lastM2WebSyncDateTimeMs) {
    Date wrappedLastM2WebSyncDateTime = new Date(lastM2WebSyncDateTimeMs);
    setLastM2WebSyncDateTime(wrappedLastM2WebSyncDateTime);
  }

  /**
   * Sets the synchronization data state last M2Web sync date/time.
   *
   * @param lastM2WebSyncDateTime the last M2Web sync date/time
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void setLastM2WebSyncDateTime(Date lastM2WebSyncDateTime) {
    checkSyncDataStateInitialized();

    // Update last M2Web sync date/time
    syncDataState.setLastM2WebSyncTime(lastM2WebSyncDateTime);
    gatewayContext.getLocalPersistenceInterface().save(syncDataState);
  }

  /**
   * Gets the synchronization data state last M2Web sync date/time.
   *
   * @return the synchronization data state last M2Web sync date/time
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @since 1.0.0
   */
  public static Date getLastM2WebSyncDateTime() {
    checkSyncDataStateInitialized();

    // Get last M2Web sync date/time
    return syncDataState.getLastM2WebSyncTime();
  }

  /**
   * Sets the synchronization data state last M2Web metadata sync date/time (in milliseconds).
   *
   * @param lastM2WebMetadataSyncDateTimeMs the last M2Web metadata sync date/time (in milliseconds)
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void setLastM2WebMetadataSyncDateTime(long lastM2WebMetadataSyncDateTimeMs) {
    Date wrappedLastM2WebMetadataSyncDateTime = new Date(lastM2WebMetadataSyncDateTimeMs);
    setLastM2WebMetadataSyncDateTime(wrappedLastM2WebMetadataSyncDateTime);
  }

  /**
   * Sets the synchronization data state last M2Web metadata sync date/time.
   *
   * @param lastM2WebMetadataSyncDateTime the last M2Web metadata sync date/time
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void setLastM2WebMetadataSyncDateTime(Date lastM2WebMetadataSyncDateTime) {
    checkSyncDataStateInitialized();

    // Update last M2Web sync date/time
    syncDataState.setLastM2WebMetadataSyncTime(lastM2WebMetadataSyncDateTime);
    gatewayContext.getLocalPersistenceInterface().save(syncDataState);
  }

  /**
   * Gets the synchronization data state last M2Web metadata sync date/time.
   *
   * @return the synchronization data state last M2Web metadata sync date/time
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @since 1.0.0
   */
  public static Date getLastM2WebMetadataSyncDateTime() {
    checkSyncDataStateInitialized();

    // Get last M2Web sync date/time
    return syncDataState.getLastM2WebMetadataSyncTime();
  }

  /**
   * Sets the synchronization data state last DMWeb sync date/time (in milliseconds).
   *
   * @param lastDMWebSyncDateTimeMs the last DMWeb sync date/time (in milliseconds)
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void setLastDMWebSyncDateTime(long lastDMWebSyncDateTimeMs) {
    Date wrappedLastDMWebSyncDateTime = new Date(lastDMWebSyncDateTimeMs);
    setLastDMWebSyncDateTime(wrappedLastDMWebSyncDateTime);
  }

  /**
   * Sets the synchronization data state last DMWeb sync date/time.
   *
   * @param lastDMWebSyncDateTime the last DMWeb sync date/time
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void setLastDMWebSyncDateTime(Date lastDMWebSyncDateTime) {
    checkSyncDataStateInitialized();

    // Update last DMWeb sync date/time
    syncDataState.setLastDMWebSyncTime(lastDMWebSyncDateTime);
    gatewayContext.getLocalPersistenceInterface().save(syncDataState);
  }

  /**
   * Gets the synchronization data state last DMWeb sync date/time.
   *
   * @return the synchronization data state last DMWeb sync date/time
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @since 1.0.0
   */
  public static Date getLastDMWebSyncDateTime() {
    checkSyncDataStateInitialized();

    // Get last DMWeb sync date/time
    return syncDataState.getLastDMWebSyncTime();
  }

  /**
   * Sets the synchronization data state last DMWeb transaction ID.
   *
   * @param lastDMWebTransactionId the last DMWeb transaction ID
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @throws SException if the last M2Web metadata sync date/time could not be saved
   * @since 1.0.0
   */
  public static void setLastDMWebTransactionId(long lastDMWebTransactionId) {
    checkSyncDataStateInitialized();

    // Update last transaction ID
    syncDataState.setLastDMWebTransactionId(lastDMWebTransactionId);
    gatewayContext.getLocalPersistenceInterface().save(syncDataState);
  }

  /**
   * Gets the synchronization data state last DMWeb transaction ID.
   *
   * @return the synchronization data state last DMWeb transaction ID
   * @throws IllegalStateException if the synchronization data state has not been initialized
   * @since 1.0.0
   */
  public static long getLastDMWebTransactionId() {
    checkSyncDataStateInitialized();

    // Return last transaction ID
    return syncDataState.getLastDMWebTransactionId();
  }

  /**
   * Gets the successful execution count of the M2Web polling thread.
   *
   * @return The successful execution count of the M2Web polling thread.
   * @since 1.0.0
   */
  public static int getSuccessfulM2WebExecutionCount() {
    return successfulM2WebExecutionCount;
  }

  /**
   * Gets the failed execution count of the M2Web polling thread.
   *
   * @return The failed execution count of the M2Web polling thread.
   * @since 1.0.0
   */
  public static int getFailedM2WebExecutionCount() {
    return failedM2WebExecutionCount;
  }

  /**
   * Increments the successful execution count of the M2Web polling thread and returns the new
   * value.
   *
   * @since 1.0.0
   */
  public static void incrementSuccessfulM2WebExecutionCount() {
    ++successfulM2WebExecutionCount;
  }

  /**
   * Increments the failed execution count of the M2Web polling thread and returns the new value.
   *
   * @since 1.0.0
   */
  public static void incrementFailedM2WebExecutionCount() {
    ++failedM2WebExecutionCount;
  }

  /**
   * Gets the successful execution count of the M2Web metadata polling thread.
   *
   * @return The successful execution count of the M2Web metadata polling thread.
   * @since 1.0.0
   */
  public static int getSuccessfulM2WebMetadataExecutionCount() {
    return successfulM2WebMetadataExecutionCount;
  }

  /**
   * Gets the failed execution count of the M2Web metadata polling thread.
   *
   * @return The failed execution count of the M2Web metadata polling thread.
   * @since 1.0.0
   */
  public static int getFailedM2WebMetadataExecutionCount() {
    return failedM2WebMetadataExecutionCount;
  }

  /**
   * Increments the successful execution count of the M2Web metadata polling thread and returns the
   * new value.
   *
   * @since 1.0.0
   */
  public static void incrementSuccessfulM2WebMetadataExecutionCount() {
    ++successfulM2WebMetadataExecutionCount;
  }

  /**
   * Increments the failed execution count of the M2Web metadata polling thread and returns the new
   * value.
   *
   * @since 1.0.0
   */
  public static void incrementFailedM2WebMetadataExecutionCount() {
    ++failedM2WebMetadataExecutionCount;
  }

  /**
   * Gets the successful execution count of the DMWeb polling thread.
   *
   * @return The successful execution count of the DMWeb polling thread.
   * @since 1.0.0
   */
  public static int getSuccessfulDMWebExecutionCount() {
    return successfulDMWebExecutionCount;
  }

  /**
   * Gets the failed execution count of the DMWeb polling thread.
   *
   * @return The failed execution count of the DMWeb polling thread.
   * @since 1.0.0
   */
  public static int getFailedDMWebExecutionCount() {
    return failedDMWebExecutionCount;
  }

  /**
   * Increments the successful execution count of the DMWeb polling thread and returns the new
   * value.
   *
   * @since 1.0.0
   */
  public static void incrementSuccessfulDMWebExecutionCount() {
    ++successfulDMWebExecutionCount;
  }

  /**
   * Increments the failed execution count of the DMWeb polling thread and returns the new value.
   *
   * @since 1.0.0
   */
  public static void incrementFailedDMWebExecutionCount() {
    ++failedDMWebExecutionCount;
  }

  /**
   * Gets a flag indicating if the startup M2Web metadata synchronization has been completed.
   *
   * @since 1.0.0
   */
  public static boolean getStartupM2WebMetadataSyncCompleted() {
    return startupM2WebMetadataSyncCompleted;
  }

  /**
   * Sets a flag indicating if the startup M2Web metadata synchronization has been completed.
   *
   * @since 1.0.0
   */
  public static void setStartupM2WebMetadataSyncCompleted() {
    startupM2WebMetadataSyncCompleted = true;
  }
}

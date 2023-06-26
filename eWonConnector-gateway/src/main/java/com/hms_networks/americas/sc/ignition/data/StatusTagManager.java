package com.hms_networks.americas.sc.ignition.data;

import com.inductiveautomation.ignition.common.model.values.QualityCode;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;
import com.inductiveautomation.ignition.gateway.tags.managed.ManagedTagProvider;

/**
 * Class for managing Ignition Ewon Connector status tags via {@link TagManager}.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
class StatusTagManager {

  /**
   * The name of the folder that stores the status tags in the tag provider.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_FOLDER_NAME = "_STATUS";

  /**
   * The name of the status tag that stores the timestamp of the most recent data point processed
   * while the Ewon Connector synchronized data with DMWeb.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_LATEST_DMWEB_DATA_POINT_TS =
      STATUS_TAG_FOLDER_NAME + "/LATEST_DMWEB_DATA_POINT_TIMESTAMP";

  /**
   * The name of the status tag that stores the last time the Ewon Connector synchronized data with
   * DMWeb.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_LAST_DMWEB_SYNC_TIME =
      STATUS_TAG_FOLDER_NAME + "/LAST_DMWEB_SYNC_TIME";

  /**
   * The name of the status tag that stores the last time the Ewon Connector synchronized data with
   * M2Web.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_LAST_M2WEB_SYNC_TIME =
      STATUS_TAG_FOLDER_NAME + "/LAST_M2WEB_SYNC_TIME";

  /**
   * The name of the status tag that stores the last time the Ewon Connector synchronized metadata
   * with M2Web.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_LAST_M2WEB_METADATA_SYNC_TIME =
      STATUS_TAG_FOLDER_NAME + "/LAST_M2WEB_METADATA_SYNC_TIME";

  /**
   * The name of the status tag that stores the last transaction ID synchronized with DMWeb.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_LAST_DMWEB_TRANSACTION_ID =
      STATUS_TAG_FOLDER_NAME + "/LAST_DMWEB_TRANSACTION_ID";

  /**
   * The name of the status tag that stores the count of successful M2Web polling thread executions.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_SUCCESSFUL_M2WEB_POLL_COUNT =
      STATUS_TAG_FOLDER_NAME + "/SUCCESSFUL_M2WEB_POLL_COUNT";

  /**
   * The name of the status tag that stores the count of successful M2Web polling thread executions.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_FAILED_M2WEB_POLL_COUNT =
      STATUS_TAG_FOLDER_NAME + "/FAILED_M2WEB_POLL_COUNT";

  /**
   * The name of the status tag that stores the count of successful M2Web metadata polling thread
   * executions.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_SUCCESSFUL_M2WEB_METADATA_POLL_COUNT =
      STATUS_TAG_FOLDER_NAME + "/SUCCESSFUL_M2WEB_METADATA_POLL_COUNT";

  /**
   * The name of the status tag that stores the count of successful M2Web metadata polling thread
   * executions.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_FAILED_M2WEB_METADATA_POLL_COUNT =
      STATUS_TAG_FOLDER_NAME + "/FAILED_M2WEB_METADATA_POLL_COUNT";

  /**
   * The name of the status tag that stores the count of successful DMWeb polling thread executions.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_SUCCESSFUL_DMWEB_POLL_COUNT =
      STATUS_TAG_FOLDER_NAME + "/SUCCESSFUL_DMWEB_POLL_COUNT";

  /**
   * The name of the status tag that stores the count of successful DMWeb polling thread executions.
   *
   * @since 1.0.0
   */
  private static final String STATUS_TAG_NAME_FAILED_DMWEB_POLL_COUNT =
      STATUS_TAG_FOLDER_NAME + "/FAILED_DMWEB_POLL_COUNT";

  /**
   * The data type of the status tag that stores the timestamp of the most recent data point
   * processed while the Ewon Connector synchronized data with DMWeb.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_LATEST_DMWEB_DATA_POINT_TS = DataType.DateTime;

  /**
   * The data type of the status tag that stores the last time the Ewon Connector synchronized data
   * with DMWeb.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_LAST_DMWEB_SYNC_TIME = DataType.DateTime;

  /**
   * The data type of the status tag that stores the last time the Ewon Connector synchronized data
   * with M2Web.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_LAST_M2WEB_SYNC_TIME = DataType.DateTime;

  /**
   * The data type of the status tag that stores the last time the Ewon Connector synchronized
   * metadata with M2Web.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_LAST_M2WEB_METADATA_SYNC_TIME =
      DataType.DateTime;

  /**
   * The data type of the status tag that stores the last transaction ID synchronized with DMWeb.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_LAST_DMWEB_TRANSACTION_ID = DataType.Int4;

  /**
   * The data type of the status tag that stores the count of successful M2Web polling thread
   * executions.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_SUCCESSFUL_M2WEB_POLL_COUNT = DataType.Int4;

  /**
   * The data type of the status tag that stores the count of failed M2Web polling thread
   * executions.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_FAILED_M2WEB_POLL_COUNT = DataType.Int4;

  /**
   * The data type of the status tag that stores the count of successful M2Web metadata polling
   * thread executions.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_SUCCESSFUL_M2WEB_METADATA_POLL_COUNT =
      DataType.Int4;

  /**
   * The data type of the status tag that stores the count of failed M2Web metadata polling thread
   * executions.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_FAILED_M2WEB_METADATA_POLL_COUNT =
      DataType.Int4;

  /**
   * The data type of the status tag that stores the count of successful DMWeb polling thread
   * executions.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_SUCCESSFUL_DMWEB_POLL_COUNT = DataType.Int4;

  /**
   * The data type of the status tag that stores the count of failed DMWeb polling thread
   * executions.
   *
   * @since 1.0.0
   */
  private static final DataType STATUS_TAG_DATA_TYPE_FAILED_DMWEB_POLL_COUNT = DataType.Int4;

  /**
   * Initializes the status tags in the provided {@link ManagedTagProvider}.
   *
   * @param managedTagProvider the managed tag provider to initialize the status tags in
   * @since 1.0.0
   */
  static void initializeStatusTags(ManagedTagProvider managedTagProvider) {
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_LATEST_DMWEB_DATA_POINT_TS,
        STATUS_TAG_DATA_TYPE_LATEST_DMWEB_DATA_POINT_TS);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_LAST_DMWEB_SYNC_TIME, STATUS_TAG_DATA_TYPE_LAST_DMWEB_SYNC_TIME);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_LAST_M2WEB_SYNC_TIME, STATUS_TAG_DATA_TYPE_LAST_M2WEB_SYNC_TIME);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_LAST_M2WEB_METADATA_SYNC_TIME,
        STATUS_TAG_DATA_TYPE_LAST_M2WEB_METADATA_SYNC_TIME);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_LAST_DMWEB_TRANSACTION_ID, STATUS_TAG_DATA_TYPE_LAST_DMWEB_TRANSACTION_ID);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_SUCCESSFUL_M2WEB_POLL_COUNT,
        STATUS_TAG_DATA_TYPE_SUCCESSFUL_M2WEB_POLL_COUNT);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_FAILED_M2WEB_POLL_COUNT, STATUS_TAG_DATA_TYPE_FAILED_M2WEB_POLL_COUNT);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_SUCCESSFUL_M2WEB_METADATA_POLL_COUNT,
        STATUS_TAG_DATA_TYPE_SUCCESSFUL_M2WEB_METADATA_POLL_COUNT);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_FAILED_M2WEB_METADATA_POLL_COUNT,
        STATUS_TAG_DATA_TYPE_FAILED_M2WEB_METADATA_POLL_COUNT);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_SUCCESSFUL_DMWEB_POLL_COUNT,
        STATUS_TAG_DATA_TYPE_SUCCESSFUL_DMWEB_POLL_COUNT);
    managedTagProvider.configureTag(
        STATUS_TAG_NAME_FAILED_DMWEB_POLL_COUNT, STATUS_TAG_DATA_TYPE_FAILED_DMWEB_POLL_COUNT);
  }

  /**
   * Updates the status tags in the provided {@link ManagedTagProvider}.
   *
   * @param managedTagProvider the managed tag provider to update the status tags in
   * @since 1.0.0
   */
  static void updateStatusTags(ManagedTagProvider managedTagProvider) {
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_LATEST_DMWEB_DATA_POINT_TS,
        SyncDataStateManager.getLatestDMWebDataPointTimeStamp(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_LAST_DMWEB_SYNC_TIME,
        SyncDataStateManager.getLastDMWebSyncDateTime(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_LAST_M2WEB_SYNC_TIME,
        SyncDataStateManager.getLastM2WebSyncDateTime(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_LAST_M2WEB_METADATA_SYNC_TIME,
        SyncDataStateManager.getLastM2WebMetadataSyncDateTime(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_LAST_DMWEB_TRANSACTION_ID,
        SyncDataStateManager.getLastDMWebTransactionId(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_SUCCESSFUL_M2WEB_POLL_COUNT,
        SyncDataStateManager.getSuccessfulM2WebExecutionCount(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_FAILED_M2WEB_POLL_COUNT,
        SyncDataStateManager.getFailedM2WebExecutionCount(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_SUCCESSFUL_M2WEB_METADATA_POLL_COUNT,
        SyncDataStateManager.getSuccessfulM2WebMetadataExecutionCount(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_FAILED_M2WEB_METADATA_POLL_COUNT,
        SyncDataStateManager.getFailedM2WebMetadataExecutionCount(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_SUCCESSFUL_DMWEB_POLL_COUNT,
        SyncDataStateManager.getSuccessfulDMWebExecutionCount(),
        QualityCode.Good);
    managedTagProvider.updateValue(
        STATUS_TAG_NAME_FAILED_DMWEB_POLL_COUNT,
        SyncDataStateManager.getFailedDMWebExecutionCount(),
        QualityCode.Good);
  }
}

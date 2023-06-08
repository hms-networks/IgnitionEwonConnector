package com.hms_networks.americas.sc.ignition.config;

import com.inductiveautomation.ignition.gateway.localdb.persistence.*;
import java.util.Date;

/**
 * This is a persistent record to keep track of the last transaction id synchronized, as well as
 * other stats related to M2Web and DMWeb synchronization state.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @author Inductive Automation/Travis Cox
 * @since 1.0.0
 * @version 2.0.0
 */
public class EwonSyncDataState extends PersistentRecord {

  /**
   * The name of the table to store {@link EwonSyncDataState} records in.
   *
   * @since 2.0.0
   */
  public static final String RECORD_TABLE_NAME = "ewonSyncDataState";

  /**
   * The default value for time fields.
   *
   * @since 2.0.0
   */
  public static final long TIME_VALUE_DEFAULT = 0L;

  /**
   * The default value for the last DMWeb transaction ID.
   *
   * @since 2.0.0
   */
  public static final long LAST_DMWEB_TRANSACTION_ID_DEFAULT = 0L;

  /**
   * Meta information for the {@link EwonSyncDataState} record.
   *
   * @since 1.0.0
   */
  public static final RecordMeta<EwonSyncDataState> META =
      new RecordMeta<>(EwonSyncDataState.class, RECORD_TABLE_NAME);

  /**
   * Internal identifier
   *
   * @since 1.0.0
   */
  public static final IdentityField ID = new IdentityField(META);

  /**
   * The timestamp of the latest DMWeb data point processed by the Ewon Connector.
   *
   * @since 2.0.0
   */
  public static final LongField LATEST_DMWEB_DATA_POINT_TIME_STAMP =
      new LongField(META, "latestDMWebDataPointTimeStamp").setDefault(TIME_VALUE_DEFAULT);

  /**
   * The timestamp for the last M2Web sync operation.
   *
   * @since 2.0.0
   */
  public static final LongField LAST_M2WEB_SYNC_TIME =
      new LongField(META, "lastM2WebSyncTime").setDefault(TIME_VALUE_DEFAULT);

  /**
   * The timestamp for the last M2Web metadata sync operation.
   *
   * @since 2.0.0
   */
  public static final LongField LAST_M2WEB_METADATA_SYNC_TIME =
      new LongField(META, "lastM2WebMetadataSyncTime").setDefault(TIME_VALUE_DEFAULT);

  /**
   * The timestamp for the last DMWeb sync operation.
   *
   * @since 2.0.0
   */
  public static final LongField LAST_DMWEB_SYNC_TIME =
      new LongField(META, "lastDMWebSyncTime").setDefault(TIME_VALUE_DEFAULT);

  /**
   * The last transaction ID from the last DMWeb sync operation.
   *
   * @since 1.0.0
   */
  public static final LongField LAST_DMWEB_TRANSACTION_ID =
      new LongField(META, "lastDMWebTransactionId").setDefault(LAST_DMWEB_TRANSACTION_ID_DEFAULT);

  /**
   * Gets the meta information for the {@link EwonSyncDataState} record.
   *
   * @return meta information for the {@link EwonSyncDataState} record
   * @since 1.0.0
   */
  @Override
  public RecordMeta<?> getMeta() {
    return META;
  }

  /**
   * Gets the timestamp of the latest DMWeb data point processed by the Ewon Connector.
   *
   * @return the timestamp of the latest DMWeb data point processed by the Ewon Connector
   * @since 1.0.0
   */
  public Date getLatestDMWebDataPointTimeStamp() {
    return new Date(getLong(LATEST_DMWEB_DATA_POINT_TIME_STAMP));
  }

  /**
   * Sets the timestamp of the latest DMWeb data point processed by the Ewon Connector.
   *
   * @param value the new timestamp of the latest DMWeb data point processed by the Ewon Connector
   * @since 1.0.0
   */
  public void setLatestDMWebDataPointTimeStamp(Date value) {
    setLong(LATEST_DMWEB_DATA_POINT_TIME_STAMP, value.getTime());
  }

  /**
   * Gets the timestamp for the last M2Web sync operation.
   *
   * @return the timestamp for the last M2Web sync operation
   * @since 1.0.0
   */
  public Date getLastM2WebSyncTime() {
    return new Date(getLong(LAST_M2WEB_SYNC_TIME));
  }

  /**
   * Sets the timestamp for the last M2Web sync operation.
   *
   * @param value the new timestamp for the last M2Web sync operation
   * @since 1.0.0
   */
  public void setLastM2WebSyncTime(Date value) {
    setLong(LAST_M2WEB_SYNC_TIME, value.getTime());
  }

  /**
   * Gets the timestamp for the last M2Web metadata sync operation.
   *
   * @return the timestamp for the last M2Web metadata sync operation
   * @since 1.0.0
   */
  public Date getLastM2WebMetadataSyncTime() {
    return new Date(getLong(LAST_M2WEB_METADATA_SYNC_TIME));
  }

  /**
   * Sets the timestamp for the last M2Web metadata sync operation.
   *
   * @param value the new timestamp for the last M2Web metadata sync operation
   * @since 1.0.0
   */
  public void setLastM2WebMetadataSyncTime(Date value) {
    setLong(LAST_M2WEB_METADATA_SYNC_TIME, value.getTime());
  }

  /**
   * Gets the timestamp for the last DMWeb sync operation.
   *
   * @return the timestamp for the last DMWeb sync operation
   * @since 1.0.0
   */
  public Date getLastDMWebSyncTime() {
    return new Date(getLong(LAST_DMWEB_SYNC_TIME));
  }

  /**
   * Sets the timestamp for the last DMWeb sync operation.
   *
   * @param value the new timestamp for the last DMWeb sync operation
   * @since 1.0.0
   */
  public void setLastDMWebSyncTime(Date value) {
    setLong(LAST_DMWEB_SYNC_TIME, value.getTime());
  }

  /**
   * Gets the last transaction ID from the last DMWeb sync data operation.
   *
   * @return the last transaction ID from the last DMWeb sync data operation
   * @since 1.0.0
   */
  public Long getLastDMWebTransactionId() {
    return isNull(LAST_DMWEB_TRANSACTION_ID)
        ? LAST_DMWEB_TRANSACTION_ID_DEFAULT
        : getLong(LAST_DMWEB_TRANSACTION_ID);
  }

  /**
   * Sets the last transaction ID from the last DMWeb sync data operation.
   *
   * @param value the new last transaction ID from the last DMWeb sync data operation
   * @since 1.0.0
   */
  public void setLastDMWebTransactionId(Long value) {
    setLong(LAST_DMWEB_TRANSACTION_ID, value == null ? LAST_DMWEB_TRANSACTION_ID_DEFAULT : value);
  }
}

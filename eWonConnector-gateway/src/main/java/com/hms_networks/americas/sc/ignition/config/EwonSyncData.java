package com.hms_networks.americas.sc.ignition.config;

import java.util.Date;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IdentityField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.LongField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.PersistentRecord;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordMeta;

/**
 * This is a persistent record to keep track of the last transaction id synchronized, along with a
 * few other stats.
 */
public class EwonSyncData extends PersistentRecord {
  /** Record meta information for Ewon sync data */
  public static final RecordMeta<EwonSyncData> META =
      new RecordMeta<>(EwonSyncData.class, "ewonsyncdata");

  /** Internal identifier */
  public static final IdentityField Id = new IdentityField(META); //

  /** Ewon reported <code>lastSyncro</code> field used to track changes across reboots. */
  public static final LongField LastRemoteSync = new LongField(META, "lastremotesync");

  /** Timestamp for last local data update from Talk2M/DataMailbox */
  public static final LongField LastLocalSync = new LongField(META, "lastlocalsync");

  /** Timestamp for last historical data update. */
  public static final LongField LastHistoryTimestamp = new LongField(META, "lasthistorytimestamp");

  /** The last TXID returned from syncdata. */
  public static final LongField LastTXID = new LongField(META, "lasttxid").setDefault(0L);

  /**
   * Get meta information for Ewon sync data
   *
   * @return Ewon sync data meta information
   */
  @Override
  public RecordMeta<?> getMeta() {
    return META;
  }

  /**
   * Get transaction identifier
   *
   * @return transaction identifier
   */
  public Long getTransactionId() {
    return isNull(LastTXID) ? 0L : getLong(LastTXID);
  }

  /**
   * Set transaction identifier
   *
   * @param value new transaction identifier
   */
  public void setTransactionId(Long value) {
    setLong(LastTXID, value == null ? 0L : value);
  }

  /**
   * Get last remote synchronization timestamp
   *
   * @return last remote synchronization timestamp
   */
  public Date getLastRemoteSync() {
    return new Date(getLong(LastRemoteSync));
  }

  /**
   * Set last remote synchronization timestamp
   *
   * @param value new remote synchronization timestamp
   */
  public void setLastRemoteSync(Date value) {
    setLong(LastRemoteSync, value.getTime());
  }

  /**
   * Get last local synchronization timestamp
   *
   * @return last local synchronization timestamp
   */
  public Date getLastLocalSync() {
    return new Date(getLong(LastLocalSync));
  }

  /**
   * Set last local synchronization timestamp
   *
   * @param value new local synchronization timestamp
   */
  public void setLastLocalSync(Date value) {
    setLong(LastLocalSync, value.getTime());
  }

  /**
   * Get last historical synchronization timestamp
   *
   * @return last historical synchronization timestamp
   */
  public Date getLastHistoryTimestamp() {
    return new Date(getLong(LastHistoryTimestamp));
  }

  /**
   * Set last historical synchronization timestamp
   *
   * @param value last historical synchronization timestamp
   */
  public void setLastHistoryTimestamp(Date value) {
    setLong(LastHistoryTimestamp, value.getTime());
  }
}

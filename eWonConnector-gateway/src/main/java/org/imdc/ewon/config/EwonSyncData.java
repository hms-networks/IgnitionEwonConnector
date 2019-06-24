package org.imdc.ewon.config;

import java.util.Date;

import com.inductiveautomation.ignition.gateway.localdb.persistence.IdentityField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.LongField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.PersistentRecord;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordMeta;

/**
 * This is a persistent record to keep track of the last transaction id synchronzed, along with a few other stats.
 *
 */
public class EwonSyncData extends PersistentRecord {
   public static final RecordMeta<EwonSyncData> META = new RecordMeta<>(EwonSyncData.class, "ewonsyncdata");

   //This is our internal id
   public static final IdentityField Id = new IdentityField(META);	//
   //This is the "lastSyncro" field reported from eWon. We keep track of it so we know across restarts if we're up to date.
   public static final LongField LastRemoteSync = new LongField(META, "lastremotesync");
   //This is the last time we stored data locally from the DataMailbox/Talk2M.
   public static final LongField LastLocalSync = new LongField(META, "lastlocalsync");
   //The last timestamp stored by history.
   public static final LongField LastHistoryTimestamp = new LongField(META, "lasthistorytimestamp");

   //The last TXID returned from syncdata.
   public static final LongField LastTXID = new LongField(META, "lasttxid").setDefault(0L);

   @Override
   public RecordMeta<?> getMeta() {
      return META;
   }

   public Long getTransactionId(){
      return isNull(LastTXID) ? 0L : getLong(LastTXID);
   }

   public void setTransactionId(Long value){
      setLong(LastTXID, value == null ? 0L : value);
   }

   public Date getLastRemoteSync(){
      return new Date(getLong(LastRemoteSync));
   }

   public void setLastRemoteSync(Date value){
      setLong(LastRemoteSync, value.getTime());
   }

   public Date getLastLocalSync(){
      return new Date(getLong(LastLocalSync));
   }

   public void setLastLocalSync(Date value){
      setLong(LastLocalSync, value.getTime());
   }

   public Date getLastHistoryTimestamp(){
      return new Date(getLong(LastHistoryTimestamp));
   }

   public void setLastHistoryTimestamp(Date value){
      setLong(LastHistoryTimestamp, value.getTime());
   }
}

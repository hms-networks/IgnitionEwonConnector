package org.imdc.ewon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.imdc.ewon.comm.CommunicationManger;
import org.imdc.ewon.config.EwonConnectorSettings;
import org.imdc.ewon.config.EwonSyncData;
import org.imdc.ewon.data.DataPoint;
import org.imdc.ewon.data.EwonData;
import org.imdc.ewon.data.EwonsData;
import org.imdc.ewon.data.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inductiveautomation.ignition.common.FormatUtil;
import com.inductiveautomation.ignition.common.TypeUtilities;
import com.inductiveautomation.ignition.common.model.values.Quality;
import com.inductiveautomation.ignition.common.sqltags.BasicTagValue;
import com.inductiveautomation.ignition.common.sqltags.history.InterpolationMode;
import com.inductiveautomation.ignition.common.sqltags.model.TagPath;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataQuality;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataTypeClass;
import com.inductiveautomation.ignition.common.sqltags.model.types.TagQuality;
import com.inductiveautomation.ignition.common.sqltags.model.types.TagType;
import com.inductiveautomation.ignition.common.sqltags.model.types.TagValue;
import com.inductiveautomation.ignition.common.sqltags.model.types.TimestampSource;
import com.inductiveautomation.ignition.common.sqltags.parser.TagPathParser;
import com.inductiveautomation.ignition.common.util.TimeUnits;
import com.inductiveautomation.ignition.gateway.history.HistoricalTagValue;
import com.inductiveautomation.ignition.gateway.history.PackedHistoricalTagValue;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.sqltags.model.BasicScanclassHistorySet;
import com.inductiveautomation.ignition.gateway.sqltags.simple.SimpleTagProvider;
import com.inductiveautomation.ignition.gateway.sqltags.simple.WriteHandler;

public class SyncManager {
   private static final String STATUS_LASTSYNCTIME = "_Status/LastSyncTime";
   private static final String STATUS_LASTSYNCDURATION = "_Status/LastSyncDurationMS";
   private static final String STATUS_LAST_H_SYNCTIME = "_Status/LastHistoricalSyncTime";
   private static final String STATUS_LASTSYNCID = "_Status/LastHistoricalTransaction";
   private static final String STATUS_RESETSYNC = "_Status/ResetSync";
   private static final String STATUS_FORCE_HSYNC = "_Status/ForceSync";

   private static final String STATUS_SUCCESSCOUNT = "_Status/SuccessfulSyncCount";
   private static final String STATUS_FAILURECOUNT = "_Status/FailedSyncCount";
   private static final String STATUS_HIST_POINT_CNT = "_Status/HistoricalPointsProcessed";

   private static HashSet<String> registeredTags = new HashSet<String>();

   GatewayContext gatewayContext;
   Logger logger = LoggerFactory.getLogger("Ewon.SyncManager");
   SimpleTagProvider provider;
   CommunicationManger comm;
   String tagHistoryStore;
   // Key = ewonId, value = lastSynchroDate
   Map<Integer, Date> lastSyncCache = new HashMap<>();
   boolean historyEnabled = false;
   EwonSyncData syncData = null;

   long successCount = 0;
   long failureCount = 0;
   long histPoints = 0;

   public SyncManager(GatewayContext context, SimpleTagProvider provider) {
      this.gatewayContext = context;
      this.provider = provider;
   }

   public void startup(EwonConnectorSettings settings) {
      logger.info("Starting eWon sync manager.");

      comm = new CommunicationManger();
      comm.setAuthInfo(settings.getAuthInfo());

      historyEnabled = settings.isHistoryEnabled();
      tagHistoryStore = settings.getHistoryProvider();

      if(historyEnabled && StringUtils.isBlank(tagHistoryStore)){
         logger.warn("History sync is enable, but no history provider has been specified for storage. No data will be stored.");
      }

      syncData = gatewayContext.getPersistenceInterface().find(EwonSyncData.META, 1L);
      if (syncData == null) {
         logger.info("eWon sync data not found, initializing.");
         syncData = gatewayContext.getLocalPersistenceInterface().createNew(EwonSyncData.META);
         syncData.setLong(EwonSyncData.Id, 1L);
         gatewayContext.getLocalPersistenceInterface().save(syncData);
      }

      long pollRateMS = TimeUnits.toMillis(settings.getPollRate().doubleValue(), settings.getPollRateUnits());
      logger.debug("Configuring polling for {} ms", pollRateMS);
      if (pollRateMS > 0) {
         gatewayContext.getExecutionManager().register("ewon", "syncpoll", this::run, (int) pollRateMS);
      }

      provider.configureTag(buildTagPath(STATUS_LASTSYNCTIME), DataType.DateTime, TagType.Custom);
      provider.configureTag(buildTagPath(STATUS_LAST_H_SYNCTIME), DataType.DateTime, TagType.Custom);
      provider.configureTag(buildTagPath(STATUS_LASTSYNCID), DataType.Int4, TagType.Custom);
      provider.configureTag(buildTagPath(STATUS_RESETSYNC), DataType.Boolean, TagType.Custom);
      provider.configureTag(buildTagPath(STATUS_FORCE_HSYNC), DataType.Boolean, TagType.Custom);

      provider.updateValue(buildTagPath(STATUS_LAST_H_SYNCTIME), syncData.getLastLocalSync(), DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_LASTSYNCID), syncData.getTransactionId(), DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_RESETSYNC), Boolean.FALSE, DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_FORCE_HSYNC), Boolean.FALSE, DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_SUCCESSCOUNT), successCount, DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_FAILURECOUNT), failureCount, DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_HIST_POINT_CNT), histPoints, DataQuality.GOOD_DATA);

      provider.registerWriteHandler(buildTagPath(STATUS_RESETSYNC), new WriteHandler() {
         @Override
         public Quality write(TagPath path, Object val) {
            logger.info("Resetting historical sync transaction id.");
            syncData.setTransactionId(0L);
            syncData.setLastLocalSync(new Date(0));
            successCount = 0;
            failureCount = 0;
            histPoints = 0;
            saveSyncData();
            updateStatusTags();
            return DataQuality.GOOD_DATA;
         }
      });

      provider.registerWriteHandler(buildTagPath(STATUS_FORCE_HSYNC), new WriteHandler() {
         @Override
         public Quality write(TagPath path, Object val) {
            logger.info("Forcing historical sync");
            gatewayContext.getExecutionManager().executeOnce(() -> executeSync());
            return DataQuality.GOOD_DATA;
         }
      });
   }

   public void shutdown() {
      logger.info("Shutting down eWon sync manager.");
      gatewayContext.getExecutionManager().unRegister("ewon", "syncpoll");
   }

   protected boolean isHistoryEnabled() {
      return historyEnabled && !StringUtils.isBlank(tagHistoryStore);
   }

   protected void run() {
      try {
         long start = System.currentTimeMillis();
         logger.debug("Starting poll...");
         executeSync();
         logger.debug("Poll completed in {}", FormatUtil.formatDurationSince(start));
      } catch (Exception e) {
         logger.error("Error polling eWon data.", e);
      }
   }

   protected void executeSync() {
      long start = System.currentTimeMillis();
      try{
      // If history is enabled, we use syncData. Otherwise, we use a lighter weight method to just get the current
      // values from each device.
      if (isHistoryEnabled()) {
         syncHistoricalData();
      }

      // Even though history *might* include the realtime values, it doesn't if the current transaction is the latest.
      // Therefore, we still need to check the realtime values, I think.
      syncLatestValues();

      provider.updateValue(buildTagPath(STATUS_LASTSYNCTIME), new Date(start), DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_LASTSYNCDURATION), System.currentTimeMillis() - start,
              DataQuality.GOOD_DATA);
      successCount++;

      }catch(Exception e){
         logger.error("Error synchronizing eWon data.", e);
         failureCount++;
      }
      updateStatusTags();
   }

   protected void updateStatusTags() {
      provider.updateValue(buildTagPath(STATUS_SUCCESSCOUNT), successCount, DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_FAILURECOUNT), failureCount, DataQuality.GOOD_DATA);
      provider.updateValue(buildTagPath(STATUS_HIST_POINT_CNT), histPoints, DataQuality.GOOD_DATA);
   }

   protected void syncLatestValues() throws Exception {
      EwonsData data = comm.queryEwonDevices();
      List<EwonData> toSync = new ArrayList<>();
      if (data != null && data.getEwons() != null) {
         for (EwonData ewon : data.getEwons()) {
            int id = ewon.getId();
            Date lastSync = lastSyncCache.get(id);
            if (lastSync == null || lastSync.before(ewon.getLastSync_Date())) {
               toSync.add(ewon);
               logger.debug("Will mark '{}' for update, device sync time={} vs local sync time={}", ewon.getName(),
                       ewon.getLastSync_Date(), lastSync);
            }
         }
      }

      for (EwonData ewon : toSync) {
         try {
            long devstart = System.currentTimeMillis();
            updateTagValues(comm.queryEwon(ewon.getId()));
            logger.debug("Sync of eWon device '{}' finished in {}", ewon.getName(),
                    FormatUtil.formatDurationSince(devstart));
            lastSyncCache.put(ewon.getId(), ewon.getLastSync_Date());
         } catch (Exception e) {
            logger.error("Error syncing eWon device '{}/{}'", ewon.getName(), ewon.getId(), e);
         }
      }
   }

   protected void syncHistoricalData() throws Exception {
      Long lastTX;
      boolean hasMore = false;
      do {
         long start = System.currentTimeMillis();
         lastTX = syncData.getTransactionId();
         logger.debug("Starting syncData for txid {}", lastTX);

         EwonsData data = comm.syncData(lastTX);

         if (data != null) {
            Long newTXID = data.getTransactionId();
            hasMore = data.isMoreDataAvailable();
            updateTagValues(data);
            logger.debug("Data retrieved and processed in {}. New txid: {}, hasMore: {}",
                    FormatUtil.formatDurationSince(start), newTXID, hasMore);

            // Update the internal database for next time, in order to not lose our spot after a reboot.
            // However, if the txid hasn't changed, don't update- each time we update, we trigger the internal db to autobackup.
            if(lastTX != newTXID){
               syncData.setTransactionId(newTXID);
               syncData.setLastLocalSync(new Date(start));
               saveSyncData();
            }
         } else {
            hasMore = false;
         }
      } while (hasMore);
   }

   protected void saveSyncData() {
      try {
         gatewayContext.getPersistenceInterface().save(syncData);
         provider.updateValue(buildTagPath(STATUS_LAST_H_SYNCTIME), syncData.getLastLocalSync(),
                 DataQuality.GOOD_DATA);
         provider.updateValue(buildTagPath(STATUS_LASTSYNCID), syncData.getTransactionId(), DataQuality.GOOD_DATA);
      } catch (Exception e) {
         logger.error("Error saving and updating sync information.", e);
      }
   }

   protected void updateTagValues(EwonsData data) {
      if (data == null || data.getEwons() == null) {
         return;
      }

      for (EwonData ewon : data.getEwons()) {
         updateTagValues(ewon);
      }
   }

   protected TagValue buildTagValue(Object v, DataQuality quality, Date ts, DataType dtype) {
      Object vToUse = v;
      if (v != null) {
         // A lot of values come in as BigDecimal, BigInteger, that Ignition doesn't really deal with well. We'll
         // convert them to what we know they should be first.
         if (v instanceof Number) {
            Number bd = (Number) v;
            switch (dtype.getTypeClass()) {
            case String:
               vToUse = bd.toString();
               break;
            case Float:
               vToUse = bd.doubleValue();
               break;
            default:
               vToUse = bd.longValue();
               break;
            }
         }
      }
      return new BasicTagValue(TypeUtilities.coerceNullSafe(vToUse, dtype.getJavaType()), quality, ts);
   }

   protected HistoricalTagValue buildHTV(TagPath path, DataTypeClass dtc, TagValue v) {
      return new PackedHistoricalTagValue(path, dtc,
              dtc == DataTypeClass.Float ? InterpolationMode.Analog_Compressed : InterpolationMode.Discrete,
              TimestampSource.Value, v);
   }

   protected void updateTagValues(EwonData data) {
      if (data == null || data.getTags() == null) {
         return;
      }
      String device = data.getName();
      Date deviceDate = data.getLastSync_Date();
      Date latestValueTS = null;
      if (data.getTags() != null) {
         BasicScanclassHistorySet historySet = new BasicScanclassHistorySet(provider.getName(), "_exempt_", -1);
         for (Tag t : data.getTags()) {
            try {
               TagPath p = buildTagPath(device, t.getName());
               DataType dType = EwonUtil.toDataType(t.getDataType());
               DataTypeClass dtc = dType.getTypeClass();
               // Note: we don't check history enabled here, because even if history is not "enabled", it can be
               // manually triggered.
               if (!StringUtils.isBlank(tagHistoryStore) && t.getHistory() != null) {
                  for (DataPoint d : t.getHistory()) {
                     HistoricalTagValue htv = buildHTV(p, dtc, buildTagValue(d.getValue(),
                             EwonUtil.toQuality(d.getQuality()), EwonUtil.toDate(d.getDate()), dType));
                     historySet.add(htv);
                     histPoints++;
                  }
               }

               // Register write callback
               if(!registeredTags.contains(p.toStringPartial())) {
                  registeredTags.add(p.toStringPartial());
                  provider.registerWriteHandler(p.toStringPartial(), new WriteHandler() {
                      public Quality write(TagPath tagPath, Object o) {
                          try {
                              comm.writeTag(tagPath.getParentPath().getItemName(), tagPath.getItemName(), o.toString());
                              provider.updateValue(p.toStringPartial(), o, TagQuality.GOOD);
                          } catch (Exception e) {
                              logger.error("Writing tag to eWON via Talk2M API Failed");
                          }
                          return TagQuality.GOOD;
                      }
                  });
              }

               // Update realtime
               TagValue v = buildTagValue(t.getValue(), EwonUtil.toQuality(t.getQuality()), deviceDate, dType);
               provider.updateValue(p, v);
               logger.trace("Updated realtime value for '{}' [id={}] to {}", p, t.getId(), v);
            } catch (Exception e) {
               logger.error("Unable to create dataset for tag '{}/{}'", device, t.getName(), e);
            }
         }
         if (historySet.size() > 0) {
            try {
               // We need the values to be sorted asc based on tstamp
               historySet.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));
               latestValueTS = historySet.get(historySet.size() - 1).getTimestamp();
               gatewayContext.getHistoryManager().storeHistory(tagHistoryStore, historySet);
            } catch (Exception e) {
               logger.error("Error storing history for device '{}'", device, e);
            }
         }
      }
      if (syncData != null) {
         syncData.setLastRemoteSync(deviceDate);
         if (latestValueTS != null) {
            syncData.setLastHistoryTimestamp(latestValueTS);
         }
      }
   }

   protected String sanitizeName(String name) {
      return name.replaceAll("[\\.]", "_");
   }

   protected TagPath buildTagPath(String tagName) {
      return TagPathParser.parseSafe(provider.getName(), sanitizeName(tagName));
   }

   /**
    * This is what determines the path for a tag. Current, the device is the first folder, and then the rest of the
    * path.
    **/
   protected TagPath buildTagPath(String ewonName, String tagName) throws IOException {
      return TagPathParser.parse(provider.getName(), sanitizeName(ewonName + "/" + tagName));
   }
}

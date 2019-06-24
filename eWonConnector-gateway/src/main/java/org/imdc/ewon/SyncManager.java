package org.imdc.ewon;

import com.inductiveautomation.ignition.common.FormatUtil;
import com.inductiveautomation.ignition.common.TypeUtilities;
import com.inductiveautomation.ignition.common.config.BasicBoundPropertySet;
import com.inductiveautomation.ignition.common.config.PropertyValue;
import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.model.values.QualityCode;
import com.inductiveautomation.ignition.common.sqltags.BasicTagValue;
import com.inductiveautomation.ignition.common.sqltags.history.InterpolationMode;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataQuality;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataTypeClass;
import com.inductiveautomation.ignition.common.sqltags.model.types.TagValue;
import com.inductiveautomation.ignition.common.sqltags.model.types.TimestampSource;
import com.inductiveautomation.ignition.common.sqltags.parser.TagPathParser;
import com.inductiveautomation.ignition.common.config.BasicProperty;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.common.tags.paths.BasicTagPath;
import com.inductiveautomation.ignition.common.util.TimeUnits;
import com.inductiveautomation.ignition.gateway.history.HistoricalTagValue;
import com.inductiveautomation.ignition.gateway.history.PackedHistoricalTagValue;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.sqltags.model.BasicScanclassHistorySet;
import com.inductiveautomation.ignition.gateway.tags.managed.DeletionHandler;
import com.inductiveautomation.ignition.gateway.tags.managed.ManagedTagProvider;
import com.inductiveautomation.ignition.gateway.tags.managed.WriteHandler;
import org.apache.commons.lang3.StringUtils;
import org.imdc.ewon.comm.CommunicationManger;
import org.imdc.ewon.config.EwonConnectorSettings;
import org.imdc.ewon.config.EwonSyncData;
import org.imdc.ewon.data.DataPoint;
import org.imdc.ewon.data.EwonData;
import org.imdc.ewon.data.EwonsData;
import org.imdc.ewon.data.TMResult;
import org.imdc.ewon.data.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    ManagedTagProvider provider;
    String providerName;
    CommunicationManger comm;
    String tagHistoryStore;
    // Key = ewonId, value = lastSynchroDate
    Map<Integer, Date> lastSyncCache = new HashMap<>();
    boolean historyEnabled = false;
    boolean replaceUnderscore = false;
    EwonSyncData syncData = null;

    long successCount = 0;
    long failureCount = 0;
    long histPoints = 0;

    public SyncManager(GatewayContext context, ManagedTagProvider provider, String providerName) {
        this.gatewayContext = context;
        this.provider = provider;
        this.providerName = providerName;
    }

    public void startup(EwonConnectorSettings settings) {
        logger.info("Starting eWon sync manager.");

        comm = new CommunicationManger();
        comm.setAuthInfo(settings.getAuthInfo());

        // Enable deletion of this tag provider's tags
        provider.setDeletionHandler(new DeletionHandler() {
            public DeletionResponse process(TagPath t) {
                return DeletionResponse.Allowed;
            }
        });

        replaceUnderscore = settings.isReplaceUnderscore();

        historyEnabled = settings.isHistoryEnabled();
        tagHistoryStore = settings.getHistoryProvider();

        if (historyEnabled && StringUtils.isBlank(tagHistoryStore)) {
            logger.warn(
                    "History sync is enable, but no history provider has been specified for storage."
                            + "No data will be stored.");
        }

        syncData = gatewayContext.getPersistenceInterface().find(EwonSyncData.META, 1L);
        if (syncData == null) {
            logger.info("eWon sync data not found, initializing.");
            syncData = gatewayContext.getLocalPersistenceInterface().createNew(EwonSyncData.META);
            syncData.setLong(EwonSyncData.Id, 1L);
            gatewayContext.getLocalPersistenceInterface().save(syncData);
        }

        long pollRateMS = TimeUnits.toMillis(settings.getPollRate().doubleValue(), TimeUnits.MIN);
        logger.debug("Configuring polling for {} ms", pollRateMS);
        if (pollRateMS > 0) {
            gatewayContext.getExecutionManager().register("ewon", "syncpoll", this::run,
                    (int) pollRateMS);
        }

        long livePollRateMS =
                TimeUnits.toMillis(settings.getLivePollRate().doubleValue(), TimeUnits.SEC);
        logger.debug("Configuring live polling for {} ms", livePollRateMS);
        if (pollRateMS > 0) {
            gatewayContext.getExecutionManager().register("ewon", "synclive", this::runLive,
                    (int) livePollRateMS);
        }

        provider.configureTag(buildTagPath(STATUS_LASTSYNCTIME), DataType.DateTime);
        provider.configureTag(buildTagPath(STATUS_LAST_H_SYNCTIME), DataType.DateTime);
        provider.configureTag(buildTagPath(STATUS_LASTSYNCID), DataType.Int4);
        provider.configureTag(buildTagPath(STATUS_RESETSYNC), DataType.Boolean);
        provider.configureTag(buildTagPath(STATUS_FORCE_HSYNC), DataType.Boolean);

        provider.updateValue(buildTagPath(STATUS_LAST_H_SYNCTIME), syncData.getLastLocalSync(),
                QualityCode.Good);
        provider.updateValue(buildTagPath(STATUS_LASTSYNCID), syncData.getTransactionId(),
                QualityCode.Good);
        provider.updateValue(buildTagPath(STATUS_RESETSYNC), Boolean.FALSE, QualityCode.Good);
        provider.updateValue(buildTagPath(STATUS_FORCE_HSYNC), Boolean.FALSE, QualityCode.Good);
        provider.updateValue(buildTagPath(STATUS_SUCCESSCOUNT), successCount, QualityCode.Good);
        provider.updateValue(buildTagPath(STATUS_FAILURECOUNT), failureCount, QualityCode.Good);
        provider.updateValue(buildTagPath(STATUS_HIST_POINT_CNT), histPoints, QualityCode.Good);

        provider.registerWriteHandler(buildTagPath(STATUS_RESETSYNC), new WriteHandler() {
            @Override
            public QualityCode write(TagPath tagPath, Object o) {
                logger.info("Resetting historical sync transaction id.");
                syncData.setTransactionId(0L);
                syncData.setLastLocalSync(new Date(0));
                successCount = 0;
                failureCount = 0;
                histPoints = 0;
                saveSyncData();
                updateStatusTags();
                return QualityCode.Good;
            }
        });

        provider.registerWriteHandler(buildTagPath(STATUS_FORCE_HSYNC), new WriteHandler() {
            @Override
            public QualityCode write(TagPath tagPath, Object o) {
                logger.info("Forcing historical sync");
                gatewayContext.getExecutionManager().executeOnce(() -> executeSync());
                return QualityCode.Good;
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

    protected void runLive() {
        updateLive();
    }

    protected void updateLive() {
        logger.info("Updating Live Values");

        HashMap<String, ArrayList<String>> liveEwonNames = new HashMap<String, ArrayList<String>>();

        BasicProperty<Boolean> prop = new BasicProperty<Boolean>("Realtime", boolean.class);
        ArrayList<TagPath> tagList = new ArrayList<TagPath>();

        for (String tagPath : registeredTags) {
            List<String> pathParts = Arrays.asList(tagPath.split("/", 0));
            BasicTagPath p = new BasicTagPath(providerName, pathParts, prop);
            tagList.add(p);
        }

        // Compile a list of the tags that should be read in "Realtime"
        CompletableFuture<java.util.List<QualifiedValue>> cf =
                gatewayContext.getTagManager().readAsync(tagList);
        List<QualifiedValue> values;
        try {
            values = cf.get();
            for (int i = 0; i < tagList.size(); i++) {
                if ((boolean) values.get(i).getValue()) {

                    final int EWON_NAME_INDEX = 0;
                    final int TAG_NAME_INDEX = 1;

                    String eWonName = tagList.get(i).getPathComponent(EWON_NAME_INDEX);
                    if (liveEwonNames.containsKey(eWonName)) {
                        liveEwonNames.get(eWonName)
                                .add(tagList.get(i).getPathComponent(TAG_NAME_INDEX));
                    } else {
                        ArrayList<String> tags = new ArrayList<String>();
                        tags.add(tagList.get(i).getPathComponent(TAG_NAME_INDEX));
                        liveEwonNames.put(eWonName, tags);
                    }
                }
            }
        } catch (InterruptedException e) {
            logger.error("Error while reading tag parameters. InterruptedException.");
            e.printStackTrace();
        } catch (ExecutionException e) {
            logger.error("Error while reading tag parameters. ExecutionException.");
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.error("Error while reading tag parameters. Realtime property does not exist or"
                    + "is the wrong datatype.");
            e.printStackTrace();
        }

        // Make the Talk2M calls and popultate the "Realtime" values into ignition
        for (String key : liveEwonNames.keySet()) {
            try {
                TMResult tagData = new TMResult(comm.getLiveData(key));

                for (String tag : liveEwonNames.get(key)) {
                    Object value;
                    String valueString = tagData.getTagValue(unSanitizeName(tag));

                    // Value is a string
                    if (valueString.charAt(0) == '\"') {
                        // Strip the "s from the string
                        // TODO: Consider the case where the string value has "s
                        value = valueString.replaceAll("\"", "");
                    }
                    // Value is a number
                    else {
                        value = Float.parseFloat(valueString);
                    }

                    provider.updateValue((key + "/" + tag), value, QualityCode.Good, new Date());
                }
            } catch (Exception e) {
                logger.error("Error while parsing live data");
            }
        }
    }

    protected void executeSync() {
        long start = System.currentTimeMillis();
        try {
            // If history is enabled, we use syncData. Otherwise, we use a lighter weight method to
            // just get the current
            // values from each device.
            if (isHistoryEnabled()) {
                syncHistoricalData();
            }

            // Even though history *might* include the realtime values, it doesn't if the current
            // transaction is the latest.
            // Therefore, we still need to check the realtime values, I think.
            syncLatestValues();

            provider.updateValue(buildTagPath(STATUS_LASTSYNCTIME), new Date(start),
                    QualityCode.Good);
            provider.updateValue(buildTagPath(STATUS_LASTSYNCDURATION),
                    System.currentTimeMillis() - start, QualityCode.Good);
            successCount++;

        } catch (Exception e) {
            logger.error("Error synchronizing eWon data.", e);
            failureCount++;
        }
        updateStatusTags();
    }

    protected void updateStatusTags() {
        provider.updateValue(buildTagPath(STATUS_SUCCESSCOUNT), successCount, QualityCode.Good);
        provider.updateValue(buildTagPath(STATUS_FAILURECOUNT), failureCount, QualityCode.Good);
        provider.updateValue(buildTagPath(STATUS_HIST_POINT_CNT), histPoints, QualityCode.Good);
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
                    logger.debug(
                            "Will mark '{}' for update, device sync time={} vs local sync time={}",
                            ewon.getName(), ewon.getLastSync_Date(), lastSync);
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

                // Update the internal database for next time, in order to not lose our spot after a
                // reboot. However, if the txid hasn't changed, don't update- each time we update,
                // we trigger the internal db to autobackup.
                if (lastTX != newTXID) {
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
                    QualityCode.Good);
            provider.updateValue(buildTagPath(STATUS_LASTSYNCID), syncData.getTransactionId(),
                    QualityCode.Good);
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

    protected BasicTagValue buildTagValue(Object v, DataQuality quality, Date ts, DataType dtype) {
        Object vToUse = v;
        if (v != null) {
            // A lot of values come in as BigDecimal, BigInteger, that Ignition doesn't really deal
            // with well. We'll
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
        return new BasicTagValue(TypeUtilities.coerceNullSafe(vToUse, dtype.getJavaType()), quality,
                ts);
    }

    protected HistoricalTagValue buildHTV(String path, DataTypeClass dtc, BasicTagValue v) {
        return new PackedHistoricalTagValue(TagPathParser.parseSafe(providerName, path), dtc,
                dtc == DataTypeClass.Float ? InterpolationMode.Analog_Compressed
                        : InterpolationMode.Discrete,
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
            BasicScanclassHistorySet historySet =
                    new BasicScanclassHistorySet(providerName, "_exempt_", -1);
            for (Tag t : data.getTags()) {
                try {
                    String p = buildTagPath(device, t.getName());
                    DataType dType = EwonUtil.toDataType(t.getDataType());
                    DataTypeClass dtc = dType.getTypeClass();
                    // Note: we don't check history enabled here, because even if history is not
                    // "enabled", it can be
                    // manually triggered.
                    if (!StringUtils.isBlank(tagHistoryStore) && t.getHistory() != null) {
                        for (DataPoint d : t.getHistory()) {
                            HistoricalTagValue htv = buildHTV(p, dtc,
                                    buildTagValue(d.getValue(), EwonUtil.toQuality(d.getQuality()),
                                            EwonUtil.toDate(d.getDate()), dType));
                            historySet.add(htv);
                            histPoints++;
                        }
                    }

                    // Register write callback
                    if (!registeredTags.contains(p)) {
                        registeredTags.add(p);

                        // Create a "Realtime" property for the tag, default state is false
                        BasicBoundPropertySet readtimeProperty =
                                new BasicBoundPropertySet(new PropertyValue(
                                        new BasicProperty<Boolean>("Realtime", boolean.class),
                                        "false"));
                        provider.configureTag(p, readtimeProperty);

                        provider.registerWriteHandler(p, new WriteHandler() {
                            public QualityCode write(TagPath tagPath, Object o) {
                                try {
                                    String tagName = replaceUnderscore
                                            ? unSanitizeName(tagPath.getItemName())
                                            : tagPath.getItemName();
                                    comm.writeTag(tagPath.getParentPath().getItemName(), tagName,
                                            o.toString());
                                    provider.updateValue(p, o, QualityCode.Good);
                                } catch (Exception e) {
                                    logger.error("Writing tag to eWON via Talk2M API Failed");
                                }
                                return QualityCode.Good;
                            }
                        });
                    }

                    // Update realtime
                    TagValue v = buildTagValue(t.getValue(), EwonUtil.toQuality(t.getQuality()),
                            deviceDate, dType);

                    // provider.updateValue does not seem to set the right data type
                    // using configureTag to force the correct data type
                    provider.configureTag(p, dType);
                    provider.updateValue(p, v.getValue(), v.getQuality(), v.getTimestamp());
                    logger.trace("Updated realtime value for '{}' [id={}] to {}", p, t.getId(), v);
                } catch (Exception e) {
                    logger.error("Unable to create dataset for tag '{}/{}'", device, t.getName(),
                            e);
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

    protected String unSanitizeName(String name) {
        return name.replaceAll("[\\_]", ".");
    }

    protected String buildTagPath(String tagName) {
        return sanitizeName(tagName);
    }

    /**
     * This is what determines the path for a tag. Current, the device is the first folder, and then
     * the rest of the path.
     **/
    protected String buildTagPath(String ewonName, String tagName) throws IOException {
        return sanitizeName(ewonName + "/" + tagName);
    }
}

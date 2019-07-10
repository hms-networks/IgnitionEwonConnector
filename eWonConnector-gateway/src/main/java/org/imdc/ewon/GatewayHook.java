package org.imdc.ewon;

import java.util.Arrays;
import java.util.List;
import org.imdc.ewon.config.EwonConfigPage;
import org.imdc.ewon.config.EwonConnectorSettings;
import org.imdc.ewon.config.EwonSyncData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordListenerAdapter;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.sqltags.simple.SimpleTagProvider;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;

public class GatewayHook extends AbstractGatewayModuleHook {


   private final Logger logger = LoggerFactory.getLogger("Ewon.ModuleManager");
   private GatewayContext gatewayContext;
   private SimpleTagProvider realtime;
   private SyncManager mgr = null;

   private RecordListenerAdapter<EwonConnectorSettings> settingsListener =
         new RecordListenerAdapter<EwonConnectorSettings>() {
            public void recordUpdated(EwonConnectorSettings record) {
               updateSettings(record);
            };
         };

   @Override
   public void setup(GatewayContext gatewayContext) {
      this.gatewayContext = gatewayContext;
      BundleUtil.get().addBundle("ewon", getClass().getClassLoader(), "ewon_gateway");
      try {
         // This creates the settings tables in the internal db if necessary.
         gatewayContext.getSchemaUpdater().updatePersistentRecords(EwonConnectorSettings.META,
               EwonSyncData.META);

         // For connector settings, we want to ensure that we have 1 row
         // defined, identified by id=0.
         // We'll load it in startup.
         EwonConnectorSettings settings =
               gatewayContext.getLocalPersistenceInterface().createNew(EwonConnectorSettings.META);
         settings.setLong(EwonConnectorSettings.ID, 0L);
         gatewayContext.getSchemaUpdater().ensureRecordExists(settings);
      } catch (Exception e) {
         logger.error("Error validating internal database tables.", e);
      }

      EwonConnectorSettings.META.addRecordListener(settingsListener);

   }

   @Override
   public void startup(LicenseState licenseState) {
      EwonConnectorSettings settings =
            gatewayContext.getPersistenceInterface().find(EwonConnectorSettings.META, 0L);
      startupMgr(settings);
   }

   @Override
   public void shutdown() {
      EwonConnectorSettings.META.removeRecordListener(settingsListener);

   }

   @Override
   public List<? extends IConfigTab> getConfigPanels() {
      return Arrays.asList(EwonConfigPage.CONFIG_TAB);
   }

   protected void updateSettings(EwonConnectorSettings settings) {
      logger.info("eWon connector settings changed, reconfiguring synchronization manager.");
      shutdownManager();
      startupMgr(settings);
   }


   protected void startupMgr(EwonConnectorSettings settings) {
      if (settings.isEnabled()) {
         realtime = new SimpleTagProvider(settings.getName());
         try {
            realtime.startup(gatewayContext);
         } catch (Exception e) {
            logger.error("Error starting up realtime tag provider", e);
            realtime = null;
         }
         mgr = new SyncManager(gatewayContext, realtime);
         mgr.startup(settings);
      } else {
         logger.debug("eWon connector is not enabled, will not be started.");
      }
   }

   protected void shutdownManager() {
      if (mgr != null) {
         mgr.shutdown();
         mgr = null;
      }
      if (realtime != null) {
         realtime.shutdown();
         realtime = null;
      }
   }


   @Override
   public boolean isFreeModule() {
      return true;
   }
}

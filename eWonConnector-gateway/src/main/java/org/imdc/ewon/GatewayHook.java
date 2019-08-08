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

/**
 * Ewon Connector GatewayHook implementation. Processes startup, shutdown and management tasks.
 */
public class GatewayHook extends AbstractGatewayModuleHook {
   /**
    * Ewon Connector Logger
    */
   private final Logger logger = LoggerFactory.getLogger("Ewon.ModuleManager");

   /**
    * Current GatewayContext
    */
   private GatewayContext gatewayContext;

   /**
    * Tag provider for realtime configured tags
    */
   private SimpleTagProvider realtime;

   /**
    * Tag synchronization manager
    */
   private SyncManager mgr = null;

   /**
    * Listener for changes in Ewon Connector settings. Invokes {@link this#updateSettings(EwonConnectorSettings)}
    */
   private RecordListenerAdapter<EwonConnectorSettings> settingsListener =
         new RecordListenerAdapter<EwonConnectorSettings>() {
            public void recordUpdated(EwonConnectorSettings record) {
               updateSettings(record);
            };
         };

   /**
    * Initialize and setup Ewon Connector using given gateway context.
    * @param gatewayContext current gateway context
    */
   @Override
   public void setup(GatewayContext gatewayContext) {
      // Store given gateway context
      this.gatewayContext = gatewayContext;

      // Add Ewon Connector to gateway bundles
      BundleUtil.get().addBundle("ewon", getClass().getClassLoader(), "ewon_gateway");
      try {
         // Create settings tables in internal database, if necessary
         gatewayContext.getSchemaUpdater().updatePersistentRecords(EwonConnectorSettings.META,
               EwonSyncData.META);

         // Create Ewon Connector settings panel
         EwonConnectorSettings settings =
               gatewayContext.getLocalPersistenceInterface().createNew(EwonConnectorSettings.META);

         // Set settings ID to 0
         settings.setLong(EwonConnectorSettings.ID, 0L);

         // Ensure created
         gatewayContext.getSchemaUpdater().ensureRecordExists(settings);
      } catch (Exception e) {
         logger.error("Error validating internal database tables.", e);
      }

      // Add Ewon Connector settings change listener to Ewon Connector settings
      EwonConnectorSettings.META.addRecordListener(settingsListener);

   }

   /**
    * Process startup of Ewon Connector
    * @param licenseState state of gateway license
    */
   @Override
   public void startup(LicenseState licenseState) {
      EwonConnectorSettings settings =
            gatewayContext.getPersistenceInterface().find(EwonConnectorSettings.META, 0L);
      startupMgr(settings);
   }

   /**
    * Process shutdown of Ewon Connector
    */
   @Override
   public void shutdown() {
      EwonConnectorSettings.META.removeRecordListener(settingsListener);

   }

   /**
    * Get and return singleton list with Ewon Connector config panel
    * @return singleton list with config panel
    */
   @Override
   public List<? extends IConfigTab> getConfigPanels() {
      return Arrays.asList(EwonConfigPage.CONFIG_TAB);
   }

   /**
    * Update Ewon Connector settings
    * @param settings updated settings
    */
   protected void updateSettings(EwonConnectorSettings settings) {
      logger.info("Ewon connector settings changed, reconfiguring synchronization manager.");
      shutdownManager();
      startupMgr(settings);
   }

   /**
    * Manage startup of Ewon Connector module
    * @param settings connector settings to use
    */
   protected void startupMgr(EwonConnectorSettings settings) {
      // Verify module is enabled in settings
      if (settings.isEnabled()) {
         // Create realtime tag provider
         realtime = new SimpleTagProvider(settings.getName());
         try {
            // Startup realtime tag provider
            realtime.startup(gatewayContext);
         } catch (Exception e) {
            logger.error("Error starting up realtime tag provider", e);
            realtime = null;
         }
         // Create sync manager and start it up with given settings
         mgr = new SyncManager(gatewayContext, realtime);
         mgr.startup(settings);
      } else {
         logger.debug("Ewon connector is not enabled, will not be started.");
      }
   }

   /**
    * Manage shutdown of Ewon Connector module
    */
   protected void shutdownManager() {
      // If sync manager exists, shut it down and remove
      if (mgr != null) {
         mgr.shutdown();
         mgr = null;
      }
      // If realtime provider exists, shut it down and remove
      if (realtime != null) {
         realtime.shutdown();
         realtime = null;
      }
   }

   /**
    * Get and return if Ewon Connector is a free modules
    * @return true
    */
   @Override
   public boolean isFreeModule() {
      return true;
   }
}

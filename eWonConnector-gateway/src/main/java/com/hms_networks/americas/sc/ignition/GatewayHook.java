package com.hms_networks.americas.sc.ignition;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordListenerAdapter;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.tags.managed.ManagedTagProvider;
import com.inductiveautomation.ignition.gateway.tags.managed.ProviderConfiguration;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import com.hms_networks.americas.sc.ignition.config.EwonConfigPage;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import com.hms_networks.americas.sc.ignition.config.EwonSyncData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;

/**
 * Ewon Connector GatewayHook implementation. Processes startup, shutdown and management tasks.
 *
 * @author HMS Networks, MU Americas Solution Center
 */
public class GatewayHook extends AbstractGatewayModuleHook {
  /** Ewon Connector Logger */
  private final Logger logger = LoggerFactory.getLogger("Ewon.EwonModuleManager");

  /** Current GatewayContext */
  private GatewayContext gatewayContext;

  /** Tag provider for configured tags */
  private ManagedTagProvider tagProvider;

  /** Tag synchronization manager */
  private SyncManager mgr = null;

  /**
   * Listener for changes in Ewon Connector settings. Invokes {@link this#restart()} to restart the
   * connector.
   */
  private RecordListenerAdapter<EwonConnectorSettings> settingsListener =
      new RecordListenerAdapter<EwonConnectorSettings>() {
        public void recordUpdated(EwonConnectorSettings record) {
          logger.info("Settings have been updated. Restarting module.");
          restart();
        }
      };

  /**
   * Initialize and setup Ewon Connector using given gateway context.
   *
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
      gatewayContext
          .getSchemaUpdater()
          .updatePersistentRecords(EwonConnectorSettings.META, EwonSyncData.META);

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
  }

  /**
   * Process startup of Ewon Connector
   *
   * @param licenseState state of gateway license
   */
  @Override
  public void startup(LicenseState licenseState) {
    EwonConnectorSettings settings =
        gatewayContext.getPersistenceInterface().find(EwonConnectorSettings.META, 0L);
    startupMgr(settings);

    // Add Ewon Connector settings change listener to Ewon Connector settings
    EwonConnectorSettings.META.addRecordListener(settingsListener);
  }

  /** Process shutdown of Ewon Connector */
  @Override
  public void shutdown() {
    EwonConnectorSettings.META.removeRecordListener(settingsListener);
    shutdownManager();
  }

  /**
   * Get and return singleton list with Ewon Connector config panel
   *
   * @return singleton list with config panel
   */
  @Override
  public List<? extends IConfigTab> getConfigPanels() {
    return Arrays.asList(EwonConfigPage.CONFIG_TAB);
  }

  /** Restarts the Ewon Connector */
  protected void restart() {
    shutdown();
    startup(null);
  }

  /**
   * Manage startup of Ewon Connector module
   *
   * @param settings connector settings to use
   */
  protected void startupMgr(EwonConnectorSettings settings) {
    // Verify module is enabled in settings
    if (settings.isEnabled()) {
      try {
        // Create realtime tag provider
        tagProvider =
            gatewayContext
                .getTagManager()
                .getOrCreateManagedProvider(
                    new ProviderConfiguration(settings.getName())
                        .setAllowTagCustomization(true)
                        .setPersistTags(true)
                        .setPersistValues(true)
                        .setAllowTagDeletion(true));
      } catch (Exception e) {
        logger.error("An error occurred while starting the " + "tag provider.", e);
        tagProvider = null;
      }
      // Create sync manager and start it up with given settings
      mgr = new SyncManager(gatewayContext, tagProvider, settings.getName());
      mgr.startup(settings);
    } else {
      logger.debug("The Ewon connector has been disabled in its options. " + "Not starting up.");
    }
  }

  /** Manage shutdown of Ewon Connector module */
  protected void shutdownManager() {
    // If sync manager exists, shut it down and remove
    if (mgr != null) {
      mgr.shutdown();
      mgr = null;
    }
    // If realtime provider exists, shut it down and remove
    if (tagProvider != null) {
      tagProvider.shutdown(false);
      tagProvider = null;
    }
  }

  /**
   * Get and return if Ewon Connector is a free module
   *
   * @return true
   */
  @Override
  public boolean isFreeModule() {
    return true;
  }
}

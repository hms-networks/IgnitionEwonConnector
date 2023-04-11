package com.hms_networks.americas.sc.ignition;

import com.hms_networks.americas.sc.ignition.comm.AsyncHttpRequestManager;
import com.hms_networks.americas.sc.ignition.comm.M2WebSessionManager;
import com.hms_networks.americas.sc.ignition.config.EwonConfigPage;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import com.hms_networks.americas.sc.ignition.config.EwonSyncDataState;
import com.hms_networks.americas.sc.ignition.data.CacheManager;
import com.hms_networks.americas.sc.ignition.data.SyncDataStateManager;
import com.hms_networks.americas.sc.ignition.data.TagManager;
import com.hms_networks.americas.sc.ignition.data.tagwrite.BufferedTagWriteManager;
import com.hms_networks.americas.sc.ignition.threading.DMWebPollingThread;
import com.hms_networks.americas.sc.ignition.threading.M2WebMetadataPollingThread;
import com.hms_networks.americas.sc.ignition.threading.M2WebPollingThread;
import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.licensing.LicenseState;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordListenerAdapter;
import com.inductiveautomation.ignition.gateway.model.AbstractGatewayModuleHook;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.web.models.IConfigTab;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Ignition Ewon Connector GatewayHook implementation. Processes startup, shutdown and
 * management tasks.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 * @version 2.0.0
 */
public class IgnitionEwonConnectorHook extends AbstractGatewayModuleHook {

  /**
   * Log handler for {@link AsyncHttpRequestManager}.
   *
   * @since 2.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(IgnitionEwonConnectorHook.class);

  /**
   * Prefix for Ignition Ewon Connector module bundle.
   *
   * @since 2.0.0
   */
  public static final String BUNDLE_PREFIX_EWON = "ewon";

  /**
   * Name of Ignition Ewon Connector module bundle.
   *
   * @since 2.0.0
   */
  public static final String BUNDLE_NAME_EWON_GATEWAY = BUNDLE_PREFIX_EWON + "_gateway";

  /**
   * Current GatewayContext.
   *
   * @since 1.0.0
   */
  private GatewayContext gatewayContext;

  /**
   * Thread for polling the DMWeb API.
   *
   * @since 2.0.0
   */
  private DMWebPollingThread dmWebPollingThread;

  /**
   * Thread for metadata polling via the M2Web API.
   *
   * @since 2.0.0
   */
  private M2WebMetadataPollingThread m2WebMetadataPollingThread;

  /**
   * Thread for polling the M2Web API.
   *
   * @since 2.0.0
   */
  private M2WebPollingThread m2WebPollingThread;

  /**
   * The Ewon Connector settings.
   *
   * @since 2.0.0
   */
  private EwonConnectorSettings connectorSettings;

  /**
   * Listener for changes in Ewon Connector settings. Invokes {@link this#restart()} to restart the
   * connector.
   *
   * @since 1.0.0
   */
  private final RecordListenerAdapter<EwonConnectorSettings> settingsListener =
      new RecordListenerAdapter<EwonConnectorSettings>() {
        public void recordUpdated(EwonConnectorSettings record) {
          LOGGER.info(
              "Ignition Ewon Connector Module settings have been updated. Restarting module.");
          restart();
        }
      };

  /**
   * Performs any necessary setup tasks for the Ignition Ewon Connector module.
   *
   * @param gatewayContext the current gateway context
   * @since 1.0.0
   */
  @Override
  public void setup(GatewayContext gatewayContext) {
    // Set up message
    LOGGER.debug("Running: Ignition Ewon Connector Module Setup");

    // Store gateway context
    this.gatewayContext = gatewayContext;

    // Add Ewon Connector to gateway bundles
    BundleUtil.get()
        .addBundle(
            BUNDLE_PREFIX_EWON,
            IgnitionEwonConnectorHook.class.getClassLoader(),
            BUNDLE_NAME_EWON_GATEWAY);

    // Set up Ewon Connector settings
    try {
      // Create settings tables in internal database, if necessary
      gatewayContext
          .getSchemaUpdater()
          .updatePersistentRecords(EwonConnectorSettings.META, EwonSyncDataState.META);

      // Create Ewon Connector settings panel
      EwonConnectorSettings settings =
          gatewayContext.getLocalPersistenceInterface().createNew(EwonConnectorSettings.META);

      // Set settings ID to 0
      settings.setLong(EwonConnectorSettings.ID, 0L);

      // Ensure created
      gatewayContext.getSchemaUpdater().ensureRecordExists(settings);
    } catch (Exception e) {
      LOGGER.error(
          "Error validating internal database tables during load of Ignition Ewon Connector module"
              + " settings.",
          e);
    }

    // Set up complete message
    LOGGER.debug("Finished: Ignition Ewon Connector Module Setup");
  }

  /**
   * Performs any necessary startup tasks for the Ignition Ewon Connector module.
   *
   * @param licenseState the current license state (not used)
   * @since 1.0.0
   */
  @Override
  public void startup(LicenseState licenseState) {
    // Startup message
    LOGGER.debug("Running: Ignition Ewon Connector Module Startup");

    // Load Ewon Connector settings
    connectorSettings =
        gatewayContext.getPersistenceInterface().find(EwonConnectorSettings.META, 0L);

    // Add settings listener
    EwonConnectorSettings.META.addRecordListener(settingsListener);

    // Verify module is enabled in settings and start up
    if (connectorSettings.isEnabled()) {
      // Initialize sync data state manager
      try {
        SyncDataStateManager.initialize(gatewayContext);
      } catch (Exception e) {
        LOGGER.error(
            "An error occurred while starting the Ignition Ewon Connector sync data state manager.",
            e);
      }

      // Initialize tag manager
      try {
        TagManager.initialize(gatewayContext, connectorSettings);
      } catch (Exception e) {
        LOGGER.error(
            "An error occurred while starting the Ignition Ewon Connector tag manager.", e);
      }

      // Initialize HTTP client
      AsyncHttpRequestManager.initialize(connectorSettings.isDebugEnabled());

      // Configure DMWeb polling thread
      final long dmWebPollingInterval = connectorSettings.getPollRate();
      final TimeUnit dmWebPollingIntervalUnit = TimeUnit.MINUTES;
      dmWebPollingThread =
          new DMWebPollingThread(
              dmWebPollingInterval, dmWebPollingIntervalUnit, this, connectorSettings);
      dmWebPollingThread.start();

      // Configure M2Web metadata polling thread
      final long m2WebMetadataPollingInterval = connectorSettings.getMetadataPollRate();
      final TimeUnit m2WebMetadataPollingIntervalUnit = TimeUnit.MINUTES;
      m2WebMetadataPollingThread =
          new M2WebMetadataPollingThread(
              m2WebMetadataPollingInterval,
              m2WebMetadataPollingIntervalUnit,
              this,
              connectorSettings);
      m2WebMetadataPollingThread.start();

      // Configure M2Web polling thread
      final long m2WebPollingInterval = connectorSettings.getLivePollRate();
      final TimeUnit m2WebPollingIntervalUnit = TimeUnit.SECONDS;
      m2WebPollingThread =
          new M2WebPollingThread(
              m2WebPollingInterval, m2WebPollingIntervalUnit, this, connectorSettings);
      m2WebPollingThread.start();
    } else {
      LOGGER.debug("The Ewon connector has been disabled in its options. Not starting up.");
    }

    // Startup complete message
    LOGGER.debug("Finished: Ignition Ewon Connector Module Startup");
  }

  /**
   * Performs any necessary shutdown tasks for the Ignition Ewon Connector module.
   *
   * @since 1.0.0
   */
  @Override
  public void shutdown() {
    // Shutdown message
    LOGGER.debug("Running: Ignition Ewon Connector Module Shutdown");

    // Remove settings listener
    EwonConnectorSettings.META.removeRecordListener(settingsListener);

    // Cancel buffered writing
    BufferedTagWriteManager.cancelBufferedTagWriteProcessing();

    // Shutdown thread for DMWeb polling
    if (dmWebPollingThread != null) {
      dmWebPollingThread.stop();
      dmWebPollingThread = null;
    }

    // Shutdown thread for M2Web metadata polling
    if (m2WebMetadataPollingThread != null) {
      m2WebMetadataPollingThread.stop();
      m2WebMetadataPollingThread = null;
    }

    // Shutdown thread for M2Web polling
    if (m2WebPollingThread != null) {
      m2WebPollingThread.stop();
      m2WebPollingThread = null;
    }

    // Shutdown HTTP client
    AsyncHttpRequestManager.shutdown();

    // Shutdown tag manager
    TagManager.shutdown();

    // Shutdown sync data state manager
    SyncDataStateManager.shutdown();

    // Log out of all M2Web sessions
    M2WebSessionManager.logoutAll(connectorSettings.getAuthInfo());

    // Clear caches
    CacheManager.clearCaches();

    // Clear Ewon Connector settings
    connectorSettings = null;

    // Request garbage collection
    System.gc();

    // Shutdown complete message
    LOGGER.debug("Finished: Ignition Ewon Connector Module Shutdown");
  }

  /**
   * Restarts the Ewon Connector by shutting it down and starting it up again.
   *
   * @since 1.0.0
   */
  protected void restart() {
    shutdown();
    startup(null);
  }

  /**
   * Returns a boolean indicating whether this module is a free module. This implementation always
   * returns true, as the Ignition Ewon Connector is a free module.
   *
   * @return true
   * @since 1.0.0
   */
  @Override
  public boolean isFreeModule() {
    return true;
  }

  /**
   * Get and return singleton list with Ewon Connector config panel
   *
   * @return singleton list with config panel
   * @since 1.0.0
   */
  @Override
  public List<? extends IConfigTab> getConfigPanels() {
    return Collections.singletonList(EwonConfigPage.CONFIG_TAB);
  }

  /**
   * Returns the Ewon Connector gateway context.
   *
   * @return the Ewon Connector gateway context
   * @since 2.0.0
   */
  public GatewayContext getGatewayContext() {
    return gatewayContext;
  }
}

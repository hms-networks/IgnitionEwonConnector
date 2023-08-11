package com.hms_networks.americas.sc.ignition.data;

import com.hms_networks.americas.sc.ignition.comm.responses.dmw.DMWebEwon;
import com.hms_networks.americas.sc.ignition.comm.responses.dmw.DMWebEwonTag;
import com.hms_networks.americas.sc.ignition.comm.responses.dmw.DMWebEwonTagHistoryEntry;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwon;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonEBDInstantValue;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonEBDTag;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import com.hms_networks.americas.sc.ignition.data.tagwrite.BufferedTagWriteManager;
import com.inductiveautomation.ignition.common.config.*;
import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.model.values.QualityCode;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;
import com.inductiveautomation.ignition.common.tags.config.CollisionPolicy;
import com.inductiveautomation.ignition.common.tags.config.TagConfigurationModel;
import com.inductiveautomation.ignition.common.tags.config.properties.WellKnownTagProps;
import com.inductiveautomation.ignition.common.tags.config.types.TagObjectType;
import com.inductiveautomation.ignition.common.tags.model.TagPath;
import com.inductiveautomation.ignition.common.tags.model.TagProvider;
import com.inductiveautomation.ignition.common.tags.paths.parser.TagPathParser;
import com.inductiveautomation.ignition.gateway.history.HistoricalTagValue;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;
import com.inductiveautomation.ignition.gateway.sqltags.model.BasicScanclassHistorySet;
import com.inductiveautomation.ignition.gateway.tags.managed.DeletionHandler;
import com.inductiveautomation.ignition.gateway.tags.managed.ManagedTagProvider;
import com.inductiveautomation.ignition.gateway.tags.managed.ProviderConfiguration;
import com.inductiveautomation.metro.utils.StringUtils;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for managing Ewon Connector tags on Ignition using {@link ManagedTagProvider}.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class TagManager {

  /**
   * Log handler for {@link TagManager}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TagManager.class);

  /**
   * Ewon Connector Tag Provider Name
   *
   * @since 1.0.0
   */
  private static final String GATEWAY_TAG_PROVIDER_NAME = "Ewon";

  /**
   * List of Ewon Flexy devices that should be polled via M2Web. This list is populated with Ewon
   * Flexy devices which have the 'forceRealtimeData' option set to 'true'.
   *
   * @since 1.0.0
   */
  private static final List<M2WebEwon> FORCED_M2WEB_EWONS = new ArrayList<>();

  /**
   * Map of Ewon Flexy names and their tags that have been initialized. This map is populated with
   * Ewon Flexy tags which have been initialized (write handler registration, realtime property
   * creation, etc.) by the connector.
   *
   * @since 1.0.0
   */
  private static final Map<String, List<String>> INITIALIZED_EWON_TAGS = new HashMap<>();

  /**
   * The name of the property used to override/force realtime data for an Ewon Flexy device or tag
   * via M2Web.
   *
   * @since 1.0.0
   */
  private static final String REALTIME_OVERRIDE_PROPERTY_NAME = "ForceRealtimeData";

  /**
   * The tag name used to override/force realtime data for an Ewon Flexy device via M2Web.
   *
   * @since 1.0.0
   */
  private static final String REALTIME_OVERRIDE_TAG_NAME =
      "_PROPERTIES/" + REALTIME_OVERRIDE_PROPERTY_NAME;

  /**
   * The data type for the tag used to override/force realtime data for an Ewon Flexy device via
   * M2Web.
   *
   * @since 1.0.0
   */
  private static final DataType REALTIME_OVERRIDE_TAG_DATA_TYPE = DataType.Boolean;

  /**
   * The tag property used to override/force realtime data for an Ewon Flexy device via M2Web.
   *
   * @since 1.0.0
   */
  private static final BasicProperty<Boolean> REALTIME_OVERRIDE_TAG_PROPERTY =
      new BasicProperty<>(REALTIME_OVERRIDE_PROPERTY_NAME, Boolean.class);

  /**
   * The name of the property used to store the raw Ewon tag name, for an Ewon Flexy tag via M2Web,
   * inside an Ignition tag.
   *
   * @since 1.0.0
   */
  private static final String RAW_EWON_TAG_NAME_PROPERTY_NAME = "RawEwonTagName";

  /**
   * The tag property used to store the raw Ewon tag name, for an Ewon Flexy tag via M2Web, inside
   * an Ignition tag.
   *
   * @since 1.0.0
   */
  private static final BasicProperty<String> RAW_EWON_TAG_NAME_PROPERTY =
      new BasicProperty<>(RAW_EWON_TAG_NAME_PROPERTY_NAME, String.class);

  /**
   * The Ewon connector gateway context.
   *
   * @since 1.0.0
   */
  private static GatewayContext gatewayContext;

  /**
   * The Ewon connector settings.
   *
   * @since 1.0.0
   */
  private static EwonConnectorSettings connectorSettings;

  /**
   * Tag provider for configured tags
   *
   * @since 1.0.0
   */
  private static ManagedTagProvider tagProvider;

  /**
   * Initializes the tag manager using the provided {@link GatewayContext} and {@link
   * EwonConnectorSettings}.
   *
   * @param gatewayContext the gateway context
   * @param connectorSettings the Ewon connector settings.
   * @since 1.0.0
   */
  public static void initialize(
      GatewayContext gatewayContext, EwonConnectorSettings connectorSettings) {
    // Store gateway context and connector settings
    TagManager.gatewayContext = gatewayContext;
    TagManager.connectorSettings = connectorSettings;

    // Create realtime tag provider
    tagProvider =
        gatewayContext
            .getTagManager()
            .getOrCreateManagedProvider(
                new ProviderConfiguration(GATEWAY_TAG_PROVIDER_NAME)
                    .setAllowTagCustomization(true)
                    .setPersistTags(true)
                    .setPersistValues(true)
                    .setAllowTagDeletion(true));

    // Initialize status tags
    StatusTagManager.initializeStatusTags(tagProvider);

    // Configure tag provider deletion handler to cleanup tags on deletion
    tagProvider.setDeletionHandler(
        tagPath -> {
          // Get Ewon Flexy name from tag path
          final int tagPathComponentIndexEwonName = 0;
          String ewonName = tagPath.getPathComponent(tagPathComponentIndexEwonName);

          // Get Ewon Flexy tag name from tag path
          final int tagPathComponentIndexTagName = 1;
          String tagName = tagPath.getPathComponent(tagPathComponentIndexTagName);

          // Remove from initialized tags
          if (INITIALIZED_EWON_TAGS.containsKey(ewonName)) {
            INITIALIZED_EWON_TAGS.get(ewonName).remove(tagName);
            if (INITIALIZED_EWON_TAGS.get(ewonName).isEmpty()) {
              INITIALIZED_EWON_TAGS.remove(ewonName);
              FORCED_M2WEB_EWONS.stream()
                  .filter(ewon -> ewon.getName().equals(ewonName))
                  .findFirst()
                  .ifPresent(FORCED_M2WEB_EWONS::remove);
            }
          }

          // Log tag deletion
          if (connectorSettings.isDebugEnabled()) {
            LOGGER.debug(
                "Tag '{}' on Ewon Flexy '{}' has been deleted from Ignition. "
                    + "Note: The tag on the Ewon Flexy device is unchanged.",
                tagName,
                ewonName);
          }

          return DeletionHandler.DeletionResponse.Allowed;
        });

    // Verify integrity of tag history storage information if tag history enabled
    if (connectorSettings.isHistoryEnabled()
        && StringUtils.isBlank(connectorSettings.getHistoryProvider())) {
      LOGGER.warn(
          "History synchronization is enabled, but no history provider "
              + "has been specified for storage. No data will be stored");
    }

    // Output warning if tag name check is disabled
    if (connectorSettings.isTagNameCheckDisabled()) {
      LOGGER.warn(
          "Strict checking for allowed tag name characters is "
              + "disabled. Tags with unsupported characters may not work properly");
    } else {
      LOGGER.warn(
          "Strict checking for allowed tag name characters is enabled. Tags with unsupported"
              + " characters will be sanitized and illegal characters replaced with '{}'",
          TagManagerUtilities.ILLEGAL_TAG_NAME_CHARACTER_REPLACEMENT);
    }
  }

  /**
   * Shuts down the tag manager.
   *
   * @since 1.0.0
   */
  public static void shutdown() {
    if (tagProvider != null) {
      tagProvider.shutdown(false);
      tagProvider = null;
    }

    // Clear all lists and maps
    FORCED_M2WEB_EWONS.clear();
    INITIALIZED_EWON_TAGS.clear();
  }

  /**
   * Gets the list of Ewon Flexy devices that should be polled via M2Web.
   *
   * @implNote This list is populated with Ewon Flexy devices which have the 'forceRealtimeData'
   *     option set to 'true'. If the {@link EwonConnectorSettings#isForceLive()} option is set to
   *     'true', this method will return the same result, as this option should be handled by the
   *     calling method.
   * @return the list of Ewon Flexy devices that should be polled via M2Web
   * @since 1.0.0
   */
  public static List<M2WebEwon> getForcedM2WebEwons() {
    synchronized (FORCED_M2WEB_EWONS) {
      return FORCED_M2WEB_EWONS;
    }
  }

  /**
   * Checks if the specified Ewon Flexy device is configured to be polled via M2Web.
   *
   * @param ewonName Ewon Flexy name to check
   * @return {@code true} if the Ewon Flexy device is configured to be polled via M2Web, {@code
   *     false} otherwise
   * @since 1.0.0
   */
  public static boolean isEwonForcedM2Web(String ewonName) {
    synchronized (FORCED_M2WEB_EWONS) {
      return FORCED_M2WEB_EWONS.stream().anyMatch(ewon -> ewon.getName().equals(ewonName));
    }
  }

  /**
   * Checks if the specified Ewon Flexy tag is configured to be polled via M2Web.
   *
   * @param dmWebEwon the DMWeb Ewon Flexy to check
   * @param dmWebEwonTag the DMWeb Ewon Flexy tag to check
   * @return {@code true} if the Ewon Flexy tag is configured to be polled via M2Web, {@code false}
   *     otherwise
   * @since 1.0.0
   */
  public static boolean isEwonTagForcedM2Web(DMWebEwon dmWebEwon, DMWebEwonTag dmWebEwonTag) {
    final String providerTagNameForDMWebEwonTag =
        TagManagerUtilities.getTagNameForProviderFromDMWebEwon(
            connectorSettings, dmWebEwon, dmWebEwonTag);
    return isEwonTagForcedM2Web(providerTagNameForDMWebEwonTag);
  }

  /**
   * Checks if the specified Ewon Flexy tag is configured to be polled via M2Web.
   *
   * @param m2WebEwon the M2Web Ewon Flexy to check
   * @param m2WebEwonEBDTag the M2Web Ewon Flexy tag to check
   * @return {@code true} if the Ewon Flexy tag is configured to be polled via M2Web, {@code false}
   *     otherwise
   * @since 1.0.0
   */
  public static boolean isEwonTagForcedM2Web(M2WebEwon m2WebEwon, M2WebEwonEBDTag m2WebEwonEBDTag) {
    final String providerTagNameForM2WebEwonEBDTag =
        TagManagerUtilities.getTagNameForProviderFromM2WebEwon(
            connectorSettings, m2WebEwon, m2WebEwonEBDTag);
    return isEwonTagForcedM2Web(providerTagNameForM2WebEwonEBDTag);
  }

  /**
   * Checks if the Ewon Flexy tag with the specified provider tag name is configured to be polled
   * via M2Web.
   *
   * @param providerTagName the name of Ewon Flexy tag in the tag provider to check
   * @return {@code true} if the Ewon Flexy tag is configured to be polled via M2Web, {@code false}
   *     otherwise
   * @since 1.0.0
   */
  private static boolean isEwonTagForcedM2Web(String providerTagName) {
    // Create boolean to store result
    boolean isForcedM2Web = false;

    // Get tag provider from gateway context
    TagProvider gwTagProvider =
        gatewayContext.getTagManager().getTagProvider(GATEWAY_TAG_PROVIDER_NAME);

    // Check if tag provider exists
    if (gwTagProvider != null) {
      try {
        // Get path to tag and read config
        final int gwTagProviderTimeoutSecs = 5;
        final int tagConfigSingletonIndex = 0;
        final boolean getTagConfigRecursive = false;
        final boolean getTagConfigIncludeProperties = true;
        TagPath tagPath = TagPathParser.parse(GATEWAY_TAG_PROVIDER_NAME, providerTagName);
        List<TagConfigurationModel> tagConfig =
            gwTagProvider
                .getTagConfigsAsync(
                    Collections.singletonList(tagPath),
                    getTagConfigRecursive,
                    getTagConfigIncludeProperties)
                .get(gwTagProviderTimeoutSecs, TimeUnit.SECONDS);
        TagConfigurationModel tagConfigModel = tagConfig.get(tagConfigSingletonIndex);

        // Get current realtime property value
        Boolean rawRealtimePropertyValue = tagConfigModel.get(REALTIME_OVERRIDE_TAG_PROPERTY);
        isForcedM2Web = Boolean.TRUE.equals(rawRealtimePropertyValue);
      } catch (Exception e) {
        LOGGER.error(
            "Could not read realtime property for tag '"
                + providerTagName
                + "'! Defaulting to "
                + isForcedM2Web
                + ".",
            e);
      }
    } else {
      LOGGER.error(
          "Could not read realtime property for tag '{}' because the tag provider '{}' could not be"
              + " found! Defaulting to {}.",
          providerTagName,
          GATEWAY_TAG_PROVIDER_NAME,
          isForcedM2Web);
    }

    return isForcedM2Web;
  }

  /**
   * Updates the sync data status tags with information on the current sync data state from {@link
   * SyncDataStateManager}.
   *
   * @since 1.0.0
   */
  public static void updateSyncDataStatusTags() {
    StatusTagManager.updateStatusTags(tagProvider);
  }

  /**
   * Configures the realtime override tag for the specified Ewon Flexy device.
   *
   * @param m2WebEwon the {@link M2WebEwon} object for the Ewon to configure the realtime override
   *     property tag for
   * @since 1.0.0
   */
  public static void configureRealtimePropertyForEwon(M2WebEwon m2WebEwon) {
    // Get tag name
    boolean isInGroup = false;
    boolean isSystemTag = true;
    final String realtimeOverridePropertyTagName =
        TagManagerUtilities.getTagNameForProviderFromRawTagInfo(
            connectorSettings,
            m2WebEwon.getName(),
            REALTIME_OVERRIDE_TAG_NAME,
            isInGroup,
            isInGroup,
            isInGroup,
            isInGroup,
            isSystemTag);

    // Configure realtime tag data type
    tagProvider.configureTag(realtimeOverridePropertyTagName, REALTIME_OVERRIDE_TAG_DATA_TYPE);

    // Check if realtime override tag value exists, and set if not
    try {
      // Attempt to read existing value (if it exists)
      final int firstIndexValueRead = 0;
      TagPath realtimeOverrideTagPath = TagPathParser.parse(realtimeOverridePropertyTagName);
      QualifiedValue isEwonRealtimeOverrideEnabled =
          gatewayContext
              .getTagManager()
              .readAsync(Collections.singletonList(realtimeOverrideTagPath))
              .get()
              .get(firstIndexValueRead);

      // Add to list of forced realtime Ewons if enabled
      if (Boolean.TRUE.equals(isEwonRealtimeOverrideEnabled.getValue())) {
        if (!FORCED_M2WEB_EWONS.contains(m2WebEwon)) {
          FORCED_M2WEB_EWONS.add(m2WebEwon);
        }
        if (connectorSettings.isDebugEnabled()) {
          LOGGER.debug("Added Ewon to forced realtime Ewons list: {}", m2WebEwon.getName());
        }
      }
    } catch (Exception e) {
      LOGGER.error(
          "Error reading realtime override tag value for Ewon: {}", m2WebEwon.getName(), e);
      // Value does not exist, need to set (default: false)
      tagProvider.updateValue(realtimeOverridePropertyTagName, Boolean.FALSE, QualityCode.Good);
    }

    // Configure realtime tag write handler
    tagProvider.registerWriteHandler(
        realtimeOverridePropertyTagName,
        (tagPath, tagValueRaw) -> {
          synchronized (FORCED_M2WEB_EWONS) {
            if (tagValueRaw instanceof Boolean) {
              boolean tagValue = (Boolean) tagValueRaw;
              if (tagValue) {
                if (!FORCED_M2WEB_EWONS.contains(m2WebEwon)) {
                  FORCED_M2WEB_EWONS.add(m2WebEwon);
                  if (connectorSettings.isDebugEnabled()) {
                    LOGGER.debug(
                        "Added Ewon to forced realtime Ewons list: {}", m2WebEwon.getName());
                  }
                }
              } else {
                FORCED_M2WEB_EWONS.remove(m2WebEwon);
                if (connectorSettings.isDebugEnabled()) {
                  LOGGER.debug(
                      "Removed Ewon from forced realtime Ewons list: {}", m2WebEwon.getName());
                }
              }
              tagProvider.updateValue(realtimeOverridePropertyTagName, tagValue, QualityCode.Good);
            } else {
              LOGGER.warn(
                  "Received invalid value for realtime override tag for Ewon: {}. "
                      + "Expected boolean, received: {}. Resetting tag data type and value!",
                  m2WebEwon.getName(),
                  tagValueRaw);
              tagProvider.configureTag(
                  realtimeOverridePropertyTagName, REALTIME_OVERRIDE_TAG_DATA_TYPE);
              tagProvider.updateValue(
                  realtimeOverridePropertyTagName, Boolean.FALSE, QualityCode.Good);
            }
          }
          return QualityCode.Good;
        });
  }

  /**
   * Configures the realtime override tag property for the specified tag on the specified Ewon Flexy
   * device.
   *
   * @param m2WebEwon the {@link M2WebEwon} object for the Ewon to configure the realtime override
   *     property tag for
   * @param m2WebEwonEBDTag the {@link M2WebEwonEBDTag} object for the tag to configure the realtime
   *     override property tag for
   * @since 1.0.0
   */
  public static void configureRealtimePropertyForTag(
      M2WebEwon m2WebEwon, M2WebEwonEBDTag m2WebEwonEBDTag) {
    // Get tag name
    final String tagName =
        TagManagerUtilities.getTagNameForProviderFromM2WebEwon(
            connectorSettings, m2WebEwon, m2WebEwonEBDTag);

    // Get tag provider from gateway context
    TagProvider gwTagProvider =
        gatewayContext.getTagManager().getTagProvider(GATEWAY_TAG_PROVIDER_NAME);

    // Check if tag provider exists
    if (gwTagProvider != null) {
      try {
        // Get path to tag and read config
        final int gwTagProviderTimeoutSecs = 5;
        final int tagConfigSingletonIndex = 0;
        final boolean getTagConfigRecursive = false;
        final boolean getTagConfigIncludeProperties = true;
        TagPath tagPath = TagPathParser.parse(GATEWAY_TAG_PROVIDER_NAME, tagName);
        List<TagConfigurationModel> tagConfig =
            gwTagProvider
                .getTagConfigsAsync(
                    Collections.singletonList(tagPath),
                    getTagConfigRecursive,
                    getTagConfigIncludeProperties)
                .get(gwTagProviderTimeoutSecs, TimeUnit.SECONDS);
        TagConfigurationModel tagConfigModel = tagConfig.get(tagConfigSingletonIndex);

        // Get current realtime property value
        boolean isForcedRealtimeExists = tagConfigModel.contains(REALTIME_OVERRIDE_TAG_PROPERTY);
        Boolean isForcedRealtime =
            Boolean.TRUE.equals(tagConfigModel.get(REALTIME_OVERRIDE_TAG_PROPERTY));

        // Create realtime override property if it does not exist
        if (!isForcedRealtimeExists) {
          // Configure realtime override property (default to false)
          PropertyValue realtimeOverridePropertyValue =
              new PropertyValue(REALTIME_OVERRIDE_TAG_PROPERTY, isForcedRealtime);
          tagConfigModel.set(realtimeOverridePropertyValue);

          // Save tag config changes
          gwTagProvider
              .saveTagConfigsAsync(
                  Collections.singletonList(tagConfigModel), CollisionPolicy.MergeOverwrite)
              .get(gwTagProviderTimeoutSecs, TimeUnit.SECONDS);

          // Log debug message
          if (connectorSettings.isDebugEnabled()) {
            LOGGER.debug("Configured realtime property for tag path {}", tagName);
          }
        }
      } catch (Exception e) {
        LOGGER.error("Could not configure realtime property for tag: " + tagName + "!", e);
      }
    } else {
      LOGGER.error(
          "Could not find tag provider with name: {}! Cannot configure realtime property for tag:"
              + " {}",
          GATEWAY_TAG_PROVIDER_NAME,
          tagName);
    }
  }

  /**
   * Configures the write handler for the specified tag on the specified Ewon Flexy device.
   *
   * @param m2WebEwon the {@link M2WebEwon} object for the Ewon to configure the write handler for
   * @param m2WebEwonEBDTag the {@link M2WebEwonEBDTag} object for the tag to configure the write
   *     handler for
   * @since 1.0.0
   */
  public static void configureWriteHandlerForTag(
      M2WebEwon m2WebEwon, M2WebEwonEBDTag m2WebEwonEBDTag) {
    // Get tag name
    final String tagName =
        TagManagerUtilities.getTagNameForProviderFromM2WebEwon(
            connectorSettings, m2WebEwon, m2WebEwonEBDTag);

    // Register tag write handler
    tagProvider.registerWriteHandler(
        tagName,
        (writeTagPath, o) -> {
          QualityCode result;
          try {
            BufferedTagWriteManager.writeTag(
                tagProvider, connectorSettings, m2WebEwon, m2WebEwonEBDTag, writeTagPath, o);
            result = QualityCode.Good;
          } catch (Exception e) {
            LOGGER.error(
                "Could not write tag ["
                    + tagName
                    + "]("
                    + m2WebEwonEBDTag.getName()
                    + ") on Ewon ["
                    + m2WebEwon.getName()
                    + "] due to an exception!",
                e);
            result = QualityCode.Bad;
          }
          return result;
        });
  }

  /**
   * Applies the specified {@link M2WebEwon} and {@link M2WebEwonEBDTag} configurations to the
   * {@link TagProvider}.
   *
   * @param m2WebEwon the {@link M2WebEwon} object for the Ewon to apply the tag configurations to
   *     the {@link TagProvider} for
   * @param m2WebEwonEBDTags the {@link M2WebEwonEBDTag} objects for the tags to apply the
   *     configurations to the {@link TagProvider} for
   * @since 1.0.0
   */
  public static void applyM2WebEwonTagConfigurations(
      M2WebEwon m2WebEwon, Collection<M2WebEwonEBDTag> m2WebEwonEBDTags) {
    // Check if Ewon is already initialized in tag provider
    boolean isEwonInitializedInTagProvider = INITIALIZED_EWON_TAGS.containsKey(m2WebEwon.getName());

    // Initialize Ewon in tag provider if not already initialized
    if (!isEwonInitializedInTagProvider) {
      // Ensure realtime override exists for Ewon
      configureRealtimePropertyForEwon(m2WebEwon);

      // Add Ewon to initialized Ewon tags list
      INITIALIZED_EWON_TAGS.put(m2WebEwon.getName(), new ArrayList<>());
    }

    for (M2WebEwonEBDTag m2WebEwonEBDTag : m2WebEwonEBDTags) {
      // Get tag name
      final String tagName =
          TagManagerUtilities.getTagNameForProviderFromM2WebEwon(
              connectorSettings, m2WebEwon, m2WebEwonEBDTag);

      // Check if tag is already initialized in tag provider
      boolean isEwonTagInitializedInTagProvider =
          INITIALIZED_EWON_TAGS.get(m2WebEwon.getName()).contains(tagName);

      // Get tag type
      final EwonTagType tagType = EwonTagType.getTagTypeFromInt(m2WebEwonEBDTag.getType());

      // Configure tag
      if (tagType != null && tagType.getIgnitionDataType() != null) {
        if (connectorSettings.isDebugEnabled()) {
          LOGGER.debug(
              "Configuring tag '{}' with type '{}/{}' for Ewon '{}'...",
              tagName,
              tagType,
              tagType.getIgnitionDataType(),
              m2WebEwon.getName());
        }
        tagProvider.configureTag(tagName, tagType.getIgnitionDataType());

        // Update tag metadata
        updateTagMetadata(m2WebEwon, m2WebEwonEBDTag);

        // Initialize tag in tag provider if not already initialized
        if (!isEwonTagInitializedInTagProvider) {
          // Ensure realtime property exists for tag
          configureRealtimePropertyForTag(m2WebEwon, m2WebEwonEBDTag);

          // Configure write handler
          configureWriteHandlerForTag(m2WebEwon, m2WebEwonEBDTag);

          // Add tag to initialized Ewon tags list
          INITIALIZED_EWON_TAGS.get(m2WebEwon.getName()).add(tagName);

          // Log debug message
          if (connectorSettings.isDebugEnabled()) {
            LOGGER.debug(
                "Initialized tag '{}' ({},{}) for Ewon '{}' in tag provider.",
                tagName,
                tagType,
                tagType.getIgnitionDataType().toString(),
                m2WebEwon.getName());
          }
        }
      } else {
        LOGGER.warn(
            "Failed to configure tag '{}' due to an unknown tag type '{}'!",
            tagName,
            m2WebEwonEBDTag.getType());
      }
    }
  }

  /**
   * Updates the metadata for the specified {@link M2WebEwonEBDTag} on the specified {@link
   * M2WebEwon}.
   *
   * @param m2WebEwon the {@link M2WebEwon} object for the Ewon to update the tag metadata for
   * @param m2WebEwonEBDTag the {@link M2WebEwonEBDTag} object for the tag to update the metadata
   *     for
   * @since 1.0.0
   */
  public static void updateTagMetadata(M2WebEwon m2WebEwon, M2WebEwonEBDTag m2WebEwonEBDTag) {
    // Update metadata such as tag descriptions
    try {
      // Get tag provider from gateway context
      final TagProvider tagProvider =
          gatewayContext.getTagManager().getTagProvider(GATEWAY_TAG_PROVIDER_NAME);

      // Get path to tag and read config
      final int gwTagProviderTimeoutSeconds = 5;
      final int tagConfigSingletonIndex = 0;
      final String tagName =
          TagManagerUtilities.getTagNameForProviderFromM2WebEwon(
              connectorSettings, m2WebEwon, m2WebEwonEBDTag);
      final TagPath tagPath = TagPathParser.parse(tagName);

      // Log debug message
      if (connectorSettings.isDebugEnabled()) {
        LOGGER.debug(
            "Updating metadata for tag '{}' from Ewon '{}'...", tagName, m2WebEwon.getName());
      }

      // Read config and update metadata if successful
      if (tagProvider != null) {
        TagConfigurationModel tagConfigurationModel =
            tagProvider
                .getTagConfigsAsync(List.of(tagPath), false, false)
                .get(gwTagProviderTimeoutSeconds, TimeUnit.SECONDS)
                .get(tagConfigSingletonIndex);
        // Check integrity of read config
        if (tagConfigurationModel != null
            && tagConfigurationModel.getType() != TagObjectType.Unknown) {
          // Set documentation and tooltip to tag description
          tagConfigurationModel.set(
              WellKnownTagProps.Documentation, m2WebEwonEBDTag.getDescription());
          tagConfigurationModel.set(WellKnownTagProps.Tooltip, m2WebEwonEBDTag.getDescription());

          // Set raw tag name
          tagConfigurationModel.set(RAW_EWON_TAG_NAME_PROPERTY, m2WebEwonEBDTag.getName());

          // Save tag config changes
          tagProvider
              .saveTagConfigsAsync(
                  Collections.singletonList(tagConfigurationModel), CollisionPolicy.MergeOverwrite)
              .get(gwTagProviderTimeoutSeconds, TimeUnit.SECONDS);
        } else if (tagConfigurationModel != null
            && tagConfigurationModel.getType() == TagObjectType.Unknown) {
          LOGGER.error(
              "Tag configuration for tag '{}' on Ewon '{}' from tag provider '{}' is an unknown"
                  + " type!",
              tagPath,
              m2WebEwon.getName(),
              GATEWAY_TAG_PROVIDER_NAME);
        } else {
          LOGGER.error(
              "Failed to get tag configuration for tag '{}' on Ewon '{}' from tag provider '{}'!",
              tagPath,
              m2WebEwon.getName(),
              GATEWAY_TAG_PROVIDER_NAME);
        }
      } else {
        LOGGER.error(
            "Failed to get tag provider '{}' from gateway context!", GATEWAY_TAG_PROVIDER_NAME);
      }

    } catch (Exception e) {
      LOGGER.error("Failed to update tag metadata due to an exception!", e);
    }
  }

  /**
   * Updates the historical value(s) for the specified {@link M2WebEwonEBDTag} on the specified
   * {@link M2WebEwon}.
   *
   * @param ewon the {@link M2WebEwon} object for the Ewon to update the tag historical value(s) for
   * @param ewonTag the {@link M2WebEwonEBDTag} object for the tag to update the historical value(s)
   *     for
   * @since 1.0.0
   */
  public static void updateDMWebEwonTag(DMWebEwon ewon, DMWebEwonTag ewonTag) {
    // Get tag name
    final String tagName =
        TagManagerUtilities.getTagNameForProviderFromDMWebEwon(connectorSettings, ewon, ewonTag);

    // Get tag type
    final EwonTagType tagType = EwonTagType.getTagTypeFromString(ewonTag.getDataType());

    // Set tag historical value(s)
    if (tagType != null && tagType.getIgnitionDataType() != null) {

      // Log debug message
      if (connectorSettings.isDebugEnabled()) {
        LOGGER.debug(
            "Updating historical tag value(s) for tag '{}' with type '{}/{}' for Ewon '{}'...",
            tagName,
            tagType,
            tagType.getIgnitionDataType(),
            ewon.getName());
      }

      // Set tag historical values with timestamp
      final String scanClassName = "_exempt_";
      final int scanClassExecutionRateSeconds = -1;
      BasicScanclassHistorySet historySet =
          new BasicScanclassHistorySet(
              GATEWAY_TAG_PROVIDER_NAME, scanClassName, scanClassExecutionRateSeconds);
      for (DMWebEwonTagHistoryEntry historyEntry : ewonTag.getHistory()) {
        final Object historyTagValue =
            TagManagerUtilities.getTagValueForType(historyEntry.getValue(), tagType);
        final EwonTagQuality ewonTagQuality = new EwonTagQuality(historyEntry.getQuality());
        final Date historyTagTimestamp = historyEntry.getDate();
        tagProvider.updateValue(
            tagName, historyTagValue, ewonTagQuality.getQualityCode(), historyTagTimestamp);

        // Set tag historical value(s) in history database, if enabled
        if (connectorSettings.isHistoryEnabled()
            && !StringUtils.isBlank(connectorSettings.getHistoryProvider())) {
          HistoricalTagValue historicalTagValue =
              TagManagerUtilities.buildHistoricalTagValue(
                  GATEWAY_TAG_PROVIDER_NAME,
                  tagName,
                  tagType.getIgnitionDataType(),
                  ewonTagQuality.getDataQuality(),
                  historyTagTimestamp,
                  historyTagValue);
          historySet.add(historicalTagValue);
        }
      }

      // Set tag current historical value
      final Object historyTagCurrentValue =
          TagManagerUtilities.getTagValueForType(ewonTag.getValue(), tagType);
      final EwonTagQuality historyTagCurrentQuality = new EwonTagQuality(ewonTag.getQuality());
      tagProvider.updateValue(
          tagName, historyTagCurrentValue, historyTagCurrentQuality.getQualityCode());

      // If history set contains historical values, save them to the history database
      if (historySet.size() > 0) {
        try {
          // Sort historical values by timestamp
          historySet.sort(Comparator.comparing(HistoricalTagValue::getTimestamp));
          Date latestTimestamp = historySet.get(historySet.size() - 1).getTimestamp();
          SyncDataStateManager.setLatestDMWebDataPointTimeStamp(latestTimestamp);
          gatewayContext
              .getHistoryManager()
              .storeHistory(connectorSettings.getHistoryProvider(), historySet);

        } catch (Exception e) {
          LOGGER.error(
              "Failed to save historical tag values to provider for tag '"
                  + tagName
                  + "' on Ewon '"
                  + ewon.getName()
                  + "'!",
              e);
        }
      }
    } else {
      LOGGER.error(
          "Failed to update historical tag value for tag '{}' on Ewon '{}' because the tag type"
              + " ({}/{}/{}) is unknown!",
          ewonTag.getName(),
          ewon.getName(),
          tagType,
          tagType != null ? tagType.getIgnitionDataType() : null,
          ewonTag.getDataType());
    }
  }

  /**
   * Updates the instant value using the specified {@link M2WebEwonEBDInstantValue} object on the
   * specified {@link M2WebEwon}.
   *
   * @param ewon the {@link M2WebEwon} object for the Ewon to update the tag instant value for
   * @param instantValue the {@link M2WebEwonEBDInstantValue} object for the tag to update the
   *     instant value for
   * @since 1.0.0
   */
  public static void updateM2WebEwonTagInstantValue(
      M2WebEwon ewon, M2WebEwonEBDInstantValue instantValue) {
    // Get M2Web tag object and tag type
    Map<String, M2WebEwonEBDTag> cachedM2WebEwonTagSet =
        CacheManager.getCachedM2WebEwonTagMap(ewon.getName());
    M2WebEwonEBDTag m2WebEwonEBDTag = cachedM2WebEwonTagSet.get(instantValue.getTagName());
    EwonTagType tagType = EwonTagType.getTagTypeFromInt(m2WebEwonEBDTag.getType());

    // Update tag value
    if (tagType != null) {
      final String tagName =
          TagManagerUtilities.getTagNameForProviderFromM2WebEwonName(
              connectorSettings, ewon.getName(), m2WebEwonEBDTag);
      final EwonTagQuality tagQuality = new EwonTagQuality(instantValue.getQuality());
      final Object tagValue =
          TagManagerUtilities.getTagValueForType(instantValue.getValue(), tagType);
      tagProvider.updateValue(tagName, tagValue, tagQuality.getQualityCode());
    } else {
      LOGGER.error(
          "Failed to update instant tag value for tag '{}' on Ewon '{}' because the tag type is"
              + " unknown!",
          instantValue.getTagName(),
          ewon.getName());
    }
  }
}

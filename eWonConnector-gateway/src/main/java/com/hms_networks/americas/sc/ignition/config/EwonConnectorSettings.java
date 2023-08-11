package com.hms_networks.americas.sc.ignition.config;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.inductiveautomation.ignition.gateway.localdb.persistence.*;
import com.inductiveautomation.ignition.gateway.web.components.editors.PasswordEditorSource;

/**
 * The main settings for the Ewon connector, containing auth data, and settings that dictate update
 * rates, etc.
 *
 * @since 1.0.0
 * @version 2.0.0
 * @author HMS Networks, MU Americas Solution Center
 * @author Inductive Automation/Travis Cox
 */
public class EwonConnectorSettings extends PersistentRecord {

  /**
   * Constant value for the tag write buffer length when disabled.
   *
   * @since 2.0.0
   */
  public static final Long TAG_WRITE_BUFFER_LENGTH_MS_DISABLED = -1L;

  /**
   * Default value for the {@link #ENABLED} setting.
   *
   * @since 2.0.0
   */
  public static final boolean DEFAULT_ENABLED = true;

  /**
   * Default value for the {@link #POLL_RATE} setting (minutes).
   *
   * @since 2.0.0
   */
  public static final int DEFAULT_POLL_RATE = 1;

  /**
   * Default value for the {@link #LIVE_POLL_RATE} setting (seconds).
   *
   * @since 2.0.0
   */
  public static final int DEFAULT_LIVE_POLL_RATE = 10;

  /**
   * Default value for the {@link #METADATA_POLL_RATE} setting (minutes).
   *
   * @since 2.0.0
   */
  public static final int DEFAULT_METADATA_POLL_RATE = 30;

  /**
   * Record meta information for the Ewon Connector.
   *
   * @since 1.0.0
   */
  public static final RecordMeta<EwonConnectorSettings> META =
      new RecordMeta<>(EwonConnectorSettings.class, "ewonConnectorSettings");

  /**
   * Ewon Connector Settings identifier.
   *
   * @since 1.0.0
   */
  public static final IdentityField ID = new IdentityField(META);

  /**
   * Configured boolean if Ewon Connector enabled.
   *
   * @since 1.0.0
   */
  public static final BooleanField ENABLED =
      new BooleanField(META, "enabled").setDefault(DEFAULT_ENABLED);

  /**
   * Configured boolean if Ewon Connector debug mode is enabled.
   *
   * @since 1.0.0
   */
  public static final BooleanField DEBUG_ENABLED = new BooleanField(META, "debugEnabled");

  /**
   * Configured Talk2M account name.
   *
   * @since 1.0.0
   */
  public static final StringField ACCOUNT = new StringField(META, "account");

  /**
   * Configured Talk2M account username.
   *
   * @since 1.0.0
   */
  public static final StringField USERNAME = new StringField(META, "username");

  /**
   * Configured Talk2M account password.
   *
   * @since 1.0.0
   */
  public static final EncodedStringField PASSWORD = new EncodedStringField(META, "password");

  /**
   * Configured Talk2M account token.
   *
   * @since 1.2.0
   */
  public static final EncodedStringField TOKEN = new EncodedStringField(META, "token");

  /**
   * Configured Ewon account username.
   *
   * @since 1.0.0
   */
  public static final StringField EWON_USERNAME = new StringField(META, "ewonUsername");

  /**
   * Configured Ewon account password.
   *
   * @since 1.0.0
   */
  public static final EncodedStringField EWON_PASSWORD =
      new EncodedStringField(META, "ewonPassword");

  /**
   * Configured Talk2M API key.
   *
   * @since 1.0.0
   */
  public static final StringField API_KEY = new StringField(META, "apikey");

  /**
   * Configured tag poll rate.
   *
   * @since 1.0.0
   */
  public static final IntField POLL_RATE =
      new IntField(META, "pollrate").setDefault(DEFAULT_POLL_RATE);

  /**
   * Configured realtime tag poll rate.
   *
   * @since 1.0.0
   */
  public static final IntField LIVE_POLL_RATE =
      new IntField(META, "livepollrate").setDefault(DEFAULT_LIVE_POLL_RATE);

  /**
   * Configured tag metadata poll rate.
   *
   * @since 1.0.0
   */
  public static final IntField METADATA_POLL_RATE =
      new IntField(META, "metadataPollRate").setDefault(DEFAULT_METADATA_POLL_RATE);

  /**
   * Configured property for combining live data with historical data.
   *
   * @since 1.0.0
   */
  public static final BooleanField COMBINE_LIVE_DATA = new BooleanField(META, "combineLiveData");

  /**
   * Configured override for enabling realtime on all tags.
   *
   * @since 1.0.0
   */
  public static final BooleanField FORCE_LIVE = new BooleanField(META, "forceLive");

  /**
   * Configured tag write buffer length (in milliseconds).
   *
   * @since 1.0.0
   */
  public static final LongField TAG_WRITE_BUFFER_LENGTH_MS =
      new LongField(META, "tagWriteBufferLengthMs");

  /**
   * Configured override for enabling tag sorting by group.
   *
   * @since 2.0.0
   */
  public static final BooleanField SORT_TAGS_BY_GROUP = new BooleanField(META, "sortTagsByGroup");

  /**
   * Configured boolean if tag history is enabled.
   *
   * @since 1.0.0
   */
  public static final BooleanField HISTORY_ENABLED = new BooleanField(META, "historyEnabled");

  /**
   * Configured tag history provider (if applicable).
   *
   * @since 1.0.0
   */
  public static final StringField HISTORY_PROVIDER = new StringField(META, "historyProvider");

  /**
   * Configured boolean if Ewon Connector enabled.
   *
   * @since 1.0.0
   */
  public static final BooleanField TAG_NAME_CHECK_DISABLED =
      new BooleanField(META, "tagNameCheckDisabled");

  /**
   * Settings category for general configuration options.
   *
   * @since 1.0.0
   */
  public static final Category GENERAL_CATEGORY =
      new Category("EwonConnectorSettings.Category.General", 10)
          .include(ENABLED, POLL_RATE, LIVE_POLL_RATE, METADATA_POLL_RATE);

  /**
   * Settings category for Talk2M account information.
   *
   * @since 1.0.0
   */
  public static final Category ACCOUNT_CATEGORY =
      new Category("EwonConnectorSettings.Category.Account", 50)
          .include(ACCOUNT, USERNAME, PASSWORD, API_KEY, TOKEN);

  /**
   * Settings category for Ewon device information.
   *
   * @since 1.0.0
   */
  public static final Category DEVICE_CATEGORY =
      new Category("EwonConnectorSettings.Category.Device", 75)
          .include(EWON_USERNAME, EWON_PASSWORD);

  /**
   * Settings category for historical tag provider configuration.
   *
   * @since 1.0.0
   */
  public static final Category HISTORY_CATEGORY =
      new Category("EwonConnectorSettings.Category.History", 100)
          .include(HISTORY_ENABLED, HISTORY_PROVIDER);

  /**
   * Settings category for advanced configuration.
   *
   * @since 1.0.0
   */
  public static final Category ADVANCED_CATEGORY =
      new Category("EwonConnectorSettings.Category.Advanced", 125)
          .include(
              FORCE_LIVE,
              COMBINE_LIVE_DATA,
              TAG_WRITE_BUFFER_LENGTH_MS,
              SORT_TAGS_BY_GROUP,
              TAG_NAME_CHECK_DISABLED,
              DEBUG_ENABLED);

  // Configure passwords and history provider fields information
  static {
    HISTORY_PROVIDER.getFormMeta().setEditorSource(TagHistoryListEditorSource.getSharedInstance());
    PASSWORD.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
    EWON_PASSWORD.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
  }

  /**
   * Gets the record meta information for the Ewon Connector.
   *
   * @return the record meta information for the Ewon Connector
   * @since 1.0.0
   */
  @Override
  public RecordMeta<?> getMeta() {
    return META;
  }

  /**
   * Gets the configured boolean indicating if the Ignition Ewon Connector is enabled.
   *
   * @return {@code true} if the Ignition Ewon Connector is enabled, {@code false} otherwise
   * @since 1.0.0
   */
  public boolean isEnabled() {
    return getBoolean(ENABLED);
  }

  /**
   * Gets the configured boolean indicating if the Ignition Ewon Connector debug mode is enabled.
   *
   * @return {@code true} if the Ignition Ewon Connector debug mode is enabled, {@code false}
   *     otherwise
   * @since 2.0.0
   */
  public boolean isDebugEnabled() {
    return getBoolean(DEBUG_ENABLED);
  }

  /**
   * Gets the configured Talk2M account name for the Ignition Ewon Connector.
   *
   * @return the configured Talk2M account name for the Ignition Ewon Connector
   * @since 1.0.0
   */
  public String getAccount() {
    return getString(ACCOUNT);
  }

  /**
   * Gets the configured Talk2M account username for the Ignition Ewon Connector.
   *
   * @return the configured Talk2M account username for the Ignition Ewon Connector
   * @since 1.0.0
   */
  public String getUserName() {
    return getString(USERNAME);
  }

  /**
   * Gets the configured Talk2M account password for the Ignition Ewon Connector.
   *
   * @return the configured Talk2M account password for the Ignition Ewon Connector
   * @since 1.0.0
   */
  public String getPassword() {
    return getString(PASSWORD);
  }

  /**
   * Gets the configured Talk2M account API token for the Ignition Ewon Connector.
   *
   * @return the configured Talk2M account API token for the Ignition Ewon Connector
   * @since 1.2.0
   */
  public String getToken() {
    return getString(TOKEN);
  }

  /**
   * Gets the configured Ewon account usernme for the Ignition Ewon Connector.
   *
   * @return the configured Ewon account username for the Ignition Ewon Connector
   * @since 1.0.0
   */
  public String getEwonUserName() {
    return getString(EWON_USERNAME);
  }

  /**
   * Gets the configured Ewon account password for the Ignition Ewon Connector.
   *
   * @return the configured Ewon account password for the Ignition Ewon Connector
   * @since 1.0.0
   */
  public String getEwonPassword() {
    return getString(EWON_PASSWORD);
  }

  /**
   * Gets the configured Talk2M API key for the Ignition Ewon Connector.
   *
   * @return the configured Talk2M API key for the Ignition Ewon Connector
   * @since 1.0.0
   */
  public String getApiKey() {
    return getString(API_KEY);
  }

  /**
   * Gets the configured DMWeb poll rate for the Ignition Ewon Connector.
   *
   * @return the configured DMWeb poll rate for the Ignition Ewon Connector
   * @since 1.0.0
   */
  public Integer getPollRate() {
    return getInt(POLL_RATE);
  }

  /**
   * Gets the configured M2Web poll rate for the Ignition Ewon Connector.
   *
   * @return the configured M2Web poll rate for the Ignition Ewon Connector
   * @since 1.0.0
   */
  public Integer getLivePollRate() {
    return getInt(LIVE_POLL_RATE);
  }

  /**
   * Gets the configured metadata poll rate for the Ignition Ewon Connector.
   *
   * @return the configured metadata poll rate for the Ignition Ewon Connector
   * @since 2.0.0
   */
  public Integer getMetadataPollRate() {
    return getInt(METADATA_POLL_RATE);
  }

  /**
   * Get boolean if force all realtime tags is enabled
   *
   * @return true/false if force all realtime tags is enabled
   * @since 1.0.0
   */
  public boolean isForceLive() {
    return getBoolean(FORCE_LIVE);
  }

  /**
   * Get boolean if combine live data is enabled
   *
   * @return true/false if combine live data is enabled
   * @since 2.0.0
   */
  public boolean isCombineLiveData() {
    return getBoolean(COMBINE_LIVE_DATA);
  }

  /**
   * Get configured tag write buffer length (in milliseconds) for the Ignition Ewon Connector.
   *
   * @return the configured tag write buffer length (in milliseconds) for the Ignition Ewon
   *     Connector
   * @since 2.0.0
   */
  public Long getTagWriteBufferLengthMs() {
    return getLong(TAG_WRITE_BUFFER_LENGTH_MS);
  }

  /**
   * Get boolean if sort tags by group is enabled
   *
   * @return true/false if sort tags by group is enabled
   * @since 2.0.0
   */
  public boolean isSortTagsByGroup() {
    return getBoolean(SORT_TAGS_BY_GROUP);
  }

  /**
   * Get boolean if tag name checking is disabled
   *
   * @return true/false if tag name checking is disabled
   * @since 1.0.0
   */
  public boolean isTagNameCheckDisabled() {
    return getBoolean(TAG_NAME_CHECK_DISABLED);
  }

  /**
   * Create and return AuthInfo object with configured information
   *
   * @return created AuthInfo object
   * @since 1.0.0
   */
  public CommunicationAuthInfo getAuthInfo() {

    CommunicationAuthInfo communicationAuthInfo;
    try {
      // Try fetching the credentials from the configuration page
      communicationAuthInfo =
          new CommunicationAuthInfo(
              getAccount(),
              getUserName(),
              getPassword(),
              getApiKey(),
              getToken(),
              getEwonUserName(),
              getEwonPassword());
    } catch (NullPointerException e) {
      // Some of the configuration page credentials are empty, force empty strings
      // Ignition logs will indicate incorrect user credentials
      communicationAuthInfo = new CommunicationAuthInfo("", "", "", "", "", "", "");
    }
    return communicationAuthInfo;
  }

  /**
   * Get boolean if tag history is enabled
   *
   * @return true/false if tag history is enabled
   * @since 1.0.0
   */
  public boolean isHistoryEnabled() {
    return getBoolean(HISTORY_ENABLED);
  }

  /**
   * Get configured tag history provider
   *
   * @return tag history provider
   * @since 1.0.0
   */
  public String getHistoryProvider() {
    return getString(HISTORY_PROVIDER);
  }
}

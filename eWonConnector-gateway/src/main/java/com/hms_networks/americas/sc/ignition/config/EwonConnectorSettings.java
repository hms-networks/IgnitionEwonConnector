package com.hms_networks.americas.sc.ignition.config;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.inductiveautomation.ignition.gateway.localdb.persistence.BooleanField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.Category;
import com.inductiveautomation.ignition.gateway.localdb.persistence.EncodedStringField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IdentityField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IntField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.PersistentRecord;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordMeta;
import com.inductiveautomation.ignition.gateway.localdb.persistence.StringField;
import com.inductiveautomation.ignition.gateway.web.components.editors.PasswordEditorSource;

/**
 * The main settings for the eWon connector, containing auth data, and settings that dictate update
 * rates, etc.
 */
public class EwonConnectorSettings extends PersistentRecord {
  /** Record meta information for Ewon Connector */
  public static final RecordMeta<EwonConnectorSettings> META =
      new RecordMeta<>(EwonConnectorSettings.class, "ewonConnectorSettings");

  /** Ewon Connector identifier */
  public static final IdentityField ID = new IdentityField(META);

  /** Configured boolean if Ewon Connector enabled */
  public static final BooleanField Enabled = new BooleanField(META, "enabled").setDefault(true);

  /** Configured boolean if Ewon Connector debug mode is enabled */
  public static final BooleanField DebugEnabled =
      new BooleanField(META, "debugEnabled").setDefault(false);

  /** Configured tag name sanitization choice */
  public static final BooleanField ReplaceUnderscore = new BooleanField(META, "replaceUnderscore");

  /** Configured Talk2M account name */
  public static final StringField Account = new StringField(META, "account");

  /** Configured Talk2M account username */
  public static final StringField UserName = new StringField(META, "username");

  /** Configured Talk2M account password */
  public static final EncodedStringField Password = new EncodedStringField(META, "password");

  /** Configured Talk2M account token */
  public static final EncodedStringField Token = new EncodedStringField(META, "token");

  /** Configured Ewon account username */
  public static final StringField EwonUserName = new StringField(META, "ewonUsername");

  /** Configured Ewon account password */
  public static final EncodedStringField EwonPassword =
      new EncodedStringField(META, "ewonPassword");

  /** Configured Talk2M API key */
  public static final StringField APIKey = new StringField(META, "apikey");

  /** Configured tag poll rate */
  public static final IntField PollRate = new IntField(META, "pollrate").setDefault(1);

  /** Configured realtime tag poll rate */
  public static final IntField LivePollRate = new IntField(META, "livepollrate").setDefault(10);

  /** Configured override for enabling realtime on all tags */
  public static final BooleanField ForceLive = new BooleanField(META, "forceLive");

  /** Configured override for enabling tag sorting by group. */
  public static final BooleanField SortTagsByGroup = new BooleanField(META, "sortTagsByGroup");

  /** Configured boolean if tag history is enabled */
  public static final BooleanField HistoryEnabled = new BooleanField(META, "historyEnabled");

  /** Configured tag history provider (if applicable) */
  public static final StringField HistoryProvider = new StringField(META, "historyProvider");

  /** Configured boolean if Ewon Connector enabled */
  public static final BooleanField TagNameCheckDisabled =
      new BooleanField(META, "tagNameCheckDisabled").setDefault(false);

  /** Settings category for general configuration options */
  public static final Category GeneralCategory =
      new Category("EwonConnectorSettings.Category.General", 10)
          .include(Enabled, ReplaceUnderscore, PollRate, LivePollRate);

  /** Settings category for Talk2M account information */
  public static final Category AccountCategory =
      new Category("EwonConnectorSettings.Category.Account", 50)
          .include(Account, UserName, Password, APIKey, Token);

  /** Settings category for Ewon device information */
  public static final Category DeviceCategory =
      new Category("EwonConnectorSettings.Category.Device", 75).include(EwonUserName, EwonPassword);

  /** Settings category for historical tag provider configuration */
  public static final Category HistoryCategory =
      new Category("EwonConnectorSettings.Category.History", 100)
          .include(HistoryEnabled, HistoryProvider);

  /** Settings category for advanced configuration */
  public static final Category AdvancedCategory =
      new Category("EwonConnectorSettings.Category.Advanced", 125)
          .include(ForceLive, SortTagsByGroup, TagNameCheckDisabled, DebugEnabled);

  // Configure passwords and history provider fields information
  static {
    HistoryProvider.getFormMeta().setEditorSource(TagHistoryListEditorSource.getSharedInstance());
    Password.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
    EwonPassword.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
  }

  /**
   * Get Ewon Connector settings meta
   *
   * @return settings meta
   */
  @Override
  public RecordMeta<?> getMeta() {
    return META;
  }

  /**
   * Get configured Talk2M account name
   *
   * @return Talk2M account name
   */
  public String getAccount() {
    return getString(Account);
  }

  /**
   * Get configured Talk2M account username
   *
   * @return Talk2M account username
   */
  public String getUserName() {
    return getString(UserName);
  }

  /**
   * Get configured Talk2M account password
   *
   * @return Talk2M account password
   */
  public String getPassword() {
    return getString(Password);
  }

  /**
   * Get configured Talk2M account token
   *
   * @return Talk2M account token
   */
  public String getToken() {
    return getString(Token);
  }

  /**
   * Get configured Ewon username
   *
   * @return Ewon username
   */
  public String getEwonUserName() {
    return getString(EwonUserName);
  }

  /**
   * Get configured Ewon password
   *
   * @return Ewon password
   */
  public String getEwonPassword() {
    return getString(EwonPassword);
  }

  /**
   * Get configured tag poll rate
   *
   * @return tag poll rate
   */
  public Integer getPollRate() {
    return getInt(PollRate);
  }

  /**
   * Get configured realtime tag poll rate
   *
   * @return realtime tag poll rate
   */
  public Integer getLivePollRate() {
    return getInt(LivePollRate);
  }

  /**
   * Get boolean if force all realtime tags is enabled
   *
   * @return true/false if force all realtime tags is enabled
   */
  public boolean isForceLive() {
    return getBoolean(ForceLive);
  }

  /**
   * Get boolean if sort tags by group is enabled
   *
   * @return true/false if sort tags by group is enabled
   */
  public boolean isSortTagsByGroup() {
    return getBoolean(SortTagsByGroup);
  }

  /**
   * Get boolean if Ewon Connector debug mode is enabled
   *
   * @return true/false if Ewon Connector debug mode is enabled
   */
  public boolean isDebugEnabled() {
    return getBoolean(DebugEnabled);
  }

  /**
   * Get boolean if Ewon Connector is enabled
   *
   * @return true/false if Ewon Connector is enabled
   */
  public boolean isEnabled() {
    return getBoolean(Enabled);
  }

  /**
   * Get boolean if tag name sanitization is enabled
   *
   * @return true/false if tag name sanitization is enabled
   */
  public boolean isReplaceUnderscore() {
    return getBoolean(ReplaceUnderscore);
  }

  /**
   * Get boolean if tag name checking is disabled
   *
   * @return true/false if tag name checking is disabled
   */
  public boolean isTagNameCheckDisabled() {
    return getBoolean(TagNameCheckDisabled);
  }

  /**
   * Get configured Talk2M API key
   *
   * @return Talk2M API key
   */
  public String getAPIKey() {
    return getString(APIKey);
  }

  /**
   * Create and return AuthInfo object with configured information
   *
   * @return created AuthInfo object
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
              getAPIKey(),
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
   */
  public boolean isHistoryEnabled() {
    return getBoolean(HistoryEnabled);
  }

  /**
   * Get configured tag history provider
   *
   * @return tag history provider
   */
  public String getHistoryProvider() {
    return getString(HistoryProvider);
  }
}

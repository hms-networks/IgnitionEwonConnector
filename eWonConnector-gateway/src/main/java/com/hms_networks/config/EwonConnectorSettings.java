package com.hms_networks.config;

import com.hms_networks.comm.AuthInfo;
import com.inductiveautomation.ignition.common.util.TimeUnits;
import com.inductiveautomation.ignition.gateway.localdb.persistence.BooleanField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.Category;
import com.inductiveautomation.ignition.gateway.localdb.persistence.EncodedStringField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.EnumField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IdentityField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.IntField;
import com.inductiveautomation.ignition.gateway.localdb.persistence.PersistentRecord;
import com.inductiveautomation.ignition.gateway.localdb.persistence.RecordMeta;
import com.inductiveautomation.ignition.gateway.localdb.persistence.StringField;
import com.inductiveautomation.ignition.gateway.web.components.editors.PasswordEditorSource;
import simpleorm.dataset.SFieldFlags;

/**
 * The main settings for the eWon connector, containing auth data, and settings that dictate update
 * rates, etc.
 *
 */
public class EwonConnectorSettings extends PersistentRecord {
   /**
    * Record meta information for Ewon Connector
    */
   public static RecordMeta<EwonConnectorSettings> META =
         new RecordMeta<>(EwonConnectorSettings.class, "ewonConnectorSettings");

   /**
    * Ewon Connector identifier
    */
   public static final IdentityField ID = new IdentityField(META);

   /**
    * Configured tag provider name
    */
   public static final StringField Name =
         new StringField(META, "name", SFieldFlags.SMANDATORY).setDefault("Ewon");

   /**
     * Configured boolean if Ewon Connector enabled
     */
   public static final BooleanField Enabled = new BooleanField(META, "enabled").setDefault(true);

   /**
    * Configured tag name sanitization choice
    */
   public static final BooleanField ReplaceUnderscore = new BooleanField(META, "replaceUnderscore");

   /**
    * Configured Talk2M account name
    */
   public static final StringField Account = new StringField(META, "account");

   /**
    * Configured Talk2M account username
    */
   public static final StringField UserName = new StringField(META, "username");

   /**
    * Configured Talk2M account password
    */
   public static final EncodedStringField Password = new EncodedStringField(META, "password");

   /**
    * Configured Ewon account username
    */
   public static final StringField EwonUserName = new StringField(META, "ewonUsername");

   /**
    * Configured Ewon account password
    */
   public static final EncodedStringField EwonPassword =
         new EncodedStringField(META, "ewonPassword");

   /**
    * Configured Talk2M API key
    */
   public static final StringField APIKey = new StringField(META, "apikey");

   /**
    * Configured tag poll rate
    */
   public static final IntField PollRate = new IntField(META, "pollrate").setDefault(1);

   /**
    * Configured realtime tag poll rate
    */
   public static final IntField LivePollRate = new IntField(META, "livepollrate").setDefault(10);

   /**
    * Configured override for enabling realtime on all tags
    */
   public static final BooleanField ForceLive = new BooleanField(META, "forceLive");

   /**
    * Configured boolean if tag history enabled
    */
   public static final BooleanField HistoryEnabled = new BooleanField(META, "historyEnabled");

   /**
    * Configured tag history provider (if applicable)
    */
   public static final StringField HistoryProvider = new StringField(META, "historyProvider");

   /**
    * Configured boolean if Ewon Connector enabled
    */
   public static final BooleanField TagNameCheckDisabled = new BooleanField(META, "tagNameCheckDisabled").setDefault(false);

   /**
    * Settings category for main configuration options
    */
   public static final Category GeneralCategory =
         new Category("EwonConnectorSettings.Category.General", 10).include(Name, Enabled,
               ReplaceUnderscore, PollRate, LivePollRate);

   /**
    * Settings category for Talk2M account information
    */
   public static final Category AccountCategory =
         new Category("EwonConnectorSettings.Category.Account", 50).include(Account, UserName,
               Password, APIKey);

   /**
    * Settings category for Ewon device information
    */
   public static final Category DeviceCategory =
         new Category("EwonConnectorSettings.Category.Device", 75).include(EwonUserName,
               EwonPassword);

   /**
    * Settings category for historical tag provider configuration
    */
   public static final Category HistoryCategory =
         new Category("EwonConnectorSettings.Category.History", 100).include(HistoryEnabled,
               HistoryProvider);

   /**
    * Settings category for advanced configuration
    */
   public static final Category AdvancedCategory =
           new Category("EwonConnectorSettings.Category.Advanced", 125).include(ForceLive,
               TagNameCheckDisabled);

   // Configure passwords and history provider fields information
   static {
      HistoryProvider.getFormMeta().setEditorSource(TagHistoryListEditorSource.getSharedInstance());
      Password.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
      EwonPassword.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
   }

   /**
    * Get Ewon Connector settings meta
    * @return settings meta
    */
   @Override
   public RecordMeta<?> getMeta() {
      return META;
   }

   /**
    * Get Ewon Connector tag provider name
    * @return tag provider name
    */
   public String getName() {
      return getString(Name);
   }

   /**
    * Get configured Talk2M account name
    * @return
    */
   public String getAccount() {
      return getString(Account);
   }

   /**
    * Get configured Talk2M account username
    * @return
    */
   public String getUserName() {
      return getString(UserName);
   }

   /**
    * Get configured Talk2 account password
    * @return Talk2M account password
    */
   public String getPassword() {
      return getString(Password);
   }

   /**
    * Get configured Ewon username
    * @return Ewon username
    */
   public String getEwonUserName() {
      return getString(EwonUserName);
   }

   /**
    * Get configured Ewon password
    * @return Ewon password
    */
   public String getEwonPassword() {
      return getString(EwonPassword);
   }

   /**
    * Get configured tag poll rate
    * @return tag poll rate
    */
   public Integer getPollRate() {
      return getInt(PollRate);
   }

   /**
    * Get configured realtime tag poll rate
    * @return realtime tag poll rate
    */
   public Integer getLivePollRate() {
      return getInt(LivePollRate);
   }

   /**
    * Get boolean if force all realtime tags is enabled
    * @return true/false if force all realtime tags is enabled
    */
   public boolean isForceLive() {
      return getBoolean(ForceLive);
   }

   /**
    * Get boolean if Ewon Connector is enabled
    * @return true/false if Ewon Connector is enabled
    */
   public boolean isEnabled() {
      return getBoolean(Enabled);
   }

   /**
    * Get boolean if tag name sanitization is enabled
    * @return true/false if tag name sanitization is enabled
    */
   public boolean isReplaceUnderscore() {
      return getBoolean(ReplaceUnderscore);
   }

   /**
    * Get boolean if tag name checking is disabled
    * @return true/false if tag name checking is disabled
    */
   public boolean isTagNameCheckDisabled() {
      return getBoolean(TagNameCheckDisabled);
   }

   /**
    * Get configured Talk2M API key
    * @return Talk2M API key
    */
   public String getAPIKey() {
      return getString(APIKey);
   }

   /**
    * Create and return AuthInfo object with configured information
    * @return created AuthInfo object
    */
   public AuthInfo getAuthInfo() {
      AuthInfo authInfo;
      try {
         // Try fetching the credentials from the configuration page
         authInfo = new AuthInfo(getAccount(), getUserName(), getPassword(), getAPIKey(),
                 getEwonUserName(), getEwonPassword());
      } catch (NullPointerException e) {
         // Some of the configuration page credentials are empty, force empty strings
         // Ignition logs will indicate incorrect user credentials
         authInfo = new AuthInfo("", "", "", "", "", "");
      }
      return authInfo;
   }

   /**
    * Get boolean if tag history is enabled
    * @return true/false if tag history is enabled
    */
   public boolean isHistoryEnabled() {
      return getBoolean(HistoryEnabled);
   }

   /**
    * Get configured tag history provider
    * @return tag history provider
    */
   public String getHistoryProvider() {
      return getString(HistoryProvider);
   }
}

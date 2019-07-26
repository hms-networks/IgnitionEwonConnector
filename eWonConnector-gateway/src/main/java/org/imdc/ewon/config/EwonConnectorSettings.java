package org.imdc.ewon.config;

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
import org.imdc.ewon.comm.AuthInfo;
import simpleorm.dataset.SFieldFlags;

/**
 * The main settings for the eWon connector, containing auth data, and settings that dictate update
 * rates, etc.
 *
 */
public class EwonConnectorSettings extends PersistentRecord {
   public static RecordMeta<EwonConnectorSettings> META =
         new RecordMeta<>(EwonConnectorSettings.class, "ewonConnectorSettings");

   public static final IdentityField ID = new IdentityField(META);
   public static final StringField Name =
         new StringField(META, "name", SFieldFlags.SMANDATORY).setDefault("eWon");
   public static final BooleanField Enabled = new BooleanField(META, "enabled");
   public static final BooleanField ReplaceUnderscore = new BooleanField(META, "replaceUnderscore");
   public static final StringField Account = new StringField(META, "account");
   public static final StringField UserName = new StringField(META, "username");
   public static final EncodedStringField Password = new EncodedStringField(META, "password");
   public static final StringField EwonUserName = new StringField(META, "ewonUsername");
   public static final EncodedStringField EwonPassword =
         new EncodedStringField(META, "ewonPassword");
   public static final StringField APIKey = new StringField(META, "apikey");
   public static final IntField PollRate = new IntField(META, "pollrate").setDefault(1);
   public static final IntField LivePollRate = new IntField(META, "livepollrate").setDefault(10);
   public static final BooleanField ForceLive = new BooleanField(META, "forceLive");

   public static final BooleanField HistoryEnabled = new BooleanField(META, "historyEnabled");
   public static final StringField HistoryProvider = new StringField(META, "historyProvider");

   public static final Category MainCategory =
         new Category("EwonConnectorSettings.Category.Main", 10).include(Name, Enabled,
               ReplaceUnderscore, PollRate, LivePollRate, ForceLive);
   public static final Category AccountCategory =
         new Category("EwonConnectorSettings.Category.Account", 50).include(Account, UserName,
               Password, APIKey);
   public static final Category DeviceCategory =
         new Category("EwonConnectorSettings.Category.Device", 75).include(EwonUserName,
               EwonPassword);
   public static final Category HistoryCategory =
         new Category("EwonConnectorSettings.Category.History", 100).include(HistoryEnabled,
               HistoryProvider);

   static {
      HistoryProvider.getFormMeta().setEditorSource(TagHistoryListEditorSource.getSharedInstance());
      Password.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
      EwonPassword.getFormMeta().setEditorSource(PasswordEditorSource.getSharedInstance());
   }

   @Override
   public RecordMeta<?> getMeta() {
      return META;
   }

   public String getName() {
      return getString(Name);
   }

   public String getAccount() {
      return getString(Account);
   }

   public String getUserName() {
      return getString(UserName);
   }

   public String getPassword() {
      return getString(Password);
   }

   public String getEwonUserName() {
      return getString(EwonUserName);
   }

   public String getEwonPassword() {
      return getString(EwonPassword);
   }

   public Integer getPollRate() {
      return getInt(PollRate);
   }

   public Integer getLivePollRate() {
      return getInt(LivePollRate);
   }

   public boolean isForceLive() {
      return getBoolean(ForceLive);
   }

   public boolean isEnabled() {
      return getBoolean(Enabled);
   }

   public boolean isReplaceUnderscore() {
      return getBoolean(ReplaceUnderscore);
   }

   public String getAPIKey() {
      return getString(APIKey);
   }

   public AuthInfo getAuthInfo() {
      return new AuthInfo(getAccount(), getUserName(), getPassword(), getAPIKey(),
            getEwonUserName(), getEwonPassword());
   }

   public boolean isHistoryEnabled() {
      return getBoolean(HistoryEnabled);
   }

   public String getHistoryProvider() {
      return getString(HistoryProvider);
   }
}

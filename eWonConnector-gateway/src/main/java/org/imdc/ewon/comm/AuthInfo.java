package org.imdc.ewon.comm;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.imdc.ewon.EwonConsts;

public class AuthInfo {
   private String account;
   private String username;
   private String password;
   private String devKey;
   private String ewonUsername;
   private String ewonPassword;

   /**
    * This constructor provides the information needed to access basic Talk2M features and DataMailbox.
    * @param account
    * @param username
    * @param password
    * @param devKey
    */
   public AuthInfo(String account, String username, String password, String devKey) {
      this.account = account;
      this.username = username;
      this.password = password;
      this.devKey = devKey;
   }

   /**
    * This constructor includes a username and password for a specific ewon device, to use the services of that device through Talk2M.
    * @param account
    * @param username
    * @param password
    * @param devKey
    * @param ewonUsername
    * @param ewonPassword
    */
   public AuthInfo(String account, String username, String password, String devKey, String ewonUsername, String ewonPassword) {
      this(account, username, password, devKey);
      this.ewonUsername = ewonUsername;
      this.ewonPassword = ewonPassword;
   }

   public String getAccount() {
      return account;
   }

   public String getUsername() {
      return username;
   }

   public String getPassword() {
      return password;
   }

   public String getDevKey() {
      return devKey;
   }

   public String getEwonUsername() {
      return ewonUsername;
   }

   public String getEwonPassword() {
      return ewonPassword;
   }

   public String toGetString(){
      String ret = String.format("%s=%s&%s=%s&%s=%s&%s=%s", EwonConsts.T2M_ACCOUNT, account, EwonConsts.T2M_USERNAME, username, EwonConsts.T2M_PASSWORD, password, EwonConsts.T2M_DEVKEY, devKey);
      if(!StringUtils.isBlank(ewonUsername) && !StringUtils.isBlank(ewonPassword)){
         ret += String.format("&%s=%s&%s=%s", EwonConsts.T2M_DEVICE_USERNAME, ewonUsername, EwonConsts.T2M_DEVICE_PASSWORD, ewonPassword);
      }
      return ret;
   }
}

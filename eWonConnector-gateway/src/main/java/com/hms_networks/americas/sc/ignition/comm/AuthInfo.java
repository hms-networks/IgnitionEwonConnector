package com.hms_networks.americas.sc.ignition.comm;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import org.apache.commons.lang3.StringUtils;
import com.hms_networks.americas.sc.ignition.EwonConsts;

/** Authentication information object */
public class AuthInfo {
  /** Talk2M account name */
  private String account;

  /** Talk2M account username */
  private String username;

  /** Talk2M account password */
  private String password;

  /** Talk2M API key */
  private String devKey;

  /** Ewon device username */
  private String ewonUsername;

  /** Ewon device password */
  private String ewonPassword;

  /**
   * This constructor provides the information needed to access basic Talk2M features and
   * DataMailbox.
   *
   * @param account
   * @param username
   * @param password
   * @param devKey
   */
  public AuthInfo(String account, String username, String password, String devKey) {
    this.account = urlEncodeValue(account);
    this.username = urlEncodeValue(username);
    this.password = urlEncodeValue(password);
    this.devKey = urlEncodeValue(devKey);
  }

  /**
   * This constructor includes a username and password for a specific ewon device, to use the
   * services of that device through Talk2M.
   *
   * @param account
   * @param username
   * @param password
   * @param devKey
   * @param ewonUsername
   * @param ewonPassword
   */
  public AuthInfo(
      String account,
      String username,
      String password,
      String devKey,
      String ewonUsername,
      String ewonPassword) {
    this(account, username, password, devKey);
    this.ewonUsername = urlEncodeValue(ewonUsername);
    this.ewonPassword = urlEncodeValue(ewonPassword);
  }

  /**
   * Get Talk2M account name
   *
   * @return Talk2M account name
   */
  public String getAccount() {
    return account;
  }

  /**
   * Get Talk2M account username
   *
   * @return Talk2M account username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Get Talk2M account password
   *
   * @return Talk2M account password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Get Talk2M API key
   *
   * @return Talk2M API key
   */
  public String getDevKey() {
    return devKey;
  }

  /**
   * Get Ewon device username
   *
   * @return Ewon device username
   */
  public String getEwonUsername() {
    return ewonUsername;
  }

  /**
   * Get Ewon device password
   *
   * @return Ewon device password
   */
  public String getEwonPassword() {
    return ewonPassword;
  }

  /**
   * Generate an HTTP GET string with authentication information
   *
   * @return authentication information HTTP GET string
   */
  public String toGetString() {
    String ret =
        String.format(
            "%s=%s&%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_ACCOUNT,
            account,
            EwonConsts.T2M_USERNAME,
            username,
            EwonConsts.T2M_PASSWORD,
            password,
            EwonConsts.T2M_DEVKEY,
            devKey);
    if (!StringUtils.isBlank(ewonUsername) && !StringUtils.isBlank(ewonPassword)) {
      ret +=
          String.format(
              "&%s=%s&%s=%s",
              EwonConsts.T2M_DEVICE_USERNAME,
              ewonUsername,
              EwonConsts.T2M_DEVICE_PASSWORD,
              ewonPassword);
    }
    return ret;
  }

  /**
   * Creates a URL safe string from the passed in value
   *
   * @param value input string to be made url safe
   * @return The URL safe string
   */
  private String urlEncodeValue(String value) {
    try {
      return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException e) {
      // Return the passed in string, the string could not be encoded
      return value;
    }
  }
}

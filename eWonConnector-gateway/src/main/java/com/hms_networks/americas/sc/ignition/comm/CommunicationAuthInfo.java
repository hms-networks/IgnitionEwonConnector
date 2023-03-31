package com.hms_networks.americas.sc.ignition.comm;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Object containing authentication information for accessing basic Talk2M features and DataMailbox.
 *
 * @author HMS Networks, MU Americas Solution Center
 */
public class CommunicationAuthInfo {

  /** Talk2M account name */
  private final String account;

  /** Talk2M account username */
  private final String username;

  /** Talk2M account password */
  private final String password;

  /** Talk2M developer ID */
  private final String devId;

  /** Talk2M token */
  private final String token;

  /** Ewon device username */
  private final String ewonUsername;

  /** Ewon device password */
  private final String ewonPassword;

  /**
   * This constructor provides the information needed to access basic Talk2M features and
   * DataMailbox.
   *
   * @param account Talk2M account name
   * @param username Talk2M account username
   * @param password Talk2M account password
   * @param devId Talk2M developer ID
   * @param token Talk2M token
   */
  public CommunicationAuthInfo(
      String account, String username, String password, String devId, String token) {
    this.account = urlEncodeValue(account);
    this.username = urlEncodeValue(username);
    this.password = urlEncodeValue(password);
    this.devId = urlEncodeValue(devId);
    this.token = urlEncodeValue(token);
    this.ewonUsername = null;
    this.ewonPassword = null;
  }

  /**
   * This constructor includes a username and password for a specific ewon device, to use the
   * services of that device through Talk2M.
   *
   * @param account Talk2M account name
   * @param username Talk2M account username
   * @param password Talk2M account password
   * @param devId Talk2M developer ID
   * @param token Talk2M token
   * @param ewonUsername Ewon device username
   * @param ewonPassword Ewon device password
   */
  public CommunicationAuthInfo(
      String account,
      String username,
      String password,
      String devId,
      String token,
      String ewonUsername,
      String ewonPassword) {
    this.account = urlEncodeValue(account);
    this.username = urlEncodeValue(username);
    this.password = urlEncodeValue(password);
    this.devId = urlEncodeValue(devId);
    this.token = urlEncodeValue(token);
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
   * Get Talk2M developer ID
   *
   * @return Talk2M developer ID
   */
  public String getDevId() {
    return devId;
  }

  /**
   * Get Talk2M token
   *
   * @return Talk2M token
   */
  public String getToken() {
    return token;
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

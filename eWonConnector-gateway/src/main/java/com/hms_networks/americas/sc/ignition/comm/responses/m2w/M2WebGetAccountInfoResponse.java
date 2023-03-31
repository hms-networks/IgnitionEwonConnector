package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.Gson;

/**
 * JSON object for an M2Web getaccountinfo response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class M2WebGetAccountInfoResponse {

  /** The success status of the getaccountinfo request. */
  private boolean success;

  /** The account reference from the getaccountinfo request. (Only populated if success is true) */
  private String accountReference;

  /** The account name from the getaccountinfo request. (Only populated if success is true) */
  private String accountName;

  /** The company name from the getaccountinfo request. (Only populated if success is true) */
  private String company;

  /** The custom attributes from the getaccountinfo request. (Only populated if success is true) */
  private String[] customAttributes;

  /** The pools from the getaccountinfo request. (Only populated if success is true) */
  private Pool[] pools;

  /**
   * The account type from the getaccountinfo request. The value is either "Free" or "Pro" depending
   * on the account. (Only populated if success is true)
   */
  private String accountType;

  /**
   * Gets the success status of the getaccountinfo request.
   *
   * @return The success status of the getaccountinfo request.
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets the account reference from the getaccountinfo request.
   *
   * @return The account reference from the getaccountinfo request.
   */
  public String getAccountReference() {
    return accountReference;
  }

  /**
   * Gets the account name from the getaccountinfo request.
   *
   * @return The account name from the getaccountinfo request.
   */
  public String getAccountName() {
    return accountName;
  }

  /**
   * Gets the company name from the getaccountinfo request.
   *
   * @return The company name from the getaccountinfo request.
   */
  public String getCompany() {
    return company;
  }

  /**
   * Gets the custom attributes from the getaccountinfo request.
   *
   * @return The custom attributes from the getaccountinfo request.
   */
  public String[] getCustomAttributes() {
    return customAttributes;
  }

  /**
   * Gets the pools from the getaccountinfo request.
   *
   * @return The pools from the getaccountinfo request.
   */
  public Pool[] getPools() {
    return pools;
  }

  /**
   * Gets the account type from the getaccountinfo request.
   *
   * @return The account type from the getaccountinfo request.
   */
  public String getAccountType() {
    return accountType;
  }

  /**
   * Gets an instance of {@link M2WebGetAccountInfoResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebGetAccountInfoResponse} parsed from the JSON string.
   */
  public static M2WebGetAccountInfoResponse getFromJson(String json) {
    return new Gson().fromJson(json, M2WebGetAccountInfoResponse.class);
  }

  /**
   * A class representing a pool in the getaccountinfo response.
   *
   * @author HMS Networks, MU Americas Solution Center
   */
  public static class Pool {

    /** The pool ID. */
    private int id;

    /** The pool name. */
    private String name;

    /**
     * Gets the pool ID.
     *
     * @return The pool ID.
     */
    public int getId() {
      return id;
    }

    /**
     * Gets the pool name.
     *
     * @return The pool name.
     */
    public String getName() {
      return name;
    }
  }
}

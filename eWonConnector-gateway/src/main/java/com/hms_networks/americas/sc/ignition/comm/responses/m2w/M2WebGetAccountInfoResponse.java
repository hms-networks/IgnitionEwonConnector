package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;

/**
 * JSON object for an M2Web getaccountinfo response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebGetAccountInfoResponse extends Talk2MResponse {

  /**
   * The account reference from the getaccountinfo request. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private String accountReference;

  /**
   * The account name from the getaccountinfo request. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private String accountName;

  /**
   * The company name from the getaccountinfo request. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private String company;

  /**
   * The custom attributes from the getaccountinfo request. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private String[] customAttributes;

  /**
   * The pools from the getaccountinfo request. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private Pool[] pools;

  /**
   * The account type from the getaccountinfo request. The value is either "Free" or "Pro" depending
   * on the account. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private String accountType;

  /**
   * Gets the account reference from the getaccountinfo request.
   *
   * @return The account reference from the getaccountinfo request.
   * @since 1.0.0
   */
  public String getAccountReference() {
    return accountReference;
  }

  /**
   * Gets the account name from the getaccountinfo request.
   *
   * @return The account name from the getaccountinfo request.
   * @since 1.0.0
   */
  public String getAccountName() {
    return accountName;
  }

  /**
   * Gets the company name from the getaccountinfo request.
   *
   * @return The company name from the getaccountinfo request.
   * @since 1.0.0
   */
  public String getCompany() {
    return company;
  }

  /**
   * Gets the custom attributes from the getaccountinfo request.
   *
   * @return The custom attributes from the getaccountinfo request.
   * @since 1.0.0
   */
  public String[] getCustomAttributes() {
    return customAttributes;
  }

  /**
   * Gets the pools from the getaccountinfo request.
   *
   * @return The pools from the getaccountinfo request.
   * @since 1.0.0
   */
  public Pool[] getPools() {
    return pools;
  }

  /**
   * Gets the account type from the getaccountinfo request.
   *
   * @return The account type from the getaccountinfo request.
   * @since 1.0.0
   */
  public String getAccountType() {
    return accountType;
  }

  /**
   * Gets an instance of {@link M2WebGetAccountInfoResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebGetAccountInfoResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     M2WebGetAccountInfoResponse} class.
   * @since 1.0.0
   */
  public static M2WebGetAccountInfoResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, M2WebGetAccountInfoResponse.class);
  }

  /**
   * A class representing a pool in the getaccountinfo response.
   *
   * @author HMS Networks, MU Americas Solution Center
   * @since 1.0.0
   * @version 1.0.0
   */
  public static class Pool {

    /**
     * The pool ID.
     *
     * @since 1.0.0
     */
    private int id;

    /**
     * The pool name.
     *
     * @since 1.0.0
     */
    private String name;

    /**
     * Gets the pool ID.
     *
     * @return The pool ID.
     * @since 1.0.0
     */
    public int getId() {
      return id;
    }

    /**
     * Gets the pool name.
     *
     * @return The pool name.
     * @since 1.0.0
     */
    public String getName() {
      return name;
    }
  }
}

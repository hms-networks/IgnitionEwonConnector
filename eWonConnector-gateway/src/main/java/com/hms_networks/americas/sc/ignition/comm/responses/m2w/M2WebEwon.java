package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

/**
 * JSON object for an Ewon in an M2Web API response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwon {

  /**
   * The ID of the Ewon.
   *
   * @since 1.0.0
   */
  private int id;

  /**
   * The name of the Ewon.
   *
   * @since 1.0.0
   */
  private String name;

  /**
   * The encoded name of the Ewon. This is the URL-friendly version of the name intended to be used
   * in the 'get' service.
   *
   * @since 1.0.0
   */
  private String encodedName;

  /**
   * The status of the Ewon.
   *
   * @since 1.0.0
   */
  private String status;

  /**
   * The description of the Ewon.
   *
   * @since 1.0.0
   */
  private String description;

  /**
   * The custom attributes of the Ewon.
   *
   * @since 1.0.0
   */
  private String[] customAttributes;

  /**
   * The M2Web server of the Ewon. This is the hostname ot be used when using the 'get' service to
   * retrieve information from this Ewon.
   *
   * @since 1.0.0
   */
  private String m2webServer;

  /**
   * Gets the ID of the Ewon.
   *
   * @return The ID of the Ewon.
   * @since 1.0.0
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the Ewon.
   *
   * @return The name of the Ewon.
   * @since 1.0.0
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the encoded name of the Ewon.
   *
   * @return The encoded name of the Ewon.
   * @since 1.0.0
   */
  public String getEncodedName() {
    return encodedName;
  }

  /**
   * Gets the status of the Ewon.
   *
   * @return The status of the Ewon.
   * @since 1.0.0
   */
  public String getStatus() {
    return status;
  }

  /**
   * Gets the description of the Ewon.
   *
   * @return The description of the Ewon.
   * @since 1.0.0
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the custom attributes of the Ewon.
   *
   * @return The custom attributes of the Ewon.
   * @since 1.0.0
   */
  public String[] getCustomAttributes() {
    return customAttributes;
  }

  /**
   * Gets the M2Web server of the Ewon.
   *
   * @return The M2Web server of the Ewon.
   * @since 1.0.0
   */
  public String getM2webServer() {
    return m2webServer;
  }
}

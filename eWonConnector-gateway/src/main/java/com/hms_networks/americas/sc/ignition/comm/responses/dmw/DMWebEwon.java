package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import java.util.List;

/**
 * JSON object for an Ewon in an DMWeb API response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebEwon {

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
   * The list of Ewon tags. This field is only present in responses from the 'getdata' and
   * 'syncdata' endpoints.
   *
   * @since 1.0.0
   */
  private List<DMWebEwonTag> tags;

  /**
   * The timezone name of the Ewon. This field is only present if the Ewon 'Record Data in UTC'
   * option is enabled.
   *
   * @since 1.0.0
   */
  private String timeZone;

  /**
   * The last synchronization date of the Ewon.
   *
   * @since 1.0.0
   */
  private String lastSynchronDate;

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
   * Gets the list of Ewon tags.
   *
   * @return The list of Ewon tags.
   * @since 1.0.0
   */
  public List<DMWebEwonTag> getTags() {
    return tags;
  }

  /**
   * Gets the timezone name of the Ewon.
   *
   * @return The timezone name of the Ewon.
   * @since 1.0.0
   */
  public String getTimeZone() {
    return timeZone;
  }

  /**
   * Gets the last synchronization date of the Ewon.
   *
   * @return The last synchronization date of the Ewon.
   * @since 1.0.0
   */
  public String getLastSynchronDate() {
    return lastSynchronDate;
  }
}

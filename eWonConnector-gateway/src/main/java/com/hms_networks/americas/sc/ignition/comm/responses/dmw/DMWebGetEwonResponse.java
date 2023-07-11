package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

/**
 * JSON object for a DMWeb getewon response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebGetEwonResponse extends Talk2MResponse {

  /**
   * The ID of the Ewon from the getewon response.
   *
   * @since 1.0.0
   */
  private int id;

  /**
   * The name of the Ewon from the getewon response.
   *
   * @since 1.0.0
   */
  private String name;

  /**
   * The list of Ewon tags from the getewon response.
   *
   * @since 1.0.0
   */
  private List<DMWebEwonTag> tags;

  /**
   * The timezone name of the Ewon from the getewon response. This field is only present if the Ewon
   * 'Record Data in UTC' option is enabled.
   *
   * @since 1.0.0
   */
  private String timeZone;

  /**
   * The last synchronization date of the Ewon from the getewon response.
   *
   * @since 1.0.0
   */
  private String lastSynchronDate;

  /**
   * Gets the ID of the Ewon from the getewon response.
   *
   * @return The ID of the Ewon from the getewon response.
   * @since 1.0.0
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the Ewon from the getewon response.
   *
   * @return The name of the Ewon from the getewon response.
   * @since 1.0.0
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the list of Ewon tags from the getewon response.
   *
   * @return The list of Ewon tags from the getewon response.
   * @since 1.0.0
   */
  public List<DMWebEwonTag> getTags() {
    return tags;
  }

  /**
   * Gets the timezone name of the Ewon from the getewon response.
   *
   * @return The timezone name of the Ewon from the getewon response.
   * @since 1.0.0
   */
  public String getTimeZone() {
    return timeZone;
  }

  /**
   * Gets the last synchronization date of the Ewon from the getewon response.
   *
   * @return The last synchronization date of the Ewon from the getewon response.
   * @throws DateTimeParseException if the date string is not valid
   * @since 1.0.0
   */
  public Date getLastSynchronDate() throws DateTimeParseException {
    return Talk2MResponse.toDate(lastSynchronDate);
  }

  /**
   * Gets an instance of {@link DMWebGetEwonResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link DMWebGetEwonResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     DMWebGetEwonResponse} class.
   * @since 1.0.0
   */
  public static DMWebGetEwonResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, DMWebGetEwonResponse.class);
  }
}

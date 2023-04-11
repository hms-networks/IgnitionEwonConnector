package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;
import java.util.List;

/**
 * JSON object for a DMWeb getstatus response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebGetStatusResponse extends Talk2MResponse {

  /**
   * The number of history points currently stored in the DataMailbox from the getstatus response.
   *
   * @since 1.0.0
   */
  private int historyCount;

  /**
   * The number of Ewon gateways sending data to the DataMailbox from the getstatus response.
   *
   * @since 1.0.0
   */
  private int ewonsCount;

  /**
   * The list of Ewon gateways and their status information sfrom the getstatus response.
   *
   * @since 1.0.0
   */
  private List<DMWebEwonStatus> ewons;

  /**
   * Gets the number of history points currently stored in the DataMailbox from the getstatus
   * response.
   *
   * @return The number of history points currently stored in the DataMailbox from the getstatus
   *     response.
   * @since 1.0.0
   */
  public int getHistoryCount() {
    return historyCount;
  }

  /**
   * Gets the number of Ewon gateways sending data to the DataMailbox from the getstatus response.
   *
   * @return The number of Ewon gateways sending data to the DataMailbox from the getstatus
   *     response.
   * @since 1.0.0
   */
  public int getEwonsCount() {
    return ewonsCount;
  }

  /**
   * Gets the list of Ewon gateways and their status information sfrom the getstatus response.
   *
   * @return The list of Ewon gateways and their status information sfrom the getstatus response.
   * @since 1.0.0
   */
  public List<DMWebEwonStatus> getEwons() {
    return ewons;
  }

  /**
   * Gets an instance of {@link DMWebGetStatusResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link DMWebGetStatusResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     DMWebGetStatusResponse} class.
   * @since 1.0.0
   */
  public static DMWebGetStatusResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, DMWebGetStatusResponse.class);
  }
}

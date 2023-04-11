package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;
import java.util.List;

/**
 * JSON object for a DMWeb getdata response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebGetDataResponse extends Talk2MResponse {

  /**
   * The flag indicating if more data is available for the getdata request.
   *
   * @since 1.0.0
   */
  private boolean moreDataAvailable;

  /**
   * The list of Ewon gateways (and their data) from the getdata request.
   *
   * @since 1.0.0
   */
  private List<DMWebEwon> ewons;

  /**
   * Gets the flag indicating if more data is available for the getdata request.
   *
   * @return The flag indicating if more data is available for the getdata request.
   * @since 1.0.0
   */
  public boolean getMoreDataAvailable() {
    return moreDataAvailable;
  }

  /**
   * Gets the list of Ewon gateways (and their data) from the getdata request.
   *
   * @return The list of Ewon gateways (and their data) from the getdata request.
   * @since 1.0.0
   */
  public List<DMWebEwon> getEwons() {
    return ewons;
  }

  /**
   * Gets an instance of {@link DMWebGetDataResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link DMWebGetDataResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     DMWebGetDataResponse} class.
   * @since 1.0.0
   */
  public static DMWebGetDataResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, DMWebGetDataResponse.class);
  }
}

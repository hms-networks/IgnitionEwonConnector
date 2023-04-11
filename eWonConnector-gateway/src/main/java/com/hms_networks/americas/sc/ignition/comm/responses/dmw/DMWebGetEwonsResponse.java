package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;
import java.util.List;

/**
 * JSON object for a DMWeb getewons response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebGetEwonsResponse extends Talk2MResponse {

  /**
   * The list of Ewon gateways from the getewons response.
   *
   * @since 1.0.0
   */
  private List<DMWebEwon> ewons;

  /**
   * Gets the list of Ewon gateways from the getewons response.
   *
   * @return The list of Ewon gateways from the getewons response.
   * @since 1.0.0
   */
  public List<DMWebEwon> getEwons() {
    return ewons;
  }

  /**
   * Gets an instance of {@link DMWebGetEwonsResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link DMWebGetEwonsResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     DMWebGetEwonsResponse} class.
   * @since 1.0.0
   */
  public static DMWebGetEwonsResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, DMWebGetEwonsResponse.class);
  }
}

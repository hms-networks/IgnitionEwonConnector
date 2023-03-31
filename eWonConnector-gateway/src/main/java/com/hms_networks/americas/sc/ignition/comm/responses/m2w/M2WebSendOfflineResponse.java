package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.Gson;

/**
 * JSON object for an M2Web sendoffline response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class M2WebSendOfflineResponse {

  /** The success status of the sendoffline request. */
  private boolean success;

  /**
   * Gets the success status of the sendoffline request.
   *
   * @return The success status of the sendoffline request.
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets an instance of {@link M2WebSendOfflineResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebSendOfflineResponse} parsed from the JSON string.
   */
  public static M2WebSendOfflineResponse getFromJson(String json) {
    return new Gson().fromJson(json, M2WebSendOfflineResponse.class);
  }
}

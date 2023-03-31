package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;

/**
 * JSON object for an M2Web sendoffline response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebSendOfflineResponse extends Talk2MResponse {

  /**
   * Gets an instance of {@link M2WebSendOfflineResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebSendOfflineResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     M2WebSendOfflineResponse} class.
   * @since 1.0.0
   */
  public static M2WebSendOfflineResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, M2WebSendOfflineResponse.class);
  }
}

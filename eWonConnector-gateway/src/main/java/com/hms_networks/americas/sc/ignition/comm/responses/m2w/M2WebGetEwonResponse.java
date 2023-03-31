package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;

/**
 * JSON object for an M2Web getewon response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebGetEwonResponse extends Talk2MResponse {

  /**
   * The returned {@link M2WebEwon} for the getewon request. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private M2WebEwon ewon;

  /**
   * Gets the returned {@link M2WebEwon} for the getewon request.
   *
   * @return The returned {@link M2WebEwon} for the getewon request.
   * @since 1.0.0
   */
  public M2WebEwon getEwon() {
    return ewon;
  }

  /**
   * Gets an instance of {@link M2WebGetEwonResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebGetEwonResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     M2WebGetEwonResponse} class.
   * @since 1.0.0
   */
  public static M2WebGetEwonResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, M2WebGetEwonResponse.class);
  }
}

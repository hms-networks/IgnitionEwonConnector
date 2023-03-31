package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.Gson;

/**
 * JSON object for an M2Web getewon response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class M2WebGetEwonResponse {

  /** The success status of the getewon request. */
  private boolean success;

  /** The returned {@link M2WebEwon} for the getewon request. (Only populated if success is true) */
  private M2WebEwon ewon;

  /**
   * Gets the success status of the getewon request.
   *
   * @return The success status of the getewon request.
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets the returned {@link M2WebEwon} for the getewon request.
   *
   * @return The returned {@link M2WebEwon} for the getewon request.
   */
  public M2WebEwon getEwon() {
    return ewon;
  }

  /**
   * Gets an instance of {@link M2WebGetEwonResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebGetEwonResponse} parsed from the JSON string.
   */
  public static M2WebGetEwonResponse getFromJson(String json) {
    return new Gson().fromJson(json, M2WebGetEwonResponse.class);
  }
}

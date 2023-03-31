package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.Gson;

/**
 * JSON object for an M2Web getewons response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class M2WebGetEwonsResponse {

  /** The success status of the getewons request. */
  private boolean success;

  /** The list of {@link M2WebEwon}s for the getewons request. (Only populated if success is true) */
  private M2WebEwon[] ewons;

  /**
   * Gets the success status of the getewons request.
   *
   * @return The success status of the getewons request.
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets the list of {@link M2WebEwon}s for the getewons request.
   *
   * @return The list of {@link M2WebEwon}s for the getewons request.
   */
  public M2WebEwon[] getEwons() {
    return ewons;
  }

  /**
   * Gets an instance of {@link M2WebGetEwonsResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebGetEwonsResponse} parsed from the JSON string.
   */
  public static M2WebGetEwonsResponse getFromJson(String json) {
    return new Gson().fromJson(json, M2WebGetEwonsResponse.class);
  }
}

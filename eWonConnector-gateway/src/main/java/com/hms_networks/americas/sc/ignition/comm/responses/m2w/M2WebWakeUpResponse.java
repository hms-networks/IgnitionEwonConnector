package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.Gson;

/**
 * JSON object for an M2Web wakeup response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class M2WebWakeUpResponse {

  /** The success status of the wakeup request. */
  private boolean success;

  /**
   * Gets the success status of the wakeup request.
   *
   * @return The success status of the wakeup request.
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets an instance of {@link M2WebWakeUpResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebWakeUpResponse} parsed from the JSON string.
   */
  public static M2WebWakeUpResponse getFromJson(String json) {
    return new Gson().fromJson(json, M2WebWakeUpResponse.class);
  }
}

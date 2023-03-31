package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.Gson;

/**
 * JSON object for an M2Web logout response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class M2WebLogoutResponse {

  /** The success status of the logout request. */
  private boolean success;

  /**
   * Gets the success status of the login request.
   *
   * @return The success status of the login request.
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets an instance of {@link M2WebLogoutResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebLogoutResponse} parsed from the JSON string.
   */
  public static M2WebLogoutResponse getFromJson(String json) {
    return new Gson().fromJson(json, M2WebLogoutResponse.class);
  }
}

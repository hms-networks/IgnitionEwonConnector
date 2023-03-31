package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.Gson;

/**
 * JSON object for an M2Web login response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class M2WebLoginResponse {

  /** The success status of the login request. */
  private boolean success;

  /** The session ID for the login request. (Only populated if success is true) */
  private String t2msession;

  /** The error code for the login request. (Only populated if success is false) */
  private int code;

  /** The error message for the login request. (Only populated if success is false) */
  private String message;

  /**
   * Gets the success status of the login request.
   *
   * @return The success status of the login request.
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets the session ID for the login request.
   *
   * @return The session ID for the login request.
   */
  public String getT2msession() {
    return t2msession;
  }

  /**
   * Gets the error code for the login request.
   *
   * @return The error code for the login request.
   */
  public int getCode() {
    return code;
  }

  /**
   * Gets the error message for the login request.
   *
   * @return The error message for the login request.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets an instance of {@link M2WebLoginResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebLoginResponse} parsed from the JSON string.
   */
  public static M2WebLoginResponse getFromJson(String json) {
    return new Gson().fromJson(json, M2WebLoginResponse.class);
  }
}

package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;

/**
 * JSON object for an M2Web login response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebLoginResponse extends Talk2MResponse {

  /**
   * The session ID for the login request. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private String t2msession;

  /**
   * The error code for the login request. (Only populated if success is false)
   *
   * @since 1.0.0
   */
  private int code;

  /**
   * The error message for the login request. (Only populated if success is false)
   *
   * @since 1.0.0
   */
  private String message;

  /**
   * Gets the session ID for the login request.
   *
   * @return The session ID for the login request.
   * @since 1.0.0
   */
  public String getT2msession() {
    return t2msession;
  }

  /**
   * Gets the error code for the login request.
   *
   * @return The error code for the login request.
   * @since 1.0.0
   */
  public int getCode() {
    return code;
  }

  /**
   * Gets the error message for the login request.
   *
   * @return The error message for the login request.
   * @since 1.0.0
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets an instance of {@link M2WebLoginResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebLoginResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     M2WebLoginResponse} class.
   * @since 1.0.0
   */
  public static M2WebLoginResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, M2WebLoginResponse.class);
  }
}

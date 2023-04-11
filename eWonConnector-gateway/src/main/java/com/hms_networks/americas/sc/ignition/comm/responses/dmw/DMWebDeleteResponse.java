package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;

/**
 * JSON object for a DMWeb delete response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebDeleteResponse extends Talk2MResponse {

  /**
   * The error code (when the request did not succeed) from the delete response. This field is only
   * populated when {@link #getSuccess()} is {@code false}.
   *
   * @since 1.0.0
   */
  private int code;

  /**
   * The error message (when the request did not succeed) from the delete response. This field is
   * only populated when {@link #getSuccess()} is {@code false}.
   *
   * @since 1.0.0
   */
  private String message;

  /**
   * Gets the error code (when the request did not succeed) from the delete response.
   *
   * @return The error code (when the request did not succeed) from the delete response.
   * @since 1.0.0
   */
  public int getCode() {
    return code;
  }

  /**
   * Gets the error message (when the request did not succeed) from the delete response.
   *
   * @return The error message (when the request did not succeed) from the delete response.
   * @since 1.0.0
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets an instance of {@link DMWebDeleteResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link DMWebDeleteResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     DMWebDeleteResponse} class.
   * @since 1.0.0
   */
  public static DMWebDeleteResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, DMWebDeleteResponse.class);
  }
}

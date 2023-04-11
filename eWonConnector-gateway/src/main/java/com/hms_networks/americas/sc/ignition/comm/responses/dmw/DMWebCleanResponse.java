package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;

/**
 * JSON object for a DMWeb clean response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebCleanResponse extends Talk2MResponse {

  /**
   * The error code (when the request did not succeed) from the clean response. This field is only
   * populated when {@link #getSuccess()} is {@code false}.
   *
   * @since 1.0.0
   */
  private int code;

  /**
   * The error message (when the request did not succeed) from the clean response. This field is
   * only populated when {@link #getSuccess()} is {@code false}.
   *
   * @since 1.0.0
   */
  private String message;

  /**
   * Gets the error code (when the request did not succeed) from the clean response.
   *
   * @return The error code (when the request did not succeed) from the clean response.
   * @since 1.0.0
   */
  public int getCode() {
    return code;
  }

  /**
   * Gets the error message (when the request did not succeed) from the clean response.
   *
   * @return The error message (when the request did not succeed) from the clean response.
   * @since 1.0.0
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets an instance of {@link DMWebCleanResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link DMWebCleanResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     DMWebCleanResponse} class.
   * @since 1.0.0
   */
  public static DMWebCleanResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, DMWebCleanResponse.class);
  }
}

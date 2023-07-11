package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.JsonSyntaxException;
import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;
import java.util.List;

/**
 * JSON object for an M2Web getewons response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebGetEwonsResponse extends Talk2MResponse {

  /**
   * The list of {@link M2WebEwon}s for the getewons request. (Only populated if success is true)
   *
   * @since 1.0.0
   */
  private List<M2WebEwon> ewons;

  /**
   * Gets the list of {@link M2WebEwon}s for the getewons request.
   *
   * @return The list of {@link M2WebEwon}s for the getewons request.
   * @since 1.0.0
   */
  public List<M2WebEwon> getEwons() {
    return ewons;
  }

  /**
   * Gets an instance of {@link M2WebGetEwonsResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebGetEwonsResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     M2WebGetEwonsResponse} class.
   * @since 1.0.0
   */
  public static M2WebGetEwonsResponse getFromJson(String json) throws JsonSyntaxException {
    return getFromJson(json, M2WebGetEwonsResponse.class);
  }
}

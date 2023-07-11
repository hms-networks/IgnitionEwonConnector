package com.hms_networks.americas.sc.ignition.comm.responses;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * An abstract class for responses from a Talk2M API. This class contains any common fields and/or
 * functionality for all Talk2M responses.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class Talk2MResponse {

  /**
   * The success status of the Talk2M request.
   *
   * @since 1.0.0
   */
  private boolean success;

  /**
   * Gets the success status of the Talk2M request.
   *
   * @return The success status of the Talk2M request.
   * @since 1.0.0
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets an instance of the specified subclass of {@link Talk2MResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @param clazz The subclass of {@link Talk2MResponse} to parse the JSON string into.
   * @return An instance of the specified subclass of {@link Talk2MResponse} parsed from the JSON
   *     string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the specified
   *     subclass of {@link Talk2MResponse}.
   * @since 1.0.0
   */
  public static <T extends Talk2MResponse> T getFromJson(String json, Class<T> clazz)
      throws JsonSyntaxException {
    return new Gson().fromJson(json, clazz);
  }

  /**
   * Converts the specified ISO 8601 date string to a {@link Date} object.
   *
   * @param dateString text string of date in UTC, parsed using <a
   *     href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_INSTANT">DateTimeFormatter.ISO_INSTANT</a>.
   * @implNote This method was adapted from the EwonUtil.toDate() method in v1.0.0 of the connector
   * @return the {@link Date} object from date string
   * @throws DateTimeParseException if the date string is not valid
   * @since 1.0.0
   */
  public static Date toDate(String dateString) throws DateTimeParseException {
    return Date.from(Instant.parse(dateString));
  }
}

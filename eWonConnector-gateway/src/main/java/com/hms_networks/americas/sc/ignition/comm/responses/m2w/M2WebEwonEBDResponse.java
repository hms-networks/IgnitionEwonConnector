package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parent object class for an M2Web Ewon EBD response. Typically, the response to an Ewon EBD
 * request will be in CSV format. However, if the request is unsuccessful, the response will be in
 * JSON format.
 *
 * <p>This class provides an interface for parsing the JSON response (if applicable) using the
 * {@link #getFromJson(String, Class)} method, and retrieving the error message and code (if
 * applicable).
 *
 * <p>A successful response will not populate the error message or code, and {@link #getSuccess()}
 * will return true. An unsuccessful response will populate the error message and code, and {@link
 * #getSuccess()} will return false.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwonEBDResponse {

  /**
   * The error code value indicating that no error was returned.
   *
   * @since 1.0.0
   */
  public static final int ERROR_CODE_NONE = 0;

  /**
   * The regex pattern for matching the resulting error message provided by the API when an Ewon is
   * unavailable.
   *
   * @since 1.0.0
   */
  private static final String UNAVAILABLE_MESSAGE_REGEX = "Device \\[.*] is currently unavailable";

  /**
   * The regex pattern for matching the resulting error message provided by the API when the Ewon
   * credentials are incorrect.
   *
   * @since 1.0.0
   */
  private static final String INCORRECT_CREDENTIALS_MESSAGE_REGEX =
      "Invalid credentials for device \\[.*]";

  /**
   * The regex pattern for matching the resulting error message provided by the API when it times
   * out while reaching the Ewon.
   *
   * @since 1.0.0
   */
  private static final String TIMEOUT_REACHING_DEVICE_MESSAGE_REGEX =
      "Timeout while reaching device \\[.*].*";

  /**
   * The regex pattern for matching the resulting error message provided by the API when it is
   * unable to reach the Ewon.
   *
   * @since 1.0.0
   */
  private static final String UNABLE_TO_REACH_DEVICE_MESSAGE_REGEX =
      "Unable to reach device \\[.*] error code \\[(.*)]";

  /**
   * The regex pattern for matching the resulting error message provided by the API when it is
   * unable to reach the Ewon.
   *
   * @since 1.0.0
   */
  private static final int UNABLE_TO_REACH_DEVICE_MESSAGE_REGEX_GROUP_INDEX_ERROR_CODE = 1;

  /**
   * The resulting error code provided by the API when an Ewon is unavailable.
   *
   * @since 1.0.0
   */
  private static final int UNAVAILABLE_CODE = 502;

  /**
   * The resulting error code provided by the API when the Ewon credentials are incorrect.
   *
   * @since 1.0.0
   */
  private static final int INCORRECT_CREDENTIALS_CODE = 401;

  /**
   * The resulting error code provided by the API when it times out while reaching the Ewon.
   *
   * @since 1.0.0
   */
  private static final int TIMEOUT_REACHING_DEVICE_CODE = 502;

  /**
   * The resulting error code provided by the API when it is unable to reach the Ewon.
   *
   * @since 1.0.0
   */
  private static final int UNABLE_TO_REACH_DEVICE_CODE = 502;

  /**
   * The resulting success flag provided by the API.
   *
   * @apiNote If the request was successful, this value will be true. If the request was
   *     unsuccessful, this value will be false.
   * @since 1.0.0
   */
  protected boolean success = true;

  /**
   * The resulting error message provided by the API.
   *
   * @apiNote This value is not populated for a successful response. If the request was successful,
   *     the appropriate response fields will be populated and this value will be null. If the
   *     request was unsuccessful, this value will be populated with the error message.
   * @since 1.0.0
   */
  private String message;

  /**
   * The resulting error code provided by the API.
   *
   * @apiNote This value is not populated for a successful response. If the request was successful,
   *     the appropriate response fields will be populated and this value will be 0. If the request
   *     was unsuccessful, this value will be populated with the error code.
   * @since 1.0.0
   */
  private int code;

  /**
   * Gets the success flag provided by the API.
   *
   * @apiNote If the request was successful, this value will be true. If the request was
   *     unsuccessful, this value will be false.
   * @return success flag
   * @since 1.0.0
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets the error message provided by the API.
   *
   * @apiNote This value is not populated for a successful response. If the request was successful,
   *     the appropriate response fields will be populated and this value will be null. If the
   *     request was unsuccessful, this value will be populated with the error message.
   * @return error message
   * @since 1.0.0
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets the error code provided by the API.
   *
   * @apiNote This value is not populated for a successful response. If the request was successful,
   *     * the appropriate response fields will be populated and this value will be 0. If the
   *     request * was unsuccessful, this value will be populated with the error code.
   * @return error code
   * @since 1.0.0
   */
  public int getCode() {
    return code;
  }

  /**
   * Checks if the response indicates that the Ewon is unavailable.
   *
   * @return {@code true} if the Ewon is unavailable, {@code false} otherwise
   * @since 1.0.0
   */
  public boolean isUnavailable() {
    return code == UNAVAILABLE_CODE && message.matches(UNAVAILABLE_MESSAGE_REGEX);
  }

  /**
   * Checks if the response indicates that the Ewon credentials are incorrect.
   *
   * @return {@code true} if the Ewon credentials are incorrect, {@code false} otherwise
   * @since 1.0.0
   */
  public boolean areEwonCredentialsIncorrect() {
    return code == INCORRECT_CREDENTIALS_CODE
        && message.matches(INCORRECT_CREDENTIALS_MESSAGE_REGEX);
  }

  /**
   * Checks if the response indicates that the API timed out while reaching the Ewon.
   *
   * @return {@code true} if the API timed out while reaching the Ewon, {@code false} otherwise
   * @since 1.0.0
   */
  public boolean didTimeoutReachingDevice() {
    return code == TIMEOUT_REACHING_DEVICE_CODE
        && message.matches(TIMEOUT_REACHING_DEVICE_MESSAGE_REGEX);
  }

  /**
   * Checks if the response indicates that the API was unable to reach the Ewon and returns the
   * error code. If the response does not indicate that the API was unable to reach the Ewon, this
   * method will return 0.
   *
   * @return the error code if the API was unable to reach the Ewon, 0 otherwise
   * @since 1.0.0
   */
  public int wasUnableToReachDevice() {
    int errorCode = ERROR_CODE_NONE;

    // Get capture group 1 (error code) from the message if code matches and message matches regex
    Pattern pattern = Pattern.compile(UNABLE_TO_REACH_DEVICE_MESSAGE_REGEX);
    Matcher matcher = pattern.matcher(message);
    if (code == UNABLE_TO_REACH_DEVICE_CODE && matcher.find()) {
      errorCode =
          Integer.parseInt(
              matcher.group(UNABLE_TO_REACH_DEVICE_MESSAGE_REGEX_GROUP_INDEX_ERROR_CODE));
    }

    return errorCode;
  }

  /**
   * Gets an instance of {@link M2WebEwonEBDResponse} from a JSON string.
   *
   * @param json The JSON string to parse.
   * @return An instance of {@link M2WebEwonEBDResponse} parsed from the JSON string.
   * @throws JsonSyntaxException if the JSON string is not valid or does not match the {@link
   *     M2WebEwonEBDResponse} class.
   * @since 1.0.0
   */
  public static <T extends M2WebEwonEBDResponse> T getFromJson(String json, Class<T> clazz)
      throws JsonSyntaxException {
    return new Gson().fromJson(json, clazz);
  }
}

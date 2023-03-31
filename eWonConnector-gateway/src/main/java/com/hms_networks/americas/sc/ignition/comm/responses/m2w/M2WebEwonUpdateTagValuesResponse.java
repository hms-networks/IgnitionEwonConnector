package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Object for an M2Web Ewon update tag values response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwonUpdateTagValuesResponse {

  /**
   * The string value used when an error table cell value returned by the M2Web server cannot be
   * parsed, or when the error table cell value is not recognized.
   *
   * @since 1.0.0
   */
  private static final String UNKNOWN_ERROR_STRING = "Unknown error";

  /**
   * The table data cell value returned by the M2Web server when a tag update request is successful.
   *
   * @since 1.0.0
   */
  private static final String SUCCESS_STRING = "<td>Done</td>";

  /**
   * The regex string used when Javascript is enabled to match and parse an error table cell value
   * returned by the M2Web server when a tag update request fails.
   *
   * @since 1.0.0
   */
  private static final String ERROR_TABLE_CELL_REGEX_JS_ENABLED = "ERROR:\\s</span>(.*)<p\\s";

  /**
   * The regex string used when Javascript is disabled to match and parse an error table cell value
   * returned by the M2Web server when a tag update request fails.
   *
   * @since 1.0.0
   */
  private static final String ERROR_TABLE_CELL_REGEX_JS_DISABLED = "ERROR:\\s</span>(.*)</td>";

  /**
   * The regex group index for an error table cell value when matched using the regex patterns
   * specified by {@link #ERROR_TABLE_CELL_REGEX_JS_ENABLED} and {@link
   * #ERROR_TABLE_CELL_REGEX_JS_DISABLED}.
   *
   * @since 1.0.0
   */
  private static final int ERROR_TABLE_CELL_REGEX_GROUP = 1;

  /**
   * The success status of the Ewon update tag values request.
   *
   * @since 1.0.0
   */
  private final boolean success;

  /**
   * The error text returned by the M2Web server when a tag update request fails.
   *
   * @since 1.0.0
   */
  private final String errorText;

  /**
   * Private constructor for an M2Web Ewon update tag values response.
   *
   * @param success The success status of the Ewon update tag values request.
   * @param errorText The error text returned by the M2Web server when a tag update request fails.
   * @since 1.0.0
   */
  private M2WebEwonUpdateTagValuesResponse(boolean success, String errorText) {
    this.success = success;
    this.errorText = errorText;
  }

  /**
   * Gets the success status of the Ewon update tag values request.
   *
   * @return The success status of the Ewon update tag values request.
   * @since 1.0.0
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets the error text returned by the M2Web server when a tag update request fails.
   *
   * @return The error text returned by the M2Web server when a tag update request fails.
   * @since 1.0.0
   */
  public String getErrorText() {
    return errorText;
  }

  /**
   * Gets an instance of {@link M2WebEwonUpdateTagValuesResponse} from a response string.
   *
   * @param responseString The response string to parse.
   * @return An instance of {@link M2WebEwonUpdateTagValuesResponse} parsed from the response
   *     string.
   * @since 1.0.0
   */
  public static M2WebEwonUpdateTagValuesResponse getFromString(String responseString) {
    // Check for success string
    boolean responseContainsSuccess = responseString.contains(SUCCESS_STRING);

    // If response does not contain success string, check for error string
    String errorString = null;
    if (!responseContainsSuccess) {
      // Check for error string with Javascript enabled
      Matcher errorTableCellRegexJsEnabledMatcher =
          Pattern.compile(ERROR_TABLE_CELL_REGEX_JS_ENABLED).matcher(responseString);
      Matcher errorTableCellRegexJsDisabledMatcher =
          Pattern.compile(ERROR_TABLE_CELL_REGEX_JS_DISABLED).matcher(responseString);
      if (errorTableCellRegexJsEnabledMatcher.matches()) {
        errorString = errorTableCellRegexJsEnabledMatcher.group(ERROR_TABLE_CELL_REGEX_GROUP);
      } else if (errorTableCellRegexJsDisabledMatcher.matches()) {
        errorString = errorTableCellRegexJsDisabledMatcher.group(ERROR_TABLE_CELL_REGEX_GROUP);
      } else {
        errorString = UNKNOWN_ERROR_STRING;
      }
    }

    return new M2WebEwonUpdateTagValuesResponse(responseContainsSuccess, errorString);
  }
}

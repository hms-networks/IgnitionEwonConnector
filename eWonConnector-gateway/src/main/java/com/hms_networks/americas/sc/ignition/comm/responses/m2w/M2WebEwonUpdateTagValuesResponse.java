package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Object for an M2Web Ewon update tag values response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 1.0.0
 */
public class M2WebEwonUpdateTagValuesResponse {

  /**
   * The table data cell value returned by the M2Web server when a tag update request is successful.
   */
  private static final String SUCCESS_STRING = "<td>Done</td>";

  /**
   * The regex string used when Javascript is enabled to match and parse an error table cell value
   * returned by the M2Web server when a tag update request fails.
   */
  private static final String ERROR_TABLE_CELL_REGEX_JS_ENABLED = "ERROR:\\s</span>(.*)<p\\s";

  /**
   * The regex string used when Javascript is disabled to match and parse an error table cell value
   * returned by the M2Web server when a tag update request fails.
   */
  private static final String ERROR_TABLE_CELL_REGEX_JS_DISABLED = "ERROR:\\s</span>(.*)</td>";

  /**
   * The regex group index for an error table cell value when matched using the regex patterns
   * specified by {@link #ERROR_TABLE_CELL_REGEX_JS_ENABLED} and {@link
   * #ERROR_TABLE_CELL_REGEX_JS_DISABLED}.
   */
  private static final int ERROR_TABLE_CELL_REGEX_GROUP = 1;

  /** The success status of the Ewon update tag values request. */
  private final boolean success;

  /** The error text returned by the M2Web server when a tag update request fails. */
  private final String errorText;

  private M2WebEwonUpdateTagValuesResponse(boolean success, String errorText) {
    this.success = success;
    this.errorText = errorText;
  }

  /**
   * Gets the success status of the Ewon update tag values request.
   *
   * @return The success status of the Ewon update tag values request.
   */
  public boolean getSuccess() {
    return success;
  }

  /**
   * Gets the error text returned by the M2Web server when a tag update request fails.
   *
   * @return The error text returned by the M2Web server when a tag update request fails.
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
      }
    }

    return new M2WebEwonUpdateTagValuesResponse(responseContainsSuccess, errorString);
  }
}

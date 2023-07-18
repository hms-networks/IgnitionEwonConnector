package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;

import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * JSON object for an Ewon tag history entry in a DMWeb API response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebEwonTagHistoryEntry {

  /**
   * The fallback/default quality for a history entry. The DMWeb API documentation indicates that
   * the quality field is optional, and if not present, the quality is assumed to be good.
   */
  private static final String FALLBACK_DEFAULT_QUALITY = "good";

  /**
   * The timestamp of the history entry.
   *
   * @since 1.0.0
   */
  private String date;

  /**
   * The quality of the history entry.
   *
   * @since 1.0.0
   */
  private String quality;

  /**
   * The value of the history entry.
   *
   * @since 1.0.0
   */
  private Object value;

  /**
   * Gets the timestamp of the history entry.
   *
   * @return The timestamp of the history entry.
   * @throws DateTimeParseException if the date string is not valid
   * @since 1.0.0
   */
  public Date getDate() throws DateTimeParseException {
    return Talk2MResponse.toDate(date);
  }

  /**
   * Gets the quality of the history entry.
   *
   * @return The quality of the history entry.
   * @since 1.0.0
   */
  public String getQuality() {
    return quality != null ? quality : FALLBACK_DEFAULT_QUALITY;
  }

  /**
   * Gets the value of the history entry.
   *
   * @return The value of the history entry.
   * @since 1.0.0
   */
  public Object getValue() {
    return value;
  }
}

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
   * The timestamp of the history entry.
   *
   * @since 1.0.0
   */
  private String date;

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
   * Gets the value of the history entry.
   *
   * @return The value of the history entry.
   * @since 1.0.0
   */
  public Object getValue() {
    return value;
  }
}

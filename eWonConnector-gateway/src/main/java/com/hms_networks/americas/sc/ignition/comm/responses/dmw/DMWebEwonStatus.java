package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import com.hms_networks.americas.sc.ignition.comm.responses.Talk2MResponse;

import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * JSON object for an Ewon in an DMWeb API response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebEwonStatus {

  /**
   * The ID of the Ewon.
   *
   * @since 1.0.0
   */
  private int id;

  /**
   * The name of the Ewon.
   *
   * @since 1.0.0
   */
  private String name;

  /**
   * The number of history points for the Ewon currently stored in the DataMailbox.
   *
   * @since 1.0.0
   */
  private int historyCount;

  /**
   * The date of the first history point for the Ewon currently stored in the DataMailbox.
   *
   * @since 1.0.0
   */
  private String firstHistoryDate;

  /**
   * The date of the last history point for the Ewon currently stored in the DataMailbox.
   *
   * @since 1.0.0
   */
  private String lastHistoryDate;

  /**
   * Gets the ID of the Ewon.
   *
   * @return The ID of the Ewon.
   * @since 1.0.0
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the Ewon.
   *
   * @return The name of the Ewon.
   * @since 1.0.0
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the number of history points for the Ewon currently stored in the DataMailbox.
   *
   * @return The number of history points for the Ewon currently stored in the DataMailbox.
   * @since 1.0.0
   */
  public int getHistoryCount() {
    return historyCount;
  }

  /**
   * Gets the date of the first history point for the Ewon currently stored in the DataMailbox.
   *
   * @return The date of the first history point for the Ewon currently stored in the DataMailbox.
   * @since 1.0.0
   */
  public Date getFirstHistoryDate() {
    return Talk2MResponse.toDate(firstHistoryDate);
  }

  /**
   * Gets the date of the last history point for the Ewon currently stored in the DataMailbox.
   *
   * @return The date of the last history point for the Ewon currently stored in the DataMailbox.
   * @throws DateTimeParseException if the date string is not valid
   * @since 1.0.0
   */
  public Date getLastHistoryDate() throws DateTimeParseException {
    return Talk2MResponse.toDate(lastHistoryDate);
  }
}

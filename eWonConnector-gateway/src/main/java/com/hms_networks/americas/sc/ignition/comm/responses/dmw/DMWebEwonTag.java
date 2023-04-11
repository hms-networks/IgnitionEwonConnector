package com.hms_networks.americas.sc.ignition.comm.responses.dmw;

import java.util.List;

/**
 * JSON object for an Ewon tag in an DMWeb API response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebEwonTag {

  /**
   * The ID of the Ewon tag.
   *
   * @since 1.0.0
   */
  private int id;

  /**
   * The name of the Ewon tag.
   *
   * @since 1.0.0
   */
  private String name;

  /**
   * The data type of the Ewon tag.
   *
   * @since 1.0.0
   */
  private String dataType;

  /**
   * The description of the Ewon tag.
   *
   * @since 1.0.0
   */
  private String description;

  /**
   * The alarm hint of the Ewon tag.
   *
   * @since 1.0.0
   */
  private String alarmHint;

  /**
   * The string value of the Ewon tag. This field is only populated when the tag data type
   * corresponds.
   *
   * @since 1.0.0
   */
  private Object value;

  /**
   * The quality of the Ewon tag value.
   *
   * @since 1.0.0
   */
  private String quality;

  /**
   * The Ewon tag ID of the Ewon tag.
   *
   * @since 1.0.0
   */
  private int ewonTagId;

  /**
   * The historical values of the Ewon tag. This field is only present in {@link
   * DMWebGetDataResponse}s.
   *
   * @since 1.0.0
   */
  private List<DMWebEwonTagHistoryEntry> history;

  /**
   * Gets the ID of the Ewon tag.
   *
   * @return The ID of the Ewon tag.
   * @since 1.0.0
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the name of the Ewon tag.
   *
   * @return The name of the Ewon tag.
   * @since 1.0.0
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the data type of the Ewon tag.
   *
   * @return The data type of the Ewon tag.
   * @since 1.0.0
   */
  public String getDataType() {
    return dataType;
  }

  /**
   * Gets the description of the Ewon tag.
   *
   * @return The description of the Ewon tag.
   * @since 1.0.0
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the alarm hint of the Ewon tag.
   *
   * @return The alarm hint of the Ewon tag.
   * @since 1.0.0
   */
  public String getAlarmHint() {
    return alarmHint;
  }

  /**
   * Gets the float value of the Ewon tag.
   *
   * @return The float value of the Ewon tag.
   * @since 1.0.0
   */
  public float getValueFloat() {
    return (Float) value;
  }

  /**
   * Gets the integer value of the Ewon tag.
   *
   * @return The integer value of the Ewon tag.
   * @since 1.0.0
   */
  public int getValueInt() {
    return (Integer) value;
  }

  /**
   * Gets the boolean value of the Ewon tag.
   *
   * @return The boolean value of the Ewon tag.
   * @since 1.0.0
   */
  public boolean getValueBool() {
    return (Boolean) value;
  }

  /**
   * Gets the string value of the Ewon tag.
   *
   * @return The string value of the Ewon tag.
   * @since 1.0.0
   */
  public String getValueString() {
    return (String) value;
  }

  /**
   * Gets the quality of the Ewon tag value.
   *
   * @return The quality of the Ewon tag value.
   * @since 1.0.0
   */
  public String getQuality() {
    return quality;
  }

  /**
   * Gets the Ewon tag ID of the Ewon tag.
   *
   * @return The Ewon tag ID of the Ewon tag.
   * @since 1.0.0
   */
  public int getEwonTagId() {
    return ewonTagId;
  }

  /**
   * Gets the historical values of the Ewon tag. This field is only present in {@link
   * DMWebGetDataResponse}s.
   *
   * @return The historical values of the Ewon tag.
   * @since 1.0.0
   */
  public List<DMWebEwonTagHistoryEntry> getHistory() {
    return history;
  }
}

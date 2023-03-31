package com.hms_networks.americas.sc.ignition.comm.responses.m2w;

import com.opencsv.bean.CsvBindByName;

/**
 * CSV object for an instant value in the M2Web EBD instant value response.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwonEBDInstantValue {

  /**
   * The value of the {@code TagId} column for a tag in the M2Web EBD instant value response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "TagId", required = true)
  private int tagId;

  /**
   * The value of the {@code TagName} column for a tag in the M2Web EBD instant value response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "TagName", required = true)
  private String tagName;

  /**
   * The value of the {@code Value} column for a tag in the M2Web EBD instant value response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Value", required = true)
  private String value;

  /**
   * The value of the {@code AlStatus} column for a tag in the M2Web EBD instant value response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlStatus", required = true)
  private int alStatus;

  /**
   * The value of the {@code AlType} column for a tag in the M2Web EBD instant value response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "AlType", required = true)
  private int alType;

  /**
   * The value of the {@code Quality} column for a tag in the M2Web EBD instant value response.
   *
   * @since 1.0.0
   */
  @CsvBindByName(column = "Quality", required = true)
  private int quality;

  /**
   * Gets the value of the {@code TagId} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code TagId} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getTagId() {
    return tagId;
  }

  /**
   * Gets the value of the {@code TagName} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code TagName} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getTagName() {
    return tagName;
  }

  /**
   * Gets the value of the {@code Value} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Value} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public String getValue() {
    return value;
  }

  /**
   * Gets the value of the {@code AlStatus} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlStatus} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlStatus() {
    return alStatus;
  }

  /**
   * Gets the value of the {@code AlType} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code AlType} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getAlType() {
    return alType;
  }

  /**
   * Gets the value of the {@code Quality} column for a tag in the M2Web EBD tag list response.
   *
   * @return The value of the {@code Quality} column for a tag in the M2Web EBD tag list response.
   * @since 1.0.0
   */
  public int getQuality() {
    return quality;
  }
}

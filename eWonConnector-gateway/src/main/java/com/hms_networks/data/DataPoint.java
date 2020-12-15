package com.hms_networks.data;

import com.inductiveautomation.ignition.common.model.values.BasicQualifiedValue;
import com.inductiveautomation.ignition.common.model.values.QualifiedValue;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataQuality;
import com.hms_networks.EwonUtil;

/** Object representing and containing data point information */
public class DataPoint {
  /** Data point date */
  String date;

  /** Data point quality */
  String quality;

  /** Data point value */
  Object value;

  /**
   * Get data point value
   *
   * @return data point value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Get data point date
   *
   * @return data point date
   */
  public String getDate() {
    return date;
  }

  /**
   * Get data point quality
   *
   * @return data point quality
   */
  public String getQuality() {
    return quality == null ? "good" : quality;
  }

  /**
   * Create and return qualified value from this data point
   *
   * @return qualified value
   */
  public QualifiedValue toQVal() {
    return new BasicQualifiedValue(
        value,
        "good".equals(getQuality()) ? DataQuality.GOOD_DATA : DataQuality.OPC_BAD_DATA,
        EwonUtil.toDate(date));
  }
}

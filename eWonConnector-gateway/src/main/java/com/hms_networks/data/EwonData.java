package com.hms_networks.data;

import java.util.Date;
import com.hms_networks.EwonUtil;

/** Object representing and containing information for an Ewon */
public class EwonData extends DMResult {
  /** Ewon identifier */
  int id;

  /** Ewon name */
  String name;

  /** Ewon last synchronization timestamp */
  String lastSynchroDate;

  /** Array of Ewon tags */
  Tag[] tags;

  /**
   * Get Ewon identifier
   *
   * @return Ewon identifier
   */
  public int getId() {
    return id;
  }

  /**
   * Get Ewon name
   *
   * @return Ewon name
   */
  public String getName() {
    return name;
  }

  /**
   * Get Ewon last synchronization timestamp as String
   *
   * @return Ewon last synchronization timestamp
   */
  public String getLastSync() {
    return lastSynchroDate;
  }

  /**
   * Get Ewon last synchronization timestamp as Date object
   *
   * @return Ewon last synchronization timestamp
   */
  public Date getLastSync_Date() {
    return EwonUtil.toDate(getLastSync());
  }

  /**
   * Get array of Ewon tags
   *
   * @return array of Ewon tags
   */
  public Tag[] getTags() {
    return tags;
  }

  /**
   * Create and return string representation of Ewon data
   *
   * @return Ewon data as string
   */
  @Override
  public String toString() {
    return String.format("%s[id=%d, lastSync=%s]", name, id, lastSynchroDate);
  }
}

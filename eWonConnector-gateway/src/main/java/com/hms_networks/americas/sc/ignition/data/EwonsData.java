package com.hms_networks.americas.sc.ignition.data;

/** Object to store multiple Ewons' data */
public class EwonsData extends DMResult {
  /** Array of every Ewon's data */
  EwonData[] ewons;

  /** Boolean if more data is available */
  boolean moreDataAvailable = false;

  /** Transaction identifier */
  final Long transactionId = 0L;

  /**
   * Get array of every Ewon's data
   *
   * @return array of every Ewon's data
   */
  public EwonData[] getEwons() {
    return ewons;
  }

  /**
   * Get if more data is available
   *
   * @return true/false if more data available
   */
  public boolean isMoreDataAvailable() {
    return moreDataAvailable;
  }

  /**
   * Get transaction identifier
   *
   * @return
   */
  public Long getTransactionId() {
    return transactionId;
  }
}

package com.hms_networks.americas.sc.ignition.data;

import com.inductiveautomation.ignition.common.model.values.QualityCode;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataQuality;

/**
 * Class to represent an Ewon tag quality and provide access to the necessary information bits.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class EwonTagQuality {

  /**
   * The string representation of 'good' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final String DMWEB_QUALITY_GOOD_STRING = "good";

  /**
   * The string representation of 'bad' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final String DMWEB_QUALITY_BAD_STRING = "bad";

  /**
   * The string representation of 'uncertain' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final String DMWEB_QUALITY_UNCERTAIN_STRING = "uncertain";

  /**
   * The string representation of 'initialGood' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final String DMWEB_QUALITY_INITIAL_GOOD_STRING = "initialGood";

  /**
   * The string representation of 'unknown' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final String DMWEB_QUALITY_UNKNOWN_STRING = "unknown";

  /**
   * The integer representation of 'good' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final int DMWEB_QUALITY_GOOD = 0;

  /**
   * The integer representation of 'bad' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final int DMWEB_QUALITY_BAD = 1;

  /**
   * The integer representation of 'uncertain' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final int DMWEB_QUALITY_UNCERTAIN = 2;

  /**
   * The integer representation of 'initialGood' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final int DMWEB_QUALITY_INITIAL_GOOD = 3;

  /**
   * The integer representation of 'unknown' tag quality from the DMWeb API.
   *
   * @since 1.0.0
   */
  private static final int DMWEB_QUALITY_UNKNOWN = 4;

  /**
   * The 16-bit value containing the Ewon tag quality and its information bits.
   *
   * <ul>
   *   <li>Bits 15-8 are vendor-specific quality information
   *   <li>Bits 7-6 represent the major quality (bad/uncertain/good)
   *   <li>Bits 5-2 represent the sub-status
   *   <li>Bits 1-0 represent the limit status
   * </ul>
   *
   * <br>
   *
   * <p>For the vendor-specific information, the following formula is applied: TagQuality =
   * (PreviousQuality+255*SuccessOfLastRead) / 2, where
   *
   * <ul>
   *   <li>TagQuality represents the new quality value
   *   <li>PreviousQuality represents the previous value of TagQuality
   *   <li>SuccessOfLastRead is 1 when the last read operation succeeded and 0 otherwise
   * </ul>
   *
   * <br>
   *
   * <p>The major quality can have one of the following values:
   *
   * <ul>
   *   <li>Good (3) : when the last reading happened without problem.
   *   <li>Bad (0) : when the calculated quality is lower than 32, meaning that at least the three
   *       last readings failed or that there is a problem with the configuration of the tag or the
   *       IO server. Details about this situation can be retrieved from the quality sub-status
   *       bits.
   *   <li>Uncertain (1) : when the last reading failed, but that the calculated quality is above
   *       34, meaning that at least one of the last three readings succeeded. In this case, you can
   *       decide whether to trust the received value or not.
   * </ul>
   *
   * <br>
   *
   * <p>The quality sub-status can have one of the following values:
   *
   * <ul>
   *   <li>Non-specific (0) : means that no additional information than the major quality is
   *       provided. This will be returned when the major quality is Good.
   *   <li>Configuration Error (1) : indicates a configuration error (tag or IO server)
   *   <li>Device Failure (3) : means that the device returned an error
   *   <li>Comm Failure (6) : means that the device did not reply
   *   <li>Out of Service (7) : means that the IO server or tag was disabled.
   * </ul>
   *
   * <p>The limit status is currently not used by the Ewon.
   *
   * @since 1.0.0
   */
  private final int quality;

  /**
   * Creates a new {@link EwonTagQuality} instance with the specified quality integer.
   *
   * @param quality the Ewon tag quality integer
   * @since 1.0.0
   */
  public EwonTagQuality(int quality) {
    this.quality = quality;
  }

  /**
   * Creates a new {@link EwonTagQuality} instance with the specified quality string.
   *
   * @param quality the Ewon tag quality string
   * @throws IllegalArgumentException if the specified quality string is {@code null} or invalid
   * @since 1.0.0
   */
  public EwonTagQuality(String quality) {
    // Convert quality string to integer
    int qualityInt;
    if (quality == null) {
      throw new IllegalArgumentException("Quality cannot be null");
    } else if (quality.equalsIgnoreCase(DMWEB_QUALITY_GOOD_STRING)) {
      qualityInt = DMWEB_QUALITY_GOOD;
    } else if (quality.equalsIgnoreCase(DMWEB_QUALITY_BAD_STRING)) {
      qualityInt = DMWEB_QUALITY_BAD;
    } else if (quality.equalsIgnoreCase(DMWEB_QUALITY_UNCERTAIN_STRING)) {
      qualityInt = DMWEB_QUALITY_UNCERTAIN;
    } else if (quality.equalsIgnoreCase(DMWEB_QUALITY_INITIAL_GOOD_STRING)) {
      qualityInt = DMWEB_QUALITY_INITIAL_GOOD;
    } else if (quality.equalsIgnoreCase(DMWEB_QUALITY_UNKNOWN_STRING)) {
      qualityInt = DMWEB_QUALITY_UNKNOWN;
    } else {
      throw new IllegalArgumentException("Invalid quality string");
    }

    // Set quality integer
    this.quality = qualityInt;
  }

  /**
   * Returns the Ewon tag quality.
   *
   * @return the Ewon tag quality
   * @since 1.0.0
   */
  public int getQuality() {
    return quality;
  }

  /**
   * Returns the vendor-specific quality information of the Ewon tag quality.
   *
   * @return the vendor-specific quality information of the Ewon tag quality
   * @since 1.0.0
   */
  public int getVendorSpecificQualityInformation() {
    return (quality >> 8) & 0xFF;
  }

  /**
   * Returns the major quality of the Ewon tag quality.
   *
   * @return the major quality of the Ewon tag quality
   * @since 1.0.0
   */
  public int getMajorQuality() {
    return (quality >> 6) & 0x03;
  }

  /**
   * Returns the sub-status of the Ewon tag quality.
   *
   * @return the sub-status of the Ewon tag quality
   * @since 1.0.0
   */
  public int getSubStatus() {
    return (quality >> 2) & 0x0F;
  }

  /**
   * Returns the limit status of the Ewon tag quality.
   *
   * @return the limit status of the Ewon tag quality
   * @since 1.0.0
   */
  public int getLimitStatus() {
    return quality & 0x03;
  }

  /**
   * Returns the Ewon tag quality as an Ignition {@link QualityCode} object.
   *
   * @return the Ewon tag quality as an Ignition {@link QualityCode} object
   * @since 1.0.0
   */
  public QualityCode getQualityCode() {
    QualityCode result;
    switch (getMajorQuality()) {
      case 0:
        result = QualityCode.Bad;
        break;
      case 1:
        result = QualityCode.Uncertain;
        break;
      case 3:
        result = QualityCode.Good;
        break;
      default:
        result = QualityCode.Bad_OutOfRange;
        break;
    }

    // If quality is not good, check sub-status
    if (result != QualityCode.Good) {
      if (getSubStatus() == 1) {
        result = QualityCode.Error_Configuration;
      } else if (getSubStatus() == 3) {
        result = QualityCode.Bad_Failure;
      } else if (getSubStatus() == 6) {
        result = QualityCode.Error_TimeoutExpired;
      } else if (getSubStatus() == 7) {
        result = QualityCode.Bad_Disabled;
      }
    }

    return result;
  }

  /**
   * Returns the Ewon tag quality as an Ignition {@link DataQuality} object.
   *
   * @return the Ewon tag quality as an Ignition {@link DataQuality} object
   * @see #getQualityCode()
   * @since 1.0.0
   */
  public DataQuality getDataQuality() {
    return DataQuality.getQualityFor(getQualityCode());
  }
}

package com.hms_networks.americas.sc.ignition.data;

import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;

/**
 * Class to represent Ewon tag type as an enumeration.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 * @apiNote This class is an adaption of the {@code
 *     com.hms_networks.americas.sc.extensions.taginfo.TagType} class from the {@code
 *     sc-ewon-flexy-extensions-lib} library.
 */
public enum EwonTagType {

  /**
   * Ewon tag type for boolean.
   *
   * @since 1.0.0
   */
  BOOLEAN,

  /**
   * Ewon tag type for floating point.
   *
   * @since 1.0.0
   */
  FLOAT,

  /**
   * Ewon tag type for integer.
   *
   * @since 1.0.0
   */
  INTEGER,

  /**
   * Ewon tag type for dword.
   *
   * @since 1.0.0
   */
  DWORD,

  /**
   * Ewon tag type for string.
   *
   * @since 1.0.0
   */
  STRING;

  /**
   * Ewon integer value for boolean type.
   *
   * @since 1.0.0
   */
  public static final int BOOLEAN_INT = 0;

  /**
   * Ewon integer value for floating point type.
   *
   * @since 1.0.0
   */
  public static final int FLOAT_INT = 1;

  /**
   * Ewon integer value for integer type.
   *
   * @since 1.0.0
   */
  public static final int INTEGER_INT = 2;

  /**
   * Ewon integer value for DWORD type.
   *
   * @since 1.0.0
   */
  public static final int DWORD_INT = 3;

  /**
   * Ewon integer value for string type.
   *
   * @since 1.0.0
   */
  public static final int STRING_INT = 6;

  /**
   * Ewon string value for boolean type.
   *
   * @since 1.0.0
   */
  public static final String BOOLEAN_STRING = "bool";

  /**
   * Ewon string value for floating point type.
   *
   * @since 1.0.0
   */
  public static final String FLOAT_STRING = "float";

  /**
   * Ewon string value for integer type.
   *
   * @since 1.0.0
   */
  public static final String INTEGER_STRING = "int";

  /**
   * Ewon string value for DWORD type.
   *
   * @since 1.0.0
   */
  public static final String DWORD_STRING = "dword";

  /**
   * Ewon string value for DWORD type from DataMailbox.
   *
   * @since 1.0.0
   */
  public static final String DWORD_STRING_DM = "uint";

  /**
   * Ewon string value for string type.
   *
   * @since 1.0.0
   */
  public static final String STRING_STRING = "string";

  /**
   * Helper method to get the equivalent {@link EwonTagType} enum for the supplied Ewon tag type
   * integer. If the specified tag type integer is not valid, null will be returned.
   *
   * @param tagType Ewon tag type integer
   * @return the {@link EwonTagType} for specified int
   * @throws IllegalArgumentException if the specified tag type int is invalid
   * @since 1.0.0
   */
  public static EwonTagType getTagTypeFromInt(int tagType) {
    EwonTagType ewonTagType;
    if (tagType == BOOLEAN_INT) {
      ewonTagType = BOOLEAN;
    } else if (tagType == FLOAT_INT) {
      ewonTagType = FLOAT;
    } else if (tagType == INTEGER_INT) {
      ewonTagType = INTEGER;
    } else if (tagType == DWORD_INT) {
      ewonTagType = DWORD;
    } else if (tagType == STRING_INT) {
      ewonTagType = STRING;
    } else {
      throw new IllegalArgumentException("Invalid tag type int");
    }
    return ewonTagType;
  }

  /**
   * Helper method to get the equivalent {@link EwonTagType} enum for the supplied Ewon tag type
   * string. If the specified tag type string is not valid, null will be returned.
   *
   * @param tagType Ewon tag type string
   * @return the {@link EwonTagType} for specified string
   * @apiNote This method is case-insensitive.
   * @throws IllegalArgumentException if the specified tag type string is {@code null} or invalid
   * @since 1.0.0
   */
  public static EwonTagType getTagTypeFromString(String tagType) {
    EwonTagType ewonTagType;
    if (tagType == null) {
      throw new IllegalArgumentException("Tag type cannot be null");
    } else if (tagType.equalsIgnoreCase(BOOLEAN_STRING)) {
      ewonTagType = BOOLEAN;
    } else if (tagType.equalsIgnoreCase(FLOAT_STRING)) {
      ewonTagType = FLOAT;
    } else if (tagType.equalsIgnoreCase(INTEGER_STRING)) {
      ewonTagType = INTEGER;
    } else if (tagType.equalsIgnoreCase(DWORD_STRING)) {
      ewonTagType = DWORD;
    } else if (tagType.equalsIgnoreCase(DWORD_STRING_DM)) {
      ewonTagType = DWORD;
    } else if (tagType.equalsIgnoreCase(STRING_STRING)) {
      ewonTagType = STRING;
    } else {
      throw new IllegalArgumentException("Invalid tag type string" + tagType);
    }
    return ewonTagType;
  }

  /**
   * Gets the equivalent {@link DataType} for the {@link EwonTagType}.
   *
   * @return the equivalent {@link DataType}
   * @throws IllegalArgumentException if the tag type enum is unknown
   * @since 1.0.0
   */
  public DataType getIgnitionDataType() {
    DataType dataType;
    if (this == EwonTagType.BOOLEAN) {
      dataType = DataType.Boolean;
    } else if (this == EwonTagType.FLOAT) {
      dataType = DataType.Float8;
    } else if (this == EwonTagType.INTEGER) {
      dataType = DataType.Int4;
    } else if (this == EwonTagType.DWORD) {
      dataType = DataType.Int8;
    } else if (this == EwonTagType.STRING) {
      dataType = DataType.String;
    } else {
      throw new IllegalArgumentException("Invalid tag type enum");
    }
    return dataType;
  }
}

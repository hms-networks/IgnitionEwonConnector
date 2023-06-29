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
   * @since 1.0.0
   */
  public static EwonTagType getTagTypeFromInt(int tagType) {
    switch (tagType) {
      case BOOLEAN_INT:
        return BOOLEAN;
      case FLOAT_INT:
        return FLOAT;
      case INTEGER_INT:
        return INTEGER;
      case DWORD_INT:
        return DWORD;
      case STRING_INT:
        return STRING;
      default:
        return null;
    }
  }

  /**
   * Helper method to get the equivalent {@link EwonTagType} enum for the supplied Ewon tag type
   * string. If the specified tag type string is not valid, null will be returned.
   *
   * @param tagType Ewon tag type string
   * @return the {@link EwonTagType} for specified string
   * @since 1.0.0
   */
  public static EwonTagType getTagTypeFromString(String tagType) {
    switch (tagType.toLowerCase()) {
      case BOOLEAN_STRING:
        return BOOLEAN;
      case FLOAT_STRING:
        return FLOAT;
      case INTEGER_STRING:
        return INTEGER;
      case DWORD_STRING:
        return DWORD;
      case STRING_STRING:
        return STRING;
      default:
        return null;
    }
  }

  /**
   * Gets the equivalent {@link DataType} for the {@link EwonTagType}.
   *
   * @return the equivalent {@link DataType}
   * @since 1.0.0
   */
  public DataType getIgnitionDataType() {
    switch (this) {
      case BOOLEAN:
        return DataType.Boolean;
      case FLOAT:
        return DataType.Float8;
      case INTEGER:
        return DataType.Int4;
      case DWORD:
        return DataType.Int8;
      case STRING:
        return DataType.String;
      default:
        return null;
    }
  }
}

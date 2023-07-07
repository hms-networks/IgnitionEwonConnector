package com.hms_networks.americas.sc.ignition.data;

import com.hms_networks.americas.sc.ignition.comm.responses.dmw.DMWebEwon;
import com.hms_networks.americas.sc.ignition.comm.responses.dmw.DMWebEwonTag;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwon;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonEBDTag;
import com.hms_networks.americas.sc.ignition.config.EwonConnectorSettings;
import com.inductiveautomation.ignition.common.sqltags.history.InterpolationMode;
import com.inductiveautomation.ignition.common.sqltags.model.TagPath;
import com.inductiveautomation.ignition.common.sqltags.model.types.*;
import com.inductiveautomation.ignition.common.sqltags.parser.TagPathParser;
import com.inductiveautomation.ignition.gateway.history.HistoricalTagValue;
import com.inductiveautomation.ignition.gateway.history.PackedHistoricalTagValue;
import java.util.Date;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for {@link TagManager} which provides a number of methods for managing tags and tag
 * values.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @see TagManager
 * @author HMS Networks, MU Americas Solution Center
 */
public class TagManagerUtilities {

  /**
   * Log handler for {@link TagManagerUtilities}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TagManagerUtilities.class);

  /**
   * The character to use when replacing illegal tag name characters during tag name sanitization.
   *
   * @since 1.0.0
   */
  public static final String ILLEGAL_TAG_NAME_CHARACTER_REPLACEMENT = "_";

  /**
   * Converts the specified {@link String} tag value to its corresponding {@link Object} class using
   * the specified {@link EwonTagType} value.
   *
   * @param value the {@link String} tag value to convert
   * @param tagType the {@link EwonTagType} of the tag value
   * @return the converted {@link Object} tag value
   * @since 1.0.0
   */
  public static Object getTagValueForType(Object value, EwonTagType tagType) {
    Object tagValue = null;
    switch (tagType) {
      case BOOLEAN:
        tagValue = Boolean.parseBoolean(value.toString());
        break;
      case INTEGER:
        tagValue = Integer.parseInt(value.toString());
        break;
      case FLOAT:
        tagValue = Float.parseFloat(value.toString());
        break;
      case DWORD:
        tagValue = Long.parseLong(value.toString());
        break;
      case STRING:
        tagValue = Objects.requireNonNullElse(value, "");
        break;
      default:
        LOGGER.error("Unknown tag type '{}'!", tagType);
        break;
    }
    return tagValue;
  }

  /**
   * Gets the corresponding tag provider tag name for the specified tag information, including the
   * Ewon name, Ewon tag name, tag group information, and system tag identifier. If configured, the
   * tag name will be sanitized to remove illegal characters.
   *
   * @param connectorSettings the {@link EwonConnectorSettings} to use
   * @param ewonName the Ewon name
   * @param ewonTagName the Ewon tag name
   * @param isGroupA boolean indicating if the tag is in group A
   * @param isGroupB boolean indicating if the tag is in group B
   * @param isGroupC boolean indicating if the tag is in group C
   * @param isGroupD boolean indicating if the tag is in group D
   * @param isSystemTag boolean indicating if the tag is a system tag (i.e. status tag, realtime
   *     override tag)
   * @return the corresponding tag provider tag name
   * @since 1.0.0
   */
  public static String getTagNameForProviderFromRawTagInfo(
      EwonConnectorSettings connectorSettings,
      String ewonName,
      String ewonTagName,
      boolean isGroupA,
      boolean isGroupB,
      boolean isGroupC,
      boolean isGroupD,
      boolean isSystemTag) {
    // Build group string (if sort by group enabled)
    String groupString = "";
    if (connectorSettings.isSortTagsByGroup()) {
      if (isGroupA) {
        groupString += "A";
      }
      if (isGroupB) {
        groupString += "B";
      }
      if (isGroupC) {
        groupString += "C";
      }
      if (isGroupD) {
        groupString += "D";
      }
    }

    // Check for and remove illegal tag name characters (if enabled)
    String sanitizedTagName = ewonTagName;
    if (!connectorSettings.isTagNameCheckDisabled() && !isSystemTag) {
      sanitizedTagName = getSanitizedTagName(ewonTagName);
    }

    // Return tag name
    return groupString.length() > 0
        ? ewonName + "/" + groupString + "/" + sanitizedTagName
        : ewonName + "/" + sanitizedTagName;
  }

  /**
   * Gets a sanitized version of the specified string tag name. This method will impose the
   * following Ignition tag name constraints:
   *
   * <ul>
   *   <li>First character must be one of the following:
   *       <ul>
   *         <li>Letter
   *         <li>Number
   *         <li>Underscore
   *       </ul>
   *   <li>Subsequent characters must be one of the following:
   *       <ul>
   *         <li>Letter
   *         <li>Number
   *         <li>Underscore
   *         <li>Space
   *         <li>Any of the following special characters: '-:()
   *       </ul>
   * </ul>
   *
   * @param string the string tag name to sanitize
   * @return the sanitized string tag name
   * @since 1.0.0
   */
  public static String getSanitizedTagName(String string) {
    String sanitizedString = string;
    if (sanitizedString != null && sanitizedString.length() > 0) {
      // Check first character
      if (!Character.isLetterOrDigit(sanitizedString.charAt(0))
          && sanitizedString.charAt(0) != '_') {
        // Character is illegal, replace
        sanitizedString = ILLEGAL_TAG_NAME_CHARACTER_REPLACEMENT + sanitizedString.substring(1);
      }

      // Check subsequent characters
      for (int i = 1; i < sanitizedString.length(); i++) {
        if (!Character.isLetterOrDigit(sanitizedString.charAt(i))
            && sanitizedString.charAt(i) != '_'
            && sanitizedString.charAt(i) != ' '
            && sanitizedString.charAt(i) != '-'
            && sanitizedString.charAt(i) != ':'
            && sanitizedString.charAt(i) != '('
            && sanitizedString.charAt(i) != ')') {
          // Character is illegal, replace
          sanitizedString =
              sanitizedString.substring(0, i)
                  + ILLEGAL_TAG_NAME_CHARACTER_REPLACEMENT
                  + sanitizedString.substring(i + 1);
        }
      }
    }
    return sanitizedString;
  }

  /**
   * Gets a boolean value from the specified integer value. This method will return {@code true} if
   * the specified integer value is equal to 1, {@code false} otherwise.
   *
   * @param booleanInt the integer value to convert to a boolean value
   * @return the boolean value
   * @since 1.0.0
   */
  public static boolean getGroupBooleanFromInt(int booleanInt) {
    return booleanInt == 1;
  }

  /**
   * Gets the corresponding tag provider tag name for the specified {@link M2WebEwonEBDTag} on the
   * Ewon with the specified name, using the specified {@link EwonConnectorSettings}.
   *
   * @param connectorSettings the {@link EwonConnectorSettings} to use
   * @param m2WebEwonName the name of the Ewon
   * @param m2WebEwonEBDTag the {@link M2WebEwonEBDTag} to get the tag name for
   * @return the corresponding tag provider tag name
   * @since 1.0.0
   */
  public static String getTagNameForProviderFromM2WebEwonName(
      EwonConnectorSettings connectorSettings,
      String m2WebEwonName,
      M2WebEwonEBDTag m2WebEwonEBDTag) {
    boolean isSystemTag = false;
    return getTagNameForProviderFromRawTagInfo(
        connectorSettings,
        m2WebEwonName,
        m2WebEwonEBDTag.getName(),
        getGroupBooleanFromInt(m2WebEwonEBDTag.getIvGroupA()),
        getGroupBooleanFromInt(m2WebEwonEBDTag.getIvGroupB()),
        getGroupBooleanFromInt(m2WebEwonEBDTag.getIvGroupC()),
        getGroupBooleanFromInt(m2WebEwonEBDTag.getIvGroupD()),
        isSystemTag);
  }

  /**
   * Gets the corresponding tag provider tag name for the specified {@link M2WebEwonEBDTag} on the
   * specified {@link M2WebEwon}, using the specified {@link EwonConnectorSettings}.
   *
   * @param connectorSettings the {@link EwonConnectorSettings} to use
   * @param m2WebEwon the {@link M2WebEwon} to get the tag name for
   * @param m2WebEwonEBDTag the {@link M2WebEwonEBDTag} to get the tag name for
   * @return the corresponding tag provider tag name
   * @since 1.0.0
   */
  public static String getTagNameForProviderFromM2WebEwon(
      EwonConnectorSettings connectorSettings,
      M2WebEwon m2WebEwon,
      M2WebEwonEBDTag m2WebEwonEBDTag) {
    return getTagNameForProviderFromM2WebEwonName(
        connectorSettings, m2WebEwon.getName(), m2WebEwonEBDTag);
  }

  /**
   * Gets the corresponding tag provider tag name for the specified {@link DMWebEwonTag} on the
   * specified {@link DMWebEwon}, using the specified {@link EwonConnectorSettings}.
   *
   * @param connectorSettings the {@link EwonConnectorSettings} to use
   * @param dmWebEwon the {@link DMWebEwon} to get the tag name for
   * @param dmWebEwonTag the {@link DMWebEwonTag} to get the tag name for
   * @return the corresponding tag provider tag name
   * @since 1.0.0
   */
  public static String getTagNameForProviderFromDMWebEwon(
      EwonConnectorSettings connectorSettings, DMWebEwon dmWebEwon, DMWebEwonTag dmWebEwonTag) {
    // Get M2Web Ewon tag object
    M2WebEwonEBDTag cachedM2WebEwonTag =
        CacheManager.getCachedM2WebEwonTagMap(dmWebEwon.getName()).get(dmWebEwonTag.getName());

    // Get tag name for provider
    boolean isSystemTag = false;
    String tagNameForProvider;
    if (cachedM2WebEwonTag != null) {
      tagNameForProvider =
          getTagNameForProviderFromRawTagInfo(
              connectorSettings,
              dmWebEwon.getName(),
              dmWebEwonTag.getName(),
              getGroupBooleanFromInt(cachedM2WebEwonTag.getIvGroupA()),
              getGroupBooleanFromInt(cachedM2WebEwonTag.getIvGroupB()),
              getGroupBooleanFromInt(cachedM2WebEwonTag.getIvGroupC()),
              getGroupBooleanFromInt(cachedM2WebEwonTag.getIvGroupD()),
              isSystemTag);
    } else {
      // Show warnin if debug enabled or if tag group sorting is enabled
      if (connectorSettings.isSortTagsByGroup() || connectorSettings.isDebugEnabled()) {
        LOGGER.warn(
            "Could not find cached M2Web Ewon tag for the corresponding DMWeb Ewon tag: {}. Tag"
                + " group sorting cannot be applied (if enabled), falling back to standard tag"
                + " name!",
            dmWebEwon.getName() + "/" + dmWebEwonTag.getName());
      }
      boolean isInGroup = false;
      tagNameForProvider =
          getTagNameForProviderFromRawTagInfo(
              connectorSettings,
              dmWebEwon.getName(),
              dmWebEwonTag.getName(),
              isInGroup,
              isInGroup,
              isInGroup,
              isInGroup,
              isSystemTag);
    }
    return tagNameForProvider;
  }

  /**
   * Builds a {@link HistoricalTagValue} object from the specified tag provider and tag data point
   * parameters.
   *
   * @param tagProviderName the name of the tag provider
   * @param tagName the name of the tag
   * @param tagDataType the {@link DataType} of the tag
   * @param tagDataQuality the {@link DataQuality} of the tag value
   * @param tagValueTimestamp the timestamp of the tag value
   * @param tagValue the value of the tag
   * @return the {@link HistoricalTagValue} object
   * @since 1.0.0
   */
  public static HistoricalTagValue buildHistoricalTagValue(
      String tagProviderName,
      String tagName,
      DataType tagDataType,
      DataQuality tagDataQuality,
      Date tagValueTimestamp,
      Object tagValue) {
    TagPath tagPath = TagPathParser.parseSafe(tagProviderName, tagName);
    return new PackedHistoricalTagValue(
        tagPath,
        tagDataType.getTypeClass(),
        tagDataType.getTypeClass() == DataTypeClass.Float
            ? InterpolationMode.Analog_Compressed
            : InterpolationMode.Discrete,
        TimestampSource.Value,
        tagValue,
        tagDataQuality,
        tagValueTimestamp);
  }
}

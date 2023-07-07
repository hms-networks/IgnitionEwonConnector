package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import java.util.Collection;
import org.apache.commons.collections4.KeyValue;

/**
 * Class for building and performing Ewon update tag values requests via M2Web.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwonUpdateTagValuesRequest extends M2WebEwonGetRequest {

  /**
   * The Ewon GET request path base for update tag values requests.
   *
   * @since 1.0.0
   */
  private static final String UPDATE_TAG_VALUES_GET_REQUEST_PATH_BASE = "/rcgi.bin/UpdateTagForm";

  /**
   * Constructs a new {@link M2WebEwonUpdateTagValuesRequest} object with the specified M2Web/Talk2M
   * server session ID, {@link CommunicationAuthInfo}, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the Ewon update tag values request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonName The name of the Ewon to perform the update tag values request on.
   * @param tagNameValuePairs The tag name and (updated) value pairs.
   * @since 1.0.0
   */
  public M2WebEwonUpdateTagValuesRequest(
      String server,
      String t2mSessionId,
      CommunicationAuthInfo communicationAuthInfo,
      String ewonName,
      Collection<KeyValue<String, Object>> tagNameValuePairs) {
    this(
        server,
        t2mSessionId,
        communicationAuthInfo.getDevId(),
        communicationAuthInfo.getEwonUsername(),
        communicationAuthInfo.getEwonPassword(),
        ewonName,
        tagNameValuePairs);
  }

  /**
   * Constructs a new {@link M2WebEwonUpdateTagValuesRequest} object with the specified M2Web/Talk2M
   * server, session ID, developer ID, device username, device password, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the Ewon update tag values request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @param t2mDeviceUsername The Talk2M device username to use for the request. Can not be null.
   * @param t2mDevicePassword The Talk2M device password to use for the request. Can not be null.
   * @param ewonName The name of the Ewon to perform the update tag values request on.
   * @param tagNameValuePairs The tag name and (updated) value pairs.
   * @since 1.0.0
   */
  public M2WebEwonUpdateTagValuesRequest(
      String server,
      String t2mSessionId,
      String t2mDeveloperId,
      String t2mDeviceUsername,
      String t2mDevicePassword,
      String ewonName,
      Collection<KeyValue<String, Object>> tagNameValuePairs) {
    super(
        server,
        t2mSessionId,
        t2mDeveloperId,
        t2mDeviceUsername,
        t2mDevicePassword,
        ewonName,
        getUpdateTagValuesRequestPath(tagNameValuePairs));
  }

  /**
   * Gets the Ewon update tag values request path for the specified tag name and (updated) value
   * pairs.
   *
   * @param tagNameValuePairs The tag name and (updated) value pairs.
   * @return The Ewon update tag values request path.
   * @since 1.0.0
   */
  private static String getUpdateTagValuesRequestPath(
      Collection<KeyValue<String, Object>> tagNameValuePairs) {
    StringBuilder requestPath = new StringBuilder(UPDATE_TAG_VALUES_GET_REQUEST_PATH_BASE);

    // Add tag name and value pairs to request path
    boolean isFirstTagValuePair = true;
    int tagValuePairIndex = 1;
    for (KeyValue<String, Object> tagNameValuePair : tagNameValuePairs) {
      // Append query string separator (? if first parameter)
      if (isFirstTagValuePair) {
        requestPath.append("?");
        isFirstTagValuePair = false;
      } else {
        requestPath.append("&");
      }

      // Append tag name
      final int tagIndex = tagValuePairIndex++;
      requestPath.append("TagName").append(tagIndex).append("=").append(tagNameValuePair.getKey());

      // Append query string separator
      requestPath.append("&");

      // Append tag value
      requestPath
          .append("TagValue")
          .append(tagIndex)
          .append("=")
          .append(tagNameValuePair.getValue().toString());
    }

    return requestPath.toString();
  }
}

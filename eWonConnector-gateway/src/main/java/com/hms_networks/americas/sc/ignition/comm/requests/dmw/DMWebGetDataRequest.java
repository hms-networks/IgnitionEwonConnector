package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.CommunicationUtilities;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import java.util.ArrayList;
import java.util.Date;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing DMWeb getdata requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebGetDataRequest extends Talk2MRequest {

  /**
   * The default value for the full config parameter.
   *
   * @since 1.0.0
   */
  private static final boolean DEFAULT_FULL_CONFIG_VALUE = false;

  /**
   * The URL for the DMWeb/Talk2M getdata service.
   *
   * @since 1.0.0
   */
  private static final String GETDATA_SERVICE_URL = "https://data.talk2m.com/getdata";

  /**
   * The parameters of the request to the DMWeb/Talk2M getdata service, generated in the {@link
   * DMWebGetDataRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId());
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID and token.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid) {
    this(t2mtoken, t2mdevid, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and full config value.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo, boolean fullConfig) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * full config value.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, null, null, null, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo, EwonId ewonId) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, EwonId ewonId) {
    this(t2mtoken, t2mdevid, ewonId, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon tag ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo, TagId tagId) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), tagId);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * targeted Ewon tag ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, TagId tagId) {
    this(t2mtoken, t2mdevid, tagId, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon tag ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo, Limit limit) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * targeted Ewon tag ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, Limit limit) {
    this(t2mtoken, t2mdevid, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, and targeted from timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo, FromDate fromDate) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), fromDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * targeted from timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, FromDate fromDate) {
    this(t2mtoken, t2mdevid, fromDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, and targeted to timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo, ToDate toDate) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), toDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * targeted to timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, ToDate toDate) {
    this(t2mtoken, t2mdevid, toDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, and targeted Ewon tag ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param tagId The ID of the targeted Ewon tag.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, TagId tagId) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId, tagId);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, and targeted Ewon tag ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param tagId The ID of the targeted Ewon tag.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, EwonId ewonId, TagId tagId) {
    this(t2mtoken, t2mdevid, ewonId, tagId, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, and targeted from timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, FromDate fromDate) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId, fromDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, and targeted from timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, EwonId ewonId, FromDate fromDate) {
    this(t2mtoken, t2mdevid, ewonId, fromDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, and targeted to timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, ToDate toDate) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId, toDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, and targeted to timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, EwonId ewonId, ToDate toDate) {
    this(t2mtoken, t2mdevid, ewonId, toDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, boolean fullConfig) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, EwonId ewonId, boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, null, null, null, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, Limit limit) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId, limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, EwonId ewonId, Limit limit) {
    this(t2mtoken, t2mdevid, ewonId, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon tag ID, and targeted from timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, FromDate fromDate) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), tagId, fromDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon tag ID, and targeted from timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, TagId tagId, FromDate fromDate) {
    this(t2mtoken, t2mdevid, tagId, fromDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon tag ID, and targeted to timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, ToDate toDate) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), tagId, toDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon tag ID, and targeted to timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, TagId tagId, ToDate toDate) {
    this(t2mtoken, t2mdevid, tagId, toDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon tag ID, and full configuration flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, boolean fullConfig) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), tagId, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon tag ID, and full configuration flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, TagId tagId, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, tagId, null, null, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon tag ID, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, Limit limit) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), tagId, limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon tag ID, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The ID of the targeted Ewon tag.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, TagId tagId, Limit limit) {
    this(t2mtoken, t2mdevid, tagId, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, and targeted from/to timestamps.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, FromDate fromDate, ToDate toDate) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), fromDate, toDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * targeted from/to timestamps.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, FromDate fromDate, ToDate toDate) {
    this(t2mtoken, t2mdevid, fromDate, toDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted from timestamp, and full configuration flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, FromDate fromDate, boolean fullConfig) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), fromDate, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted from timestamp, and full configuration flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, FromDate fromDate, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, null, fromDate, null, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted to timestamp, and full configuration flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, ToDate toDate, boolean fullConfig) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), toDate, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted to timestamp, and full configuration flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, ToDate toDate, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, null, null, toDate, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted to timestamp, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, ToDate toDate, Limit limit) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), toDate, limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted to timestamp, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, ToDate toDate, Limit limit) {
    this(t2mtoken, t2mdevid, toDate, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, full configuration flag, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, Limit limit, boolean fullConfig) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * full configuration flag, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(String t2mtoken, String t2mdevid, Limit limit, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, null, null, null, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, tag ID, and timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, TagId tagId, FromDate fromDate) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fromDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, targeted Ewon tag ID, and timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, TagId tagId, FromDate fromDate) {
    this(t2mtoken, t2mdevid, ewonId, tagId, fromDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, targeted Ewon tag ID, and timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, TagId tagId, ToDate toDate) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId, tagId, toDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, targeted Ewon tag ID, and timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, TagId tagId, ToDate toDate) {
    this(t2mtoken, t2mdevid, ewonId, tagId, toDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, targeted Ewon tag ID, and full configuration
   * flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, TagId tagId, boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, targeted Ewon tag ID, and full configuration flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, TagId tagId, boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, tagId, null, null, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, targeted Ewon tag ID, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, TagId tagId, Limit limit) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId, tagId, limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, targeted Ewon tag ID, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, TagId tagId, Limit limit) {
    this(t2mtoken, t2mdevid, ewonId, tagId, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, from date, and to date.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      FromDate fromDate,
      ToDate toDate) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        fromDate,
        toDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, from date, and to date.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, FromDate fromDate, ToDate toDate) {
    this(t2mtoken, t2mdevid, ewonId, fromDate, toDate, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, from date, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      FromDate fromDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        fromDate,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, from date, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, FromDate fromDate, boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, null, fromDate, null, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, from date, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, FromDate fromDate, Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        fromDate,
        limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, from date, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, FromDate fromDate, Limit limit) {
    this(t2mtoken, t2mdevid, ewonId, null, fromDate, null, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, to date, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      ToDate toDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        toDate,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, to date, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, ToDate toDate, boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, null, null, toDate, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, to date, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, ToDate toDate, Limit limit) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId, toDate, limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, to date, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, ToDate toDate, Limit limit) {
    this(t2mtoken, t2mdevid, ewonId, null, null, toDate, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, EwonId ewonId, Limit limit, boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, Limit limit, boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, null, null, null, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, from date, and to date.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, FromDate fromDate, ToDate toDate) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        tagId,
        fromDate,
        toDate);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, from date, and to date.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, TagId tagId, FromDate fromDate, ToDate toDate) {
    this(t2mtoken, t2mdevid, null, tagId, fromDate, toDate, null, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, from date, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      TagId tagId,
      FromDate fromDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        tagId,
        fromDate,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, from date, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, TagId tagId, FromDate fromDate, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, tagId, fromDate, null, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, from date, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, FromDate fromDate, Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        tagId,
        fromDate,
        null,
        limit,
        DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, from date, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, TagId tagId, FromDate fromDate, Limit limit) {
    this(t2mtoken, t2mdevid, null, tagId, fromDate, null, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, to date, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The targeted Ewon tag ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, ToDate toDate, boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        tagId,
        null,
        toDate,
        null,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, to date, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The targeted Ewon tag ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, TagId tagId, ToDate toDate, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, tagId, null, toDate, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, to date, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The targeted Ewon tag ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, ToDate toDate, Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        tagId,
        null,
        toDate,
        limit,
        DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, to date, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The targeted Ewon tag ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, TagId tagId, ToDate toDate, Limit limit) {
    this(t2mtoken, t2mdevid, null, tagId, null, toDate, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The targeted Ewon tag ID.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, TagId tagId, Limit limit, boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        tagId,
        null,
        null,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The targeted Ewon tag ID.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, TagId tagId, Limit limit, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, tagId, null, null, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, from date, to date, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      FromDate fromDate,
      ToDate toDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        null,
        fromDate,
        toDate,
        null,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * from date, to date, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, FromDate fromDate, ToDate toDate, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, null, fromDate, toDate, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, from date, to date, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, FromDate fromDate, ToDate toDate, Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        null,
        fromDate,
        toDate,
        limit,
        false);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * from date, to date, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, FromDate fromDate, ToDate toDate, Limit limit) {
    this(t2mtoken, t2mdevid, null, null, fromDate, toDate, limit, false);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, from date, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      FromDate fromDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        null,
        fromDate,
        null,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * from date, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, FromDate fromDate, Limit limit, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, null, fromDate, null, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, to date, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo, ToDate toDate, Limit limit, boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        null,
        null,
        toDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, to
   * date, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, ToDate toDate, Limit limit, boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, null, null, toDate, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, tag ID, from date, and to date.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fromDate,
        toDate,
        null,
        false);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, tag ID, from date, and to date.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate) {
    this(t2mtoken, t2mdevid, ewonId, tagId, fromDate, toDate, null, false);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, tag ID, from date, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fromDate,
        null,
        null,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, tag ID, from date, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, tagId, fromDate, null, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, tag ID, from date, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fromDate,
        null,
        limit,
        false);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, tag ID, from date, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      Limit limit) {
    this(t2mtoken, t2mdevid, ewonId, tagId, fromDate, null, limit, false);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, tag ID, to date, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      ToDate toDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        null,
        toDate,
        null,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, tag ID, to date, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      ToDate toDate,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, tagId, null, toDate, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, tag ID, to date, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      ToDate toDate,
      Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        toDate,
        limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, tag ID, to date, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken, String t2mdevid, EwonId ewonId, TagId tagId, ToDate toDate, Limit limit) {
    this(t2mtoken, t2mdevid, ewonId, tagId, null, toDate, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, tag ID, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        null,
        null,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, tag ID, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, tagId, null, null, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, from date, to date, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      FromDate fromDate,
      ToDate toDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        null,
        fromDate,
        toDate,
        null,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, from date, to date, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      FromDate fromDate,
      ToDate toDate,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, null, fromDate, toDate, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, from date, to date, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        fromDate,
        toDate,
        limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, from date, to date, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit) {
    this(t2mtoken, t2mdevid, ewonId, null, fromDate, toDate, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, from date, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      FromDate fromDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        null,
        fromDate,
        null,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, from date, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      FromDate fromDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, null, fromDate, null, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, Ewon ID, to date, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        null,
        null,
        toDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * Ewon ID, to date, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The Ewon ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, null, null, toDate, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, from/to timestamps, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        tagId,
        fromDate,
        toDate,
        null,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, from/to timestamps, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, tagId, fromDate, toDate, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, from/to timestamps, and limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        tagId,
        fromDate,
        toDate,
        limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, from/to timestamps, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit) {
    this(t2mtoken, t2mdevid, null, tagId, fromDate, toDate, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, from timestamp, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      TagId tagId,
      FromDate fromDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        tagId,
        fromDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, from timestamp, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      TagId tagId,
      FromDate fromDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, tagId, fromDate, null, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, to timestamp, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The tag ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      TagId tagId,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        tagId,
        toDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, to timestamp, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The tag ID to use for the request.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      TagId tagId,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, tagId, null, toDate, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, from/to timestamps, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      FromDate fromDate,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        fromDate,
        toDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * from/to timestamps, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      FromDate fromDate,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, null, fromDate, toDate, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, targeted tag ID, from/to timestamps, and full
   * config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fromDate,
        toDate,
        null,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, targeted tag ID, from/to timestamps, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, tagId, fromDate, toDate, null, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, targeted tag ID, from/to timestamps, and
   * limit.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fromDate,
        toDate,
        limit);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, targeted tag ID, from/to timestamps, and limit.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit) {
    this(t2mtoken, t2mdevid, ewonId, tagId, fromDate, toDate, limit, DEFAULT_FULL_CONFIG_VALUE);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, targeted tag ID, from timestamp, limit, and
   * full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fromDate,
        null,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, targeted tag ID, from timestamp, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, tagId, fromDate, null, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, targeted tag ID, to timestamp, limit, and full
   * config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        null,
        toDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, targeted tag ID, to timestamp, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum number of data points to return. If null, all data points will be
   *     returned.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, tagId, null, toDate, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, from/to timestamps, limit, and full config
   * flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you request 1000 data points, you
   *     can get 500 tag values and 500 alarms, or 1000 tag values and 0 alarms, or 0 tag values and
   *     1000 alarms.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        null,
        fromDate,
        toDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted Ewon device ID, from/to timestamps, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you request 1000 data points, you
   *     can get 500 tag values and 500 alarms, or 1000 tag values and 0 alarms, or 0 tag values and
   *     1000 alarms.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, ewonId, null, fromDate, toDate, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted tag ID, from/to timestamps, limit, and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you request 1000 data points, you
   *     can get 500 tag values and 500 alarms, or 1000 tag values and 0 alarms, or 0 tag values and
   *     1000 alarms.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        null,
        tagId,
        fromDate,
        toDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token,
   * targeted tag ID, from/to timestamps, limit, and full config flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you request 1000 data points, you
   *     can get 500 tag values and 500 alarms, or 1000 tag values and 0 alarms, or 0 tag values and
   *     1000 alarms.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   * @since 1.0.0
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(t2mtoken, t2mdevid, null, tagId, fromDate, toDate, limit, fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, targeted Ewon device ID, targeted tag ID, from/to timestamps, limit,
   * and full config flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   */
  public DMWebGetDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        ewonId,
        tagId,
        fromDate,
        toDate,
        limit,
        fullConfig);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified Talk2M account token,
   * Talk2M developer ID, targeted Ewon device ID, targeted tag ID, from/to timestamps, limit, and
   * full config flag.
   *
   * @param t2mtoken The Talk2M account token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The targeted Ewon device ID.
   * @param tagId The targeted Ewon tag ID.
   * @param fromDate The timestamp after which data should be returned. No data older than this
   *     timestamp will be sent.
   * @param toDate The timestamp before which data should be returned. No data newer than this
   *     timestamp will be sent.
   * @param limit The maximum amount of historical data returned. The historical data is the
   *     historical tag values but also the historical alarms. If you set the limit to 4, the
   *     response will consist of 4 historical tag values and 4 historical alarms (if available) for
   *     each applicable Ewon gateway.
   * @param fullConfig If true, the all tags/Ewon gateways will appear in the returned data set,
   *     even if they do not contain historical data. By default, only tags/Ewon gateways that
   *     contain historical data will appear in the returned data set.
   */
  public DMWebGetDataRequest(
      String t2mtoken,
      String t2mdevid,
      EwonId ewonId,
      TagId tagId,
      FromDate fromDate,
      ToDate toDate,
      Limit limit,
      boolean fullConfig) {
    // Build the request parameters
    ArrayList<NameValuePair> buildingParams = new ArrayList<>();

    // Add the token and developer ID
    buildingParams.add(new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken));
    buildingParams.add(
        new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid));

    // Add the ewon ID if it is not null
    if (ewonId != null) {
      buildingParams.add(
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_EWONID_KEY,
              Integer.toString(ewonId.getValue())));
    }

    // Add the tag ID if it is not null
    if (tagId != null) {
      buildingParams.add(
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_TAGID_KEY, Integer.toString(tagId.getValue())));
    }

    // Add the from timestamp if it is not null
    if (fromDate != null) {
      buildingParams.add(
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_FROM_KEY,
              CommunicationUtilities.convertDateToIso8601String(fromDate.getValue())));
    }

    // Add the to timestamp if it is not null
    if (toDate != null) {
      buildingParams.add(
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_TO_KEY,
              CommunicationUtilities.convertDateToIso8601String(toDate.getValue())));
    }

    // Add the limit if it is not null
    if (limit != null) {
      buildingParams.add(
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_LIMIT_KEY, Integer.toString(limit.getValue())));
    }

    // Add the full config flag if it is true
    if (fullConfig) {
      buildingParams.add(
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_FULL_CONFIG_KEY,
              CommunicationConstants.T2M_DMW_PARAM_FULL_CONFIG_VALUE));
    }

    // Add the request parameters to the request
    requestParams = buildingParams.toArray(new NameValuePair[0]);
  }

  /**
   * Gets the URL of the clean request to the DMWeb API.
   *
   * @return The URL of the clean request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return GETDATA_SERVICE_URL;
  }

  /**
   * Gets the body of the clean request to the DMWeb API.
   *
   * @return The body of the clean request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public NameValuePair[] getRequestParams() {
    return requestParams;
  }

  /**
   * Utility class used to wrap an object of the same type inside a class that can be easily
   * differentiated from other instances of the same type.
   *
   * @since 1.0.0
   * @version 1.0.0
   * @author HMS Networks, MU Americas Solution Center
   */
  public static class WrappedObject<T> {

    /**
     * The wrapped object value.
     *
     * @since 1.0.0
     */
    private T value;

    /**
     * Gets the wrapped object value.
     *
     * @return The wrapped object value.
     * @since 1.0.0
     */
    public T getValue() {
      return value;
    }

    /**
     * Constructs a new {@link WrappedObject} object with the specified object value to wrap.
     *
     * @param value The object value to wrap.
     */
    public WrappedObject(T value) {
      this.value = value;
    }
  }

  /**
   * Utility class used to wrap an Ewon ID integer inside a class that can be easily differentiated
   * from other integers.
   *
   * @since 1.0.0
   * @version 1.0.0
   * @author HMS Networks, MU Americas Solution Center
   */
  public static class EwonId extends WrappedObject<Integer> {

    /**
     * Constructs a new {@link EwonId} object with the specified Ewon ID.
     *
     * @param ewonId The Ewon ID.
     * @since 1.0.0
     */
    public EwonId(int ewonId) {
      super(ewonId);
    }
  }

  /**
   * Utility class used to wrap a tag ID integer inside a class that can be easily differentiated
   * from other integers.
   *
   * @since 1.0.0
   * @version 1.0.0
   * @author HMS Networks, MU Americas Solution Center
   */
  public static class TagId extends WrappedObject<Integer> {

    /**
     * Constructs a new {@link TagId} object with the specified tag ID.
     *
     * @param tagId The tag ID.
     * @since 1.0.0
     */
    public TagId(int tagId) {
      super(tagId);
    }
  }

  /**
   * Utility class used to wrap a limit integer inside a class that can be easily differentiated
   * from other integers.
   *
   * @since 1.0.0
   * @version 1.0.0
   * @author HMS Networks, MU Americas Solution Center
   */
  public static class Limit extends WrappedObject<Integer> {

    /**
     * Constructs a new {@link Limit} object with the specified tag ID.
     *
     * @param limit The limit.
     * @since 1.0.0
     */
    public Limit(int limit) {
      super(limit);
    }
  }

  /**
   * Utility class used to wrap a from date inside a class that can be easily differentiated from
   * other dates.
   *
   * @since 1.0.0
   * @version 1.0.0
   */
  public static class FromDate extends WrappedObject<Date> {

    /**
     * Constructs a new {@link FromDate} object with the specified from Date.
     *
     * @param fromDate The from date.
     * @since 1.0.0
     */
    public FromDate(Date fromDate) {
      super(fromDate);
    }
  }

  /**
   * Utility class used to wrap a to date inside a class that can be easily differentiated from
   * other dates.
   *
   * @since 1.0.0
   * @version 1.0.0
   */
  public static class ToDate extends WrappedObject<Date> {

    /**
     * Constructs a new {@link ToDate} object with the specified to Date.
     *
     * @param toDate The to date.
     * @since 1.0.0
     */
    public ToDate(Date toDate) {
      super(toDate);
    }
  }
}

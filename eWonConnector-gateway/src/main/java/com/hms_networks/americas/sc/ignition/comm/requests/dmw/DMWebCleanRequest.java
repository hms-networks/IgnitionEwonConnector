package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing DMWeb clean requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebCleanRequest extends Talk2MRequest {

  /**
   * The URL for the DMWeb/Talk2M clean service.
   *
   * @since 1.0.0
   */
  private static final String CLEAN_SERVICE_URL = "https://data.talk2m.com/clean";

  /**
   * The parameters of the request to the DMWeb/Talk2M clean service, generated in the {@link
   * DMWebCleanRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link DMWebCleanRequest} object with the specified {@link
   * CommunicationAuthInfo}. Without a targeted Ewon device ID, this request will clean the entire
   * DataMailbox.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @since 1.0.0
   */
  public DMWebCleanRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId());
  }

  /**
   * Constructs a new {@link DMWebCleanRequest} object with the specified developer ID and token.
   * Without a targeted Ewon device ID, this request will clean the entire DataMailbox.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @since 1.0.0
   */
  public DMWebCleanRequest(String t2mtoken, String t2mdevid) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair("all", null)
        };
  }

  /**
   * Constructs a new {@link DMWebCleanRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @since 1.0.0
   */
  public DMWebCleanRequest(CommunicationAuthInfo communicationAuthInfo, int ewonId) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonId);
  }

  /**
   * Constructs a new {@link DMWebCleanRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   * @since 1.0.0
   */
  public DMWebCleanRequest(String t2mtoken, String t2mdevid, int ewonId) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_EWONID_KEY, String.valueOf(ewonId))
        };
  }

  /**
   * Gets the URL of the clean request to the DMWeb API.
   *
   * @return The URL of the clean request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return CLEAN_SERVICE_URL;
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
}

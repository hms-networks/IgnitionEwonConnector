package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing DMWeb getewon requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebGetEwonRequest extends Talk2MRequest {

  /**
   * The URL for the DMWeb/Talk2M getewon service.
   *
   * @since 1.0.0
   */
  private static final String GETEWON_SERVICE_URL = "https://data.talk2m.com/getewon";

  /**
   * The parameters of the request to the DMWeb/Talk2M getewon service, generated in the {@link
   * DMWebGetEwonRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link DMWebGetEwonRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param id The ID of the targeted Ewon device.
   * @since 1.0.0
   */
  public DMWebGetEwonRequest(CommunicationAuthInfo communicationAuthInfo, int id) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), id);
  }

  /**
   * Constructs a new {@link DMWebGetEwonRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param id The ID of the targeted Ewon device.
   * @since 1.0.0
   */
  public DMWebGetEwonRequest(String t2mtoken, String t2mdevid, int id) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_PARAM_ID_KEY, String.valueOf(id))
        };
  }

  /**
   * Constructs a new {@link DMWebGetEwonRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param name The name of the targeted Ewon device.
   * @since 1.0.0
   */
  public DMWebGetEwonRequest(CommunicationAuthInfo communicationAuthInfo, String name) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), name);
  }

  /**
   * Constructs a new {@link DMWebGetEwonRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param name The name of the targeted Ewon device.
   * @since 1.0.0
   */
  public DMWebGetEwonRequest(String t2mtoken, String t2mdevid, String name) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_PARAM_NAME_KEY, name)
        };
  }

  /**
   * Gets the URL of the getewon request to the DMWeb API.
   *
   * @return The URL of the getewon request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return GETEWON_SERVICE_URL;
  }

  /**
   * Gets the body of the getewon request to the DMWeb API.
   *
   * @return The body of the getewon request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public NameValuePair[] getRequestParams() {
    return requestParams;
  }
}

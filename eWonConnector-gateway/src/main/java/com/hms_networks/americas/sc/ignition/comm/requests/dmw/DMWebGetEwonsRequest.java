package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing DMWeb getewons requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebGetEwonsRequest extends Talk2MRequest {

  /**
   * The URL for the DMWeb/Talk2M getewons service.
   *
   * @since 1.0.0
   */
  private static final String GETEWONS_SERVICE_URL = "https://data.talk2m.com/getewons";

  /**
   * The parameters of the request to the DMWeb/Talk2M getewons service, generated in the {@link
   * DMWebGetEwonsRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link DMWebGetEwonsRequest} object with the specified {@link
   * CommunicationAuthInfo}.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @since 1.0.0
   */
  public DMWebGetEwonsRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId());
  }

  /**
   * Constructs a new {@link DMWebGetEwonsRequest} object with the specified developer ID and token.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @since 1.0.0
   */
  public DMWebGetEwonsRequest(String t2mtoken, String t2mdevid) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid)
        };
  }

  /**
   * Gets the URL of the getewons request to the DMWeb API.
   *
   * @return The URL of the getewons request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return GETEWONS_SERVICE_URL;
  }

  /**
   * Gets the body of the getewons request to the DMWeb API.
   *
   * @return The body of the getewons request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public NameValuePair[] getRequestParams() {
    return requestParams;
  }
}

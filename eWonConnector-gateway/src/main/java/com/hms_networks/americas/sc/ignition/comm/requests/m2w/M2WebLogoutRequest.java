package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing M2Web logout requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebLogoutRequest extends Talk2MRequest {

  /**
   * The endpoint for the M2Web/Talk2M logout service.
   *
   * @since 1.0.0
   */
  private static final String LOGOUT_SERVICE_ENDPOINT = "/t2mapi/logout";

  /**
   * The URL of the request to the M2Web/Talk2M logout service, generated in the {@link
   * M2WebLogoutRequest} constructor.
   *
   * @since 1.0.0
   */
  private final String requestUrl;

  /**
   * The parameters of the request to the M2Web/Talk2M logout service, generated in the {@link
   * M2WebLogoutRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link M2WebLogoutRequest} object with the specified M2Web/Talk2M server and
   * {@link CommunicationAuthInfo}.
   *
   * @param server The M2Web/Talk2M server to perform the logout request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with and log out of.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @since 1.0.0
   */
  public M2WebLogoutRequest(
      String server, String t2mSessionId, CommunicationAuthInfo communicationAuthInfo) {
    this(server, t2mSessionId, communicationAuthInfo.getDevId());
  }

  /**
   * Constructs a new {@link M2WebLogoutRequest} object with the specified M2Web/Talk2M server,
   * session ID, and developer ID.
   *
   * @param server The M2Web/Talk2M server to perform the logout request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with and log out of.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @since 1.0.0
   */
  public M2WebLogoutRequest(String server, String t2mSessionId, String t2mDeveloperId) {
    this.requestUrl = server + LOGOUT_SERVICE_ENDPOINT;
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_SESSION_ID_KEY, t2mSessionId),
          new BasicNameValuePair(CommunicationConstants.T2M_M2W_DEVELOPER_ID_KEY, t2mDeveloperId)
        };
  }

  /**
   * Gets the URL of the logout request to the M2Web API.
   *
   * @return The URL of the logout request to the M2Web API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return requestUrl;
  }

  /**
   * Gets the body of the logout request to the M2Web API.
   *
   * @return The body of the logout request to the M2Web API.
   * @since 1.0.0
   */
  @Override
  public NameValuePair[] getRequestParams() {
    return requestParams;
  }
}

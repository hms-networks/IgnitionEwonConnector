package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing M2Web login requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebLoginRequest extends Talk2MRequest {

  /**
   * The endpoint for the M2Web/Talk2M login service.
   *
   * @since 1.0.0
   */
  private static final String LOGIN_SERVICE_ENDPOINT = "/t2mapi/login";

  /**
   * The URL of the request to the M2Web/Talk2M login service, generated in the {@link
   * M2WebLoginRequest} constructor.
   *
   * @since 1.0.0
   */
  private final String requestUrl;

  /**
   * The parameters of the request to the M2Web/Talk2M login service, generated in the {@link
   * M2WebLoginRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link M2WebLoginRequest} object with the specified M2Web/Talk2M server and
   * authentication information.
   *
   * @param server The M2Web/Talk2M server to perform the login request on.
   * @param communicationAuthInfo The authentication information of the M2Web/Talk2M account to
   *     login to.
   * @since 1.0.0
   */
  public M2WebLoginRequest(String server, CommunicationAuthInfo communicationAuthInfo) {
    this(
        server,
        communicationAuthInfo.getAccount(),
        communicationAuthInfo.getUsername(),
        communicationAuthInfo.getPassword(),
        communicationAuthInfo.getDevId());
  }

  /**
   * Constructs a new {@link M2WebLoginRequest} object with the specified M2Web/Talk2M server,
   * account name, username, password, and developer ID.
   *
   * @param server The M2Web/Talk2M server to perform the login request on.
   * @param t2mAccount The account name of the Talk2M account to login to.
   * @param t2mUsername The username of the Talk2M account to login to.
   * @param t2mPassword The password of the Talk2M account to login to.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @since 1.0.0
   */
  public M2WebLoginRequest(
      String server,
      String t2mAccount,
      String t2mUsername,
      String t2mPassword,
      String t2mDeveloperId) {
    this.requestUrl = server + LOGIN_SERVICE_ENDPOINT;
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_ACCOUNT_KEY, t2mAccount),
          new BasicNameValuePair(CommunicationConstants.T2M_USERNAME_KEY, t2mUsername),
          new BasicNameValuePair(CommunicationConstants.T2M_PASSWORD_KEY, t2mPassword),
          new BasicNameValuePair(CommunicationConstants.T2M_M2W_DEVELOPER_ID_KEY, t2mDeveloperId),
        };
  }

  /**
   * Gets the URL of the login request to the M2Web API.
   *
   * @return The URL of the login request to the M2Web API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return requestUrl;
  }

  /**
   * Gets the body of the login request to the M2Web API.
   *
   * @return The body of the login request to the M2Web API.
   * @since 1.0.0
   */
  @Override
  public NameValuePair[] getRequestParams() {
    return requestParams;
  }
}

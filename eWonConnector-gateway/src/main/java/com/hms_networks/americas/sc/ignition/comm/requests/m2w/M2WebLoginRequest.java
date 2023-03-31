package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing M2Web login requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class M2WebLoginRequest extends M2WebRequest {

  /** The endpoint for the M2Web/Talk2M login service. */
  private static final String LOGIN_SERVICE_ENDPOINT = "/t2mapi/login";

  /**
   * The URL of the request to the M2Web/Talk2M login service, generated in the {@link
   * M2WebLoginRequest} constructor.
   */
  private final String requestUrl;

  /**
   * The body of the request to the M2Web/Talk2M login service, generated in the {@link
   * M2WebLoginRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link M2WebLoginRequest} object with the specified M2Web/Talk2M server and
   * authentication information.
   *
   * @param server The M2Web/Talk2M server to perform the login request on.
   * @param communicationAuthInfo The authentication information of the M2Web/Talk2M account to login to.
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
   */
  public M2WebLoginRequest(
      String server,
      String t2mAccount,
      String t2mUsername,
      String t2mPassword,
      String t2mDeveloperId) {
    this.requestUrl = server + LOGIN_SERVICE_ENDPOINT;
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_ACCOUNT,
            t2mAccount,
            EwonConsts.T2M_USERNAME,
            t2mUsername,
            EwonConsts.T2M_PASSWORD,
            t2mPassword,
            EwonConsts.T2M_DEVKEY,
            t2mDeveloperId);
  }

  /**
   * Gets the URL of the login request to the M2Web API.
   *
   * @return The URL of the login request to the M2Web API.
   */
  @Override
  public String getRequestUrl() {
    return requestUrl;
  }

  /**
   * Gets the body of the login request to the M2Web API.
   *
   * @return The body of the login request to the M2Web API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

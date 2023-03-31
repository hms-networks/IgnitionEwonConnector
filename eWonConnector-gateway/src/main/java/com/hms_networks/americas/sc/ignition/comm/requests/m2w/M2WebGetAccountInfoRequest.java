package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing M2Web getaccountinfo requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class M2WebGetAccountInfoRequest extends M2WebRequest {

  /** The endpoint for the M2Web/Talk2M getaccountinfo service. */
  private static final String GETACCOUNTINFO_SERVICE_ENDPOINT = "/t2mapi/getaccountinfo";

  /**
   * The URL of the request to the M2Web/Talk2M getaccountinfo service, generated in the {@link
   * M2WebGetAccountInfoRequest} constructor.
   */
  private final String requestUrl;

  /**
   * The body of the request to the M2Web/Talk2M getaccountinfo service, generated in the {@link
   * M2WebGetAccountInfoRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link M2WebGetAccountInfoRequest} object with the specified M2Web/Talk2M
   * server and {@link CommunicationAuthInfo}.
   *
   * @param server The M2Web/Talk2M server to perform the getaccountinfo request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for the request.
   */
  public M2WebGetAccountInfoRequest(String server, String t2mSessionId, CommunicationAuthInfo communicationAuthInfo) {
    this(server, t2mSessionId, communicationAuthInfo.getDevId());
  }

  /**
   * Constructs a new {@link M2WebGetAccountInfoRequest} object with the specified M2Web/Talk2M
   * server, session ID, and developer ID.
   *
   * @param server The M2Web/Talk2M server to perform the getaccountinfo request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   */
  public M2WebGetAccountInfoRequest(String server, String t2mSessionId, String t2mDeveloperId) {
    this.requestUrl = server + GETACCOUNTINFO_SERVICE_ENDPOINT;
    this.requestBody =
        String.format(
            "%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY, t2mSessionId, EwonConsts.T2M_DEVKEY, t2mDeveloperId);
  }

  /**
   * Gets the URL of the getaccountinfo request to the M2Web API.
   *
   * @return The URL of the getaccountinfo request to the M2Web API.
   */
  @Override
  public String getRequestUrl() {
    return requestUrl;
  }

  /**
   * Gets the body of the getaccountinfo request to the M2Web API.
   *
   * @return The body of the getaccountinfo request to the M2Web API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

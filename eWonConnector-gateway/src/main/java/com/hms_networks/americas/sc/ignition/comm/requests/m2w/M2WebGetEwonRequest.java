package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;

/**
 * Class for building and performing M2Web getewon requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class M2WebGetEwonRequest extends M2WebRequest {

  /** The endpoint for the M2Web/Talk2M getewon service. */
  private static final String GETEWON_SERVICE_ENDPOINT = "/t2mapi/getewons";

  /**
   * The URL of the request to the M2Web/Talk2M getewon service, generated in the {@link
   * M2WebGetEwonRequest} constructor.
   */
  private final String requestUrl;

  /**
   * The body of the request to the M2Web/Talk2M getewon service, generated in the {@link
   * M2WebGetEwonRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link M2WebGetEwonRequest} object with the specified M2Web/Talk2M server
   * session ID, {@link CommunicationAuthInfo}, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the getewon request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for the request.
   * @param ewon The Ewon to get information for.
   */
  public M2WebGetEwonRequest(String server, String t2mSessionId, CommunicationAuthInfo communicationAuthInfo, String ewon) {
    this(server, t2mSessionId, communicationAuthInfo.getDevId(), ewon);
  }

  /**
   * Constructs a new {@link M2WebGetEwonRequest} object with the specified M2Web/Talk2M server,
   * session ID, developer ID, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the getewon request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @param ewon The Ewon to get information for.
   */
  public M2WebGetEwonRequest(
      String server, String t2mSessionId, String t2mDeveloperId, String ewon) {
    this.requestUrl = server + GETEWON_SERVICE_ENDPOINT;
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_DEVKEY,
            t2mDeveloperId,
            CommunicationConstants.T2M_EWON_KEY,
            ewon);
  }

  /**
   * Gets the URL of the getewon request to the M2Web API.
   *
   * @return The URL of the getewon request to the M2Web API.
   */
  @Override
  public String getRequestUrl() {
    return requestUrl;
  }

  /**
   * Gets the body of the getewon request to the M2Web API.
   *
   * @return The body of the getewon request to the M2Web API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

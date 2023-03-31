package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing DMWeb getstatus requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class DMWebGetStatusRequest extends DMWebRequest {

  /** The URL for the DMWeb/Talk2M getstatus service. */
  private static final String GETSTATUS_SERVICE_URL = "https://data.talk2m.com/getstatus";

  /**
   * The body of the request to the DMWeb/Talk2M getstatus service, generated in the {@link
   * DMWebGetStatusRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link DMWebGetStatusRequest} object with the specified {@link
   * CommunicationAuthInfo}.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   */
  public DMWebGetStatusRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken());
  }

  /**
   * Constructs a new {@link DMWebGetStatusRequest} object with the specified developer ID and
   * token.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   */
  public DMWebGetStatusRequest(String t2mSessionId, String t2mtoken) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY, t2mSessionId, EwonConsts.T2M_TOKEN_KEY, t2mtoken);
  }

  /**
   * Gets the URL of the getstatus request to the DMWeb API.
   *
   * @return The URL of the getstatus request to the DMWeb API.
   */
  @Override
  public String getRequestUrl() {
    return GETSTATUS_SERVICE_URL;
  }

  /**
   * Gets the body of the getstatus request to the DMWeb API.
   *
   * @return The body of the getstatus request to the DMWeb API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

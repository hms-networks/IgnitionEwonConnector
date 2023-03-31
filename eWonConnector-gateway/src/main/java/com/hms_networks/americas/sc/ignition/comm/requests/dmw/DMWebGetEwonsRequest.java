package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing DMWeb getewons requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class DMWebGetEwonsRequest extends DMWebRequest {

  /** The URL for the DMWeb/Talk2M getewons service. */
  private static final String GETEWONS_SERVICE_URL = "https://data.talk2m.com/getewons";

  /**
   * The body of the request to the DMWeb/Talk2M getewons service, generated in the {@link
   * DMWebGetEwonsRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link DMWebGetEwonsRequest} object with the specified {@link
   * CommunicationAuthInfo}.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   */
  public DMWebGetEwonsRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken());
  }

  /**
   * Constructs a new {@link DMWebGetEwonsRequest} object with the specified developer ID and token.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   */
  public DMWebGetEwonsRequest(String t2mSessionId, String t2mtoken) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY, t2mSessionId, EwonConsts.T2M_TOKEN_KEY, t2mtoken);
  }

  /**
   * Gets the URL of the getewons request to the DMWeb API.
   *
   * @return The URL of the getewons request to the DMWeb API.
   */
  @Override
  public String getRequestUrl() {
    return GETEWONS_SERVICE_URL;
  }

  /**
   * Gets the body of the getewons request to the DMWeb API.
   *
   * @return The body of the getewons request to the DMWeb API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

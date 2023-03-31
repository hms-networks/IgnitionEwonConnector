package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing DMWeb getdata requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class DMWebGetDataRequest extends DMWebRequest {

  /** The URL for the DMWeb/Talk2M getdata service. */
  private static final String GETDATA_SERVICE_URL = "https://data.talk2m.com/getdata";

  /**
   * The body of the request to the DMWeb/Talk2M getdata service, generated in the {@link
   * DMWebGetDataRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo}.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken());
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID and token.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   */
  public DMWebGetDataRequest(String t2mSessionId, String t2mtoken) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY, t2mSessionId, EwonConsts.T2M_TOKEN_KEY, t2mtoken);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   */
  public DMWebGetDataRequest(CommunicationAuthInfo communicationAuthInfo, int ewonId) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), ewonId);
  }

  /**
   * Constructs a new {@link DMWebGetDataRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   */
  public DMWebGetDataRequest(String t2mSessionId, String t2mtoken, int ewonId) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_EWONID,
            ewonId);
  }

  /**
   * Gets the URL of the clean request to the DMWeb API.
   *
   * @return The URL of the clean request to the DMWeb API.
   */
  @Override
  public String getRequestUrl() {
    return GETDATA_SERVICE_URL;
  }

  /**
   * Gets the body of the clean request to the DMWeb API.
   *
   * @return The body of the clean request to the DMWeb API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

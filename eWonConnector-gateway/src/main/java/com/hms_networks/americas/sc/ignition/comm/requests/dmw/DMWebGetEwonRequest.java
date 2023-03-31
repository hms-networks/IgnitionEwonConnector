package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing DMWeb getewon requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class DMWebGetEwonRequest extends DMWebRequest {

  /** The URL for the DMWeb/Talk2M getewon service. */
  private static final String GETEWON_SERVICE_URL = "https://data.talk2m.com/getewon";

  /**
   * The body of the request to the DMWeb/Talk2M getewon service, generated in the {@link
   * DMWebGetEwonRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link DMWebGetEwonRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param id The ID of the targeted Ewon device.
   */
  public DMWebGetEwonRequest(CommunicationAuthInfo communicationAuthInfo, int id) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), id);
  }

  /**
   * Constructs a new {@link DMWebGetEwonRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param id The ID of the targeted Ewon device.
   */
  public DMWebGetEwonRequest(String t2mSessionId, String t2mtoken, int id) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_ID,
            id);
  }

  /**
   * Constructs a new {@link DMWebGetEwonRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param name The name of the targeted Ewon device.
   */
  public DMWebGetEwonRequest(CommunicationAuthInfo communicationAuthInfo, String name) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), name);
  }

  /**
   * Constructs a new {@link DMWebGetEwonRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param name The name of the targeted Ewon device.
   */
  public DMWebGetEwonRequest(String t2mSessionId, String t2mtoken, String name) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_NAME,
            name);
  }

  /**
   * Gets the URL of the getewon request to the DMWeb API.
   *
   * @return The URL of the getewon request to the DMWeb API.
   */
  @Override
  public String getRequestUrl() {
    return GETEWON_SERVICE_URL;
  }

  /**
   * Gets the body of the getewon request to the DMWeb API.
   *
   * @return The body of the getewon request to the DMWeb API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

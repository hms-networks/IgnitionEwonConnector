package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing DMWeb clean requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class DMWebCleanRequest extends DMWebRequest {

  /** The URL for the DMWeb/Talk2M clean service. */
  private static final String CLEAN_SERVICE_URL = "https://data.talk2m.com/clean";

  /**
   * The body of the request to the DMWeb/Talk2M clean service, generated in the {@link
   * DMWebCleanRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link DMWebCleanRequest} object with the specified {@link
   * CommunicationAuthInfo}. Without a targeted Ewon device ID, this request will clean the entire
   * DataMailbox.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   */
  public DMWebCleanRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken());
  }

  /**
   * Constructs a new {@link DMWebCleanRequest} object with the specified developer ID and token.
   * Without a targeted Ewon device ID, this request will clean the entire DataMailbox.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   */
  public DMWebCleanRequest(String t2mSessionId, String t2mtoken) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&all",
            EwonConsts.T2M_SESSION_ID_KEY, t2mSessionId, EwonConsts.T2M_TOKEN_KEY, t2mtoken);
  }

  /**
   * Constructs a new {@link DMWebCleanRequest} object with the specified {@link
   * CommunicationAuthInfo} and targeted Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the targeted Ewon device.
   */
  public DMWebCleanRequest(CommunicationAuthInfo communicationAuthInfo, int ewonId) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), ewonId);
  }

  /**
   * Constructs a new {@link DMWebCleanRequest} object with the specified developer ID, token, and
   * targeted Ewon device ID.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param ewonId The ID of the targeted Ewon device.
   */
  public DMWebCleanRequest(String t2mSessionId, String t2mtoken, int ewonId) {
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
    return CLEAN_SERVICE_URL;
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

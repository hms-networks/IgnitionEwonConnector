package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing DMWeb delete requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class DMWebDeleteRequest extends DMWebRequest {

  /** The URL for the DMWeb/Talk2M delete service. */
  private static final String DELETE_SERVICE_URL = "https://data.talk2m.com/delete";

  /**
   * The body of the request to the DMWeb/Talk2M delete service, generated in the {@link
   * DMWebDeleteRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified {@link
   * CommunicationAuthInfo}. Without a specified filter parameter, all historical data will be
   * deleted from DataMailbox.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   */
  public DMWebDeleteRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken());
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified developer ID and token.
   * Without a specified filter parameter, all historical data will be deleted from DataMailbox.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   */
  public DMWebDeleteRequest(String t2mSessionId, String t2mtoken) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&all",
            EwonConsts.T2M_SESSION_ID_KEY, t2mSessionId, EwonConsts.T2M_TOKEN_KEY, t2mtoken);
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified {@link
   * CommunicationAuthInfo} and target transaction ID or Ewon device ID.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param id The target transaction ID or Ewon device ID.
   * @param isEwonId Boolean indicating if the specified ID is an Ewon device ID or if it is a
   *     transaction ID. If true, the specified ID is an Ewon device ID. If false, the specified ID
   *     is a transaction ID.
   */
  public DMWebDeleteRequest(CommunicationAuthInfo communicationAuthInfo, int id, boolean isEwonId) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), id, isEwonId);
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified developer ID, token, and
   * target transaction ID or Ewon device ID.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param id The target transaction ID or Ewon device ID.
   * @param isEwonId Boolean indicating if the specified ID is an Ewon device ID or if it is a
   *     transaction ID. If true, the specified ID is an Ewon device ID. If false, the specified ID
   *     is a transaction ID.
   */
  public DMWebDeleteRequest(String t2mSessionId, String t2mtoken, int id, boolean isEwonId) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            isEwonId ? EwonConsts.DM_PARAM_EWONID : EwonConsts.DM_PARAM_TRANSACTIONID,
            id);
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified {@link
   * CommunicationAuthInfo} and target timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param toTimestamp The timestamp to delete data up to. Data older than this timestamp will be
   *     deleted.
   */
  public DMWebDeleteRequest(CommunicationAuthInfo communicationAuthInfo, String toTimestamp) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), toTimestamp);
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified developer ID, token, and
   * target timestamp.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param toTimestamp The timestamp to delete data up to. Data older than this timestamp will be
   *     deleted.
   */
  public DMWebDeleteRequest(String t2mSessionId, String t2mtoken, String toTimestamp) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_TO,
            toTimestamp);
  }

  /**
   * Gets the URL of the delete request to the DMWeb API.
   *
   * @return The URL of the delete request to the DMWeb API.
   */
  @Override
  public String getRequestUrl() {
    return DELETE_SERVICE_URL;
  }

  /**
   * Gets the body of the delete request to the DMWeb API.
   *
   * @return The body of the delete request to the DMWeb API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

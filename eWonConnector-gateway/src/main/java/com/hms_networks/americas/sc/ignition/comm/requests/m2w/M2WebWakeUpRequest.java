package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;

/**
 * Class for building and performing M2Web wakeup requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class M2WebWakeUpRequest extends M2WebRequest {

  /** The endpoint for the M2Web/Talk2M wakeup service. */
  private static final String WAKEUP_SERVICE_ENDPOINT = "/t2mapi/wakeup";

  /**
   * The URL of the request to the M2Web/Talk2M wakeup service, generated in the {@link
   * M2WebWakeUpRequest} constructor.
   */
  private final String requestUrl;

  /**
   * The body of the request to the M2Web/Talk2M wakeup service, generated in the {@link
   * M2WebWakeUpRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link M2WebWakeUpRequest} object with the specified M2Web/Talk2M server
   * session ID, {@link CommunicationAuthInfo}, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the wakeup request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for the request.
   * @param ewonName The name of the Ewon to perform the wakeup on.
   */
  public M2WebWakeUpRequest(
      String server, String t2mSessionId, CommunicationAuthInfo communicationAuthInfo, String ewonName) {
    this(server, t2mSessionId, communicationAuthInfo.getDevId(), ewonName);
  }

  /**
   * Constructs a new {@link M2WebWakeUpRequest} object with the specified M2Web/Talk2M server
   * session ID, {@link CommunicationAuthInfo}, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the wakeup request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for the request.
   * @param ewonId The ID of the Ewon to perform the wakeup on.
   */
  public M2WebWakeUpRequest(String server, String t2mSessionId, CommunicationAuthInfo communicationAuthInfo, int ewonId) {
    this(server, t2mSessionId, communicationAuthInfo.getDevId(), ewonId);
  }

  /**
   * Constructs a new {@link M2WebWakeUpRequest} object with the specified M2Web/Talk2M server,
   * session ID, developer ID, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the wakeup request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @param ewonName The name of the Ewon to perform the wakeup on.
   */
  public M2WebWakeUpRequest(
      String server, String t2mSessionId, String t2mDeveloperId, String ewonName) {
    this.requestUrl = server + WAKEUP_SERVICE_ENDPOINT;
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_DEVKEY,
            t2mDeveloperId,
            CommunicationConstants.T2M_WAKE_UP_EWON_NAME_KEY,
            ewonName);
  }

  /**
   * Constructs a new {@link M2WebWakeUpRequest} object with the specified M2Web/Talk2M server,
   * session ID, developer ID, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the wakeup request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the Ewon to perform the wakeup on.
   */
  public M2WebWakeUpRequest(String server, String t2mSessionId, String t2mDeveloperId, int ewonId) {
    this.requestUrl = server + WAKEUP_SERVICE_ENDPOINT;
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_DEVKEY,
            t2mDeveloperId,
            CommunicationConstants.T2M_WAKE_UP_EWON_ID_KEY,
            ewonId);
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

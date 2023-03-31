package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing M2Web sendoffline requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebSendOfflineRequest extends Talk2MRequest {

  /**
   * The endpoint for the M2Web/Talk2M sendoffline service.
   *
   * @since 1.0.0
   */
  private static final String SENDOFFLINE_SERVICE_ENDPOINT = "/t2mapi/sendoffline";

  /**
   * The URL of the request to the M2Web/Talk2M sendoffline service, generated in the {@link
   * M2WebSendOfflineRequest} constructor.
   *
   * @since 1.0.0
   */
  private final String requestUrl;

  /**
   * The parameters of the request to the M2Web/Talk2M sendoffline service, generated in the {@link
   * M2WebSendOfflineRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link M2WebSendOfflineRequest} object with the specified M2Web/Talk2M server
   * session ID, {@link CommunicationAuthInfo}, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the sendoffline request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonName The name of the Ewon to send offline.
   * @since 1.0.0
   */
  public M2WebSendOfflineRequest(
      String server,
      String t2mSessionId,
      CommunicationAuthInfo communicationAuthInfo,
      String ewonName) {
    this(server, t2mSessionId, communicationAuthInfo.getDevId(), ewonName);
  }

  /**
   * Constructs a new {@link M2WebSendOfflineRequest} object with the specified M2Web/Talk2M server
   * session ID, {@link CommunicationAuthInfo}, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the sendoffline request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonId The ID of the Ewon to send offline.
   * @since 1.0.0
   */
  public M2WebSendOfflineRequest(
      String server, String t2mSessionId, CommunicationAuthInfo communicationAuthInfo, int ewonId) {
    this(server, t2mSessionId, communicationAuthInfo.getDevId(), ewonId);
  }

  /**
   * Constructs a new {@link M2WebSendOfflineRequest} object with the specified M2Web/Talk2M server,
   * session ID, developer ID, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the sendoffline request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @param ewonName The name of the Ewon to send offline.
   * @since 1.0.0
   */
  public M2WebSendOfflineRequest(
      String server, String t2mSessionId, String t2mDeveloperId, String ewonName) {
    this.requestUrl = server + SENDOFFLINE_SERVICE_ENDPOINT;
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_SESSION_ID_KEY, t2mSessionId),
          new BasicNameValuePair(CommunicationConstants.T2M_M2W_DEVELOPER_ID_KEY, t2mDeveloperId),
          new BasicNameValuePair(CommunicationConstants.T2M_M2W_EWON_NAME_KEY, ewonName)
        };
  }

  /**
   * Constructs a new {@link M2WebSendOfflineRequest} object with the specified M2Web/Talk2M server,
   * session ID, developer ID, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the sendoffline request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @param ewonId The ID of the Ewon to send offline.
   * @since 1.0.0
   */
  public M2WebSendOfflineRequest(
      String server, String t2mSessionId, String t2mDeveloperId, int ewonId) {
    this.requestUrl = server + SENDOFFLINE_SERVICE_ENDPOINT;
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_SESSION_ID_KEY, t2mSessionId),
          new BasicNameValuePair(CommunicationConstants.T2M_M2W_DEVELOPER_ID_KEY, t2mDeveloperId),
          new BasicNameValuePair(CommunicationConstants.T2M_M2W_EWON_ID_KEY, String.valueOf(ewonId))
        };
  }

  /**
   * Gets the URL of the sendoffline request to the M2Web API.
   *
   * @return The URL of the sendoffline request to the M2Web API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return requestUrl;
  }

  /**
   * Gets the body of the sendoffline request to the M2Web API.
   *
   * @return The body of the sendoffline request to the M2Web API.
   * @since 1.0.0
   */
  @Override
  public NameValuePair[] getRequestParams() {
    return requestParams;
  }
}

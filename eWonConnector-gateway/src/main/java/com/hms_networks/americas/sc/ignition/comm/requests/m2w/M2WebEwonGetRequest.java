package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing Ewon GET requests via M2Web.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class M2WebEwonGetRequest extends M2WebRequest {

  /** The endpoint for the M2Web/Talk2M Ewon GET request service. */
  private static final String GETEWON_SERVICE_ENDPOINT = "/t2mapi/get";

  /**
   * The URL of the request to the M2Web/Talk2M Ewon GET request service, generated in the {@link
   * M2WebEwonGetRequest} constructor.
   */
  private final String requestUrl;

  /**
   * The body of the request to the M2Web/Talk2M Ewon GET request service, generated in the {@link
   * M2WebEwonGetRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link M2WebEwonGetRequest} object with the specified M2Web/Talk2M server
   * session ID, {@link CommunicationAuthInfo}, Ewon name, and Ewon request path (i.e.
   * rcgi.bin/ParamForm?AST_Param=...).
   *
   * @param server The M2Web/Talk2M server to perform the Ewon GET request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for the request.
   * @param ewonName The name of the Ewon to perform the GET request on.
   * @param ewonRequestPath The Ewon path to perform the GET request on.
   */
  public M2WebEwonGetRequest(
      String server,
      String t2mSessionId,
      CommunicationAuthInfo communicationAuthInfo,
      String ewonName,
      String ewonRequestPath) {
    this(
        server,
        t2mSessionId,
        communicationAuthInfo.getDevId(),
        communicationAuthInfo.getEwonUsername(),
        communicationAuthInfo.getEwonPassword(),
        ewonName,
        ewonRequestPath);
  }

  /**
   * Constructs a new {@link M2WebEwonGetRequest} object with the specified M2Web/Talk2M server,
   * session ID, developer ID, device username, device password, Ewon name, and Ewon request path
   * (i.e. rcgi.bin/ParamForm?AST_Param=...).
   *
   * @param server The M2Web/Talk2M server to perform the getewon request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @param t2mDeviceUsername The Talk2M device username to use for the request. Can not be null.
   * @param t2mDevicePassword The Talk2M device password to use for the request. Can not be null.
   * @param ewonName The name of the Ewon to perform the GET request on.
   * @param ewonRequestPath The Ewon path to perform the GET request on.
   */
  public M2WebEwonGetRequest(
      String server,
      String t2mSessionId,
      String t2mDeveloperId,
      String t2mDeviceUsername,
      String t2mDevicePassword,
      String ewonName,
      String ewonRequestPath) {
    // Ensure device username and password are not null
    if (t2mDeviceUsername == null) {
      throw new IllegalArgumentException("Device username cannot be null.");
    }
    if (t2mDevicePassword == null) {
      throw new IllegalArgumentException("Device password cannot be null.");
    }

    this.requestUrl = server + GETEWON_SERVICE_ENDPOINT + "/" + ewonName + "/" + ewonRequestPath;
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_DEVKEY,
            t2mDeveloperId,
            EwonConsts.T2M_DEVICE_USERNAME,
            t2mDeviceUsername,
            EwonConsts.T2M_DEVICE_PASSWORD,
            t2mDevicePassword);
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

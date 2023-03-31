package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;

/**
 * Class for building and performing Ewon Export Block Descriptor (EBD) Tag List requests via M2Web.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class M2WebEwonEBDTagListRequest extends M2WebEwonGetRequest {

  /**
   * The Ewon GET request path for EBD Tag List requests.
   *
   * @since 1.0.0
   */
  private static final String EBD_TAG_LIST_GET_REQUEST_PATH =
      "/rcgi.bin/ParamForm?AST_Param=$dtTL$ftT";

  /**
   * Constructs a new {@link M2WebEwonEBDTagListRequest} object with the specified M2Web/Talk2M
   * server session ID, {@link CommunicationAuthInfo}, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the Ewon EBD Tag List request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonName The name of the Ewon to perform the EBD Tag List request on.
   * @since 1.0.0
   */
  public M2WebEwonEBDTagListRequest(
      String server,
      String t2mSessionId,
      CommunicationAuthInfo communicationAuthInfo,
      String ewonName) {
    this(
        server,
        t2mSessionId,
        communicationAuthInfo.getDevId(),
        communicationAuthInfo.getEwonUsername(),
        communicationAuthInfo.getEwonPassword(),
        ewonName);
  }

  /**
   * Constructs a new {@link M2WebEwonEBDTagListRequest} object with the specified M2Web/Talk2M
   * server, session ID, developer ID, device username, device password, and Ewon name.
   *
   * @param server The M2Web/Talk2M server to perform the Ewon EBD Tag List request on.
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mDeveloperId The Talk2M developer ID to use for the request.
   * @param t2mDeviceUsername The Talk2M device username to use for the request. Can not be null.
   * @param t2mDevicePassword The Talk2M device password to use for the request. Can not be null.
   * @param ewonName The name of the Ewon to perform the EBD Tag List request on.
   * @since 1.0.0
   */
  public M2WebEwonEBDTagListRequest(
      String server,
      String t2mSessionId,
      String t2mDeveloperId,
      String t2mDeviceUsername,
      String t2mDevicePassword,
      String ewonName) {
    super(
        server,
        t2mSessionId,
        t2mDeveloperId,
        t2mDeviceUsername,
        t2mDevicePassword,
        ewonName,
        EBD_TAG_LIST_GET_REQUEST_PATH);
  }
}

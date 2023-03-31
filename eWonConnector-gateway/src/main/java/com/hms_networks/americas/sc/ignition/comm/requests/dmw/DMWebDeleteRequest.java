package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.CommunicationUtilities;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import java.util.Date;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing DMWeb delete requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebDeleteRequest extends Talk2MRequest {

  /**
   * The URL for the DMWeb/Talk2M delete service.
   *
   * @since 1.0.0
   */
  private static final String DELETE_SERVICE_URL = "https://data.talk2m.com/delete";

  /**
   * The parameters of the request to the DMWeb/Talk2M delete service, generated in the {@link
   * DMWebDeleteRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified {@link
   * CommunicationAuthInfo}. Without a specified filter parameter, all historical data will be
   * deleted from DataMailbox.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @since 1.0.0
   */
  public DMWebDeleteRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId());
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified developer ID and token.
   * Without a specified filter parameter, all historical data will be deleted from DataMailbox.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @since 1.0.0
   */
  public DMWebDeleteRequest(String t2mtoken, String t2mdevid) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair("all", null)
        };
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
   * @since 1.0.0
   */
  public DMWebDeleteRequest(CommunicationAuthInfo communicationAuthInfo, int id, boolean isEwonId) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), id, isEwonId);
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified developer ID, token, and
   * target transaction ID or Ewon device ID.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param id The target transaction ID or Ewon device ID.
   * @param isEwonId Boolean indicating if the specified ID is an Ewon device ID or if it is a
   *     transaction ID. If true, the specified ID is an Ewon device ID. If false, the specified ID
   *     is a transaction ID.
   * @since 1.0.0
   */
  public DMWebDeleteRequest(String t2mtoken, String t2mdevid, int id, boolean isEwonId) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              isEwonId
                  ? CommunicationConstants.T2M_DMW_PARAM_EWONID_KEY
                  : CommunicationConstants.T2M_DMW_PARAM_TRANSACTIONID_KEY,
              Integer.toString(id))
        };
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified {@link
   * CommunicationAuthInfo} and target timestamp.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param toTimestamp The timestamp to delete data up to. Data older than this timestamp will be
   *     deleted.
   * @since 1.0.0
   */
  public DMWebDeleteRequest(CommunicationAuthInfo communicationAuthInfo, Date toTimestamp) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), toTimestamp);
  }

  /**
   * Constructs a new {@link DMWebDeleteRequest} object with the specified developer ID, token, and
   * target timestamp.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param toTimestamp The timestamp to delete data up to. Data older than this timestamp will be
   *     deleted.
   * @since 1.0.0
   */
  public DMWebDeleteRequest(String t2mtoken, String t2mdevid, Date toTimestamp) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_TO_KEY,
              CommunicationUtilities.convertDateToIso8601String(toTimestamp))
        };
  }

  /**
   * Gets the URL of the delete request to the DMWeb API.
   *
   * @return The URL of the delete request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return DELETE_SERVICE_URL;
  }

  /**
   * Gets the body of the delete request to the DMWeb API.
   *
   * @return The body of the delete request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public NameValuePair[] getRequestParams() {
    return requestParams;
  }
}

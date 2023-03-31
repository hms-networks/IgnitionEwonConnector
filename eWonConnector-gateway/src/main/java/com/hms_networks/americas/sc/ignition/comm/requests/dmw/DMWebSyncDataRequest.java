package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import java.util.Arrays;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

/**
 * Class for building and performing DMWeb syncdata requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public class DMWebSyncDataRequest extends Talk2MRequest {

  /**
   * The URL for the DMWeb/Talk2M syncdata service.
   *
   * @since 1.0.0
   */
  private static final String SYNCDATA_SERVICE_URL = "https://data.talk2m.com/syncdata";

  /**
   * The parameters of the request to the DMWeb/Talk2M syncdata service, generated in the {@link
   * DMWebSyncDataRequest} constructor.
   *
   * @since 1.0.0
   */
  private final NameValuePair[] requestParams;

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo}.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId());
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID and token.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(String t2mtoken, String t2mdevid) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid)
        };
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and create transaction flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      CommunicationAuthInfo communicationAuthInfo, boolean createTransaction) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), createTransaction);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * and create transaction flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(String t2mtoken, String t2mdevid, boolean createTransaction) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_CREATE_TRANSACTION_KEY,
              String.valueOf(createTransaction))
        };
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and create transaction flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(CommunicationAuthInfo communicationAuthInfo, long lastTransactionId) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), lastTransactionId);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * and create transaction flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(String t2mtoken, String t2mdevid, long lastTransactionId) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_LAST_TID_KEY, String.valueOf(lastTransactionId))
        };
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, last transaction ID, and create transaction flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      long lastTransactionId,
      boolean createTransaction) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        lastTransactionId,
        createTransaction);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * last transaction ID, and create transaction flag.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      String t2mtoken, String t2mdevid, long lastTransactionId, boolean createTransaction) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_LAST_TID_KEY, String.valueOf(lastTransactionId)),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_CREATE_TRANSACTION_KEY,
              String.valueOf(createTransaction))
        };
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and Ewon gateway IDs.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(CommunicationAuthInfo communicationAuthInfo, int[] ewonIds) {
    this(communicationAuthInfo.getToken(), communicationAuthInfo.getDevId(), ewonIds);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * and Ewon gateway IDs.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(String t2mtoken, String t2mdevid, int[] ewonIds) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_EWON_IDS_KEY,
              getCommaSeparatedStringOfEwonIds(ewonIds))
        };
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, create transaction flag, and Ewon gateway IDs.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      CommunicationAuthInfo communicationAuthInfo, boolean createTransaction, int[] ewonIds) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        createTransaction,
        ewonIds);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * create transaction flag, and Ewon gateway IDs.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      String t2mtoken, String t2mdevid, boolean createTransaction, int[] ewonIds) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_CREATE_TRANSACTION_KEY,
              String.valueOf(createTransaction)),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_EWON_IDS_KEY,
              getCommaSeparatedStringOfEwonIds(ewonIds))
        };
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, last transaction ID, and Ewon gateway IDs.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      CommunicationAuthInfo communicationAuthInfo, long lastTransactionId, int[] ewonIds) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        lastTransactionId,
        ewonIds);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * last transaction ID, and Ewon gateway IDs.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      String t2mtoken, String t2mdevid, long lastTransactionId, int[] ewonIds) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_LAST_TID_KEY, String.valueOf(lastTransactionId)),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_EWON_IDS_KEY,
              getCommaSeparatedStringOfEwonIds(ewonIds))
        };
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo}, last transaction ID, create transaction flag, and Ewon gateway IDs.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      long lastTransactionId,
      boolean createTransaction,
      int[] ewonIds) {
    this(
        communicationAuthInfo.getToken(),
        communicationAuthInfo.getDevId(),
        lastTransactionId,
        createTransaction,
        ewonIds);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * last transaction ID, create transaction flag, and Ewon gateway IDs.
   *
   * @param t2mtoken The Talk2M token to use for the request.
   * @param t2mdevid The Talk2M developer ID to use for the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   * @since 1.0.0
   */
  public DMWebSyncDataRequest(
      String t2mtoken,
      String t2mdevid,
      long lastTransactionId,
      boolean createTransaction,
      int[] ewonIds) {
    this.requestParams =
        new NameValuePair[] {
          new BasicNameValuePair(CommunicationConstants.T2M_TOKEN_KEY, t2mtoken),
          new BasicNameValuePair(CommunicationConstants.T2M_DMW_DEVELOPER_ID_KEY, t2mdevid),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_LAST_TID_KEY, String.valueOf(lastTransactionId)),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_CREATE_TRANSACTION_KEY,
              String.valueOf(createTransaction)),
          new BasicNameValuePair(
              CommunicationConstants.T2M_DMW_PARAM_EWON_IDS_KEY,
              getCommaSeparatedStringOfEwonIds(ewonIds))
        };
  }

  /**
   * Converts an array of Ewon gateway IDs to a comma-separated string list of Ewon gateway IDs.
   *
   * @param ewonIds The array of Ewon gateway IDs to convert.
   * @return The comma-separated string list of Ewon gateway IDs.
   * @since 1.0.0
   */
  private String getCommaSeparatedStringOfEwonIds(int[] ewonIds) {
    return Arrays.stream(ewonIds).mapToObj(String::valueOf).reduce("", (a, b) -> a + "," + b);
  }

  /**
   * Gets the URL of the syncdata request to the DMWeb API.
   *
   * @return The URL of the syncdata request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public String getRequestUrl() {
    return SYNCDATA_SERVICE_URL;
  }

  /**
   * Gets the body of the syncdata request to the DMWeb API.
   *
   * @return The body of the syncdata request to the DMWeb API.
   * @since 1.0.0
   */
  @Override
  public NameValuePair[] getRequestParams() {
    return requestParams;
  }
}

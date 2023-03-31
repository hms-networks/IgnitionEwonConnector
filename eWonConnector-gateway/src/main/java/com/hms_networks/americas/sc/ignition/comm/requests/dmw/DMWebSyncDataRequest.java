package com.hms_networks.americas.sc.ignition.comm.requests.dmw;

import com.hms_networks.americas.sc.ignition.EwonConsts;
import com.hms_networks.americas.sc.ignition.comm.CommunicationAuthInfo;
import java.util.Arrays;

/**
 * Class for building and performing DMWeb syncdata requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class DMWebSyncDataRequest extends DMWebRequest {

  /** The URL for the DMWeb/Talk2M syncdata service. */
  private static final String SYNCDATA_SERVICE_URL = "https://data.talk2m.com/syncdata";

  /**
   * The body of the request to the DMWeb/Talk2M syncdata service, generated in the {@link
   * DMWebSyncDataRequest} constructor.
   */
  private final String requestBody;

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo}.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   */
  public DMWebSyncDataRequest(CommunicationAuthInfo communicationAuthInfo) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken());
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID and token.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   */
  public DMWebSyncDataRequest(String t2mSessionId, String t2mtoken) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY, t2mSessionId, EwonConsts.T2M_TOKEN_KEY, t2mtoken);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified {@link
   * CommunicationAuthInfo} and create transaction flag.
   *
   * @param communicationAuthInfo The Talk2M account {@link CommunicationAuthInfo} object to use for
   *     the request.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   */
  public DMWebSyncDataRequest(
      CommunicationAuthInfo communicationAuthInfo, boolean createTransaction) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), createTransaction);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * and create transaction flag.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   */
  public DMWebSyncDataRequest(String t2mSessionId, String t2mtoken, boolean createTransaction) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_CREATE_TRANSACTION,
            createTransaction);
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
   */
  public DMWebSyncDataRequest(CommunicationAuthInfo communicationAuthInfo, int lastTransactionId) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), lastTransactionId);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * and create transaction flag.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   */
  public DMWebSyncDataRequest(String t2mSessionId, String t2mtoken, int lastTransactionId) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_LAST_TID,
            lastTransactionId);
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
   */
  public DMWebSyncDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      int lastTransactionId,
      boolean createTransaction) {
    this(
        communicationAuthInfo.getDevId(),
        communicationAuthInfo.getToken(),
        lastTransactionId,
        createTransaction);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * last transaction ID, and create transaction flag.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   */
  public DMWebSyncDataRequest(
      String t2mSessionId, String t2mtoken, int lastTransactionId, boolean createTransaction) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_LAST_TID,
            lastTransactionId,
            EwonConsts.DM_PARAM_CREATE_TRANSACTION,
            createTransaction);
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
   */
  public DMWebSyncDataRequest(CommunicationAuthInfo communicationAuthInfo, int[] ewonIds) {
    this(communicationAuthInfo.getDevId(), communicationAuthInfo.getToken(), ewonIds);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * and Ewon gateway IDs.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   */
  public DMWebSyncDataRequest(String t2mSessionId, String t2mtoken, int[] ewonIds) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_EWON_IDS,
            getCommaSeparatedStringOfEwonIds(ewonIds));
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
   */
  public DMWebSyncDataRequest(
      CommunicationAuthInfo communicationAuthInfo,
      int lastTransactionId,
      boolean createTransaction,
      int[] ewonIds) {
    this(
        communicationAuthInfo.getDevId(),
        communicationAuthInfo.getToken(),
        lastTransactionId,
        createTransaction,
        ewonIds);
  }

  /**
   * Constructs a new {@link DMWebSyncDataRequest} object with the specified developer ID, token,
   * last transaction ID, create transaction flag, and Ewon gateway IDs.
   *
   * @param t2mSessionId The Talk2M session ID to authenticate with.
   * @param t2mtoken The Talk2M token to use for the request.
   * @param lastTransactionId The last transaction ID to use for the request. By referencing the
   *     last transaction ID, the DataMailbox will send a set of data more recent than the data
   *     linked to this transaction ID.
   * @param createTransaction The indication to the server that a new transaction ID should be
   *     created for this request.
   * @param ewonIds A comma-separated list of Ewon gateway IDs. If {@code ewonIds} is used,
   *     DataMailbox sends values history of the targeted Ewon gateways. If not used, DataMailbox
   *     sends the values history of all Ewon gateways.
   */
  public DMWebSyncDataRequest(
      String t2mSessionId,
      String t2mtoken,
      int lastTransactionId,
      boolean createTransaction,
      int[] ewonIds) {
    this.requestBody =
        String.format(
            "%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
            EwonConsts.T2M_SESSION_ID_KEY,
            t2mSessionId,
            EwonConsts.T2M_TOKEN_KEY,
            t2mtoken,
            EwonConsts.DM_PARAM_LAST_TID,
            lastTransactionId,
            EwonConsts.DM_PARAM_CREATE_TRANSACTION,
            createTransaction,
            EwonConsts.DM_PARAM_EWON_IDS,
            getCommaSeparatedStringOfEwonIds(ewonIds));
  }

  /**
   * Converts an array of Ewon gateway IDs to a comma-separated string list of Ewon gateway IDs.
   *
   * @param ewonIds The array of Ewon gateway IDs to convert.
   * @return The comma-separated string list of Ewon gateway IDs.
   */
  private String getCommaSeparatedStringOfEwonIds(int[] ewonIds) {
    return Arrays.stream(ewonIds).mapToObj(String::valueOf).reduce("", (a, b) -> a + "," + b);
  }

  /**
   * Gets the URL of the syncdata request to the DMWeb API.
   *
   * @return The URL of the syncdata request to the DMWeb API.
   */
  @Override
  public String getRequestUrl() {
    return SYNCDATA_SERVICE_URL;
  }

  /**
   * Gets the body of the syncdata request to the DMWeb API.
   *
   * @return The body of the syncdata request to the DMWeb API.
   */
  @Override
  public String getRequestBody() {
    return requestBody;
  }
}

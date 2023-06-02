package com.hms_networks.americas.sc.ignition.comm;

import com.hms_networks.americas.sc.ignition.comm.requests.dmw.DMWebGetEwonsRequest;
import com.hms_networks.americas.sc.ignition.comm.requests.dmw.DMWebSyncDataRequest;
import com.hms_networks.americas.sc.ignition.comm.responses.dmw.DMWebGetEwonsResponse;
import com.hms_networks.americas.sc.ignition.comm.responses.dmw.DMWebSyncDataResponse;
import com.hms_networks.americas.sc.ignition.threading.FutureUtilities;
import java.util.concurrent.Future;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;

/**
 * Communication manager for the Talk2M DMWeb API.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class DMWebCommunicationManager {

  /**
   * Gets the Ewon gateway list using the specified authentication information and server.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param httpResponseFutureCallback the callback to use for the request
   * @since 1.0.0
   */
  public static Future<DMWebGetEwonsResponse> getEwonGatewayList(
      CommunicationAuthInfo communicationAuthInfo,
      FutureCallback<SimpleHttpResponse> httpResponseFutureCallback) {
    // Build Ewon gateway list request
    DMWebGetEwonsRequest getEwonsRequest = new DMWebGetEwonsRequest(communicationAuthInfo);

    // Perform Ewon gateway list request
    return FutureUtilities.getWrappedFuture(
        getEwonsRequest.doRequest(httpResponseFutureCallback), DMWebGetEwonsResponse.class);
  }

  /**
   * Gets the latest DMWeb sync data using the specified authentication information, last
   * transaction ID, and create transaction flag.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param lastTransactionId the last transaction ID to use for the request
   * @param createTransaction the create transaction flag to use for the request
   * @param httpResponseFutureCallback the callback to use for the request
   * @since 1.0.0
   */
  public static Future<DMWebSyncDataResponse> syncData(
      CommunicationAuthInfo communicationAuthInfo,
      long lastTransactionId,
      boolean createTransaction,
      FutureCallback<SimpleHttpResponse> httpResponseFutureCallback) {
    // Build Ewon gateway list request
    DMWebSyncDataRequest syncDataRequest =
        new DMWebSyncDataRequest(communicationAuthInfo, lastTransactionId, createTransaction);

    // Perform Ewon gateway list request
    return FutureUtilities.getWrappedFuture(
        syncDataRequest.doRequest(httpResponseFutureCallback), DMWebSyncDataResponse.class);
  }

  /**
   * Gets the latest DMWeb sync data using the specified authentication information and create
   * transaction flag.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param createTransaction the create transaction flag to use for the request
   * @param httpResponseFutureCallback the callback to use for the request
   * @since 1.0.0
   */
  public static Future<DMWebSyncDataResponse> syncData(
      CommunicationAuthInfo communicationAuthInfo,
      boolean createTransaction,
      FutureCallback<SimpleHttpResponse> httpResponseFutureCallback) {
    // Build Ewon gateway list request
    DMWebSyncDataRequest syncDataRequest =
        new DMWebSyncDataRequest(communicationAuthInfo, createTransaction);

    // Perform Ewon gateway list request
    return FutureUtilities.getWrappedFuture(
        syncDataRequest.doRequest(httpResponseFutureCallback), DMWebSyncDataResponse.class);
  }
}

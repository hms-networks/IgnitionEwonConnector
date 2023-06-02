package com.hms_networks.americas.sc.ignition.comm.requests;

import com.hms_networks.americas.sc.ignition.comm.AsyncHttpRequestManager;
import com.hms_networks.americas.sc.ignition.comm.CommunicationUtilities;
import java.util.concurrent.Future;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.NameValuePair;

/**
 * An abstract class for performing an HTTP POST request to a Talk2M API. This class performs any
 * common functionality for all Talk2M requests, such as setting the content type header.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 * @version 1.0.0
 */
public abstract class Talk2MRequest {

  /**
   * Performs the HTTP POST request to a Talk2M API using the request URL and body defined by the
   * implementation of the {@link Talk2MRequest#getRequestUrl()} and {@link
   * Talk2MRequest#getRequestParams()} methods.
   *
   * @param callback The callback to be executed when the request is completed.
   * @return The {@link Future} object for the request.
   * @since 1.0.0
   */
  public Future<SimpleHttpResponse> doRequest(FutureCallback<SimpleHttpResponse> callback) {
    // Build HTTP POST request
    SimpleHttpRequest request =
        CommunicationUtilities.createPostRequest(getRequestUrl(), getRequestParams());

    // Perform HTTP POST request
    return AsyncHttpRequestManager.sendAsyncRequest(request, callback);
  }

  /**
   * Gets the URL of the request to a Talk2M API which is defined by the implementation of this
   * method.
   *
   * @return The URL of the request to a Talk2M API.
   * @since 1.0.0
   */
  public abstract String getRequestUrl();

  /**
   * Gets the parameters of the request to a Talk2M API which is defined by the implementation of
   * this method.
   *
   * @return The parameters of the request to a Talk2M API.
   * @since 1.0.0
   */
  public abstract NameValuePair[] getRequestParams();
}

package com.hms_networks.americas.sc.ignition.comm.requests;

import com.hms_networks.americas.sc.ignition.comm.AsyncHttpRequestManager;
import com.hms_networks.americas.sc.ignition.comm.CommunicationUtilities;
import java.util.concurrent.Future;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;

/**
 * An abstract class for performing an HTTP POST request to a Talk2M API. This class performs any
 * common functionality for all Talk2M requests, such as setting the content type header.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public abstract class Talk2MRequest {

  /**
   * Performs the HTTP POST request to a Talk2M API using the request URL and body defined by the
   * implementation of the {@link Talk2MRequest#getRequestUrl()} and {@link
   * Talk2MRequest#getRequestBody()} methods.
   *
   * @param callback The callback to be executed when the request is completed.
   * @param contentType The content type of the request body.
   * @param charset The charset of the request body.
   * @return The {@link Future} object for the request.
   */
  public Future<HttpResponse> doRequest(
      FutureCallback<HttpResponse> callback, String contentType, String charset) {
    // Build HTTP POST request
    HttpPost request =
        CommunicationUtilities.createPostRequest(
            getRequestUrl(), getRequestBody(), contentType, charset);

    // Perform HTTP POST request
    return AsyncHttpRequestManager.sendAsyncRequest(request, callback);
  }

  /**
   * Gets the URL of the request to a Talk2M API which is defined by the implementation of this
   * method.
   *
   * @return The URL of the request to a Talk2M API.
   */
  public abstract String getRequestUrl();

  /**
   * Gets the body of the request to a Talk2M API which is defined by the implementation of this
   * method.
   *
   * @return The body of the request to a Talk2M API.
   */
  public abstract String getRequestBody();
}

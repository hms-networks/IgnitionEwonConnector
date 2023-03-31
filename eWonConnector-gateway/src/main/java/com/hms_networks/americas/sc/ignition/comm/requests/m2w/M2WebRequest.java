package com.hms_networks.americas.sc.ignition.comm.requests.m2w;

import com.hms_networks.americas.sc.ignition.comm.CommunicationConstants;
import com.hms_networks.americas.sc.ignition.comm.requests.Talk2MRequest;
import java.util.concurrent.Future;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

/**
 * An abstract class for performing an HTTP POST request to the M2Web API. This class performs any
 * common functionality for all M2Web requests, such as setting the content type header.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public abstract class M2WebRequest extends Talk2MRequest {

  /**
   * Performs the HTTP POST request to the M2Web API using the request URL and body defined by the
   * implementation of the {@link M2WebRequest#getRequestUrl()} and {@link
   * M2WebRequest#getRequestBody()} methods.
   *
   * @param callback The callback to be executed when the request is completed.
   * @return The {@link Future} object for the request.
   */
  public Future<HttpResponse> doRequest(FutureCallback<HttpResponse> callback) {
    return super.doRequest(
        callback,
        CommunicationConstants.M2WEB_POST_REQUEST_CONTENT_TYPE,
        CommunicationConstants.M2WEB_POST_REQUEST_CHARSET);
  }
}

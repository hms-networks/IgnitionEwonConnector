package com.hms_networks.americas.sc.ignition.comm;

import com.hms_networks.americas.sc.ignition.comm.requests.m2w.M2WebEwonEBDInstantValuesRequest;
import com.hms_networks.americas.sc.ignition.comm.requests.m2w.M2WebEwonEBDTagListRequest;
import com.hms_networks.americas.sc.ignition.comm.requests.m2w.M2WebEwonUpdateTagValuesRequest;
import com.hms_networks.americas.sc.ignition.comm.requests.m2w.M2WebGetEwonsRequest;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonEBDInstantValuesResponse;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonEBDTagListResponse;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebEwonUpdateTagValuesResponse;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebGetEwonsResponse;
import com.hms_networks.americas.sc.ignition.threading.FutureUtilities;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import org.apache.commons.collections4.KeyValue;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;

/**
 * Communication manager for the Talk2M M2Web API.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class M2WebCommunicationManager {

  /**
   * The URL of the Talk2M M2Web API.
   *
   * @since 1.0.0
   */
  public static final String M2WEB_URL = "https://m2web.talk2m.com/";

  /**
   * Gets the tag list for the specified Ewon using the specified authentication information and the
   * server specified by {@link #M2WEB_URL}.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param ewonName the name of the Ewon to get the tag list for
   * @param httpResponseFutureCallback the callback to use for the request
   * @return future for asynchronous retrieval of the tag list
   * @since 1.0.0
   */
  public static Future<M2WebEwonEBDTagListResponse> getEwonTagList(
      CommunicationAuthInfo communicationAuthInfo,
      String ewonName,
      FutureCallback<M2WebEwonEBDTagListResponse> httpResponseFutureCallback) {
    return getEwonTagList(communicationAuthInfo, M2WEB_URL, ewonName, httpResponseFutureCallback);
  }

  /**
   * Gets the tag list for the specified Ewon using the specified authentication information and
   * server.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param server the Talk2M server to use for the request
   * @param ewonName the name of the Ewon to get the tag list for
   * @param httpResponseFutureCallback the callback to use for the request
   * @return future for asynchronous retrieval of the tag list
   * @since 1.0.0
   */
  public static Future<M2WebEwonEBDTagListResponse> getEwonTagList(
      CommunicationAuthInfo communicationAuthInfo,
      String server,
      String ewonName,
      FutureCallback<M2WebEwonEBDTagListResponse> httpResponseFutureCallback) {
    // Get session ID
    String sessionId = M2WebSessionManager.getLoginSessionId(communicationAuthInfo, server);

    // Build tag list request
    M2WebEwonEBDTagListRequest tagListRequest =
        new M2WebEwonEBDTagListRequest(server, sessionId, communicationAuthInfo, ewonName);

    // Build tag list request future and callback
    CompletableFuture<M2WebEwonEBDTagListResponse> future = new CompletableFuture<>();
    FutureCallback<SimpleHttpResponse> wrappedHttpResponseFutureCallback =
        new FutureCallback<>() {
          @Override
          public void completed(SimpleHttpResponse simpleHttpResponse) {
            try {
              M2WebEwonEBDTagListResponse response =
                  M2WebEwonEBDTagListResponse.getFromString(simpleHttpResponse.getBodyText());
              future.complete(response);
              if (httpResponseFutureCallback != null) {
                httpResponseFutureCallback.completed(response);
              }
            } catch (Exception e) {
              future.completeExceptionally(e);
              if (httpResponseFutureCallback != null) {
                httpResponseFutureCallback.failed(e);
              }
            }
          }

          @Override
          public void failed(Exception e) {
            future.completeExceptionally(e);
            if (httpResponseFutureCallback != null) {
              httpResponseFutureCallback.failed(e);
            }
          }

          @Override
          public void cancelled() {
            boolean mayInterruptIfRunning = true;
            future.cancel(mayInterruptIfRunning);
            if (httpResponseFutureCallback != null) {
              httpResponseFutureCallback.cancelled();
            }
          }
        };

    // Perform tag request and return future
    tagListRequest.doRequest(wrappedHttpResponseFutureCallback);
    return future;
  }

  /**
   * Gets the Ewon gateway list using the specified authentication information and the server
   * specified by {@link #M2WEB_URL}.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @return future for asynchronous retrieval of the Ewon gateway list
   * @since 1.0.0
   */
  public static Future<M2WebGetEwonsResponse> getEwonGatewayList(
      CommunicationAuthInfo communicationAuthInfo) {
    FutureCallback<SimpleHttpResponse> httpResponseFutureCallback = null;
    return getEwonGatewayList(communicationAuthInfo, M2WEB_URL, httpResponseFutureCallback);
  }

  /**
   * Gets the Ewon gateway list using the specified authentication information and the server
   * specified by {@link #M2WEB_URL}.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param httpResponseFutureCallback the callback to use for the request
   * @return future for asynchronous retrieval of the Ewon gateway list
   * @since 1.0.0
   */
  public static Future<M2WebGetEwonsResponse> getEwonGatewayList(
      CommunicationAuthInfo communicationAuthInfo,
      FutureCallback<SimpleHttpResponse> httpResponseFutureCallback) {
    return getEwonGatewayList(communicationAuthInfo, M2WEB_URL, httpResponseFutureCallback);
  }

  /**
   * Gets the Ewon gateway list using the specified authentication information and server.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param server the Talk2M server to use for the request
   * @return future for asynchronous retrieval of the Ewon gateway list
   * @since 1.0.0
   */
  public static Future<M2WebGetEwonsResponse> getEwonGatewayList(
      CommunicationAuthInfo communicationAuthInfo, String server) {
    FutureCallback<SimpleHttpResponse> httpResponseFutureCallback = null;
    return getEwonGatewayList(communicationAuthInfo, server, httpResponseFutureCallback);
  }

  /**
   * Gets the Ewon gateway list using the specified authentication information and server.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param server the Talk2M server to use for the request
   * @param httpResponseFutureCallback the callback to use for the request
   * @return future for asynchronous retrieval of the Ewon gateway list
   * @since 1.0.0
   */
  public static Future<M2WebGetEwonsResponse> getEwonGatewayList(
      CommunicationAuthInfo communicationAuthInfo,
      String server,
      FutureCallback<SimpleHttpResponse> httpResponseFutureCallback) {
    // Get session ID
    String sessionId = M2WebSessionManager.getLoginSessionId(communicationAuthInfo, server);

    // Build Ewon gateway list request
    M2WebGetEwonsRequest getEwonsRequest =
        new M2WebGetEwonsRequest(server, sessionId, communicationAuthInfo);

    // Perform Ewon gateway list request and return future
    return FutureUtilities.getWrappedFuture(
        getEwonsRequest.doRequest(httpResponseFutureCallback), M2WebGetEwonsResponse.class);
  }

  /**
   * Gets the instant tag values for the specified Ewon using the specified authentication
   * information, server, and callback.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param ewonName the name of the Ewon to get the instant tag values for
   * @param httpResponseFutureCallback the callback to use for the request
   * @return future for asynchronous retrieval of the instant tag values
   * @since 1.0.0
   */
  public static Future<M2WebEwonEBDInstantValuesResponse> getEwonInstantValues(
      CommunicationAuthInfo communicationAuthInfo,
      String ewonName,
      FutureCallback<M2WebEwonEBDInstantValuesResponse> httpResponseFutureCallback) {
    return getEwonInstantValues(
        communicationAuthInfo, M2WEB_URL, ewonName, httpResponseFutureCallback);
  }

  /**
   * Gets the instant tag values for the specified Ewon using the specified authentication
   * information, and server.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param server the Talk2M server to use for the request
   * @param ewonName the name of the Ewon to get the instant tag values for
   * @return future for asynchronous retrieval of the instant tag values
   * @since 1.0.0
   */
  public static Future<M2WebEwonEBDInstantValuesResponse> getEwonInstantValues(
      CommunicationAuthInfo communicationAuthInfo, String server, String ewonName) {
    FutureCallback<M2WebEwonEBDInstantValuesResponse> httpResponseFutureCallback = null;
    return getEwonInstantValues(
        communicationAuthInfo, server, ewonName, httpResponseFutureCallback);
  }

  /**
   * Gets the instant tag values for the specified Ewon using the specified authentication
   * information, and server.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param ewonName the name of the Ewon to get the instant tag values for
   * @return future for asynchronous retrieval of the instant tag values
   * @since 1.0.0
   */
  public static Future<M2WebEwonEBDInstantValuesResponse> getEwonInstantValues(
      CommunicationAuthInfo communicationAuthInfo, String ewonName) {
    return getEwonInstantValues(communicationAuthInfo, M2WEB_URL, ewonName);
  }

  /**
   * Gets the instant tag values for the specified Ewon using the specified authentication
   * information, server, and callback.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param server the Talk2M server to use for the request
   * @param ewonName the name of the Ewon to get the instant tag values for
   * @param httpResponseFutureCallback the callback to use for the request
   * @return future for asynchronous retrieval of the instant tag values
   * @since 1.0.0
   */
  public static Future<M2WebEwonEBDInstantValuesResponse> getEwonInstantValues(
      CommunicationAuthInfo communicationAuthInfo,
      String server,
      String ewonName,
      FutureCallback<M2WebEwonEBDInstantValuesResponse> httpResponseFutureCallback) {
    // Get session ID
    String sessionId = M2WebSessionManager.getLoginSessionId(communicationAuthInfo, server);

    // Build instant tag values request
    M2WebEwonEBDInstantValuesRequest instantValuesRequest =
        new M2WebEwonEBDInstantValuesRequest(server, sessionId, communicationAuthInfo, ewonName);

    // Build instant tag values request future and callback
    CompletableFuture<M2WebEwonEBDInstantValuesResponse> future = new CompletableFuture<>();
    FutureCallback<SimpleHttpResponse> wrappedHttpResponseFutureCallback =
        new FutureCallback<>() {
          @Override
          public void completed(SimpleHttpResponse simpleHttpResponse) {
            try {
              M2WebEwonEBDInstantValuesResponse response =
                  M2WebEwonEBDInstantValuesResponse.getFromString(simpleHttpResponse.getBodyText());
              future.complete(response);
              if (httpResponseFutureCallback != null) {
                httpResponseFutureCallback.completed(response);
              }
            } catch (Exception e) {
              future.completeExceptionally(e);
              if (httpResponseFutureCallback != null) {
                httpResponseFutureCallback.failed(e);
              }
            }
          }

          @Override
          public void failed(Exception e) {
            future.completeExceptionally(e);
            if (httpResponseFutureCallback != null) {
              httpResponseFutureCallback.failed(e);
            }
          }

          @Override
          public void cancelled() {
            boolean mayInterruptIfRunning = true;
            future.cancel(mayInterruptIfRunning);
            if (httpResponseFutureCallback != null) {
              httpResponseFutureCallback.cancelled();
            }
          }
        };

    // Perform instant tag values request and return future
    instantValuesRequest.doRequest(wrappedHttpResponseFutureCallback);
    return future;
  }

  /**
   * Updates the Ewon tag values using the specified authentication information, Ewon name, tag
   * name/value pair collection, and callback.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param ewonName the name of the Ewon to update the tag values for
   * @param tagNameValuePairs the collection of tag name/value pairs to update
   * @param httpResponseFutureCallback the callback to use for the request
   * @return future for asynchronous retrieval of the update tag values response
   * @since 1.0.0
   */
  public static Future<M2WebEwonUpdateTagValuesResponse> updateEwonTagValues(
      CommunicationAuthInfo communicationAuthInfo,
      String ewonName,
      Collection<KeyValue<String, Object>> tagNameValuePairs,
      FutureCallback<M2WebEwonUpdateTagValuesResponse> httpResponseFutureCallback) {
    return updateEwonTagValues(
        communicationAuthInfo, M2WEB_URL, ewonName, tagNameValuePairs, httpResponseFutureCallback);
  }

  /**
   * Updates the Ewon tag values using the specified authentication information, server, Ewon name,
   * and tag name/value pair collection.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param server the Talk2M server to use for the request
   * @param ewonName the name of the Ewon to update the tag values for
   * @param tagNameValuePairs the collection of tag name/value pairs to update
   * @return future for asynchronous retrieval of the update tag values response
   * @since 1.0.0
   */
  public static Future<M2WebEwonUpdateTagValuesResponse> updateEwonTagValues(
      CommunicationAuthInfo communicationAuthInfo,
      String server,
      String ewonName,
      Collection<KeyValue<String, Object>> tagNameValuePairs) {
    FutureCallback<M2WebEwonUpdateTagValuesResponse> httpResponseFutureCallback = null;
    return updateEwonTagValues(
        communicationAuthInfo, server, ewonName, tagNameValuePairs, httpResponseFutureCallback);
  }

  /**
   * Updates the Ewon tag values using the specified authentication information, Ewon name, and tag
   * name/value pair collection.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param ewonName the name of the Ewon to update the tag values for
   * @param tagNameValuePairs the collection of tag name/value pairs to update
   * @return future for asynchronous retrieval of the update tag values response
   * @since 1.0.0
   */
  public static Future<M2WebEwonUpdateTagValuesResponse> updateEwonTagValues(
      CommunicationAuthInfo communicationAuthInfo,
      String ewonName,
      Collection<KeyValue<String, Object>> tagNameValuePairs) {
    return updateEwonTagValues(communicationAuthInfo, M2WEB_URL, ewonName, tagNameValuePairs);
  }

  /**
   * Updates the Ewon tag values using the specified authentication information, server, Ewon name,
   * tag name/value pair collection, and callback.
   *
   * @param communicationAuthInfo the authentication information to use for the request
   * @param server the Talk2M server to use for the request
   * @param ewonName the name of the Ewon to update the tag values for
   * @param tagNameValuePairs the collection of tag name/value pairs to update
   * @param httpResponseFutureCallback the callback to use for the request
   * @return future for asynchronous retrieval of the update tag values response
   * @since 1.0.0
   */
  public static Future<M2WebEwonUpdateTagValuesResponse> updateEwonTagValues(
      CommunicationAuthInfo communicationAuthInfo,
      String server,
      String ewonName,
      Collection<KeyValue<String, Object>> tagNameValuePairs,
      FutureCallback<M2WebEwonUpdateTagValuesResponse> httpResponseFutureCallback) {
    // Get session ID
    String sessionId = M2WebSessionManager.getLoginSessionId(communicationAuthInfo, server);

    // Build update tag values request
    M2WebEwonUpdateTagValuesRequest updateTagValuesRequest =
        new M2WebEwonUpdateTagValuesRequest(
            server, sessionId, communicationAuthInfo, ewonName, tagNameValuePairs);

    // Build update tag values request future and callback
    CompletableFuture<M2WebEwonUpdateTagValuesResponse> future = new CompletableFuture<>();
    FutureCallback<SimpleHttpResponse> wrappedHttpResponseFutureCallback =
        new FutureCallback<>() {
          @Override
          public void completed(SimpleHttpResponse simpleHttpResponse) {
            try {
              M2WebEwonUpdateTagValuesResponse response =
                  M2WebEwonUpdateTagValuesResponse.getFromString(simpleHttpResponse.getBodyText());
              future.complete(response);
              if (httpResponseFutureCallback != null) {
                httpResponseFutureCallback.completed(response);
              }
            } catch (Exception e) {
              future.completeExceptionally(e);
              if (httpResponseFutureCallback != null) {
                httpResponseFutureCallback.failed(e);
              }
            }
          }

          @Override
          public void failed(Exception e) {
            future.completeExceptionally(e);
            if (httpResponseFutureCallback != null) {
              httpResponseFutureCallback.failed(e);
            }
          }

          @Override
          public void cancelled() {
            boolean mayInterruptIfRunning = true;
            future.cancel(mayInterruptIfRunning);
            if (httpResponseFutureCallback != null) {
              httpResponseFutureCallback.cancelled();
            }
          }
        };

    // Perform update tag values request and return future
    updateTagValuesRequest.doRequest(wrappedHttpResponseFutureCallback);
    return future;
  }
}

package com.hms_networks.americas.sc.ignition.comm;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for managing HTTP requests to the various Talk2M APIs asynchronously. This class is a
 * significantly modified/upgraded version of the previous HTTP functionality in the {@code
 * EwonUtil} class. This class is intended to improve the performance of HTTP requests by using
 * asynchronous requests and multiple threads.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @version 1.0.0
 * @since 2.0.0
 */
public class AsyncHttpRequestManager {

  /**
   * Log handler for {@link AsyncHttpRequestManager}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(AsyncHttpRequestManager.class);

  /**
   * Default connect timeout for HTTP connections (in milliseconds).
   *
   * @since 1.0.0
   */
  private static final int CONNECT_TIMEOUT_MILLIS = 5000;

  /**
   * Default socket timeout for HTTP connections (in milliseconds).
   *
   * @since 1.0.0
   */
  private static final int SOCKET_TIMEOUT_MILLIS = 10000;

  /**
   * Asynchronous HTTP client for sending requests to the various Talk2M APIs.
   *
   * @since 1.0.0
   */
  private static CloseableHttpAsyncClient httpAsyncClient = null;

  /**
   * Boolean indicating if debug logging is enabled. This value is used to prevent unnecessary debug
   * logging and associated string concatenation when disabled.
   *
   * @since 1.0.0
   */
  private static boolean isDebugEnabled = false;

  /**
   * Initializes the asynchronous HTTP manager if it has not already been initialized. If the
   * asynchronous HTTP manager has already been initialized, this method will do nothing.
   *
   * @param isDebugEnabled boolean indicating if debug logging is enabled.
   * @since 1.0.0
   */
  public static void initialize(boolean isDebugEnabled) {
    if (isNotInitialized()) {
      LOGGER.info("Initializing Asynchronous HTTP manager...");

      // Initialize the asynchronous HTTP client
      RequestConfig requestConfig =
          RequestConfig.custom()
              .setConnectionRequestTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
              .setResponseTimeout(SOCKET_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
              .build();
      httpAsyncClient = HttpAsyncClients.custom().setDefaultRequestConfig(requestConfig).build();
      httpAsyncClient.start();

      // Store debug logging enabled status
      AsyncHttpRequestManager.isDebugEnabled = isDebugEnabled;

      LOGGER.info("Finished Asynchronous HTTP manager initialization.");
    } else {
      if (AsyncHttpRequestManager.isDebugEnabled) {
        LOGGER.debug(
            "Asynchronous HTTP manager already initialized. Skipping duplicate initialization.");
      }
    }
  }

  /**
   * Returns a boolean indicating if the asynchronous HTTP manager has not yet been initialized.
   *
   * @return true if the asynchronous HTTP manager has not yet been initialized, false otherwise
   * @since 1.0.0
   */
  public static boolean isNotInitialized() {
    return httpAsyncClient == null;
  }

  /**
   * Shuts down the asynchronous HTTP manager. This method should be called when the asynchronous
   * HTTP manager is no longer needed.
   *
   * @since 1.0.0
   */
  public static void shutdown() {
    if (httpAsyncClient != null) {
      try {
        httpAsyncClient.close();
        httpAsyncClient = null;
      } catch (Exception e) {
        LOGGER.error("Error shutting down asynchronous HTTP manager.", e);
      }
    }
  }

  /**
   * Sends the specified HTTP request to the desired Talk2M API asynchronously. The asynchronous
   * HTTP manager must be initialized before this method is called.
   *
   * @param request HTTP request to send
   * @param callback callback to execute when the request completes
   * @return {@link Future} object representing the request
   * @since 1.0.0
   */
  public static Future<SimpleHttpResponse> sendAsyncRequest(
      final SimpleHttpRequest request, final FutureCallback<SimpleHttpResponse> callback) {
    // Throw IllegalStateException if asynchronous HTTP manager not initialized
    if (isNotInitialized()) {
      throw new IllegalStateException("Asynchronous HTTP manager has not been initialized.");
    }

    // Create wrapped callback to log the request
    FutureCallback<SimpleHttpResponse> wrappedCallback =
        new FutureCallback<>() {
          public void completed(SimpleHttpResponse response) {
            // Log the request completion (debug only)
            if (AsyncHttpRequestManager.isDebugEnabled) {
              LOGGER.debug(
                  "Received asynchronous HTTP response for request "
                      + request.hashCode()
                      + " from a Talk2M API: success");
            }

            // Execute the callback (if provided)
            if (callback != null) {
              callback.completed(response);
            }
          }

          public void failed(Exception ex) {
            // Log the request failure (debug only)
            if (AsyncHttpRequestManager.isDebugEnabled) {
              LOGGER.debug(
                  "Received asynchronous HTTP response for request "
                      + request.hashCode()
                      + " from a Talk2M API: failed");
            }

            // Execute the callback (if provided)
            if (callback != null) {
              callback.failed(ex);
            }
          }

          public void cancelled() {
            // Log the request cancellation (debug only)
            if (AsyncHttpRequestManager.isDebugEnabled) {
              LOGGER.debug(
                  "Received asynchronous HTTP response for request "
                      + request.hashCode()
                      + " from a Talk2M API: cancelled");
            }

            // Execute the callback (if provided)
            if (callback != null) {
              callback.cancelled();
            }
          }
        };

    // Log the request execution (debug only)
    if (AsyncHttpRequestManager.isDebugEnabled) {
      LOGGER.debug("Executing asynchronous HTTP response request: " + request.hashCode());
    }

    // Send the request
    return httpAsyncClient.execute(request, wrappedCallback);
  }

  /**
   * Sends the specified HTTP request to the desired Talk2M API asynchronously. The asynchronous
   * HTTP manager must be initialized before this method is called.
   *
   * @param request HTTP request to send
   * @return {@link Future} object representing the request
   * @since 1.0.0
   */
  public static Future<SimpleHttpResponse> sendAsyncRequest(SimpleHttpRequest request) {
    return sendAsyncRequest(request, null);
  }

  /**
   * Returns a boolean indicating if debug logging is enabled. This value corresponds with the
   * configured debug setting for the connector.
   *
   * @return {@code true} if debug logging is enabled, {@code false} otherwise
   * @since 1.0.0
   */
  public static boolean isDebugEnabled() {
    return isDebugEnabled;
  }
}

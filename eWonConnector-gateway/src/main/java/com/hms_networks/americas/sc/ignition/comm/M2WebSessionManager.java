package com.hms_networks.americas.sc.ignition.comm;

import com.hms_networks.americas.sc.ignition.comm.requests.m2w.M2WebLoginRequest;
import com.hms_networks.americas.sc.ignition.comm.requests.m2w.M2WebLogoutRequest;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebLoginResponse;
import com.hms_networks.americas.sc.ignition.comm.responses.m2w.M2WebLogoutResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for managing sessions with the M2Web APIs across one or multiple Talk2M servers.
 *
 * <p>This class can be used to login new sessions, logout existing sessions, and check the status
 * of existing sessions.
 *
 * <p>Support has been included for multiple/simultaneous sessions across different Talk2M servers.
 *
 * @since 2.0.0
 * @version 1.0.0
 * @author HMS Networks, MU Americas Solution Center
 */
public class M2WebSessionManager {

  /**
   * Log handler for {@link M2WebSessionManager}.
   *
   * @since 1.0.0
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(M2WebSessionManager.class);

  /**
   * The map of Talk2M servers to their session IDs. This is used to maintain a single session per
   * server.
   *
   * @since 1.0.0
   */
  private static final Map<String, String> m2wSessionIdMap = new HashMap<>();

  /**
   * Logs in to the specified Talk2M server using the specified authentication information.
   *
   * @param server the Talk2M server to login to
   * @param communicationAuthInfo the authentication information to use for the login
   * @since 1.0.0
   */
  public static void login(String server, CommunicationAuthInfo communicationAuthInfo) {
    // Create login request
    M2WebLoginRequest loginRequest = new M2WebLoginRequest(server, communicationAuthInfo);

    // Perform login request
    FutureCallback<SimpleHttpResponse> httpResponseFutureCallback = null;
    Future<SimpleHttpResponse> httpResponseFuture =
        loginRequest.doRequest(httpResponseFutureCallback);

    // Wait for login request to complete and parse
    try {
      SimpleHttpResponse loginRequestResponse = httpResponseFuture.get();
      try {
        // Parse response
        M2WebLoginResponse loginResponse =
            M2WebLoginResponse.getFromJson(loginRequestResponse.getBodyText());

        // Check if login was successful
        if (loginResponse.getSuccess()) {
          // Store resulting session ID
          m2wSessionIdMap.put(server, loginResponse.getT2msession());

          // Log debug message
          if (AsyncHttpRequestManager.isDebugEnabled()) {
            LOGGER.error("Successfully logged in to Talk2M server \"" + server + "\".");
          }
        } else {
          LOGGER.error(
              "Failed to login to Talk2M server \""
                  + server
                  + "\" because the API reported that the request was unsuccessful!");
        }

      } catch (Exception e) {
        LOGGER.error(
            "Failed to login to Talk2M server \""
                + server
                + "\" due to an error parsing the response!",
            e);
      }
    } catch (CancellationException e) {
      LOGGER.error("Failed to login to Talk2M server \"" + server + "\" due to cancellation!");
    } catch (InterruptedException e) {
      LOGGER.error(
          "Failed to login to Talk2M server \""
              + server
              + "\" due to an interruption while awaiting completion!");
    } catch (ExecutionException e) {
      LOGGER.error("Failed to login to Talk2M server \"" + server + "\" due to an exception!", e);
    }
  }

  /**
   * Logs out of the specified Talk2M server using the specified authentication information.
   *
   * @param server the Talk2M server to logout of
   * @param communicationAuthInfo the authentication information to use for the request
   * @since 1.0.0
   */
  public static void logout(String server, CommunicationAuthInfo communicationAuthInfo) {
    // If logged in, logout
    if (isLoggedIn(server)) {
      // Create logout request
      String logoutSessionId = m2wSessionIdMap.get(server);
      M2WebLogoutRequest logoutRequest =
          new M2WebLogoutRequest(server, logoutSessionId, communicationAuthInfo);

      // Perform logout request
      FutureCallback<SimpleHttpResponse> httpResponseFutureCallback = null;
      Future<SimpleHttpResponse> httpResponseFuture =
          logoutRequest.doRequest(httpResponseFutureCallback);

      // Wait for logout request to complete and parse
      try {
        SimpleHttpResponse logoutRequestResponse = httpResponseFuture.get();
        try {
          // Parse response
          M2WebLogoutResponse logoutResponse =
              M2WebLogoutResponse.getFromJson(logoutRequestResponse.getBodyText());

          // Check if logout was successful
          if (logoutResponse.getSuccess()) {
            // Log debug message
            if (AsyncHttpRequestManager.isDebugEnabled()) {
              LOGGER.error("Successfully logged out of Talk2M server \"" + server + "\".");
            }
          } else {
            LOGGER.error(
                "Failed to logout of Talk2M server \""
                    + server
                    + "\" because the API reported that the request was unsuccessful!");
          }
        } catch (Exception e) {
          LOGGER.error(
              "Failed to logout of Talk2M server \""
                  + server
                  + "\" due to an error parsing the response!",
              e);
        }
      } catch (CancellationException e) {
        LOGGER.error("Failed to logout of Talk2M server \"" + server + "\" due to cancellation!");
      } catch (InterruptedException e) {
        LOGGER.error(
            "Failed to logout of Talk2M server \""
                + server
                + "\" due to an interruption while awaiting completion!");
      } catch (ExecutionException e) {
        LOGGER.error(
            "Failed to logout of Talk2M server \"" + server + "\" due to an exception!", e);
      }

      // Remove session ID from map
      m2wSessionIdMap.remove(server);
    }
  }

  /**
   * Checks if the user is logged in to the specified server.
   *
   * @param server The server to check.
   * @return {@code true} if the user is logged in to the specified server, {@code false} otherwise.
   * @since 1.0.0
   */
  public static boolean isLoggedIn(String server) {
    return m2wSessionIdMap.containsKey(server);
  }

  /**
   * Gets the session ID for the specified server. If the login has not been performed, or a session
   * ID has not been stored, this method will perform a login request using the provided {@link
   * CommunicationAuthInfo}. If the login request fails, this method will return {@code null}.
   *
   * @param communicationAuthInfo The authentication information to use for the login request.
   * @param server The server to get the session ID for.
   * @return The session ID for the specified server.
   * @since 1.0.0
   */
  public static String getLoginSessionId(
      CommunicationAuthInfo communicationAuthInfo, String server) {
    // If not logged in, login
    if (!isLoggedIn(server)) {
      login(server, communicationAuthInfo);
    }

    // Return session ID
    return m2wSessionIdMap.get(server);
  }
}

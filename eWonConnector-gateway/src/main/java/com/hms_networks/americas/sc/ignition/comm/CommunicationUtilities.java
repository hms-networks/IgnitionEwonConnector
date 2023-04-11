package com.hms_networks.americas.sc.ignition.comm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleRequestBuilder;
import org.apache.hc.core5.http.NameValuePair;

/**
 * Class containing utility methods for performing communication tasks, such as creating HTTP post
 * requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class CommunicationUtilities {

  /**
   * Creates an HTTP post request with the given URL and parameters.
   *
   * @param httpPostRequestUrl URL of the request
   * @param httpPostRequestParams parameters of the request
   * @return HTTP post request with the given URL and parameters
   */
  public static SimpleHttpRequest createPostRequest(
      String httpPostRequestUrl, NameValuePair[] httpPostRequestParams) {
    return SimpleRequestBuilder.post(httpPostRequestUrl)
        .addParameters(httpPostRequestParams)
        .build();
  }

  /**
   * Converts the specified {@link Date} object to an ISO 8601-compatible date string (UTC).
   *
   * @param value {@link Date} object to convert
   * @return resulting ISO 8601-compatible date string (UTC)
   */
  public static String convertDateToIso8601String(Date value) {
    final TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
    final DateFormat iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    iso8601DateFormat.setTimeZone(utcTimeZone);
    return iso8601DateFormat.format(value);
  }
}

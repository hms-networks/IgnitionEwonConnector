package com.hms_networks.americas.sc.ignition.comm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

/**
 * Class containing utility methods for performing communication tasks, such as creating HTTP post
 * requests.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class CommunicationUtilities {

  /**
   * Sets the request body of an HTTP post request using the given string body and the default
   * encoding.
   *
   * @param httpPost HTTP post request to set the body of
   * @param httpPostRequestBody string body to set the request body to (i.e.
   *     "param1=value1&param2=value2")
   * @param httpPostCharset charset of the request (i.e. "utf-8")
   */
  public static void setPostRequestBody(
      HttpPost httpPost, String httpPostRequestBody, String httpPostCharset) {
    httpPost.setEntity(new StringEntity(httpPostRequestBody, httpPostCharset));
  }

  /**
   * Creates an HTTP post request with the given URL and body.
   *
   * @param httpPostRequestUrl URL of the request
   * @param httpPostRequestBody string body of the request (i.e. "param1=value1&param2=value2")
   * @param httpPostContentType content type of the request (i.e.
   *     "application/x-www-form-urlencoded")
   * @param httpPostCharset charset of the request (i.e. "utf-8")
   * @return HTTP post request with the given URL and body
   */
  public static HttpPost createPostRequest(
      String httpPostRequestUrl,
      String httpPostRequestBody,
      String httpPostContentType,
      String httpPostCharset) {
    HttpPost request = new HttpPost(httpPostRequestUrl);
    setPostRequestBody(request, httpPostRequestBody, httpPostCharset);
    request.setHeader(CommunicationConstants.HTTP_HEADER_KEY_CONTENT_TYPE, httpPostContentType);
    request.setHeader(CommunicationConstants.HTTP_HEADER_KEY_CHARSET, httpPostCharset);
    return request;
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

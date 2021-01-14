package com.hms_networks.americas.sc.ignition.comm;

import java.util.Map;

/**
 * Object containing the elements of an HTTP request to the Talk2M/M2Web APIs.
 *
 * @author HMS Networks, MU Americas Solution Center
 */
public class TMHttpRequest {

  /** HTTP request URL */
  private final String url;

  /** HTTP request headers */
  private final Map<String, String> headers;

  /** HTTP request body */
  private final String body;

  /**
   * Creates a new HTTP request object with the specified URL, headers and body.
   *
   * @param url HTTP request URL
   * @param headers HTTP request headers
   * @param body HTTP request body
   */
  public TMHttpRequest(String url, Map<String, String> headers, String body) {
    this.url = url;
    this.headers = headers;
    this.body = body;
  }

  /**
   * Gets the URL of the HTTP request
   *
   * @return HTTP request URL
   */
  public String getUrl() {
    return url;
  }

  /**
   * Gets the headers of the HTTP request
   *
   * @return HTTP request headers
   */
  public Map<String, String> getHeaders() {
    return headers;
  }

  /**
   * Gets the body of the HTTP request
   *
   * @return HTTP request body
   */
  public String getBody() {
    return body;
  }
}

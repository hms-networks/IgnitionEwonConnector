package com.hms_networks.americas.sc.ignition.comm;

/**
 * Class containing shared/common variables (constants) for the {@link
 * com.hms_networks.americas.sc.ignition.comm} package.
 *
 * @author HMS Networks, MU Americas Solution Center
 * @since 2.0.0
 */
public class CommunicationConstants {

  /** The HTTP header key for the content type. */
  public static final String HTTP_HEADER_KEY_CONTENT_TYPE = "Content-Type";

  /** The HTTP header key for the charset. */
  public static final String HTTP_HEADER_KEY_CHARSET = "Charset";

  /** The HTTP header value for the content type of POST requests to the M2Web API. */
  public static final String M2WEB_POST_REQUEST_CONTENT_TYPE = "application/x-www-form-urlencoded";

  /** The HTTP header value for the charset of POST requests to the M2Web API. */
  public static final String M2WEB_POST_REQUEST_CHARSET = "utf-8";

  /** The HTTP header value for the content type of POST requests to the DMWeb API. */
  public static final String DMWEB_POST_REQUEST_CONTENT_TYPE = "application/x-www-form-urlencoded";

  /** The HTTP header value for the charset of POST requests to the DMWeb API. */
  public static final String DMWEB_POST_REQUEST_CHARSET = "utf-8";
}

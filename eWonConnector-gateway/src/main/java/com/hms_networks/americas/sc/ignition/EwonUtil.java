package com.hms_networks.americas.sc.ignition;

import com.inductiveautomation.ignition.common.Base64;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataQuality;
import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;
import org.apache.commons.lang3.StringUtils;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.text.DateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Various utilities used across EwonConnector to facilitate functionality */
public class EwonUtil {
  /** Default timeout for HTTP connections */
  private static final int defaultConnectTimeout = 10000;

  /** Default timeout for HTTP connection reading */
  private static final int defaultReadTimeout = 60000;

  /** Log handler */
  private static Logger logger = LoggerFactory.getLogger("Ewon.EwonUtil");

  /** Trust manager used for HTTP connections. Note: Bypasses trust verification. */
  private static final TrustManager[] BYPASS_TRUST_MGR =
      new TrustManager[] {new TrustAllX509TrustManager()};

  /** Hostname verifier used for HTTP connections. Note: Bypasses hostname verification. */
  private static final HostnameVerifier BYPASS_HOSTNAME =
      new HostnameVerifier() {

        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      };

  /**
   * Convert a data quality string to DataQuality object
   *
   * @param value string representation of data quality
   * @return DataQuality object from given value
   */
  public static DataQuality toQuality(String value) {
    switch (value) {
      case "good":
        return DataQuality.GOOD_DATA;
      case "unknown":
        return DataQuality.OPC_UNCERTAIN;
      default:
        return DataQuality.OPC_BAD_DATA;
    }
  }

  /**
   * Convert ISO 8601 date string to Date object
   *
   * @param value string representation of ISO 8601 Date
   * @return Date object from given value
   */
  public static Date toDate(String value) {
    // http://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
    // return javax.xml.bind.DatatypeConverter.parseDateTime(value).getTime();

    // updated to now work in Java 11
    return Date.from(java.time.Instant.parse(value));
  }

  /**
   * Convert DataType string to DataType object
   *
   * @param value string representation of DataType
   * @return DataType object from given value
   */
  public static DataType toDataType(String value) {
    if (value == null) {
      return DataType.Int4;
    }
    switch (value.toLowerCase()) {
      case "int":
        return DataType.Int4;
      case "float":
        return DataType.Float8;
      case "bool":
        return DataType.Boolean;
      case "string":
        return DataType.String;
      default:
        return DataType.Int4;
    }
  }

  /**
   * Convert Date object to ISO 8601 date string
   *
   * @param value Date object to convert
   * @return ISO 8601 date string from given value
   */
  public static String toString(Date value) {
    // Calendar c = GregorianCalendar.getInstance();
    // c.setTime(value);
    // return javax.xml.bind.DatatypeConverter.printDateTime(c);

    TimeZone tz = TimeZone.getTimeZone("UTC");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
    df.setTimeZone(tz);
    return df.format(value);
  }

  /**
   * Perform an HTTP GET on given URL, and return result.
   *
   * @param url URL to perform HTTP GET on
   * @return the return data from HTTP GET
   * @throws Exception if HTTP connection fails
   */
  public static String httpGet(String url) throws Exception {
    int connectTimeout = defaultConnectTimeout;
    int readTimeout = defaultReadTimeout;
    Boolean bypassCertValidation = false;

    HttpURLConnection con = null;
    try {
      // Attempt to create HTTP connection with given URL string
      con =
          setupHttpConnection(
              url, "GET", connectTimeout, readTimeout, null, null, null, bypassCertValidation);

      // Configure HTTP connection settings
      con.setDoOutput(false);
      con.setUseCaches(false);

      // Create Reader to read connection data and
      // Create StringBuilder to build HTTP GET return data
      Reader reader = new InputStreamReader(con.getInputStream());
      StringBuilder sb = new StringBuilder();

      // Create buffer for storing data from Reader and
      // Create counter for characters read to track end of data from Reader
      char[] buf = new char[1024];
      int charsRead = 0;

      while ((charsRead = reader.read(buf)) != -1) {
        sb.append(buf, 0, charsRead);
      }

      // Close Reader and return resulting HTTP GET data
      reader.close();
      return sb.toString();
    } catch (IOException e) {
      final int HTTP_UNAUTHORIZED = 401;
      final int HTTP_NOT_FOUND = 404;

      int httpResponse = HTTP_NOT_FOUND;

      // Set the HTTP error code based off the response
      try {
        httpResponse = con.getResponseCode();
      } catch (UnknownHostException he) {
        httpResponse = HTTP_NOT_FOUND;
      }

      // Generate a "cleaned" exception based on the response code.
      if (httpResponse == HTTP_UNAUTHORIZED) {
        logger.error(
            "The configured Talk2M account credentials are invalid! "
                + "Verify your account name, username, password and developer ID are "
                + "correctly entered on the Ewon Connector configuration page.");
        throw new IOException("Authentication Error: Please check your account credentials.");
      } else if (httpResponse == HTTP_NOT_FOUND) {
        throw new IOException("Network Error: Ewon services not found. Check network link.");
      } else {
        throw new IOException("HTTP request failed. HTTP Error: " + httpResponse + ".");
      }
    } finally {
      try {
        // Close HTTP connection if possible
        if (con != null) {
          con.disconnect();
        }
      } catch (Exception ignore) {
      }
    }
  }

  /**
   * Setup and Create an HTTP Connection with Given Parameters
   *
   * @param url URL for connection
   * @param method HTTP connection method (i.e. GET, POST, PUT)
   * @param connectTimeout timeout for connection
   * @param readTimeout timeout for reading data
   * @param username username for HTTP basic authentication (may be blank
   * @param password password for HTTP basic authentication
   * @param headerValues HTTP header contents
   * @param bypassCertValidation true/false for HTTP connection certificate validation
   * @return a new HTTP connection based on given parameters
   * @throws Exception if unable to create HTTP connection with given parameters
   */
  private static HttpURLConnection setupHttpConnection(
      String url,
      String method,
      int connectTimeout,
      int readTimeout,
      String username,
      String password,
      Map<String, String> headerValues,
      boolean bypassCertValidation)
      throws Exception {

    // Create new HttpURLConnection with specified URL
    HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();

    // Configure connection to bypass certificate validation if specified
    if (bypassCertValidation && con instanceof HttpsURLConnection) {
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, BYPASS_TRUST_MGR, null);
      ((HttpsURLConnection) con).setSSLSocketFactory(sc.getSocketFactory());
      ((HttpsURLConnection) con).setHostnameVerifier(BYPASS_HOSTNAME);
    }

    // Set request method for created connection
    try {
      con.setRequestMethod(method);
    } catch (ProtocolException e) {
    }

    // Set timeout values for connection and
    // Configure connection for input and output
    con.setConnectTimeout(connectTimeout);
    con.setReadTimeout(readTimeout);
    con.setDoOutput(true);
    con.setDoInput(true);

    // Set connection authorization configuration if username/password is specified.
    if (!StringUtils.isBlank(username)) {
      String authToken =
          Base64.encodeBytes((username + ":" + password).getBytes()).replace("\n", "");
      con.setRequestProperty("Authorization", "Basic " + authToken);
    }

    // Set connection header values with supplied values, if any
    if (headerValues != null) {
      for (Entry<String, String> e : headerValues.entrySet()) {
        con.setRequestProperty(e.getKey(), e.getValue());
      }
    }

    // Return created and configured HttpURLConnection
    return con;
  }

  /** Implementation of X509TrustManager that trusts everything */
  protected static class TrustAllX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {}

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
        throws CertificateException {}

    @Override
    public X509Certificate[] getAcceptedIssuers() {
      return null;
    }
  }
}

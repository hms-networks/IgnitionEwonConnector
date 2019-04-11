package org.imdc.ewon;

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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.text.DateFormat;

public class EwonUtil {
	private static final int defaultConnectTimeout = 10000;
	private static final int defaultReadTimeout = 60000;
	private final static TrustManager[] BYPASS_TRUST_MGR = new TrustManager[] { new TrustAllX509TrustManager() };
	private final static HostnameVerifier BYPASS_HOSTNAME = new HostnameVerifier() {

		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public static DataQuality toQuality(String value) {
		switch(value){
			case "good":
				return DataQuality.GOOD_DATA;
			case "unknown":
				return DataQuality.OPC_UNCERTAIN;
			default:
					return DataQuality.OPC_BAD_DATA;
		}
	}

	/** Converts a string to an ISO 8601 Date **/
	public static Date toDate(String value) {
		// http://stackoverflow.com/questions/2201925/converting-iso-8601-compliant-string-to-java-util-date
		// return javax.xml.bind.DatatypeConverter.parseDateTime(value).getTime();

		// updated to now work in Java 11
		return Date.from(java.time.Instant.parse(value));
	}

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
		default:
			return DataType.Int4;
		}
	}

	/**
	 * Converts a date to an ISO 8601 string.
	 *
	 * @param value
	 * @return
	 */
	public static String toString(Date value) {
		//Calendar c = GregorianCalendar.getInstance();
		//c.setTime(value);
		//return javax.xml.bind.DatatypeConverter.printDateTime(c);

		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
		df.setTimeZone(tz);
		return df.format(value);
	}

	public static String httpGet(String url) throws Exception {
		int connectTimeout = defaultConnectTimeout;
		int readTimeout = defaultReadTimeout;
		Boolean bypassCertValidation = true; // args.getBooleanArg("bypassCertValidation", Boolean.FALSE);

		HttpURLConnection con = null;
		try {
			con = setupHttpConnection(url, "GET", connectTimeout, readTimeout, null, null, null, bypassCertValidation);

			con.setDoOutput(false);
			con.setUseCaches(false);

			Reader reader = new InputStreamReader(con.getInputStream());
			StringBuilder sb = new StringBuilder();

			char[] buf = new char[1024];
			int charsRead = 0;

			while ((charsRead = reader.read(buf)) != -1) {
				sb.append(buf, 0, charsRead);
			}
			reader.close();
			return sb.toString();
		} finally {
			try {
				if (con != null) {
					con.disconnect();
				}
			} catch (Exception ignore) {
			}
		}
	}

	private static HttpURLConnection setupHttpConnection(String url, String method, int connectTimeout, int readTimeout,
	        String username, String password, Map<String, String> headerValues, boolean bypassCertValidation)
	        throws Exception {

		HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();

		if (bypassCertValidation && con instanceof HttpsURLConnection) {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, BYPASS_TRUST_MGR, null);
			((HttpsURLConnection) con).setSSLSocketFactory(sc.getSocketFactory());
			((HttpsURLConnection) con).setHostnameVerifier(BYPASS_HOSTNAME);
		}

		try {
			con.setRequestMethod(method);
		} catch (ProtocolException e) {
		}

		con.setConnectTimeout(connectTimeout);
		con.setReadTimeout(readTimeout);
		con.setDoOutput(true);
		con.setDoInput(true);

		// We support basic http auth if a username/password is specified.
		if (!StringUtils.isBlank(username)) {
			String authToken = Base64.encodeBytes((username + ":" + password).getBytes()).replace("\n", "");
			con.setRequestProperty("Authorization", "Basic " + authToken);
		}

		if (headerValues != null) {
			for (Entry<String, String> e : headerValues.entrySet()) {
				con.setRequestProperty(e.getKey(), e.getValue());
			}
		}

		return con;
	}

	protected static class TrustAllX509TrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}
}

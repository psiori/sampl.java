package com.fivedlab.sample.sample_java;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Sample {

	private static String endpoint = "https://events.neurometry.com/sample/v01/event";
	private static String appToken = "";
	private static boolean serverSide = true;
	private static String sdk = "sample.java";

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	private static boolean disableSSLAuthentification = true;

	public static String getEndpoint() {
		return Sample.endpoint;
	}


	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public static void setDateFormat(SimpleDateFormat dateFormat) {
		Sample.dateFormat = dateFormat;
	}

	public static void setEndpoint(String endpoint) {
		if (endpoint != null) {
			Sample.endpoint = endpoint;
		}
	}
	
	public static String getAppToken() {
		return Sample.appToken;
	}

	public static void setAppToken(String appToken) {
		if (appToken != null) {
			Sample.appToken = appToken;
		}
	}

	public static boolean isServerSide() {
		return serverSide;
	}

	public static void setServerSide(boolean serverSide) {
		Sample.serverSide = serverSide;
	}

	public static String getSdk() {
		return Sample.sdk;
	}

	public static String getSdkVersion() {
		return Version.version;
	}

	@SuppressWarnings(value = { "rawtypes" })
	public static String track(String eventName) {
		return performTracking(eventName, "custom", new HashMap());
	}

	@SuppressWarnings(value = { "rawtypes" })
	public static String track(String eventName, String eventCategory, Map args) {
		return performTracking(eventName, eventCategory, args);
	}

	@SuppressWarnings(value = { "rawtypes", "unchecked" })
	private static String performTracking(String eventName,
			String eventCategory, Map args) {

		try {
			if (disableSSLAuthentification) {
				// Install the all-trusting trust manager
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc
						.getSocketFactory());

				// Install the all-trusting host verifier
				HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			}

			URL url = new URL(endpoint);

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/json; charset=utf8");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			JSONObject topData = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("sdk", sdk);
			data.put("sdk_version", getSdkVersion());
			data.put("app_token", appToken);
			data.put("server_side", serverSide);
			data.put("event_name", eventName);
			data.put("event_category", eventCategory);
			data.put("timestamp", dateFormat.format(new Date()));
			if (args != null) {
				Iterator iter = args.keySet().iterator();
				while (iter.hasNext()) {
					String key = (String) iter.next();
					Object content = args.get(key);
					if (content instanceof Date ) {
						content = dateFormat.format((Date)content);
					}
					data.put(key, content);
				}
			}

			topData.put("p", data);

			
			String output = null;
			// Hack to run it on Android
			try {
				topData.getClass().getMethod("write", Writer.class);
				StringWriter out = new StringWriter();
				topData.write(out);
				output = out.toString();
			} catch (NoSuchMethodException e) {
				output = topData.toString();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(output);

			wr.flush();
			wr.close();

			return String.valueOf(conn.getResponseCode());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Disables the ssl authentification
	 */
	// Create a trust manager that does not validate certificate chains
	private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
			// TODO Auto-generated method stub

		}
	} };

	// Create all-trusting host name verifier
	private static HostnameVerifier allHostsValid = new HostnameVerifier() {
		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}
	};
}

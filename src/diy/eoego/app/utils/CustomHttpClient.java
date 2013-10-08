package diy.eoego.app.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;

public class CustomHttpClient {
	
	private static final String CHARSET_UTF8 = HTTP.UTF_8;
	
	private static HttpClient customerHttpClient;
	
	private CustomHttpClient() {}

	public static String getFromWebByHttpClient(Context context, String strUrl,
			NameValuePair... nameValuePairs) throws Exception {
		// "http://192.168.1.110:8080/httpget.jsp?par=HttpClient_android_Get";
		StringBuilder sb = new StringBuilder();
		sb.append(strUrl);
		if (nameValuePairs != null && nameValuePairs.length > 0) {
			sb.append("?");
			for (int i=0; i<nameValuePairs.length; i++) {
				if (i > 0) {
					sb.append("&");
				}
				sb.append(String.format("%s=%s", nameValuePairs[i].getClass(), nameValuePairs[i].getValue()));
			}
		}
		
		HttpGet httpRequest = new HttpGet(sb.toString());
		HttpClient httpClient = getHttpClient(context);
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		
		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new RuntimeException();
		}
		
		return EntityUtils.toString(httpResponse.getEntity());
	}
	
	private static synchronized HttpClient getHttpClient(Context context) {
		if (null == customerHttpClient) {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, CHARSET_UTF8);
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setUserAgent(params, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
					+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			
			ConnManagerParams.setTimeout(params, 1000);
			int connectionTimeOut = 3000;
			/*if (!HttpUtils.isWifiDataEnable(context)) {
				connectionTimeOut = 10000;
			}*/
			HttpConnectionParams.setConnectionTimeout(params, connectionTimeOut);
			HttpConnectionParams.setSoTimeout(params, 4000);
			
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 443));
			
			ClientConnectionManager conman = new ThreadSafeClientConnManager(params, schReg);
			
			customerHttpClient = new DefaultHttpClient(conman, params);
			
		}
		return customerHttpClient;
	}

}

package diy.eoego.app.utils;

import org.apache.http.NameValuePair;

import android.content.Context;

public class HttpUtils {

	public static String getByHttpClient(Context context, String strUrl, 
			NameValuePair... nameValuePairs) throws Exception {
		return CustomHttpClient.getFromWebByHttpClient(context, strUrl, nameValuePairs);
	}
}

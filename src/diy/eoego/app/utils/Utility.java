package diy.eoego.app.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Utility {
	
	private static char sHexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static String getScreenParams(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return "&screen=" +
				(dm.heightPixels > dm.widthPixels ? 
						dm.widthPixels + "*" + dm.heightPixels : 
						dm.heightPixels + "*" + dm.widthPixels);
	}
	
	public static String hexString(byte[] source) {
		if (source == null || source.length <= 0) {
			return "";
		}

		final int size = source.length;
		final char str[] = new char[size * 2];
		int index = 0;
		byte b;
		for (int i = 0; i < size; i++) {
			b = source[i];
			str[index++] = sHexDigits[b >>> 4 & 0xf];
			str[index++] = sHexDigits[b & 0xf];
		}
		return new String(str);
	}
}

package diy.eoego.app.utils;

import android.os.Environment;

public class CommonUtil {

	
	public static boolean sdCardIsAvailable() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}
}

package diy.eoego.app.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class Utility {
	public static String getScreenParams(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return "&screen=" +
				(dm.heightPixels > dm.widthPixels ? 
						dm.widthPixels + "*" + dm.heightPixels : 
						dm.heightPixels + "*" + dm.widthPixels);
	}
}

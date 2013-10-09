package diy.eoego.app.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import diy.eoego.app.R;
import diy.eoego.app.db.DBHelper;

public class ImageUtil {
	
	private static final int dayCount = 15;
	private static final long CLEARTIME = dayCount * 24 * 60 * 60 * 1000;
	
	private static final int default_img = R.drawable.bg_load_default;
	
	private static LinkedHashMap<String, SoftReference> imageCache = new LinkedHashMap<String, SoftReference>(20);
	
	public interface ImageCallback {
		public void loadImage(Bitmap bitmap, String imagePath);
	}
	
	public static void setThumbnailView(String imageUrl, ImageView image, 
			Context context, ImageCallback callback, boolean b) {
		DBHelper dbHelper = DBHelper.getInstance(context);
		String md5 = MD5.encode(imageUrl);
		
		if (!CommonUtil.sdCardIsAvailable()) {
			String cachePath = context.getCacheDir().getAbsolutePath() + "/" + md5;
			setThumbnailImage(image, imageUrl, cachePath, dbHelper, callback, b);
			image.setTag(cachePath);
		} else {
			String imagePath = getExternalCacheDir(context) + File.separator + md5;
			setThumbnailImage(image, imageUrl, imagePath, dbHelper, callback, b);
			image.setTag(imagePath);
		}
		
	}
	
	@SuppressLint("NewApi")
	public static String getExternalCacheDir(Context context) {
		// android 2.2 以后才支持的特性
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir().getPath() + File.separator
					+ "img";
		}

		// Before Froyo we need to construct the external cache dir ourselves
		// 2.2以前我们需要自己构造
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/img/";
		return Environment.getExternalStorageDirectory().getPath() + cacheDir;
	}
	
	public static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}
	
	private static void setThumbnailImage(ImageView image, String imageUrl,
			String cachePath, DBHelper dbHelper, ImageCallback callback,
			boolean b) {
		Bitmap bitmap = loadThumbnailImage(cachePath, imageUrl, dbHelper, callback, b);
		if (bitmap == null) {
			image.setImageResource(default_img);
		} else {
			image.setImageBitmap(bitmap);
		}
	}

	private static Bitmap loadThumbnailImage(final String imagePath, final String imageUrl,
			final DBHelper dbHelper, final ImageCallback callback, final boolean b) {
		if (imageCache.containsKey(imageUrl)) {
			SoftReference reference = imageCache.get(imageUrl);
			Bitmap bitmap = (Bitmap) reference.get();
			if (bitmap != null) return bitmap;
		}
		
		Bitmap bm = getImageFromDB(imagePath, imageUrl, dbHelper);
		if (bm != null) {
			return bm;
		} else {
			final Handler handler = new Handler() {
				public void handleMessage(Message msg) {
					if (msg.obj != null) {
						callback.loadImage((Bitmap)msg.obj, imagePath);
					}
				}
			};
			
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					URL url;
					try {
						url = new URL(imageUrl);
					
						URLConnection conn = url.openConnection();
						conn.setConnectTimeout(5000);
						conn.setReadTimeout(5000);
						conn.connect();
						
						InputStream in = conn.getInputStream();
						BitmapFactory.Options options = new Options();
						options.inSampleSize = 1;
						Bitmap bitmap = BitmapFactory.decodeStream(in, new Rect(0, 0, 0, 0), options);
						
						imageCache.put(imageUrl, new SoftReference(bitmap));
						
						Message msg = handler.obtainMessage();
						msg.obj = bitmap;
						handler.sendMessage(msg);
						
						if (bitmap != null) {
							//saveImage(imagePath, bitmap);
							//saveImageByDB(imageUrl, dbHelper);
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		}
		
		return null;
	}

	private static Bitmap getImageFromDB(String imagePath, String imageUrl,
			DBHelper dbHelper) {
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

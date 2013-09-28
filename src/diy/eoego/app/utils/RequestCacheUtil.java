package diy.eoego.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import org.apache.http.util.EncodingUtils;

import diy.eoego.app.config.Configs;
import diy.eoego.app.config.Constants;
import diy.eoego.app.db.DBHelper;
import diy.eoego.app.db.RequestCacheColumn;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Environment;

public class RequestCacheUtil {
	
	private static LinkedHashMap<String, SoftReference<String>> requestCache = 
			new LinkedHashMap<String, SoftReference<String>>(20);
	
	public static String getRequestContent(Context context, String requestUrl, 
			String source_type, String content_type, boolean useCache) {
		DBHelper dbHelper = DBHelper.getInstance(context);
		String md5 = MD5.encode(requestUrl);
		
		if (!CommonUtil.sdCardIsAvailable()) {
			String cachePath = context.getCacheDir().getAbsolutePath() + File.separator + md5;
			return getCacheRequest(context, requestUrl, cachePath, source_type, content_type, dbHelper, useCache);
		} else {
			String imagePath = getExternalCacheDir(context) + File.separator + md5; //sdcard
			return getCacheRequest(context, requestUrl, imagePath, source_type, content_type, dbHelper, useCache);
		}
	}
	
	private static String getExternalCacheDir(Context context) {
		//after android 2.2
		if (hasExternalCacheDir()) {
			return context.getExternalCacheDir().getParent() + File.separator + "request";
		}
		final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/request/";
		return Environment.getExternalStorageDirectory().getParent() + cacheDir;
	}
	
	private static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}
	
	private static String getCacheRequest(Context context, String requestUrl, String imagePath,
			String source_type, String content_type, DBHelper dbHelper, boolean useCache) {
		String result = "";
		if (useCache) {
			//memory
			result = getStringFromSoftReference(requestUrl);
			if (result != null && !result.equals("")) {
				return result;
			}
			//disk
			result = getStringFromLocal(imagePath, requestUrl, dbHelper);
			if (result != null && !result.equals("")) {
				putStringToSoftReference(requestUrl, result);
				return result;
			}
		}
		result = getStringFromWeb(context, imagePath, requestUrl,
				source_type, content_type, dbHelper);
		
		return result;
	}
	
	private static String getStringFromWeb(Context context, String requestPath,
			String requestUrl, String source_type, String content_type,
			DBHelper dbHelper) {
		String result = "";
		//result = HttpUtils.get....
		if (result == null || "".equals(result)) {
			return result;
		}
		
		Cursor cursor = getStringFromDB(requestUrl, dbHelper);
		updateDB(cursor, requestUrl, source_type, content_type, dbHelper);
		saveFileByRequestPath(requestPath, result);
		putStringToSoftReference(requestUrl, result);
		return result;
	}
	
	private static void saveFileByRequestPath(String requestPath, String result) {
		deleteFileFromLocal(requestPath);
		saveFileForLocal(requestPath, result);
	}
	
	private static void saveFileForLocal(String requestPath, String result) {
		File file = new File(requestPath);
		if (!file.exists()) {
			try {
				File parentFile = file.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = result.getBytes();
				fos.write(buffer);
				fos.flush();
				fos.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void updateDB(Cursor cursor, String requestUrl,
			String source_type, String content_type, DBHelper dbHelper) {
		if (cursor.moveToFirst()) {
			//update
			int id = cursor.getInt(cursor.getColumnIndex(RequestCacheColumn._ID));
			long timestamp = System.currentTimeMillis();
			String sql = "update " + RequestCacheColumn.TABLE_NAME + " set " +
					RequestCacheColumn.Timestamp + "=" + timestamp +
					" where " + RequestCacheColumn._ID + "=" + id;
			dbHelper.ExecSQL(sql);
		} else {
			//add
			String sql = "inset into " + RequestCacheColumn.TABLE_NAME + "(" +
					RequestCacheColumn.URL + "," + 
					RequestCacheColumn.SOURCE_TYPE + "," + 
					RequestCacheColumn.Content_type + "," + 
					RequestCacheColumn.Timestamp + ")" +
					"values ('" + requestUrl + "','" + source_type + "','" + content_type + "','"
					+ System.currentTimeMillis() + "')";
			dbHelper.ExecSQL(sql);
		}
	}
	
	private static void putStringToSoftReference(String requestUrl, String result) {
		SoftReference<String> reference = new SoftReference<String>(result);
		requestCache.put(requestUrl, reference);
	}
	
	private static String getStringFromSoftReference(String requestUrl) {
		if (requestCache.containsKey(requestUrl)) {
			SoftReference<String> reference = requestCache.get(requestUrl);
			final String result = (String) reference.get();
			if (result != null && !result.equals("")) {
				return result;
			}
		}
		return "";
	}
	
	private static String getStringFromLocal(String imagePath, String requestUrl, DBHelper dbHelper) {
		String result = "";
		Cursor cursor = getStringFromDB(requestUrl, dbHelper);
		if (cursor.moveToFirst()) {
			Long timestamp = cursor.getLong(cursor.getColumnIndex(RequestCacheColumn.Timestamp));
			String contentType = cursor.getString(cursor.getColumnIndex(RequestCacheColumn.Content_type));
			long span = getSpanTimeFromConfigs(contentType);
			long nowTime = System.currentTimeMillis();
			if ((nowTime - timestamp) > span * 60 * 1000) {
				deleteFileFromLocal(imagePath);
			} else {
				result = getFileFromLocal(imagePath);
			}
		}
		return result;
	}
	
	private static Cursor getStringFromDB(String requestUrl, DBHelper dbHelper) {
		String sql = "select * from " + RequestCacheColumn.TABLE_NAME +
				" where " + RequestCacheColumn.URL + "='" + requestUrl + "'";
		return dbHelper.rawQuery(sql, new String[]{});
	}
	
	private static long getSpanTimeFromConfigs(String contentType) {
		long span = 0;
		if (contentType.equals(Constants.DBContentType.Content_list)) {
			span = Configs.Content_ListCacheTime;
		} else if (contentType.equals(Constants.DBContentType.Content_content)) {
			span = Configs.Content_ContentCacheTime;
		} else if (contentType.equals(Constants.DBContentType.Discuss)) {
			span = Configs.DiscussCacheTime;
		} else {
			span = Configs.Content_DefaultCacheTime;
		}
		return span;
	}
	
	private static String getFileFromLocal(String imagePath) {
		File file = new File(imagePath);
		String result = "";
		if (file.exists()) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
				
				int length = fis.available();
				byte[] buffer = new byte[length];
				fis.read(buffer);
				result = EncodingUtils.getString(buffer, "UTF-8");
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private static void deleteFileFromLocal(String imagePath) {
		File file = new File(imagePath);
		if (file.exists()) {
			file.delete();
		}
	}
	
}

package diy.eoego.app.db;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;

public class ImageCacheColumn extends DatabaseColumn {

	public final static String TABLE_NAME = "imageCache";
	public final static String TIMESTAMP = "timestamp";
	public final static String Url = "url";
	/**
	 * ��λ����
	 */
	public final static String PAST_TIME = "past_time";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + TABLE_NAME);
	private static final Map<String, String> mColumnMap = new HashMap<String, String>();
	static {

		mColumnMap.put(_ID, "integer primary key autoincrement");
		mColumnMap.put(TIMESTAMP, "TimeStamp");
		mColumnMap.put(Url, "text");
		mColumnMap.put(PAST_TIME, "TimeStamp");
	}
	

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Uri getTableContent() {
		return CONTENT_URI;
	}

	@Override
	protected Map<String, String> getTableMap() {
		return mColumnMap;
	}

}

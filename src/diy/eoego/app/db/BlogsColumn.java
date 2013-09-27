package diy.eoego.app.db;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;

public class BlogsColumn extends DatabaseColumn {
	
	public static final String TABLE_NAME = "news";
	public static final String UPDATE_TIME = "update_time";
	public static final String JSON_PATH = "json_path";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
													//content://diy.eoego.app.provider/news
	
	private static final Map<String, String> mColumnMap = new HashMap<String, String>();
	static {
		mColumnMap.put(_ID, "integer primary key autoincrement");
		mColumnMap.put(UPDATE_TIME, "timestamp");
		mColumnMap.put(JSON_PATH, "text not null");
	}
	

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Map<String, String> getTableMap() {
		return mColumnMap;
	}

	@Override
	public Uri getTableContent() {
		return CONTENT_URI;
	}

}

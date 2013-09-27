package diy.eoego.app.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DBProvider extends ContentProvider {
	private static final int NEWS = 1;
	private static final int BLOGS = 2;
	
	private DBHelper mDBHelper = null;
	private static final UriMatcher URI_MATCHER;
	
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(DatabaseColumn.AUTHORITY, NewsColumn.TABLE_NAME, NEWS);
		URI_MATCHER.addURI(DatabaseColumn.AUTHORITY, BlogsColumn.TABLE_NAME, BLOGS);
	}
	
	@Override
	public boolean onCreate() {
		mDBHelper = DBHelper.getInstance(getContext());
		return mDBHelper.getReadableDatabase() == null ? false : true;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int witch = URI_MATCHER.match(uri);
		switch (witch) {
		case NEWS:
			break;
		case BLOGS:
			insert(BlogsColumn.TABLE_NAME, null, values);
			break;
		}
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}
	
	private long insert(String table, String nullColumnHack, ContentValues values) {
		synchronized(mDBHelper) {
			SQLiteDatabase db = mDBHelper.getWritableDatabase();
			if (db == null || !db.isOpen()) {
				return 0;
			}
			return db.insert(table, nullColumnHack, values);
		}
	}

}

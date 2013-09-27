package diy.eoego.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "eoego";
	private static final int DB_VERSION = 2;
	
	private SQLiteDatabase db;
	private static DBHelper mdbHelper;
	
	public static DBHelper getInstance(Context context) {
		if (mdbHelper == null) {
			mdbHelper = new DBHelper(context);
		}
		return mdbHelper;
	}
	
	private DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		operateTable(db, "");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion == newVersion) {
			return;
		}
		operateTable(db, "DRAOP TABLE IF EXISTS ");
		onCreate(db);
	}
	
	public void operateTable(SQLiteDatabase db, String actionString) {
		Class<DatabaseColumn>[] columnClasses = DatabaseColumn.getSubClasses();
		DatabaseColumn columns = null;
		for(int i=0; i<columnClasses.length; i++) {
			try {
				columns = columnClasses[i].newInstance();
				if ("".equals(actionString) || actionString == null) {
					db.execSQL(columns.getTableCreator());
				} else {
					db.execSQL(actionString + columns.getTableName());
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public long insert(String table, ContentValues values) {
		if (db == null) {
			db = getWritableDatabase();
		}
		return db.insert(table, null, values);
	}
	
	public int delete(String table, int id) {
		if (db == null) {
			db = getWritableDatabase();
		}
		return db.delete(table, BaseColumns._ID + "=?", new String[]{ String.valueOf(id) });
	}
	
	public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
		if (db == null) {
			db = getWritableDatabase();
		}
		return db.update(table, values, whereClause, whereArgs);
	}
	
	public Cursor query(String table, String[] columns, String whereStr,
			String[] whereArgs) {
		if (db == null) {
			db = getReadableDatabase();
		}
		return db.query(table, columns, whereStr, whereArgs, null, null, null);
	}
	
	public Cursor rawQuery(String sql, String[] args) {
		if (db == null) {
			db = getReadableDatabase();
		}
		return db.rawQuery(sql, args);
	}
	
	public void ExecSQL(String sql) {
		if (db == null) {
			db = getWritableDatabase();
		}
		db.execSQL(sql);
	}
	
	public void closeDb() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

}

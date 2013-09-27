package diy.eoego.app.db;

import java.util.ArrayList;
import java.util.Map;

import android.net.Uri;
import android.provider.BaseColumns;

public abstract class DatabaseColumn implements BaseColumns {
	
	public static final String AUTHORITY = "diy.eoego.app.provider";
	
	public static final String DATABASE_NAME = "eoego.db";
	
	public static final int DATABASE_VERSION = 1;
	
	public static final String[] SUBCLASSES = new String[] {
		"diy.eoego.app.db.BlogColumn", "diy.eoego.app.db.NewsColumn",
		"diy.eoego.app.db.DetailColumn", "diy.eoego.app.db.ImageCacheColumn",
		"diy.eoego.app.db.RequestCacheColumn"
	};
	
	public String getTableCreator() {
		return getTableCreator(getTableName(), getTableMap());
	}
	
	@SuppressWarnings("unchecked")
	public static final Class<DatabaseColumn>[] getSubClasses() {
		ArrayList<Class<DatabaseColumn>> classes = new ArrayList<Class<DatabaseColumn>>();
		Class<DatabaseColumn> subClass = null;
		for (int i = 0; i < SUBCLASSES.length; i++) {
			try {
				subClass = (Class<DatabaseColumn>) Class.forName(SUBCLASSES[i]);
				classes.add(subClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classes.toArray(new Class[0]);
	}
	
	private static final String getTableCreator(String tableName, Map<String, String> map) {
		// create table blogs (_ID interger primary key autoincrement,
		//						update_time localtime,
		//						json_path text no null
		//					);
		String keys[] = map.keySet().toArray(new String[0]);
		int length = keys.length;
		String value = null;
		StringBuilder creator = new StringBuilder();
		
		creator.append("CREATE TABLE ").append(tableName).append("(");
		for (int i=0; i < length; i++) {
			value = map.get(keys[i]);
			creator.append(keys[i]).append(" ");
			creator.append(value);
			if (i < length -1) {
				creator.append(",");
			}
		}
		creator.append(")");
		
		return creator.toString();
	}
	
	abstract public String getTableName();
	abstract public Uri getTableContent();
	abstract public Map<String, String> getTableMap();
}

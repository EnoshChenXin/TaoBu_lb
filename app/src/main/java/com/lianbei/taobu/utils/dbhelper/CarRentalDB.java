package com.lianbei.taobu.utils.dbhelper;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SqlLite 地图页面站点数据管理工具SqlLite
 * 
 * @author HASEE
 * 
 */
public class CarRentalDB {
	private static final String TAG = "CarRentalDB";
	/*
	 * station_database name
	 */
	private static final String DATABASE_NAME = "CarRentalDB";

	/*
	 * statcion_database version
	 */
	public static final int DATABASE_VERSION = 1;
	/*
	 * Station Operation area Table Name
	 */

	private static final String OPERATION_AREA_TABLE = "operation_area_tb";
	/*
	 * Station Table Name
	 */
	private static final String STATION_TABLE = "station_tb";

	/**
	 * operation　area Table colums;
	 */
	private static final String OPERATION_KEY_IS_PUBLIC = "is_public";
	private static final String OPERATION_KEY_IS_DAILY_RENT = "is_daily_rent";
	private static final String OPERATION_KEY_IS_NIGHT_RENT = "is_night_rent";
	private static final String OPERATION_KEY_MAX_DAILY_RENT_DAY = "max_daily_rent_day";
	private static final String OPERATION_KEY_MARK_ICON_URL = "mark_icon_url";
	private static final String OPERATION_KEY_OPERAION_AREA_GROUP_ID = "operation_area_group_id";
	private static final String OPERATION_KEY_OPERAIONAREA_GROUP_NAME = "operaionarea_group_name";
	private static final String OPERATION_KEY_USE_ATP = "use_atp";

	/**
	 * STATION TABLE COLUMS;
	 */

	private static final String STATION_KEY_ID = "id";
	private static final String STATION_KEY_DETAIL_ADDRESS = "detail_address";
	private static final String STATION_KEY_LATITUDE = "latitude";
	private static final String STATION_KEY_LONGITUDE = "longitude";
	private static final String STATION_KEY_STATION_ID = "station_id";
	private static final String STATION_KEY_STATION_NAME = "station_name";
	private static final String STATION_KEY_OPERATION_AREA_GROUP_ID = "operation_area_group_id";

	/**
	 * Database creation sql statement
	 */

	private static final String CREATE_STATION_TABLE = "CREATE TABLE "
			+ STATION_TABLE
			+ " ("
			+ STATION_KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ STATION_KEY_DETAIL_ADDRESS
			+ " VARCHAR(255), "
			+ STATION_KEY_LATITUDE
			+ " DOUBLE(10,6), "
			+ STATION_KEY_LONGITUDE
			+ " DOUBLE(10,6), "
			+ STATION_KEY_STATION_ID
			+ " INTEGER(11), "
			+ STATION_KEY_STATION_NAME
			+ " VARCHAR(32), "
			+ STATION_KEY_OPERATION_AREA_GROUP_ID
			+ " VARCHAR(12), "
			+ "constraint fk_station_tb_opeartion foreign key(operation_area_group_id) references operation_area_tb(operation_area_group_id)ON DELETE CASCADE ON UPDATE CASCADE"
			+ ");";
	/*
	 * creat station table SQL
	 */
	private static final String CREATE_OPERATION_TABLE = "CREATE TABLE "
			+ OPERATION_AREA_TABLE + " ("
			+ OPERATION_KEY_OPERAION_AREA_GROUP_ID
			+ " VARCHAR(12) PRIMARY KEY, " + OPERATION_KEY_IS_PUBLIC
			+ " BOOLEAN, " + OPERATION_KEY_IS_DAILY_RENT + " BOOLEAN, "
			+ OPERATION_KEY_IS_NIGHT_RENT + " BOOLEAN, "
			+ OPERATION_KEY_MAX_DAILY_RENT_DAY + " INTEGER(2), "
			+ OPERATION_KEY_MARK_ICON_URL + " VARCHAR(100), "
			+ OPERATION_KEY_OPERAIONAREA_GROUP_NAME + " VARCHAR(32), "
			+ OPERATION_KEY_USE_ATP + " BOOLEAN);";
	/**
	 * Context
	 */
	/**
	 * Context
	 */
	private final Context mCtx;
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	// private Data
	/**
	 * 
	 /**@author JXPC Inner private class. Database Helper class for creating
	 * and updating database.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			Log.e(TAG, "CREATE_OPERATION_TABLE:  " + CREATE_OPERATION_TABLE);
			Log.e(TAG, "CREATE_STATION_TABLE:  " + CREATE_STATION_TABLE);
		//	db.execSQL(CREATE_OPERATION_TABLE);
			db.execSQL(CREATE_STATION_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion);

		}

		/**
		 * 
		 * 判断某张表是否存在
		 * 
		 * @param tabName
		 *            表名
		 * @return
		 */
		public boolean tabIsExist(String tabName) {
			boolean result = false;
			if (tabName == null) {
				return false;
			}
			SQLiteDatabase db = null;
			Cursor cursor = null;
			try {
				db = this.getReadableDatabase();// 此this是继承SQLiteOpenHelper类得到的
				String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"
						+ tabName.trim() + "' ";
				cursor = db.rawQuery(sql, null);
				if (cursor.moveToNext()) {
					int count = cursor.getInt(0);
					if (count > 0) {
						result = true;
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			return result;
		}

		public Cursor getStationIdDB() {
			SQLiteDatabase db = null;
			Cursor cursor = null;
			try {
				db = this.getReadableDatabase();// 此this是继承SQLiteOpenHelper类得到的
				// String sql =
				// "select * from station_tb s , operation_area_tb a where s.operation_area_group_id ='"
				// +
				// stationId.trim()+"'" ;
				String sql = "select * from station_tb s , operation_area_tb a where s.operation_area_group_id = a.operation_area_group_id";
				Log.e("sql: ", sql);
				cursor = db.rawQuery(sql, null);
				return cursor;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return cursor;
		}


		/**
		 * search 模糊查询按照地址（搜索站点使用）
		 * @param search_address
		 * @return
		 */
		public Cursor getSerchStationDB(String search_address) {
			SQLiteDatabase db = null;
			Cursor cursor = null;
			try {
				db = this.getReadableDatabase();// 此this是继承SQLiteOpenHelper类得到的
				// String sql =
				// "select * from station_tb s , operation_area_tb a where s.operation_area_group_id ='"
				// +
				// stationId.trim()+"'" ;
				String sql = "select * from station_tb where station_tb.detail_address like ? ";
				String[] selectionArgs = new String[] { "%" + search_address
						+ "%" };
				Log.e("sql: ", sql);
				cursor = db.rawQuery(sql, selectionArgs);
				return cursor;
			} catch (Exception e) {
				// TODO: handle exception
			}
			return cursor;
		}
	}

	// private SQLiteDatabase getReadableDatabase() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	/**
	 * 按照站点名称查看整个站点数据
	 */
	private void selectStationId(String stationId) {

	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */

	public CarRentalDB(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * This method is used for creating/opening connection
	 * 
	 * @return instance of DatabaseUtil
	 * @throws SQLException
	 */
	public CarRentalDB open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	/**
	 * This method is used for closing the connection.
	 */
	public void close() {
		mDbHelper.close();
	}
	

	/**
	 * This method is used to create/insert new record Station record.
	 * 
	 * @return long
	 */
	public long createStation(String detail_address, Double latitude,
			Double longitude, Integer station_id, String station_name,
			String operation_area_group_id) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(STATION_KEY_DETAIL_ADDRESS, detail_address);
		initialValues.put(STATION_KEY_LATITUDE, latitude);
		initialValues.put(STATION_KEY_LONGITUDE, longitude);
		initialValues.put(STATION_KEY_STATION_ID, station_id);
		initialValues.put(STATION_KEY_STATION_NAME, station_name);
		initialValues.put(STATION_KEY_OPERATION_AREA_GROUP_ID,
				operation_area_group_id);
		return mDb.insert(STATION_TABLE, null, initialValues);
	}

	/**
	 * This method is used to create/insert new operation record.
	 * 
	 * @param carid
	 * @param name
	 * @param detailedAddre
	 * @param gps
	 * @return long
	 */
	public long createOperation(String operation_area_group_id,
			String is_public, String is_daily_rent, String is_night_rent,
			int max_daily_rent_day, String mark_icon_url,
			String operaionarea_group_name, String use_atp) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(OPERATION_KEY_OPERAION_AREA_GROUP_ID,
				operation_area_group_id);
		initialValues.put(OPERATION_KEY_IS_PUBLIC, is_public);
		initialValues.put(OPERATION_KEY_IS_DAILY_RENT, is_daily_rent);
		initialValues.put(OPERATION_KEY_IS_NIGHT_RENT, is_night_rent);
		initialValues.put(OPERATION_KEY_MAX_DAILY_RENT_DAY, max_daily_rent_day);
		initialValues.put(OPERATION_KEY_MARK_ICON_URL, mark_icon_url);
		initialValues.put(OPERATION_KEY_OPERAIONAREA_GROUP_NAME,
				operaionarea_group_name);
		initialValues.put(OPERATION_KEY_USE_ATP, use_atp);
		return mDb.insert(OPERATION_AREA_TABLE, null, initialValues);
	}

	/**
	 * This method will delete Station record.
	 * @return boolean
	 */
	public boolean deleteStation(Integer stationId) {
		return mDb.delete(STATION_TABLE, STATION_KEY_STATION_ID + " = "
				+ stationId, null) > 0;
	}

	/**
	 * delete CarRentalDB
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(DATABASE_NAME);
	}

	/**
	 * This method will update Station record.
	 * 
	 * @param id
	 * @param name
	 * @param standard
	 * @return boolean
	 */
	public boolean updateStation(String detail_address, Double latitude,
			Double longitude, Integer station_id, String station_name,
			String operation_area_group_id) {
		ContentValues args = new ContentValues();
		args.put(STATION_KEY_DETAIL_ADDRESS, detail_address);
		args.put(STATION_KEY_LATITUDE, latitude);
		args.put(STATION_KEY_LONGITUDE, longitude);
		args.put(STATION_KEY_STATION_ID, station_id);
		args.put(STATION_KEY_STATION_NAME, station_name);
		args.put(STATION_KEY_OPERATION_AREA_GROUP_ID, operation_area_group_id);
		return mDb.update(STATION_TABLE, args, STATION_KEY_STATION_ID + "=" + Integer.valueOf(station_id), null) > 0;
	}

	/**
	 * This method will update Opersion record.
	 * 
	 * @param operation_area_group_id
	 * @param is_public
	 * @param is_daily_rent
	 * @param is_night_rent
	 * @param max_daily_rent_day
	 * @param mark_icon_url
	 * @param operaionarea_group_name
	 * @param use_atp
	 * @return
	 */
	public boolean updateOperation(String operation_area_group_id,
			String is_public, String is_daily_rent, String is_night_rent,
			int max_daily_rent_day, String mark_icon_url,
			String operaionarea_group_name, String use_atp) {
		ContentValues args = new ContentValues();
		args.put(OPERATION_KEY_OPERAION_AREA_GROUP_ID, operation_area_group_id);
		args.put(OPERATION_KEY_IS_PUBLIC, is_public);
		args.put(OPERATION_KEY_IS_DAILY_RENT, is_daily_rent);
		args.put(OPERATION_KEY_IS_NIGHT_RENT, is_night_rent);
		args.put(OPERATION_KEY_MAX_DAILY_RENT_DAY, max_daily_rent_day);
		args.put(OPERATION_KEY_MARK_ICON_URL, mark_icon_url);
		args.put(OPERATION_KEY_OPERAIONAREA_GROUP_NAME, operaionarea_group_name);
		args.put(OPERATION_KEY_USE_ATP, use_atp);
		return mDb.update(
				STATION_TABLE,
				args,
				OPERATION_KEY_OPERAION_AREA_GROUP_ID + "="
						+ Integer.valueOf(operation_area_group_id), null) > 0;
	}

	public Cursor getStationDB() {
		return mDbHelper.getStationIdDB();
	}

	/**
	 * 根据站点地址获取站点信息
	 * 
	 * @return
	 */
	public Cursor getSearchStationDBOfAddress(String search_address) {
		return mDbHelper.getSerchStationDB(search_address);
	}
	private void safd() {
		// TODO Auto-generated method stub

	}

}

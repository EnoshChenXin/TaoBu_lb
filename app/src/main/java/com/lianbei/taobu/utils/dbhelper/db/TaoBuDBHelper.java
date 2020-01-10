package com.lianbei.taobu.utils.dbhelper.db;

import android.content.Context;
import android.util.Log;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;
import net.sqlcipher.database.SQLiteOpenHelper;

/**
 * SqlLite 地图页面站点数据管理工具SqlLite
 *
 * @author HASEE
 */

/**
 *
 /**@author JXPC Inner private class. Database Helper class for creating
 * and updating database.
 */
public class TaoBuDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "CarRentalDB";
    /*
     * station_database name
     */
    public static final String DATABASE_NAME = "taobu_db";

    /*
     * statcion_database version
     */
    public static final int DATABASE_VERSION = 1;
    /*
     * Station Operation area Table Name
     */

    public static final String OPERATION_AREA_TABLE = "operation_area_tb";
    /*
     * Station Table Name
     */
    public static final String STATION_TABLE = "station_tb";


    /*
     * Station Table OPT Name
     */
    public static final String OPT_TABLE = "opt_tb";

    /**
     * operation　area Table colums;
     */
    public static final String OPERATION_KEY_IS_PUBLIC = "is_public";
    public static final String OPERATION_KEY_IS_DAILY_RENT = "is_daily_rent";
    public static final String OPERATION_KEY_IS_NIGHT_RENT = "is_night_rent";
    public static final String OPERATION_KEY_MAX_DAILY_RENT_DAY = "max_daily_rent_day";
    public static final String OPERATION_KEY_MARK_ICON_URL = "mark_icon_url";
    public static final String OPERATION_KEY_OPERAION_AREA_GROUP_ID = "operation_area_group_id";
    public static final String OPERATION_KEY_OPERAIONAREA_GROUP_NAME = "operaionarea_group_name";
    public static final String OPERATION_KEY_USE_ATP = "use_atp";

    /**
     * STATION TABLE COLUMS;
     */
    public static final String STATION_KEY_ID = "id";
    public static final String STATION_KEY_DETAIL_ADDRESS = "detail_address";
    public static final String STATION_KEY_LATITUDE = "latitude";
    public static final String STATION_KEY_LONGITUDE = "longitude";
    public static final String STATION_KEY_STATION_ID = "station_id";
    public static final String STATION_KEY_STATION_NAME = "station_name";
    public static final String STATION_KEY_OPERATION_AREA_GROUP_ID = "operation_area_group_id";

    /**
     * STATION TABLE COLUMS;
     */
    public static final String OPT_KEY_ID = "id";
    public static final String OPT_KEY_OPT_ID = "opt_id";
    public static final String OPT_KEY_OPT_NAME = "opt_name";
    public static final String OPT_KEY_LEVEL = "level";
    public static final String OPT_KEY_PARENT_OPT_ID = "parent_opt_id";

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

    private static final String CREATE_OPT_TABLE = "CREATE TABLE "
            + OPT_TABLE + " ("
            + OPT_KEY_OPT_ID
            + " VARCHAR(12) PRIMARY KEY, " + OPT_KEY_OPT_NAME
            + " VARCHAR(100), " + OPT_KEY_LEVEL + " VARCHAR(100), "
            + OPT_KEY_PARENT_OPT_ID + " VARCHAR(100));";


    public TaoBuDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public TaoBuDBHelper(Context context, String name,
                         CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "CREATE_OPERATION_TABLE:  " + CREATE_OPERATION_TABLE);
        Log.e(TAG, "CREATE_OPT_TABLE:  " + CREATE_OPT_TABLE);
        //	db.execSQL(CREATE_OPERATION_TABLE);
        //	db.execSQL(CREATE_STATION_TABLE);
       // db.execSQL(CREATE_STATION_TABLE);
        db.execSQL(CREATE_OPT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }
}

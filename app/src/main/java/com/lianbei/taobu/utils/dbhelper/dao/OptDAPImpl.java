package com.lianbei.taobu.utils.dbhelper.dao;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.shop.model.GoodsOptBean;
import com.lianbei.taobu.shop.model.SearchRecord;
import com.lianbei.taobu.utils.dbhelper.db.CarRentalDBHelper;
import com.lianbei.taobu.utils.dbhelper.db.SQLiteHelperFactory;
import com.lianbei.taobu.utils.dbhelper.db.TaoBuDBHelper;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class OptDAPImpl implements OptDAO{
    private SQLiteOpenHelper sqLiteOpenHelper;
    private String password = "";
    SQLiteDatabase db = null;

    public OptDAPImpl(Context context) {
        sqLiteOpenHelper = SQLiteHelperFactory.create(context);
    }
    @Override
    public long insertGoodsOpt(GoodsOptBean goodsOptBean) {
        try {
            db = sqLiteOpenHelper.getWritableDatabase(password);
            ContentValues values = new ContentValues();
            values.put(TaoBuDBHelper.OPT_KEY_OPT_ID,goodsOptBean.getOpt_id());
            values.put(TaoBuDBHelper.OPT_KEY_OPT_NAME,String.valueOf(goodsOptBean.getOpt_name()));
            values.put(TaoBuDBHelper.OPT_KEY_LEVEL,String.valueOf(goodsOptBean.getLevel()));
            values.put(TaoBuDBHelper.OPT_KEY_PARENT_OPT_ID,String.valueOf(goodsOptBean.getParent_opt_id()));
            return db.insert(TaoBuDBHelper.OPT_TABLE, null, values);
        } finally {
//			if (db != null)
//				db.close();
        }
    }

    @Override
    public long insertSearchRecord(SearchRecord searchRecord) {
        try {
            db = sqLiteOpenHelper.getWritableDatabase(password);
            ContentValues values = new ContentValues();
            values.put(TaoBuDBHelper.SEARCH_KEY_SEARCH_RECORD,searchRecord.getKeywordRecord());
            values.put(TaoBuDBHelper.SEARCH_KEY_HOT_NUMBER,searchRecord.getHotNum());
            return db.insert(TaoBuDBHelper.SEARCH_RECORD_TABLE, null, values);
        } finally {
//            if (db != null)
//                db.close();
        }
    }

    @Override
    public List<GoodsOptBean> queryOptBean() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
//			db = sqLiteOpenHelper.getWritableDatabase(password);
//			cursor = db.query("station_tb", new String[]{"latitude","longitude","operaionarea_group_name"}, null,
//					null, null, null, null);
            db = sqLiteOpenHelper.getWritableDatabase(password);
            String sql = "select * from opt_tb";
//			String sql = "select * from operation_area_tb";
            cursor = db.rawQuery(sql, null);
            List<GoodsOptBean> modelStationList = new ArrayList<GoodsOptBean>();
            while (cursor != null && cursor.moveToNext()) {
                GoodsOptBean goodsOptBean = new GoodsOptBean();
                String opt_id = cursor.getString(cursor.getColumnIndex(TaoBuDBHelper.OPT_KEY_OPT_ID));
                String opt_name = cursor.getString(cursor.getColumnIndex(TaoBuDBHelper.OPT_KEY_OPT_NAME));
                String level = cursor.getString(cursor.getColumnIndex(TaoBuDBHelper.OPT_KEY_LEVEL));
                String parent_opt_id = cursor.getString(cursor.getColumnIndex(TaoBuDBHelper.OPT_KEY_PARENT_OPT_ID));
                goodsOptBean.setOpt_id(opt_id);
                goodsOptBean.setOpt_name(opt_name);
                goodsOptBean.setLevel(level);
                goodsOptBean.setParent_opt_id(parent_opt_id);
                modelStationList.add(goodsOptBean);
            }
            return modelStationList;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null)
                db.close();
        }
    }

    @Override
    public List<SearchRecord> querySearchRecord() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
//			db = sqLiteOpenHelper.getWritableDatabase(password);
//			cursor = db.query("station_tb", new String[]{"latitude","longitude","operaionarea_group_name"}, null,
//					null, null, null, null);
            db = sqLiteOpenHelper.getWritableDatabase(password);
            String sql = "select * from search_record_tb order by hot_num desc";
            cursor = db.rawQuery(sql, null);
            List<SearchRecord> searchRecords = new ArrayList<SearchRecord>();
            while (cursor != null && cursor.moveToNext()) {
                SearchRecord searchRecord = new SearchRecord();
                String keywordRecord = cursor.getString(cursor.getColumnIndex(TaoBuDBHelper.SEARCH_KEY_SEARCH_RECORD));
                int hotNum = cursor.getInt(cursor.getColumnIndex(TaoBuDBHelper.SEARCH_KEY_HOT_NUMBER));
                searchRecord.setKeywordRecord(keywordRecord);
                searchRecord.setHotNum(hotNum);
                searchRecords.add(searchRecord);
            }
            return searchRecords;
        }finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null)
                db.close();
        }
    }

    @Override
    public Cursor rawQuery() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = sqLiteOpenHelper.getWritableDatabase(password);
          //  String sql = "select * from station_tb s , operation_area_tb a where s.operation_area_group_id = a.operation_area_group_id";
         //   cursor = db.rawQuery(sql, null);
            return cursor;
        } finally {
//			if (cursor != null) {
//				cursor.close();
//			}
//			if (db != null)
//				db.close();
        }
    }

    /**
     * search 模糊查询按照地址（搜索站点使用）
     * @param search_address
     * @return
     */
    @Override
    public Cursor getSerchGoodsOpt(String search_address) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = sqLiteOpenHelper.getWritableDatabase(password);
          //  String sql = "select * from station_tb where station_tb.station_name like ? ";
         //   String[] selectionArgs = new String[] { "%" + search_address+ "%" };
         //   Log.e("sql: ", sql);
         //   cursor = db.rawQuery(sql, selectionArgs);
            return cursor;
        } finally{
//			if (cursor != null) {
//				cursor.close();
//			}
//			if (db != null)
//				db.close();
        }
    }

    /**
     * delete CarRentalDB
     *
     * @param context
     * @return
     */
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(CarRentalDBHelper.DATABASE_NAME);
    }

    /**
     * This method will delete Station record.
     *
     * @return boolean
     */
    public boolean deleteStation(Integer stationId) {
        SQLiteDatabase db = null;
        try {
            db = sqLiteOpenHelper.getWritableDatabase(password);
            return db.delete(CarRentalDBHelper.STATION_TABLE, CarRentalDBHelper.STATION_KEY_STATION_ID + " = "
                    + stationId, null) > 0;
        } finally{
//			if (db != null)
//				db.close();
        }
    }



    /**
     * 清空本地表数据
     */
    public void clearTable(){
        SQLiteDatabase db = null;
        db = sqLiteOpenHelper.getWritableDatabase(password);
        db.execSQL("DELETE FROM " +CarRentalDBHelper.OPERATION_AREA_TABLE);
        db.execSQL("DELETE FROM " +CarRentalDBHelper.STATION_TABLE);
    }
    /**
     * 清空本地表数据
     */
    public void clearSearchTable(){
        SQLiteDatabase db = null;
        db = sqLiteOpenHelper.getWritableDatabase(password);
        db.execSQL("DELETE FROM " +TaoBuDBHelper.SEARCH_RECORD_TABLE);
    }

    /**
     * This method will update Station record.
     * @return boolean
     */
    public boolean updateStation(GoodsOptBean goodsOptBean) {
        SQLiteDatabase db = null;
        try {
            db = sqLiteOpenHelper.getWritableDatabase(password);
            ContentValues args = new ContentValues();
            args.put(CarRentalDBHelper.OPT_KEY_OPT_ID, goodsOptBean.getOpt_id());
            args.put(CarRentalDBHelper.OPT_KEY_OPT_NAME, goodsOptBean.getOpt_name());
            args.put(CarRentalDBHelper.OPT_KEY_LEVEL, goodsOptBean.getLevel());
            args.put(CarRentalDBHelper.OPT_KEY_PARENT_OPT_ID, goodsOptBean.getParent_opt_id());
            return db.update(CarRentalDBHelper.OPT_TABLE, args, CarRentalDBHelper.OPT_KEY_OPT_ID + "="+ Integer.valueOf(goodsOptBean.getOpt_id()), null) > 0;
        } finally{
//			if (db != null)
//				db.close();
        }
    }
    /**
     * This method will update Station record.
     * @return boolean
     */
    public boolean updateSearchRecord(SearchRecord searchRecord) {
        SQLiteDatabase db = null;
        try {
            db = sqLiteOpenHelper.getWritableDatabase(password);
            ContentValues args = new ContentValues();
            args.put(TaoBuDBHelper.SEARCH_KEY_SEARCH_RECORD, searchRecord.getKeywordRecord());
            args.put(TaoBuDBHelper.SEARCH_KEY_HOT_NUMBER, Integer.valueOf(searchRecord.getHotNum()));
            return  db.update(TaoBuDBHelper.SEARCH_RECORD_TABLE, args, TaoBuDBHelper.SEARCH_KEY_SEARCH_RECORD+"=?", new String[]{ searchRecord.getKeywordRecord()}) > 0;
        } finally{
//			if (db != null)
//				db.close();
        }
    }
}

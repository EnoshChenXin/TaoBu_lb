package com.lianbei.taobu.utils.dbhelper.db;

import net.sqlcipher.database.SQLiteDatabase;
import android.content.Context;

import com.lianbei.taobu.utils.dbhelper.util.Logger;


/**
 * SQLiteOpenHelper 工厂
 * @author Ricky
 *
 */
public class SQLiteHelperFactory {
	
	private static final String TAG = SQLiteHelperFactory.class.getSimpleName();
	
	private static TaoBuDBHelper sqLiteOpenHelper;
	//private static TaoBuDBHelper taoBuDBHelper;
	
	private SQLiteHelperFactory(){
		
	}
	
	public static TaoBuDBHelper create(Context context){
		if(sqLiteOpenHelper==null){
			
			synchronized (SQLiteHelperFactory.class) {
				
				if(sqLiteOpenHelper==null){
					Logger.e(TAG, "init SQLiteOpenHelper");
					sqLiteOpenHelper = new TaoBuDBHelper(context.getApplicationContext());
					Logger.e(TAG, "SQLiteDatabase loadLibs");
					//必须先调用此方法
					SQLiteDatabase.loadLibs(context);
				}
			}
		}
		return sqLiteOpenHelper;
	}
}

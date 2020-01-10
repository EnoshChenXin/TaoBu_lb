package com.lianbei.taobu.utils.dbhelper.util;

import android.util.Log;

public class Logger {
	public static final int DEBUG = 1;
	public static final int INFO = 2;
	public static final int WARN = 3;
	public static final int ERROR = 4;
	
	public static int DEV_MODE = 2;
	
	public static void d(String tag,String msg){
		if(DEBUG >= DEV_MODE){
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag,String msg,Throwable tr){
		if(DEBUG >= DEV_MODE){
			Log.d(tag, msg, tr);
		}
	}
	
	public static void i(String tag,String msg){
		if(INFO >= DEV_MODE){
			Log.i(tag, msg);
		}
	}
	
	public static void w(String tag,String msg){
		if(WARN >= DEV_MODE){
			Log.w(tag, msg);
		}
	}
	
	public static void e(String tag,String msg){
		if(ERROR >= DEV_MODE){
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag,String msg,Throwable tr){
		if(ERROR >= DEV_MODE){
			Log.e(tag, msg,tr);
		}
	}
}

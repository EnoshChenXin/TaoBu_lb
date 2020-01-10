package com.lianbei.taobu.utils;

import android.util.Log;

import com.lianbei.taobu.global.GlobalConstant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class GLog {

	
	public static void b(String msg){
		if(GlobalConstant.debug)
			Log.e("ibm", msg);
	}
	
	public static void bi(String msg){
		if(GlobalConstant.debug)
			Log.e("chg", msg);
	}
	
	public static void d(String msg){
		if(GlobalConstant.debug)
			Log.v("chg", msg);
	}
	
	public static void z(String str){
		bi(str);
		if(GlobalConstant.crashlog)
			try {
				if(buf==null)
					beginCollect("gloabl");
				writeToFile(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	

	private static SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");

	public static void beginCollect(String name){
		File logFile = new File ("sdcard/logcat_" + name+"_"+ System.currentTimeMillis()
				+ ".txt");
		if (logFile.exists()) {
			logFile.delete();
		}

		try {
			logFile.createNewFile();
			buf = new BufferedWriter (new FileWriter (logFile,
					true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void endCollect(){
		try {
			if(buf!=null)
				buf.close();
			buf = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static BufferedWriter buf;
	
	public static void writeToFile(String text) {
		try {
			if(buf!=null){
				buf.append("["+sdf.format(new Date ())+"] "+text);
				buf.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

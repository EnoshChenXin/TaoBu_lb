package com.lianbei.taobu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

import android.content.Context;
import android.util.Log;

public class CacheHelp {
	public static void clearDirCacheLogout(){
		try {
			ACache.get(new File(FileUtils.getDataPath())).clear();
			DirectoryHelp.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveObjList(Context contxt,HashSet<? extends Object> list,String key){
		 try {
			 if(list==null)return;
			 ByteArrayOutputStream st = new ByteArrayOutputStream();
			 ObjectOutputStream out = new ObjectOutputStream(st);    
			 out.writeObject(list);    
			 byte[] alBytes = st.toByteArray();
			 Log.e("saveObjListDataPath()",FileUtils.getDataPath()+"");
			ACache.get(new File(FileUtils.getDataPath())).put(key+".list", alBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void saveObjListhistor(Context contxt,HashSet<? extends Object> list,String key){
		 try {
			// if(list==null ||  !ViewSearchStationListActivity.isSave)return;
			 ByteArrayOutputStream st = new ByteArrayOutputStream();    
			 ObjectOutputStream out = new ObjectOutputStream(st);    
			 out.writeObject(list);    
			 byte[] alBytes = st.toByteArray();     
			ACache.get(new File(FileUtils.getDataPath())).put(key+".list", alBytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HashSet<? extends Object> getStrList(String key){
		try {  
			byte[] alBytes = ACache.get(new File(FileUtils.getDataPath())).getAsBinary(key+".list");
			HashSet<? extends Object> all = (HashSet<? extends Object>) new ObjectInputStream(new ByteArrayInputStream(alBytes)).readObject();  
			 for(Object s : all) {  
	             Log.e("tag","tag pp ::"+s);    
	         } 
			 return all;
		} catch (Exception e) {  
		    e.printStackTrace();  
		}    
	    return new HashSet<Object>();
	}

	public static void remove(String key){
		try {
			ACache.get(new File(FileUtils.getDataPath())).remove(key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	
//	public static void saveObj(UsingCar usingCar, String key) {
//		try {
//			// TODO Auto-generated method stub
//			ACache.get(new File(DirectoryHelp.CACHE_PATH)).put(key+".obj", usingCar);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
	public static Object getObj(String key) throws Exception{
		return ACache.get(new File(FileUtils.getDataPath())).getAsObject(key+".obj");
	}
}

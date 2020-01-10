package com.lianbei.taobu.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HASEE on 2017/3/9.
 * 全局数据存储工具类
 */

public class ShareUtils {
    private static final String TAG = "ShareUtils";
    public static final String OPT_INFO ="optname";

    /**
     * 保存序列化的用户数据
     * @param context
     * @param strObject 序列化的用户数据
     */
    public static void saveUserInfo(Context context, String strObject){
        saveStringValue(context,"userinfo",strObject);
    }
    /**
     * 从本地获取用户信息
     * @param context
     * @return strObject 获取到的保存在本地的用户数据
     */
    public static String getUserInfo(Context context){
        return getStringValue(context,"userinfo");
    }

    public static void clearUserInfo(Context context){
        SharedPreferences share = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.remove("userinfo");
        editor.commit();
    }

    /**
     * 保存会员头像 Url
     * @param context
     * @param str
     */
    public static void saveUserHeadImageUrl(Context context, String str) {
        saveStringValue(context, "HeadImageUrl", str);
    }

    /**
     * 获取会员Id
     * @param ontext
     * @return
     */
    public static String getUserHeadImageUrl(Context ontext){
        return getStringValue(ontext, "HeadImageUrl");
    }



    /**
     * 保存会员id
     * @param context
     * @param str
     */
    public static void saveUserId(Context context, String str) {
        saveStringValue(context, "userId", str);
    }

    /**
     * 获取会员Id
     * @param ontext
     * @return
     */
    public static String getUserId(Context ontext){
        return getStringValue(ontext, "userId");
    }

    /**
     * 保存登录令牌
     * @param context
     * @param str
     */
    public static void saveToken(Context context, String str){
        saveStringValue(context, "saveToken", str);
    }

    /**
     *  获取令牌
     * @param context
     * @return  123456zzzzz
     */
    public static String getToken(Context context){
        return getStringValue(context, "saveToken");
    }

    /**
     * 保存设备号
     * @param context
     * @param str appId
     */

   public static void saveAppId(Context context, String str){
       saveStringValue(context, "appid", str);
   }

    /**
     * 获取设备号
     * @param context
     * @return  appId
     */
    public static String getAppId(Context context){
        return getStringValue(context, "appid");
    }


    /**
     * 保存用户定位的gps坐标
     * @param coxt
     * @param gps
     */
    public static void  saveLocationGPS(Context coxt, String gps){
        saveStringValue(coxt, "locationGPS",gps);
    }
    /**
     *  获取用户定位的GPS坐标
     * @param coxt
     * @return
     */
    public static String getLocationGPS(Context coxt) {
        return getStringValue(coxt, "locationGPS");
    }

    private static void saveStringValue(Context coxt, String key, String value) {
        SharedPreferences.Editor editor = coxt.getSharedPreferences(TAG,
                Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }
    private static String getStringValue(Context coxt, String key) {
        SharedPreferences share = coxt.getSharedPreferences(TAG,
                Context.MODE_PRIVATE);
        return share.getString(key, "");
    }

    /**
     * 是否第一次使用ａｐｐ　　０　第一次　　　１　已是用过
     * @param context
     * @param str
     */
    public static void saveIsSingUserApp(Context context, String str){
        saveStringValue(context, "isSingUserApp", str);
    }

    /**
     *  获取令牌
     * @param context
     * @return  123456zzzzz
     */
    public static String getIsSingUserApp(Context context){
        return getStringValue(context, "isSingUserApp");
    }

    /**
     * 从本地获取用户信息
     * @param context
     * @return strObject 获取到的保存在本地的用户数据
     */
    public static String getWXUserInfo(Context context){
        return getStringValue(context,"wxUserInfo");
    }
    /**
     * 保存用户数据 微信
     * @param context
     * @param strObject 序列化的用户数据
     */
    public static void saveWXUserInfo(Context context, String strObject){
        saveStringValue(context,"wxUserInfo",strObject);
    }
    /**
     * 清楚用户数据 微信
     * @param context
     */
    public static void clearWXUserInfo(Context context){
        SharedPreferences share = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.remove("wxUserInfo");
        editor.commit();
    }
    /**
     * 保存序列化配置信息
     * @param context
     * @param strObject 序列化的用户数据
     */
    public static void saveConfigInfo(Context context, String strObject){
        saveStringValue(context,"configinfo",strObject);
    }
    /**
     * 从本地获取配置信息
     * @param context
     * @return strObject 获取到的保存在本地的用户数据
     */
    public static String getConfigInfo(Context context){
        return getStringValue(context,"configinfo");
    }

    public static void clearConfigInfo(Context context){
        SharedPreferences share = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.remove("configinfo");
        editor.commit();
    }

    /**
     * 使用SharedPreferences保存List
     * 支持类型：List<String>，List<JavaBean>
     *
     * @param context  上下文
     * @param key      储存的key
     * @param dataList 存储数据
     * @param <T>      泛型
     */
    public static <T> void setDataList(Context context, String key, List<T> dataList) {
        if (null == dataList || dataList.size() < 0) {
            return;
        }

        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(dataList);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, strJson);
        editor.commit();
    }
    /**
     * 获取SharedPreferences保存的List
     *
     * @param context 上下文
     * @param key     储存的key
     * @param <T>     泛型
     * @return 存储List<T>数据
     */
    public static <T> List<T> getDataList(Context context, String key, Class<T> cls) {
        SharedPreferences sp = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        List<T> dataList = new ArrayList<T>();
        String strJson = sp.getString(key, null);
        if (null == strJson) {
            return dataList;
        }

        Gson gson = new Gson();

        //使用泛型解析数据会出错，返回的数据类型是LinkedTreeMap
//        dataList = gson.fromJson(strJson, new TypeToken<List<T>>() {
//        }.getType());

        //这样写，太死
//        dataList = gson.fromJson(strJson, new TypeToken<List<UserModel>>() {
//        }.getType());

        JsonArray arry = new JsonParser().parse(strJson).getAsJsonArray();
        for (JsonElement jsonElement : arry) {
            dataList.add(gson.fromJson(jsonElement, cls));
        }

        return dataList;
}
}

package com.lianbei.taobu.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.lianbei.taobu.application.GlobalApplication;

/**
 * Created by HASEE on 2017/3/16.
 */

public class JXConstants {
    public static boolean fileDialog = false;//判断用户是否已经提醒强制登录，如果提醒则就不在继续提醒
    public static boolean  loadingRefish = true;//站点加载刷新控制
    public static String getImei(){
        String imei = ((TelephonyManager) GlobalApplication.getApplication ().getSystemService( Context.TELEPHONY_SERVICE)).getDeviceId();
        Log.e("Imei",imei+"");
        return imei;
    };
}

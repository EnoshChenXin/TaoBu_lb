package com.lianbei.taobu.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class CheckAndRequestPermission {



    private static  Activity mContext;
    public static final int SOGOU_SDK_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    public static final int SOGOU_SDK_READ_PHONE_STATE_REQUEST_CODE = 2;


    public  static void ReadExternal(Activity context){
        mContext = context;
        try {
            if(mContext == null)
                return;
            if (ContextCompat.checkSelfPermission ( mContext, Manifest.permission.READ_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions ( mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        SOGOU_SDK_WRITE_EXTERNAL_STORAGE_REQUEST_CODE );
            }
        }catch (Exception e){
            e.printStackTrace ();
        }

    }



    private void vdd(Activity context){
        mContext = context;
        try{
            if(mContext == null)
                return;
            if (ContextCompat.checkSelfPermission ( mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE )
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions ( mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        SOGOU_SDK_WRITE_EXTERNAL_STORAGE_REQUEST_CODE );
            }
            if (ContextCompat.checkSelfPermission ( mContext, Manifest.permission.READ_PHONE_STATE )
                    != PackageManager.PERMISSION_GRANTED) {
                //申请READ_PHONE_STATE权限
                ActivityCompat.requestPermissions ( mContext, new String[]{Manifest.permission.READ_PHONE_STATE},
                        SOGOU_SDK_READ_PHONE_STATE_REQUEST_CODE );
            }
            if (ContextCompat.checkSelfPermission ( mContext, Manifest.permission.ACCESS_COARSE_LOCATION )
                    != PackageManager.PERMISSION_GRANTED) {
                //申请READ_PHONE_STATE权限
                ActivityCompat.requestPermissions ( mContext, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        SOGOU_SDK_READ_PHONE_STATE_REQUEST_CODE );
            }
            if (ContextCompat.checkSelfPermission ( mContext, Manifest.permission.ACCESS_FINE_LOCATION )
                    != PackageManager.PERMISSION_GRANTED) {
                //申请READ_PHONE_STATE权限
                ActivityCompat.requestPermissions ( mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        SOGOU_SDK_READ_PHONE_STATE_REQUEST_CODE );
            }
        }catch (Exception e){

        }
    }
}

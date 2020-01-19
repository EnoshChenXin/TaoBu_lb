package com.lianbei.taobu.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsUtil {
    private RequestPermissionCallBack mRequestPermissionCallBack;
    private final int mRequestCode = 1024;
    private Context context;

    /**
     * 发起权限请求
     *
     * @param context
     * @param permissions
     * @param callback
     */
    public void requestPermissions(final Context context, final String[] permissions,
                                   RequestPermissionCallBack callback) {
        this.context= context;
        this.mRequestPermissionCallBack = callback;
        StringBuilder permissionNames = new StringBuilder ( );
        for (String s : permissions) {
            permissionNames = permissionNames.append ( s + "\r\n" );
        }
        //如果所有权限都已授权，则直接返回授权成功,只要有一项未授权，则发起权限请求
        boolean isAllGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission ( context, permission ) == PackageManager.PERMISSION_DENIED) {
                isAllGranted = false;
                if (ActivityCompat.shouldShowRequestPermissionRationale ( (Activity) context, permission )) {
                    new AlertDialog.Builder ( context ).setTitle ( "PermissionTest" )//设置对话框标题
                            .setMessage ( "【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】" +
                                    "您好，需要如下权限：" + permissionNames +
                                    " 请允许，否则将影响部分功能的正常使用。" )//设置显示的内容
                            .setPositiveButton ( "确定", new DialogInterface.OnClickListener ( ) {//添加确定按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    //TODO Auto-generated method stub
                                    ActivityCompat.requestPermissions ( ((Activity) context), permissions, mRequestCode );
                                }
                            } ).show ( );//在按键响应事件中显示此对话框
                } else {
                    ActivityCompat.requestPermissions ( ((Activity) context), permissions, mRequestCode );
                }
                break;
            }
        }
        if (isAllGranted) {
            mRequestPermissionCallBack.granted ( );
            return;
        }
    }


    /**
     * 权限请求结果回调接口
     */
    public interface RequestPermissionCallBack {
        /**
         * 同意授权
         */
        void granted();

        /**
         * 取消授权
         */
        void denied();
    }



}

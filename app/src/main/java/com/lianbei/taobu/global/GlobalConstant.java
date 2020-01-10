package com.lianbei.taobu.global;

/** 全局常量
 * Created by HASEE on 2017/3/24.
 */

public class GlobalConstant {
    public static boolean debug = true;
    public static boolean crashlog = false;

    public final static int ACTIVITY_FLAG_PHOTO = 0x00f;
    public final static int ACTIVITY_FLAG_ALBUM = ACTIVITY_FLAG_PHOTO + 1;
    public static final int IMAGE_COMPLETE = 2; // 结果


    public static  final String USER_LOGIN= "请登录";
    public static  final String USER_GETORDER_NO= "获取用户订单";

    public static final String Eectricize = "充电";
    public static final String RETURN_CAR = "还车";
   //用户当前选择用车的运营商户
    public static String operatorSn = "";
}

package com.lianbei.httplbrary;


import com.lianbei.httplbrary.utils.ApiRequestParamInterface;

/**
 * Created by chen_yxin on 2017/3/2.
 */

public class ApiResultManager {

    public static final String SUCCESS_CODE = "0000";//成功
    public static final String UNKOWN_CODE = "0001";//未知
    public static final String USER_ALREADY_EXISTS_CODE = "0003";//已存在
    public static final String USER_UNFINDABLE_CODE = "0002";//用户不存在
    public static final String MESSAGE_UNFINDABLE_CODE = "0004";//验证码不存在
    public static final String MESSAGE_PAST_DUE_CODE = "0005";//验证码过期
    public static final String MESSAGE_MISTAKE_CODE = "0006";//验证码过去
    public static final String READ_CODE_FAIL = "0007";//阅读错误


    /**
     * Toast提醒
     */
    /**
     * 记录全部需要单独处理的异常code
     *
     * @param mesgCode
     */
    private static boolean notificationApp(int mesgCode) {
        int[] failMesgCode = new int[]{0001, 0003, 0002, 0004, 0005, 0006};

        for (int i = 0; i < failMesgCode.length; i++) {
            if (mesgCode == failMesgCode[i]) {
                return true;
            }
            return false;
        }
        return false;
    }

    //定义多种异常处理模式

    /**
     * 定义登录异常 统一结果参数 4000 ：重新登录
     */


    public static int disposeCordError(int cord) {

        int[] failMesgCode = new int[]{4123, 4124, 4125, 4127};

        for (int i = 0; i < failMesgCode.length; i++) {
            if (cord == failMesgCode[i]) {
                return 4000;
            }
        }
        return 4001;
    }

    /**
     * 400 数据异常
     *
     * @param apiResultModel
     * @param result
     * @param paramInterface{"return_code":"SUCCESS","code":"0000","return_msg":"成功",
     * @return
     */
    public static boolean apiResult(ApiResultModel apiResultModel, String result, ApiRequestParamInterface paramInterface) {
        // if (!apiResultModel.getCode ().equals("0000")) {
        try {
            if ((apiResultModel.getData ( ) == null
                    || apiResultModel.getData ( ).equals ( "null" )
                    || apiResultModel.getData ( ).equals ( "" )
                    || apiResultModel.getCode ().equals ( "FAIL" )
                    || !apiResultModel.getCode ().equals ( "0" )))
                     {
                         //请求结果异常，去处理数据
                        paramInterface.result ( result );//去处理结果
                     }else{
                         return true; //错误
                     }
        } catch (Exception e) {
            e.printStackTrace();
            return true; //错误
        }
        return true;//成功
    }
    //处理特殊提醒信息 自定义消息提醒  // 成功提醒
    public static String toastMsg(StringBuffer msg) {
        //这里根据特殊请求自行处理提醒结果
        return "";
    }
}

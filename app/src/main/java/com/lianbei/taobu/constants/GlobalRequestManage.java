package com.lianbei.taobu.constants;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 全局异常信息处理，
 * Created by HASEE on 2017/3/17.
 */

public class GlobalRequestManage {

    public  static final String SUCCESS_CODE="0000";//成功
    public  static final String UNKOWN_CODE="0001";//未知
    public  static final String USER_ALREADY_EXISTS_CODE="0003";//已存在
    public  static final String USER_UNFINDABLE_CODE="0002";//用户不存在
    public  static final String MESSAGE_UNFINDABLE_CODE="0004";//验证码不存在
    public  static final String MESSAGE_PAST_DUE_CODE="0005";//验证码过期
    public  static final String MESSAGE_MISTAKE_CODE="0006";//验证码过去
    public  static final String READ_CODE_FAIL = "0007";//阅读错误
    //

    private static Context mContext;
    private ModelGlobalRequest globalRequest;
    private static GlobalRequestManage sSingleton = null;


    public static synchronized GlobalRequestManage getInstance(Context context) {
        if (sSingleton == null) {
            sSingleton = new GlobalRequestManage ( );
            mContext = context;
        }
        mContext = context;
        return sSingleton;
    }

    /**
     * 处理异常数据
     * @param object
     */
    public void apiResult(Object object) {
        Gson gson = new Gson ( );
        java.lang.reflect.Type type = new TypeToken <ModelGlobalRequest> ( ) {
        }.getType ( );
        Log.e ( "异常数据", object + "" );
        globalRequest = gson.fromJson ( object.toString ( ), type );
        int code = globalRequest.getCode ();
        String Return_msg = globalRequest.getMsg ();
        if(code !=  APIs.SUCCESS_CODE){
            ToastUtil.showShort ( mContext, Return_msg);
            if(code== 1 || code== 2 || code== 3 || code== 4 || code== 5
                    || code== 6 || code== 0007 || code == 8 ){
                Timer timer = new Timer();
                timer.schedule(new TimerTask () {
                                   public void run() {
                                       Message msg = new Message();
                                       msg.what = 0;
                                       msg.obj =globalRequest.getMsg () ;
                                       handler.sendMessage(msg);
                                      }
                               },
                        100);
            }
          /*  if(code.equals ( APIs.FAIL ) && globalRequest.getReturn_msg ().equals ( "失败" )){ //后台提示用户需要升级版本

                GlobalDialogModel.getInstance ().singleDialog ( mContext, "失败", globalRequest.getData ().toString (), new GlobalDialogModel.DialogCallBack ( ) {
                    @Override
                    public void oklBtn() {

                    }
                } );
            }
        }else{
            ToastUtil.showShort (mContext,Return_msg);//服务器异常提醒
            */
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           // if(msg.what == 0){
               // LoadProgress.getInstance().dialogalert(msg.obj+ "", mContext);
          //  }
        }
    };
}
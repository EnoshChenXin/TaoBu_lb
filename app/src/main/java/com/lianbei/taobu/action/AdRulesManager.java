package com.lianbei.taobu.action;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.lianbei.commomview.adlibrary.AdConstant;
import com.lianbei.commomview.adlibrary.AdManager;
import com.lianbei.commomview.adlibrary.bean.ADctionInfo;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.utils.SPUtils;
import com.lianbei.taobu.utils.TimeUtils;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.h5.H5PublicActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.http.Url;

public class AdRulesManager {
    static int  i =0;
    static  boolean   islogin = false ;//false :已登录
    static String  tag = "";
    //广告//活动提醒规则//弹窗提醒规则
    /*
    1.广告是否弹出由后台配置
    2.没种广告每天只弹出一次
    3.次日重新弹出重置
     */
    public static List<ADctionInfo> allwindowpopData = new ArrayList <> (  );
    /**
     *
     * @param context 上下文
     * @param actionInfoList 广告数据
     *                       int 新闻页面提现只
     */
  public static void ShowActionWindow(Activity context, List<ADctionInfo> actionInfoList, String windowPage){
      islogin= UserInfo.UserInfoIsEmpty ( context );
      Log.e ( "tag" ,tag+"");
      if(allwindowpopData != null){
          allwindowpopData.clear ();
      }
      Date date = new Date();
      //当天实时时间
      SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String nowdata = dff.format ( date );
      String loaclendtimes = (String) SPUtils.get ( "endtimes","2008-01-01 23:59:59");
      Log.e ( "loaclendtimes" ,loaclendtimes+"");
      if(TimeUtils.compare_Stringdate (nowdata,loaclendtimes) == -1 ){//当前时间小于结束时间
          //今天继续执行 操作，查看是否有可弹出
          for (int i = 0; i <actionInfoList.size () ; i++) {
              if(!Validator.isStrNotEmpty ( actionInfoList.get (i).getWindowpage () )){
                  actionInfoList.get ( i ).setWindowpage ( Constant.NEWS_WINDOWPOP );
              }
              String ID  =actionInfoList.get ( i ).getId ();
              String activityID = (String)SPUtils.get ( ID,"");
              if(actionInfoList.get ( i ).getWindowpage ().equals ( windowPage ) ){
                 // if(!Validator.isStrNotEmpty ( activityID )){  //为空 说明未弹出框框
                  if(1 == 1){  //为空 说明未弹出框框
                      /** 这里进行对应页面弹窗
                       */
                       allwindowpopData.add ( actionInfoList.get ( i ) );
                      for (int j = 0; j < allwindowpopData.size (); j++) {
                          if(allwindowpopData.get ( j ).getType ().equals ( "7" ) && !islogin ){
                              initListener(context,allwindowpopData);
                              return;//只执行一次
                          }else if(allwindowpopData.get ( j ).getType ().equals ( "7" ) && islogin) {
                              return;//只执行一次
                          }else {
                              //弹出过得框进行记录
                              SPUtils.put ( ID + "", ID + "" );
                              initListener ( context, allwindowpopData );
                              //保存弹出信息
                              return;//只执行一次
                          }
                      }

                  }
              }else{
                  //执行弹框操作//不为空 说明已经弹出来框框
              }
          }
      }else if(TimeUtils.compare_Stringdate (nowdata ,loaclendtimes) ==1){
          //当前时间大于结束时间
          //保存当前 日 中最后时间  //当前为新的一天 //1.重置所有广告缓存文件
          SimpleDateFormat endtime = new SimpleDateFormat("yyyy-MM-dd");
          String  endtime1 =  endtime.format ( date );
          String  endtimes =  endtime1+" 23:59:59";
          SPUtils.put ("endtimes", endtimes+"");
          //清空所有弹框信息
          for (int i = 0; i <actionInfoList.size () ; i++) {
              String id = actionInfoList.get ( i ).getId ();
              SPUtils.put (id+"","null" );
          }
          //再次执行当前方法
          if(i < 2){
              i++;
              ShowActionWindow(context,actionInfoList,windowPage);
          }

      }else{

      }
  }
   //获取的
   private static void initListener(Activity context, List<ADctionInfo> actionInfoList) {
      try{
      Log.e ( "AdManageractionInfoList" ,actionInfoList.size ()+"");
       AdManager adManager = new AdManager (context, actionInfoList);
       adManager.setOnImageClickListener ( new AdManager.OnImageClickListener ( ) {
           @Override
           public void onImageClick(View view, ADctionInfo advInfo) {
               adManager.dismissAdDialog ();
               //这里处理图片点击后的情况 //提现1:提现 2：领奖励  3：邀请   4：阅读  5，任务  6，H5活动
               Intent intent=null;
               intent= new Intent(context, H5PublicActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               if(!isEmptyUrl(advInfo.getUrl ())){
                   intent.putExtra("url", advInfo.getUrl ()+"");
                   intent.putExtra("title", advInfo.getTitle ()+"");
                   context.startActivity(intent);
               }else if(advInfo.getType ().equals ( "1" )){
                   if(!islogin){
                      // Intent intentWithdrawa = new Intent (context, WithdrawalsActivity.class );
                      // context.startActivity ( intentWithdrawa );
                   }else {
                      // Intent intentlogin = new Intent (context, LoginActivity.class );
                      // context.startActivity ( intentlogin );
                   }

               }else if(advInfo.getType ().equals ( "2" )){//领取奖励
                   if(!islogin){
                      // Intent intent_action = new Intent(context, ActionCenterActivity.class); //活动中心页面
                      //  context.startActivity(intent_action);
                   }else{
//                       Intent intentlogin = new Intent (context, LoginActivity.class );
//                       context.startActivity ( intentlogin );
                   }

               }else if(advInfo.getType ().equals ( "3" )){//邀请
                   //去邀请
                   Intent intentinvite = new Intent ( );
                   intentinvite.setAction ( "com.action.invite" );
                   context.sendBroadcast ( intentinvite );
               }else if(advInfo.getType ().equals ( "4" )){ //阅读
                   Intent intentread = new Intent ( );
                   intentread.setAction ( "com.action.read" );
                   context.sendBroadcast ( intentread );
               }else if(advInfo.getType ().equals ( "5" )){//任务中心
                   if(!islogin){
//                       Intent intent_taskcenter = new Intent(context, TaskCenterActivity.class);
//                       context.startActivity(intent_taskcenter);
                   }else{
//                       Intent intentlogin = new Intent (context, LoginActivity.class );
//                       context.startActivity ( intentlogin );
                   }
               }else if(advInfo.getType ().equals ( "6" )){
                   if(!islogin){
//                       Intent intent_action = new Intent(context, ActionCenterActivity.class); //活动中心页面
//                       context.startActivity(intent_action);
                   }else{
//                       Intent intentlogin = new Intent (context, LoginActivity.class );
//                       context.startActivity ( intentlogin );
                   }
               }else if(advInfo.getType ().equals ( "7" )){ //用户未注册/登录
                   if(!islogin){
//                       Intent intent_action = new Intent(context, ActionCenterActivity.class); //活动中心页面
//                       context.startActivity(intent_action);
                   }else{
//                       Intent intent_action = new Intent(context, LoginActivity.class); //活动中心页面
//                       context.startActivity(intent_action);
                   }

               }
           }

       } )  .setBackViewColor( Color.parseColor("#AA333333"))
               .setBounciness(5)
               .setSpeed(4)
               .showAdDialog ( AdConstant.ANIM_CENTER_TO_VISIBLE);
      }catch (Exception e){
          e.printStackTrace ();
      }
   }

    private static boolean isEmptyUrl(String Url){
        if(Url != null && !Url.equals ( "null" ) && !Url.equals ( "" )){
            return  false;
        }else{
            return  true;
        }
    }
}

package com.lianbei.taobu.ShareInvitation;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.listener.RequestCompletion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InvitationViewManager {
    private Context context;


    public InvitationViewManager(Context context){
        this.context = context;
    }

    /**
     * 获取用户邀请数据
     * @param completion
     */
   public void InviteData(RequestCompletion completion){
        new HttpUtil.Builder ( APIs.INVITE_DETAIL )
                .Tag ( context )
                .Params ( new HashMap <> (  ) )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        completion.Success ( model,"");
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {

            }
        } ).post ();
   }

    /**
     * 徒弟列表
     * @param completion
     */
    public void tudiList(RequestCompletion completion){
        new HttpUtil.Builder ( APIs.TUDI_LIST )
                .Tag ( context )
                .Params ( new HashMap <> (  ) )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        completion.Success ( model,"");
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error (values[0]+"");
            }
        } ).post ();
    }

    /**
     * 每日分享时间
     * @param completion
     */
    public void  shareDailyPre(RequestCompletion completion){
        new HttpUtil.Builder ( APIs.SHARE_DAILYSHARE_PRE )
                .Tag ( context )
                .Params ( new HashMap <> (  ) )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        completion.Success ( model,"");
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                completion.Error (values[0]+"");
            }
        } ).post ();
    }
    /**
     * 每日分享接口：分享成功后调用
     */
    public void  shareDailyShareAfter(RequestCompletion completion){
        Map<String,String> params=new HashMap<> ();
        params.put("fo","Y");
        new HttpUtil.Builder ( APIs.SHARE_DAILYSHARE_AFTER )
                .Tag ( context )
                .Params ( params )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        if(completion != null){
                            completion.Success (model,"");
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {

            }
        } ).post ();
    }
    /**
     *晒收入 基础数据
     */
    public void shareIncome(RequestCompletion completion){
        new HttpUtil.Builder ( APIs.ISHARE_INCOME)
                .Tag ( context )
                .Params ( new HashMap <> (  ) )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        if(completion != null){
                           completion.Success (model,"");
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {

            }
        } ).post ();
    }
    /**
     * 晒收入   每日分享成功任务
     */
    public void dailyMissionShare(){
      new HttpUtil.Builder ( APIs.DAILY_MISSION_SHARE )
              .Tag ( context )
              .Params ( new HashMap <> (  ) )
              .Success ( new Success ( ) {
                  @Override
                  public void Success(String model) {
                      try {
                          JSONObject jsonObject=new JSONObject(model);
                          String code = jsonObject.getString("code");
                          if (code.equals("SUCCESS")){
                              Intent intent=new Intent();
                              intent.setAction("com.action.share.success");
                              context.sendBroadcast(intent);
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }

                  }
              } ).Error ( new Error ( ) {
          @Override
          public void Error(Object... values) {

          }
      } ).post ();
    }

    /**
     * 唤醒好友列表
     * @param completion
     */
    public void callBackFriendList(RequestCompletion completion){

        new HttpUtil.Builder (APIs.CALL_BACK_FRIEND_LIST )
                .Tag ( context )
                .Params ( new HashMap <> (  ) )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        if(completion != null){
                            completion .Success (model,""  );
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                if(completion != null){
                    completion .Error (values[0]+"");
                }
            }
        } ).post ();
    }
    /**
     * //唤醒好友
     */
    public void recall(String recallId,RequestCompletion completion){
        Map<String,String> params=new HashMap<>();
        params.put("recallUser",recallId);
        new HttpUtil.Builder ( APIs.RECALL )
                .Tag ( context )
                .Params ( params )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        if(completion != null){
                            completion.Success (model,"");
                        }

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                if(completion != null){
                    completion.Error (values[0],"");
                }
            }
        } ).post ();
    }
}

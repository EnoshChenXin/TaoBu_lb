package com.lianbei.taobu.mine.login;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.listener.LoginRequestCompletions;
import com.lianbei.taobu.listener.WXLoginResuleInterface;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.mine.model.WXUserInfo;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Utils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class WXLoginViewManager {
    LoginRequestCompletions completion;
    private Activity mActivity;
    private IWXAPI mWxApi;
    private Gson gson=new Gson ();
    public WXLoginViewManager(Activity activity, LoginRequestCompletions completion){
        this.mActivity = activity;
        this.completion = completion;
        mWxApi = WXAPIFactory.createWXAPI(mActivity, APIs.APP_ID, false);
        // 将该app注册到微信
        mWxApi.registerApp( APIs.APP_ID);
    }

   public void WXLogin(){
        //这里判断是否安装微信
       // 判断是否安装了微信客户端
       if (!mWxApi.isWXAppInstalled()) {
           Toast.makeText(mActivity.getApplicationContext(), "您还未安装微信客户端！", Toast.LENGTH_SHORT).show();
           if(completion != null){
               completion.WXLoginFail ( "");
           }
           return;
       }
        // 将该app注册到微信
        final SendAuth.Req req = new SendAuth.Req ( );
        req.scope = "snsapi_userinfo";
        req.state = "lianbei_wx_login";
        mWxApi.sendReq ( req );
        WxLogin();
    }

    private void WxLogin() {
        WXLoginResuleInterface.getInstance ().wxExecuteMessage ( new LoginRequestCompletions ( ) {
            @Override
            public void WXLoginSuccess(Object value) {
                getAccessToken(value.toString ());
            }

            @Override
            public void WXLoginFail(Object value) {
                if(completion != null){
                    completion.WXLoginFail (value );
                }
            }
        } );
    }

    /***
     * 获取AccessToken
     * @param code
     */
    private void getAccessToken(String code) {
        String GetAccessTokenURL="https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String ,String> map = new HashMap <> (  );
        map.put ("appid",   APIs.APP_ID );
        map.put ("secret",   APIs.APP_SECRET );
        map.put ("code",  code );
        map.put ("grant_type", "authorization_code");
        new HttpUtil.Builder ().
                 Tag ( mActivity )
                .Params (map  )
                .Url ( GetAccessTokenURL )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                         // 判断是否获取成功，成功则去获取用户信息，否则提示失败
                        processGetAccessTokenResult(model);
                        //Log.i("ee",result);
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                if(completion != null){
                    completion.WXLoginFail (values[1] +"");
                }
            }
        } ).getRequest ();
    }

    /**
     * 处理获取的授权信息结果
     * @param response 授权信息结果
     */
    private void processGetAccessTokenResult(String response) {
        // 验证获取授权口令返回的信息是否成功
        Log.e ( "www-response:",response+"" );
        if (validateSuccess(response)) {
            // 使用Gson解析返回的授权口令信息
            WXAccessTokenInfo tokenInfo = gson.fromJson(response, WXAccessTokenInfo.class);
            // 保存信息到手机本地
            Utils.saveAccessInfotoLocation(tokenInfo,mActivity);
            // 获取用户信息
            Log.e ( "www-Access_token", tokenInfo.getAccess_token());
            getWXUserInfo(tokenInfo.getAccess_token(), tokenInfo.getOpenid());
        } else {
            // 授权口令获取失败，解析返回错误信息
            WXErrorInfo wxErrorInfo = gson.fromJson(response, WXErrorInfo.class);
            // 提示错误信息
            Log.i("www-wxErrorInfo","错误信息: " + wxErrorInfo.getErrmsg());

        }
    }
    /**
     * 验证是否成功
     *
     * @param response 返回消息
     * @return 是否成功
     */
    private boolean validateSuccess(String response) {
        String errFlag = "errmsg";
        return (errFlag.contains(response) && !"ok".equals(response))
                || (!"errcode".contains(response) && !errFlag.contains(response));
    }

    /**
     * 获取用户微信用户信息
     * @param access_token
     * @param openid
     */

    private void getWXUserInfo(String access_token, String openid){
        String url="https://api.weixin.qq.com/sns/userinfo";
        Map<String ,String> map = new HashMap <> (  );
        map.put ("access_token", access_token);
        map.put ("openid",  openid );
        new HttpUtil.Builder (  )
                .Tag ( mActivity )
                .Params ( map )
                .Url ( url )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Log.e ( "www-model" ,model+"");
                        ToastUtil.showShort ( mActivity,"获取微信用户信息:"+ model);
                        Gson gson = new Gson ();
                        WXUserInfo wxUserInfo = gson.fromJson ( model, WXUserInfo.class );
                            if (wxUserInfo != null) {
                                wxUserInfo.updateUserInfo(mActivity);
                                if(completion != null){
                                    completion.WXLoginSuccess (wxUserInfo );
                                }
                            }else{
                                if(completion != null){
                                    completion.WXLoginFail ( "");
                                }
                            }
                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {
                if(completion != null){
                    completion.WXLoginFail (values[1] +"");
                }
            }
        } ).getRequest ();
    }
}

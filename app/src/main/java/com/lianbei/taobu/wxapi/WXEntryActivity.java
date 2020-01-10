package com.lianbei.taobu.wxapi;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.listener.WXLoginResuleInterface;
import com.lianbei.taobu.listener.WXShareResuleInterface;
import com.lianbei.taobu.utils.ToastUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    public  int WX_LOGIN = 1;
    private Gson gson=new Gson ();

    private IWXAPI iwxapi;
    private String userId;
    private boolean isFront;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        iwxapi = WXAPIFactory.createWXAPI(this, APIs.APP_ID, false);
        //接收到分享以及登录的intent传递handleIntent方法，处理结果
        iwxapi.handleIntent(getIntent(), this);
    }
    @Override
    public void onReq(BaseReq baseReq) {
    }
    //请求回调结果处理
    @Override
    public void onResp(BaseResp baseResp) {
        //微信登录为getType为1，分享为0
        if (baseResp.getType() == WX_LOGIN){
            //登录回调
            //登录回调
            SendAuth.Resp resp = (SendAuth.Resp) baseResp;
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    String code = String.valueOf(resp.code);
                    //获取用户信息
                    WXLoginResuleInterface.getInstance ().setLoginSuc (code);
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    ToastUtil.showShort ( this,"取消登录" );
                    WXLoginResuleInterface.getInstance ().setLoginFail ( "取消登录" );
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    WXLoginResuleInterface.getInstance ().setLoginFail ( "取消登录" );
                    ToastUtil.showShort ( this,"取消登录" );
                    break;
                default:
                    break;
            }
        }else  if (baseResp.getType() == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            WXLaunchMiniProgram.Resp launchMiniProResp = (WXLaunchMiniProgram.Resp) baseResp;
            String extraData = launchMiniProResp.extMsg; //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
        }else{
            //分享成功回调
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    //分享成功
                    Toast.makeText(WXEntryActivity.this, "分享成功", Toast.LENGTH_LONG).show();
                    WXShareResuleInterface.getInstance ().setShareSuc (baseResp.errCode+"" );
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    //分享取消
                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_LONG).show();
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    //分享拒绝
                    Toast.makeText(WXEntryActivity.this, "分享拒绝", Toast.LENGTH_LONG).show();

                    break;
            }
        }
        finish();
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


    @Override
    protected void onRestart() {
        super.onRestart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 如果0.2秒后没有调用onResume，则认为是分享成功并且留着微信。
                if (!isFront) {
                    Log.i("TAG", "分享成功，留在微信");
                }
            }
        }, 200);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFront=false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        isFront=true;
    }
}

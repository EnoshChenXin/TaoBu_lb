package com.lianbei.taobu.mine.login;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.listener.LoginRequestCompletions;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.model.WXUserInfo;
import com.lianbei.taobu.mine.viewmanager.UserInfoManager;
import com.lianbei.taobu.views.dialog.CustomLoadingDialog;

import butterknife.OnClick;

public class WXLoginActivity extends BaseActivity implements View.OnClickListener {
    UserInfoManager userInfoManager;
    private CustomLoadingDialog customLoadingDialog;

    @Override
    public int getContentViewId() {
        return  R.layout.activity_wxlogin;
    }

    @Override
    public void initViews() {

    }

    /**
     * 微信登录
     */
    private void loginWx() {
        WXLoginViewManager WXLG = new WXLoginViewManager ( this, loginRequestCompletion );
        WXLG.WXLogin ( );
    }
    @Override
    public void initData() {
        customLoadingDialog=new CustomLoadingDialog(this);
        userInfoManager = new UserInfoManager (this);
    }

    /**
     * 用户微信登录
     */
    LoginRequestCompletions loginRequestCompletion = new LoginRequestCompletions ( ) {
        @Override
        public void WXLoginSuccess(Object value) {
            try {
                if (value != null) {
                    WXUserInfo wxUserInfo = (WXUserInfo) value;
                    userInfoManager.WechatLogin ( wxUserInfo, new RequestCompletion ( ) {
                        @Override
                        public void Success(Object value, String tag) {
                            customLoadingDialog.dismiss ();
                            Intent intent1 = new Intent ( WXLoginActivity.this, Fill_InviteCodeActivity.class );
                            startActivity ( intent1 );
                            finish ();
                        }

                        @Override
                        public void Fail(Object value) {
                            customLoadingDialog.dismiss ();
                        }

                        @Override
                        public void Error(Object... values) {
                            customLoadingDialog.dismiss ();
                        }
                    } );

                }
            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }

        @Override
        public void WXLoginFail(Object value) {
            customLoadingDialog.dismiss ();
        }
    };
    @OnClick({R.id.close_wxlogin_img, R.id.wx_btn_lin})
    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.close_wxlogin_img:
                finish ();
                break;
            case R.id.wx_btn_lin:
                customLoadingDialog.show();
                loginWx();
                break;
        }

    }

    @Override
    public void initListener() {
    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}

package com.lianbei.taobu.mine.login;


import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.model.WXUserInfo;
import com.lianbei.taobu.mine.viewmanager.UserInfoManager;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.MButton;
import com.lianbei.taobu.views.mMaterialEditText;

import butterknife.BindView;

public class Fill_InviteCodeActivity extends BaseActivity{
    @BindView ( R.id.mybutton )
    MButton mybutton;
    @BindView ( R.id.invitationCode )
    mMaterialEditText invitationCode;
    @BindView ( R.id.welcome_tv )
    TextView welcome_tv;
    @BindView ( R.id.Waiting )
    TextView Waiting;

    UserInfoManager userInfoManager;

    @Override
    public int getContentViewId() {
        return R.layout.activity_fill__invite_code;
    }

    @Override
    public void initViews() {
            try {
                String nickname = WXUserInfo.getWXUserInfo ( this ).getNickname ();
                if(Validator.isStrNotEmpty (nickname))
                 welcome_tv.setText ("你好："+nickname+ "\u3000 \u3000欢迎加入淘步优选" );
            }catch (Exception e){
                e.printStackTrace ();
            }

    }

    @Override
    public void initData() {
         userInfoManager = new UserInfoManager ( this );
    }

    @Override
    public void initListener() {
        Waiting.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction( Constant.REC_LOGON_OK);
                sendBroadcast(intent);
                finish ();
            }
        } );
        mybutton.setButtonEditTextType (new mMaterialEditText[]{invitationCode});
        mybutton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if(mybutton.isNotEmpty ()){
                    userInfoManager.setInviteCode ( invitationCode.getText ( ).toString ().trim (), new RequestCompletion ( ) {
                        @Override
                        public void Success(Object value, String tag) {

                        }

                        @Override
                        public void Fail(Object value) {

                        }

                        @Override
                        public void Error(Object... values) {

                        }
                    } );
                }
            }
        } );
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
    public void onBackPressed() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return false;
    }
}

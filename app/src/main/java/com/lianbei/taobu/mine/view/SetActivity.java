package com.lianbei.taobu.mine.view;


import android.content.Intent;
import android.view.View;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.mine.model.WXUserInfo;
import com.lianbei.taobu.views.MButton;

import butterknife.BindView;
import butterknife.OnClick;

public class SetActivity extends BaseActivity implements View.OnClickListener {
    @BindView ( R.id.outButton )
    MButton outButton;


    @Override
    public int getContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );

    }
    @OnClick(R.id.outButton)
    @Override
    public void onClick(View view) {
        switch (view.getId ()){
            case R.id.outButton:
                WXUserInfo.clearWXUserInfo ( this );
                UserInfo.clearUserInfo ( this );
                Intent intent=new Intent();
                intent.setAction( Constant.REC_QUIT_OK);
                sendBroadcast(intent);
                finish ();
                break;
        }

    }
    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onLeftClick() {
        finish ();

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }


}

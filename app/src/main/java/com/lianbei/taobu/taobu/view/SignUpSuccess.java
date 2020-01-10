package com.lianbei.taobu.taobu.view;


import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;

public class SignUpSuccess extends BaseActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_sign_up_success;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );

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

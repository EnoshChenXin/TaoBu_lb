package com.lianbei.taobu.taobu.view;


import android.content.Intent;

import com.lianbei.taobu.R;
import com.lianbei.taobu.action.AdDialogManager;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.mine.login.Fill_InviteCodeActivity;
import com.lianbei.taobu.mine.login.WXLoginActivity;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.mine.viewmanager.UserUtils;

public class HuanWuActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_huan_wu;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume ( );
        if(UserUtils.isNotLogin ( this)){
            finish ();
            return;
        }else{
            if(!UserInfo.getUserInfo ( this ).isInvite ()){
                Intent intent1 = new Intent ( this, Fill_InviteCodeActivity.class );
                startActivity ( intent1 );
                finish ();
                //AdDialogManager.getInstance ().AdDialogManager (this, Constant.MINE_WINDOWPOP);
            }
        }
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

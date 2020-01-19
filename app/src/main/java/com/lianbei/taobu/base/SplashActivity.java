package com.lianbei.taobu.base;

import android.os.Bundle;

import com.lianbei.taobu.R;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.utils.SPUtils;
import com.lianbei.taobu.utils.flyn.Eyes;

/**
 * 首次进入页面的广告为了不易混乱，在这里进行进入广点通或搜狗做判断
 */

public class SplashActivity extends BaseActivity {
 private int i = 1;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main_splash;
    }
    @Override
    public boolean enableSlideClose() {
        return false;
    }
    @Override
    public void initViews() {
       String str  = (String) SPUtils.get ("HOME_ADV" ,"GDT" );
        //如果启动app的Intent中带有额外的参数，表明app是从点击通知栏的动作中启动的
        //将参数取出，传递到MainActivity中
        Bundle bundle = getIntent().getBundleExtra( Constant.JPUSH_KEY);
        if(bundle != null){
            //如果bundle存在，取出其中的参数，启动DetailActivity
            Constant.JPUSH_TYPE =bundle.getString("type");
            Constant.JPUSH_ID =bundle.getString("id");
            Constant.JPUSH_URL =bundle.getString("url");
            Constant.JPUSH_DES =bundle.getString("des");
            Constant.JPUSH_REDMONEY =bundle.getString("redMoney");
            Constant.JPUSH_ISRED =bundle.getString("isRed");
        }
        if("GDT".equals ( str )){
            jumpActivity( SplashActivity.this, SplashCommonActivity.class );
        }else if("BAIDU".equals ( str )){
            //jumpActivity( SplashActivity.this, BaiduSplashActivity.class );
        }
        finish();
    }

    @Override
    public void initBeforeView() {
        Eyes.translucentStatusBar(this,false);//
    }

    @Override
    public void initData() {

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
}

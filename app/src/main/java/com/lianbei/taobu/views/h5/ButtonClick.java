package com.lianbei.taobu.views.h5;

import android.app.Activity;
import android.webkit.JavascriptInterface;

/**
 * Created by Administrator on 2018/4/9 0009.
 */

class ButtonClick {
    Activity mActivity;
    public ButtonClick(Activity activity){
        this.mActivity=activity;
    }

    @JavascriptInterface
    public void click0(){
        mActivity.finish();
    }
}

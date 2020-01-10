package com.lianbei.taobu.views.h5;

import android.webkit.JavascriptInterface;

public class JSuserinfo {

    private String userID; //用户id
    @JavascriptInterface
    public String getUserID() {
        return userID;
    }

    @JavascriptInterface
    public void setUserID(String userID) {
        this.userID = userID;
    }
}

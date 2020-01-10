package com.lianbei.taobu.views.bannerview.ui;

import android.graphics.Bitmap;
import android.view.View;

import com.lianbei.taobu.views.bannerview.bean.IBanner;

/**
 * 描述：广告信息</br>
 */
public class ADInfo implements IBanner {

    public String id = "";
    public String ImageUrl = "";
    public String url ="";
    public String photoDesc = "";
    public String type = "";
    public  View view = null;
    public String urlType = "";//  0:新手赛 1：H5
    public String clickUrlTitle ="";
    public String clickUrl ="";

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Bitmap getImgBg() {
        return null;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getClickType() {
        return type;
    }

    @Override
    public String getClickID() {
        return null;
    }

    @Override
    public String getClickUrl() {
        return clickUrl;
    }

    @Override
    public int getResource() {
        return 0;
    }

    @Override
    public String getPhotoDesc() {
        return photoDesc;
    }

    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public String getUrlType() {
        return urlType;
    }

    @Override
    public String getClickUrlTitle() {
        return clickUrlTitle;
    }

}

package com.lianbei.taobu.shop.model;

/**
 * Created by Administrator on 2018/5/22.
 */

public class BannerImgInfo {
    private String id;
    private String url; //点击图片链接地址
    private String imgUrl; //当前图片获取路径
    private String urlType; //0  h5，1,走呗任务
    private String clickUrl; //点击跳转ur
    private String clickUrlTitle;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getClickUrlTitle() {
        return clickUrlTitle;
    }

    public void setClickUrlTitle(String clickUrlTitle) {
        this.clickUrlTitle = clickUrlTitle;
    }
}
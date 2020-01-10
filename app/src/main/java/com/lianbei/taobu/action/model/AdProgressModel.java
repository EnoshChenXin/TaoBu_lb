package com.lianbei.taobu.action.model;

public class AdProgressModel {

    private String adImageurl;

    private String pagerType;


    private boolean isClose;//true:可以关闭  false：不可以关闭

    public String getAdImageurl() {
        return adImageurl;
    }

    public void setAdImageurl(String adImageurl) {
        this.adImageurl = adImageurl;
    }

    public String getPagerType() {
        return pagerType;
    }

    public void setPagerType(String pagerType) {
        this.pagerType = pagerType;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    public String getAdContentUrl() {
        return adContentUrl;
    }

    public void setAdContentUrl(String adContentUrl) {
        this.adContentUrl = adContentUrl;
    }

    private String adContentUrl;//广告内容链接




}

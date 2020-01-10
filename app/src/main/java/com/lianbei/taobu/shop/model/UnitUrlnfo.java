package com.lianbei.taobu.shop.model;

import java.io.Serializable;

public class UnitUrlnfo implements Serializable {
    String url; // 单人团推广长链接


    String short_url; // 单人团推广短链接


    String mobile_url; // 推广长链接（唤起拼多多app）


    String mobile_short_url; // 推广短链接（可唤起拼多多app）


    String multi_group_url; //  双人团推广长链接


    String multi_group_short_url; //  双人团推广短链接


    String multi_group_mobile_url; // 推广长链接（可唤起拼多多app）


    String multi_group_mobile_short_url; //推广短链接（唤起拼多多app）

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getMobile_short_url() {
        return mobile_short_url;
    }

    public void setMobile_short_url(String mobile_short_url) {
        this.mobile_short_url = mobile_short_url;
    }

    public String getMulti_group_url() {
        return multi_group_url;
    }

    public void setMulti_group_url(String multi_group_url) {
        this.multi_group_url = multi_group_url;
    }

    public String getMulti_group_short_url() {
        return multi_group_short_url;
    }

    public void setMulti_group_short_url(String multi_group_short_url) {
        this.multi_group_short_url = multi_group_short_url;
    }

    public String getMulti_group_mobile_url() {
        return multi_group_mobile_url;
    }

    public void setMulti_group_mobile_url(String multi_group_mobile_url) {
        this.multi_group_mobile_url = multi_group_mobile_url;
    }

    public String getMulti_group_mobile_short_url() {
        return multi_group_mobile_short_url;
    }

    public void setMulti_group_mobile_short_url(String multi_group_mobile_short_url) {
        this.multi_group_mobile_short_url = multi_group_mobile_short_url;
    }
}

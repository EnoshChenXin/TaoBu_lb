package com.lianbei.taobu.shop.model;

import java.io.Serializable;

/**
 * 多多进宝推广链接
 */
public class PromotionUrlInfo implements Serializable {
    private String we_app_web_view_short_url;// 唤起微信app推广短链接


    private String we_app_web_view_url; //唤起微信app推广链接


    private String mobile_super_short_url;  //唤醒拼多多app的推广短链接


    private String mobile_url; //唤醒拼多多app的推广长链接


    private String short_url;//推广短链接


    private String url; // 推广长链接

    private WeAppInfo we_app_info;//小程序信息


    private String weibo_app_web_view_short_url; //微博推广短链接


    private String weibo_app_web_view_url; // 微博推广链接


    private String schema_url; // schema的链接


    private QQAppInfo qq_app_info; // qq小程序信息

    public class WeAppInfo {

        private String weibo_app_web_view_short_url;//微博推广短链接

        private String weibo_app_web_view_url; // 微博推广链接


        private String schema_url; //  schema的链接

        public String getWeibo_app_web_view_short_url() {
            return weibo_app_web_view_short_url;
        }

        public void setWeibo_app_web_view_short_url(String weibo_app_web_view_short_url) {
            this.weibo_app_web_view_short_url = weibo_app_web_view_short_url;
        }

        public String getWeibo_app_web_view_url() {
            return weibo_app_web_view_url;
        }

        public void setWeibo_app_web_view_url(String weibo_app_web_view_url) {
            this.weibo_app_web_view_url = weibo_app_web_view_url;
        }

        public String getSchema_url() {
            return schema_url;
        }

        public void setSchema_url(String schema_url) {
            this.schema_url = schema_url;
        }
    }

    public class QQAppInfo {

        private String app_id;// 拼多多小程序id


        private String banner_url; // Banner图


        private String desc; //描述


        private String page_path; //小程序path值


        private String qq_app_icon_url; //  小程序icon


        private String source_display_name; // 来源名


        private String title; //小程序标题


        private String user_name; //  用户名

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPage_path() {
            return page_path;
        }

        public void setPage_path(String page_path) {
            this.page_path = page_path;
        }

        public String getQq_app_icon_url() {
            return qq_app_icon_url;
        }

        public void setQq_app_icon_url(String qq_app_icon_url) {
            this.qq_app_icon_url = qq_app_icon_url;
        }

        public String getSource_display_name() {
            return source_display_name;
        }

        public void setSource_display_name(String source_display_name) {
            this.source_display_name = source_display_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }

    public String getWe_app_web_view_short_url() {
        return we_app_web_view_short_url;
    }

    public void setWe_app_web_view_short_url(String we_app_web_view_short_url) {
        this.we_app_web_view_short_url = we_app_web_view_short_url;
    }

    public String getWe_app_web_view_url() {
        return we_app_web_view_url;
    }

    public void setWe_app_web_view_url(String we_app_web_view_url) {
        this.we_app_web_view_url = we_app_web_view_url;
    }

    public String getMobile_super_short_url() {
        return mobile_super_short_url;
    }

    public void setMobile_super_short_url(String mobile_super_short_url) {
        this.mobile_super_short_url = mobile_super_short_url;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WeAppInfo getWe_app_info() {
        return we_app_info;
    }

    public void setWe_app_info(WeAppInfo we_app_info) {
        this.we_app_info = we_app_info;
    }

    public String getWeibo_app_web_view_short_url() {
        return weibo_app_web_view_short_url;
    }

    public void setWeibo_app_web_view_short_url(String weibo_app_web_view_short_url) {
        this.weibo_app_web_view_short_url = weibo_app_web_view_short_url;
    }

    public String getWeibo_app_web_view_url() {
        return weibo_app_web_view_url;
    }

    public void setWeibo_app_web_view_url(String weibo_app_web_view_url) {
        this.weibo_app_web_view_url = weibo_app_web_view_url;
    }

    public String getSchema_url() {
        return schema_url;
    }

    public void setSchema_url(String schema_url) {
        this.schema_url = schema_url;
    }

    public QQAppInfo getQq_app_info() {
        return qq_app_info;
    }

    public void setQq_app_info(QQAppInfo qq_app_info) {
        this.qq_app_info = qq_app_info;
    }
}

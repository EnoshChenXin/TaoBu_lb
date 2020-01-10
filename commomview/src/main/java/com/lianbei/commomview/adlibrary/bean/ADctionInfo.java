package com.lianbei.commomview.adlibrary.bean;

public class ADctionInfo {
    private String id;   //活动id
    private String type;//活动类型  1:提现 2：领奖励  3：邀请   4：阅读  5，任务  6，H5活动  7.未登录用户
    private String pagetype;// 1：活动类型   2：banner类型   3，弹窗类型
    private String windowpage; //0,全局提现 1,新闻页面提醒  2,视频页面提醒  3，邀请页面提醒  ，4，我的页面提醒
    private String windowUrl;//弹窗url
    private String title;//
    private String money;//活动金额/金币
    private String desc; //
    private String startTime;  //活动开始时间
    private String endTime; //活动结束时间
    private String imgUrl;//活动图片
    private String state; //    0：未开启  1：未完成  2：已完成 ( -1：已过期)不一定会有
    private String bannerUrl;//获取banner图片URL
    private String url; // 活动URL
    private String sort; //排序
    private String mystate;//自定义的状态，用于调用接口改变页面状态 状态 1未完成 2已完成

    public String getWindowpage() {
        return windowpage;
    }

    public void setWindowpage(String windowpage) {
        this.windowpage = windowpage;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMystate() {
        return mystate;
    }

    public void setMystate(String mystate) {
        this.mystate = mystate;
    }

    public String getPagetype() {
        return pagetype;
    }

    public void setPagetype(String pagetype) {
        this.pagetype = pagetype;
    }

    public String getWindowUrl() {
        return windowUrl;
    }

    public void setWindowUrl(String windowUrl) {
        this.windowUrl = windowUrl;
    }
}

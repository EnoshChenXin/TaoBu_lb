package com.lianbei.taobu.circle.model;

import java.io.Serializable;

/**
 * 商品内容描述
 */
public class GroupPoperBean implements Serializable {
    private String imgUrl;//头像
    private String nickName;//昵称
    private String date;//日期
    private String state;//状态 0：申请中  1，同意   2，拒绝
    private String groupType; //0,我创建的组  1 ，他人创建


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
}

package com.lianbei.taobu.mine.model;

import java.io.Serializable;

/**
 * author: sunjian
 * created on: 2017/8/24 下午3:14
 * description:
 */

public class InvitationImageBean implements Serializable {
    private long id;
    private String img;

    public InvitationImageBean(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "id=" + id +
                ", img='" + img + '\'' +
                '}';
    }
}

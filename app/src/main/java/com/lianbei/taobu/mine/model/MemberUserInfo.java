package com.lianbei.taobu.mine.model;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by wangjiangong on 2017/3/17.
 */

public class MemberUserInfo implements Serializable {

    public String msg;
    public int code;
    public int data;


    public int getReturnData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setReturnData(int returnData) {
        this.data = returnData;
    }


    @Override
    public String toString() {
        return "MemberInfo{" +
                "returnData='" + data + '\'' +
                '}';
    }

    private Context context;


}

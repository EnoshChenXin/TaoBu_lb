package com.lianbei.taobu.mine.model;

import android.content.Context;

import java.io.Serializable;

/**
 */

public class MemberInfo implements Serializable {

    public String msg;
    public int code;
    public Object data;


    public Object getReturnData() {
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

    public void setReturnData(Object returnData) {
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

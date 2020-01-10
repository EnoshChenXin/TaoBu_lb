package com.lianbei.httplbrary;

/**
 * Created by chen_yxin on 2017/3/2.
 */

/**
 * 返回数据公共数据Model
 */
public class ApiResultModel {
    /**
    /**
     * 返回标识
     */
    private String code;
    private String msg; //0000
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

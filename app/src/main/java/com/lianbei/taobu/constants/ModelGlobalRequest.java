package com.lianbei.taobu.constants;

/**
 * Created by HASEE on 2017/3/17.
 */

/**
 * 公共异常数据模型
 */

public class ModelGlobalRequest {

   private String msg;
   private int code;
   private Object data;


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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

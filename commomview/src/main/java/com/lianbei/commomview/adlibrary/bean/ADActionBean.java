package com.lianbei.commomview.adlibrary.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ADActionBean {
    private String return_code;
    private String code;
    private String return_msg;
    private List<ADctionInfo> data;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public List <ADctionInfo> getData() {
        return data;
    }

    public void setData(List <ADctionInfo> data) {
        this.data = data;
    }
}

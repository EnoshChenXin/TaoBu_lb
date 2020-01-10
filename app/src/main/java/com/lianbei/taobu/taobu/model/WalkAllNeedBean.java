package com.lianbei.taobu.taobu.model;

import java.util.List;

/**
 * 提现基本数据
 */
public class WalkAllNeedBean {

    private String return_code;
    private String code;
    private String return_msg;
    private List<WalkApplyBean>  data;

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

    public List <WalkApplyBean> getData() {
        return data;
    }

    public void setData(List <WalkApplyBean> data) {
        this.data = data;
    }
}
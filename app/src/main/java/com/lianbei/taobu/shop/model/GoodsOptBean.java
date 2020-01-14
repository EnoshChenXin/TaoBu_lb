package com.lianbei.taobu.shop.model;

import java.io.Serializable;

public class GoodsOptBean  implements Serializable {


    //主题标签
    String level; //层级，1-一级，2-二级，3-三级，4-四级
    String parent_opt_id; // id所属父ID，其中，parent_id=0时为顶级节点
    String opt_name ;  // 商品标签名
    String opt_id; //商品标签ID
    private boolean isSelect;

    //主题详情


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getParent_opt_id() {
        return parent_opt_id;
    }

    public void setParent_opt_id(String parent_opt_id) {
        this.parent_opt_id = parent_opt_id;
    }

    public String getOpt_name() {
        return opt_name;
    }

    public void setOpt_name(String opt_name) {
        this.opt_name = opt_name;
    }

    public String getOpt_id() {
        return opt_id;
    }

    public void setOpt_id(String opt_id) {
        this.opt_id = opt_id;
    }
}

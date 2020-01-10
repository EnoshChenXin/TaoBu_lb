package com.lianbei.taobu.shop.model;

public class TabModel {
    private String title;
    private String describe;


    public TabModel(String title,String describe){
        super();
        this.title = title;
        this.describe = describe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

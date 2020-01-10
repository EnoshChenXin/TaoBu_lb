package com.lianbei.taobu.shop.model;

public class GoodsBasicInfo {


    private long goods_id; // 商品id


    private long min_normal_price; // 最小单买价格，单位分


    private long min_group_price; // 最小成团价格，单位分


    private String goods_pic; // 商品缩略图


    private String goods_name; //  商品标题

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public long getMin_normal_price() {
        return min_normal_price;
    }

    public void setMin_normal_price(long min_normal_price) {
        this.min_normal_price = min_normal_price;
    }

    public long getMin_group_price() {
        return min_group_price;
    }

    public void setMin_group_price(long min_group_price) {
        this.min_group_price = min_group_price;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
}

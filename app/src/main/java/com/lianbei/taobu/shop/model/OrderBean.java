package com.lianbei.taobu.shop.model;

public class OrderBean {

    private long goods_id; // 商品ID

    private String goods_name;// 商品标题

    private long goods_price; // 订单中sku的单件价格，单位为分

    private long goods_quantity; //  购买商品的数量

    private String goods_thumbnail_url; //  商品缩略图

    private long order_amount; // 实际支付金额，单位为分


    private long order_create_time; // 订单生成时间，UNIX时间戳

    private long order_group_success_time; //  成团时间

    private long order_modify_at; // 最后更新时间

    private long order_pay_time; // 支付时间

    private String order_sn; //推广订单编号

    // 订单状态： -1 未支付; 0-已支付；1-已成团；2-确认收货；3-审核成功；4-审核失败（不可提现）；5-已经结算；8-非多多进宝商品（无佣金订单）
    private int order_status;

    private String order_status_desc; //订单状态描述

    private long order_verify_time;//审核时间

    private String p_id; //   推广位ID

    private long promotion_amount; //  佣金金额，单位为分


    private long promotion_rate; //佣金比例，千分比


    private int cpa_new; //  是否是 cpa 新用户，1表示是，0表示否


    private String custom_parameters; // 自定义参数


    private String type; // 订单类型：0：领券页面， 1： 红包页， 2：领券页， 3： 题页

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public long getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(long goods_price) {
        this.goods_price = goods_price;
    }

    public long getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(long goods_quantity) {
        this.goods_quantity = goods_quantity;
    }

    public String getGoods_thumbnail_url() {
        return goods_thumbnail_url;
    }

    public void setGoods_thumbnail_url(String goods_thumbnail_url) {
        this.goods_thumbnail_url = goods_thumbnail_url;
    }

    public long getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(long order_amount) {
        this.order_amount = order_amount;
    }

    public long getOrder_create_time() {
        return order_create_time;
    }

    public void setOrder_create_time(long order_create_time) {
        this.order_create_time = order_create_time;
    }

    public long getOrder_group_success_time() {
        return order_group_success_time;
    }

    public void setOrder_group_success_time(long order_group_success_time) {
        this.order_group_success_time = order_group_success_time;
    }

    public long getOrder_modify_at() {
        return order_modify_at;
    }

    public void setOrder_modify_at(long order_modify_at) {
        this.order_modify_at = order_modify_at;
    }

    public long getOrder_pay_time() {
        return order_pay_time;
    }

    public void setOrder_pay_time(long order_pay_time) {
        this.order_pay_time = order_pay_time;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getOrder_status_desc() {
        return order_status_desc;
    }

    public void setOrder_status_desc(String order_status_desc) {
        this.order_status_desc = order_status_desc;
    }

    public long getOrder_verify_time() {
        return order_verify_time;
    }

    public void setOrder_verify_time(long order_verify_time) {
        this.order_verify_time = order_verify_time;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public long getPromotion_amount() {
        return promotion_amount;
    }

    public void setPromotion_amount(long promotion_amount) {
        this.promotion_amount = promotion_amount;
    }

    public long getPromotion_rate() {
        return promotion_rate;
    }

    public void setPromotion_rate(long promotion_rate) {
        this.promotion_rate = promotion_rate;
    }

    public int getCpa_new() {
        return cpa_new;
    }

    public void setCpa_new(int cpa_new) {
        this.cpa_new = cpa_new;
    }

    public String getCustom_parameters() {
        return custom_parameters;
    }

    public void setCustom_parameters(String custom_parameters) {
        this.custom_parameters = custom_parameters;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.lianbei.taobu.shop.model;

import java.util.List;

public class MainGoodsBean {
    //{"error_response":{"error_msg":"签名验证失败","sub_msg":"签名验证失败","sub_code":"20004","error_code":20004,"request_id":"15749321782103707"}}
    private topListGet top_goods_list_get_response;//多多客工具获取爆款排行商品接口
    private Data goods_detail_response;  //多多进宝商品详情查询
    private UrlgenerateData goods_promotion_url_generate_response; //多多进宝推广链接生成
    private opt_get_response goods_opt_get_response;//获得拼多多商品标签列表
    private theme_get_detail  theme_list_get_response;//商品主题查询
    private goods_search goods_search_response;//
    private order_list_get_response order_list_get_response;//用时间段查询推广订单接口
    private goods_basic_detail_response   goods_basic_detail_response; //获取商品基本信息接口

    private UnitUrlnfo goods_zs_unit_generate_response; //多多进宝转链接口

    public theme_get_detail getTheme_list_get_response() {
        return theme_list_get_response;
    }

    public void setTheme_list_get_response(theme_get_detail theme_list_get_response) {
        this.theme_list_get_response = theme_list_get_response;
    }

    public UnitUrlnfo getGoods_zs_unit_generate_response() {
        return goods_zs_unit_generate_response;
    }

    public void setGoods_zs_unit_generate_response(UnitUrlnfo goods_zs_unit_generate_response) {
        this.goods_zs_unit_generate_response = goods_zs_unit_generate_response;
    }

    public class goods_basic_detail_response{

        private List<TopGoodsBean> goods_list; //
        public List<TopGoodsBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<TopGoodsBean> goods_list) {
            this.goods_list = goods_list;
        }
    }

    public class order_list_get_response{
        private List<OrderBean> order_list; //多多进宝订单对象列表

        public List <OrderBean> getOrder_list() {
            return order_list;
        }

        public void setOrder_list(List <OrderBean> order_list) {
            this.order_list = order_list;
        }
    }

    public  class topListGet{
        private List<TopGoodsBean> list; //多多进宝商品对象
        private String total;
        private String request_id;

        public List <TopGoodsBean> getList() {
            return list;
        }

        public void setList(List <TopGoodsBean> list) {
            this.list = list;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }
    }

    public  class Data{
        private List<GoodDetailBean> goods_details; //多多进宝商品对象
        public List <GoodDetailBean> getGoods_details() {
            return goods_details;
        }

        public void setGoods_details(List <GoodDetailBean> goods_details) {
            this.goods_details = goods_details;
        }
    }

    public class  UrlgenerateData{
        private List<PromotionUrlInfo> goods_promotion_url_list; //多多进宝推广链接对象列表
        private String request_id;

        public List <PromotionUrlInfo> getGoods_promotion_url_list() {
            return goods_promotion_url_list;
        }

        public void setGoods_promotion_url_list(List <PromotionUrlInfo> goods_promotion_url_list) {
            this.goods_promotion_url_list = goods_promotion_url_list;
        }

        public String getRequest_id() {
            return request_id;
        }

        public void setRequest_id(String request_id) {
            this.request_id = request_id;
        }
    }

    public class opt_get_response{
        private List <GoodsOptBean> goods_opt_list;

        public List <GoodsOptBean> getGoods_opt_list() {
            return goods_opt_list;
        }

        public void setGoods_opt_list(List <GoodsOptBean> goods_opt_list) {
            this.goods_opt_list = goods_opt_list;
        }
    }
    public class theme_get_detail{
        private List <TopGoodsBean> goods_list;
        private List<GoodsThemeBean> theme_list;

        public List<TopGoodsBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List<TopGoodsBean> goods_list) {
            this.goods_list = goods_list;
        }

        public List<GoodsThemeBean> getTheme_list() {
            return theme_list;
        }

        public void setTheme_list(List<GoodsThemeBean> theme_list) {
            this.theme_list = theme_list;
        }
    }


    public class goods_search{
        private List <TopGoodsBean> goods_list;
        private int total_count;//  返回商品总数

        public List <TopGoodsBean> getGoods_list() {
            return goods_list;
        }

        public void setGoods_list(List <TopGoodsBean> goods_list) {
            this.goods_list = goods_list;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }
    }

    public Data getGoods_detail_response() {
        return goods_detail_response;
    }

    public void setGoods_detail_response(Data goods_detail_response) {
        this.goods_detail_response = goods_detail_response;
    }

    public UrlgenerateData getGoods_promotion_url_generate_response() {
        return goods_promotion_url_generate_response;
    }

    public void setGoods_promotion_url_generate_response(UrlgenerateData goods_promotion_url_generate_response) {
        this.goods_promotion_url_generate_response = goods_promotion_url_generate_response;
    }

    public opt_get_response getGoods_opt_get_response() {
        return goods_opt_get_response;
    }

    public void setGoods_opt_get_response(opt_get_response goods_opt_get_response) {
        this.goods_opt_get_response = goods_opt_get_response;
    }

    public topListGet getTop_goods_list_get_response() {
        return top_goods_list_get_response;
    }

    public void setTop_goods_list_get_response(topListGet top_goods_list_get_response) {
        this.top_goods_list_get_response = top_goods_list_get_response;
    }

    public goods_search getGoods_search_response() {
        return goods_search_response;
    }

    public void setGoods_search_response(goods_search goods_search_response) {
        this.goods_search_response = goods_search_response;
    }

    public MainGoodsBean.order_list_get_response getOrder_list_get_response() {
        return order_list_get_response;
    }

    public void setOrder_list_get_response(MainGoodsBean.order_list_get_response order_list_get_response) {
        this.order_list_get_response = order_list_get_response;
    }

    public MainGoodsBean.goods_basic_detail_response getGoods_basic_detail_response() {
        return goods_basic_detail_response;
    }

    public void setGoods_basic_detail_response(MainGoodsBean.goods_basic_detail_response goods_basic_detail_response) {
        this.goods_basic_detail_response = goods_basic_detail_response;
    }
}

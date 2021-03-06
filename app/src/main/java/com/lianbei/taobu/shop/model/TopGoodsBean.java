package com.lianbei.taobu.shop.model;

import java.util.List;

public class TopGoodsBean {
       // 商品基本信息
        private String goods_id; //商品编码
        private String goods_name; //商品名称
        private String goods_desc; //商品描述
        private String goods_thumbnail_url; //商品缩略图
        private String goods_image_url;  //商品主图
        private List <Integer> goods_gallery_urls;//商品详情图列表
        private String merchant_type;
        private long min_group_price; //最小拼团价格,单位为分
        private String min_normal_price; //最小单买价格,单位为分
        private String mall_name;   //店铺名称
        private String opt_id;        //商品标签类目ID,使用pdd.goods.opt.get获取
        private String opt_name;   //商品标签名
        private List <Integer> cat_ids; //商品一~四级类目ID列表
        private boolean has_coupon;  //商品是否带券,true-带券,false-不带券
        private String coupon_min_order_amount;  //优惠券门槛价格,单位为分
        private long coupon_discount; //优惠券面额,单位为分
        private String coupon_total_quantity; //优惠券总数量
        private String coupon_remain_quantity;  //优惠券剩余数量
        private String coupon_start_time; //优惠券生效时间,UNIX时间戳
        private String coupon_end_time;  //优惠券失效时间,UNIX时间戳
        private long promotion_rate; //佣金比例,千分比
        private String goods_eval_count; //商品评价数量
        private String sales_tip; //已售卖件数
        private String desc_txt; //描述分
        private String serv_txt;  //服务分
        private String lgst_txt;  //物流分

        private List <Integer> opt_ids;
        private String mall_cps;


//     搜索商品
        private String mall_coupon_id;//店铺优惠券id
        private String mall_coupon_discount_pct; //店铺折扣
        private String mall_coupon_min_order_amount; //最小使用金额
        private String mall_coupon_max_discount_amount;//最大使用金额
        private String mall_coupon_total_quantity; //店铺券总量
        private String mall_coupon_remain_quantity;// 店铺券余量
        private String mall_coupon_start_time;// 店铺券使用开始时间
        private String mall_coupon_end_time; // 店铺券使用结束时间
        private String cat_id;//商品类目ID，使用pdd.goods.cats.get接口获取
        private String mall_id;//商家id
        //    服务标签: 4-送货入户并安装,5-送货入户,6-电子发票,9-坏果包赔,11-闪电退款,12-24小时发货,13-48小时发货,17-顺丰包邮,18-只换不修,19-全国联保,20-分期付款,24-极速退款,25-品质保障,26-缺重包退,27-当日发货,28-可定制化,29-预约配送,1000001-正品发票,1000002-送货入户并安装
        private List <Integer> service_tags;//
        private String clt_cpn_batch_sn;// 店铺收藏券id
        private String clt_cpn_start_time;//店铺收藏券起始时间
        private String clt_cpn_end_time;// 店铺收藏券截止时间
        private String clt_cpn_quantity;// 店铺收藏券总量
        private String clt_cpn_remain_quantity;//店铺收藏券剩余量
        private String clt_cpn_discount;//店铺收藏券面额，单位为分
        private String clt_cpn_min_amt;//店铺收藏券使用门槛价格，单位为分
        private String plan_type;// 推广计划类型
        private String zs_duo_id;//招商团长id
        private boolean only_scene_auth;// 快手专享

         //搜索商品
         private String goods_pic;//商品缩略图

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public String getGoods_id() {
            return goods_id;
        }
        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }
        public String getGoods_name() {
            return goods_name;
        }
        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }
        public String getGoods_desc() {
            return goods_desc;
        }
        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public String getGoods_thumbnail_url() {
            return goods_thumbnail_url;
        }

        public void setGoods_thumbnail_url(String goods_thumbnail_url) {
            this.goods_thumbnail_url = goods_thumbnail_url;
        }

        public String getGoods_image_url() {
            return goods_image_url;
        }

        public void setGoods_image_url(String goods_image_url) {
            this.goods_image_url = goods_image_url;
        }


        public String getMerchant_type() {
            return merchant_type;
        }

        public void setMerchant_type(String merchant_type) {
            this.merchant_type = merchant_type;
        }

        public long getMin_group_price() {
            return min_group_price;
        }

        public void setMin_group_price(long min_group_price) {
            this.min_group_price = min_group_price;
        }

        public String getMin_normal_price() {
            return min_normal_price;
        }

        public void setMin_normal_price(String min_normal_price) {
            this.min_normal_price = min_normal_price;
        }

        public String getMall_name() {
            return mall_name;
        }

        public void setMall_name(String mall_name) {
            this.mall_name = mall_name;
        }

        public String getOpt_id() {
            return opt_id;
        }

        public void setOpt_id(String opt_id) {
            this.opt_id = opt_id;
        }

        public String getOpt_name() {
            return opt_name;
        }

        public void setOpt_name(String opt_name) {
            this.opt_name = opt_name;
        }

        public List <Integer> getGoods_gallery_urls() {
            return goods_gallery_urls;
        }

        public void setGoods_gallery_urls(List <Integer> goods_gallery_urls) {
            this.goods_gallery_urls = goods_gallery_urls;
        }

        public List <Integer> getCat_ids() {
            return cat_ids;
        }

        public void setCat_ids(List <Integer> cat_ids) {
            this.cat_ids = cat_ids;
        }

        public List <Integer> getOpt_ids() {
            return opt_ids;
        }

        public void setOpt_ids(List <Integer> opt_ids) {
            this.opt_ids = opt_ids;
        }

        public boolean isHas_coupon() {
            return has_coupon;
        }

        public void setHas_coupon(boolean has_coupon) {
            this.has_coupon = has_coupon;
        }

        public String getCoupon_min_order_amount() {
            return coupon_min_order_amount;
        }

        public void setCoupon_min_order_amount(String coupon_min_order_amount) {
            this.coupon_min_order_amount = coupon_min_order_amount;
        }

        public long getCoupon_discount() {
            return coupon_discount;
        }

        public void setCoupon_discount(long coupon_discount) {
            this.coupon_discount = coupon_discount;
        }

        public String getCoupon_total_quantity() {
            return coupon_total_quantity;
        }

        public void setCoupon_total_quantity(String coupon_total_quantity) {
            this.coupon_total_quantity = coupon_total_quantity;
        }

        public String getCoupon_remain_quantity() {
            return coupon_remain_quantity;
        }

        public void setCoupon_remain_quantity(String coupon_remain_quantity) {
            this.coupon_remain_quantity = coupon_remain_quantity;
        }

        public String getCoupon_start_time() {
            return coupon_start_time;
        }

        public void setCoupon_start_time(String coupon_start_time) {
            this.coupon_start_time = coupon_start_time;
        }

        public String getCoupon_end_time() {
            return coupon_end_time;
        }

        public void setCoupon_end_time(String coupon_end_time) {
            this.coupon_end_time = coupon_end_time;
        }

        public long getPromotion_rate() {
            return promotion_rate;
        }

        public void setPromotion_rate(long promotion_rate) {
            this.promotion_rate = promotion_rate;
        }

        public String getGoods_eval_count() {
            return goods_eval_count;
        }

        public void setGoods_eval_count(String goods_eval_count) {
            this.goods_eval_count = goods_eval_count;
        }

        public String getSales_tip() {
            return sales_tip;
        }

        public void setSales_tip(String sales_tip) {
            this.sales_tip = sales_tip;
        }

        public String getDesc_txt() {
            return desc_txt;
        }

        public void setDesc_txt(String desc_txt) {
            this.desc_txt = desc_txt;
        }

        public String getServ_txt() {
            return serv_txt;
        }

        public void setServ_txt(String serv_txt) {
            this.serv_txt = serv_txt;
        }

        public String getLgst_txt() {
            return lgst_txt;
        }

        public void setLgst_txt(String lgst_txt) {
            this.lgst_txt = lgst_txt;
        }


        public String getMall_cps() {
            return mall_cps;
        }

        public void setMall_cps(String mall_cps) {
            this.mall_cps = mall_cps;
        }

        public String getMall_coupon_id() {
            return mall_coupon_id;
        }

        public void setMall_coupon_id(String mall_coupon_id) {
            this.mall_coupon_id = mall_coupon_id;
        }

        public String getMall_coupon_discount_pct() {
            return mall_coupon_discount_pct;
        }

        public void setMall_coupon_discount_pct(String mall_coupon_discount_pct) {
            this.mall_coupon_discount_pct = mall_coupon_discount_pct;
        }

        public String getMall_coupon_min_order_amount() {
            return mall_coupon_min_order_amount;
        }

        public void setMall_coupon_min_order_amount(String mall_coupon_min_order_amount) {
            this.mall_coupon_min_order_amount = mall_coupon_min_order_amount;
        }

        public String getMall_coupon_max_discount_amount() {
            return mall_coupon_max_discount_amount;
        }

        public void setMall_coupon_max_discount_amount(String mall_coupon_max_discount_amount) {
            this.mall_coupon_max_discount_amount = mall_coupon_max_discount_amount;
        }

        public String getMall_coupon_total_quantity() {
            return mall_coupon_total_quantity;
        }

        public void setMall_coupon_total_quantity(String mall_coupon_total_quantity) {
            this.mall_coupon_total_quantity = mall_coupon_total_quantity;
        }

        public String getMall_coupon_remain_quantity() {
            return mall_coupon_remain_quantity;
        }

        public void setMall_coupon_remain_quantity(String mall_coupon_remain_quantity) {
            this.mall_coupon_remain_quantity = mall_coupon_remain_quantity;
        }

        public String getMall_coupon_start_time() {
            return mall_coupon_start_time;
        }

        public void setMall_coupon_start_time(String mall_coupon_start_time) {
            this.mall_coupon_start_time = mall_coupon_start_time;
        }

        public String getMall_coupon_end_time() {
            return mall_coupon_end_time;
        }

        public void setMall_coupon_end_time(String mall_coupon_end_time) {
            this.mall_coupon_end_time = mall_coupon_end_time;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getMall_id() {
            return mall_id;
        }

        public void setMall_id(String mall_id) {
            this.mall_id = mall_id;
        }

        public List <Integer> getService_tags() {
            return service_tags;
        }

        public void setService_tags(List <Integer> service_tags) {
            this.service_tags = service_tags;
        }

        public String getClt_cpn_batch_sn() {
            return clt_cpn_batch_sn;
        }

        public void setClt_cpn_batch_sn(String clt_cpn_batch_sn) {
            this.clt_cpn_batch_sn = clt_cpn_batch_sn;
        }

        public String getClt_cpn_start_time() {
            return clt_cpn_start_time;
        }

        public void setClt_cpn_start_time(String clt_cpn_start_time) {
            this.clt_cpn_start_time = clt_cpn_start_time;
        }

        public String getClt_cpn_end_time() {
            return clt_cpn_end_time;
        }

        public void setClt_cpn_end_time(String clt_cpn_end_time) {
            this.clt_cpn_end_time = clt_cpn_end_time;
        }

        public String getClt_cpn_quantity() {
            return clt_cpn_quantity;
        }

        public void setClt_cpn_quantity(String clt_cpn_quantity) {
            this.clt_cpn_quantity = clt_cpn_quantity;
        }

        public String getClt_cpn_remain_quantity() {
            return clt_cpn_remain_quantity;
        }

        public void setClt_cpn_remain_quantity(String clt_cpn_remain_quantity) {
            this.clt_cpn_remain_quantity = clt_cpn_remain_quantity;
        }

        public String getClt_cpn_discount() {
            return clt_cpn_discount;
        }

        public void setClt_cpn_discount(String clt_cpn_discount) {
            this.clt_cpn_discount = clt_cpn_discount;
        }

        public String getClt_cpn_min_amt() {
            return clt_cpn_min_amt;
        }

        public void setClt_cpn_min_amt(String clt_cpn_min_amt) {
            this.clt_cpn_min_amt = clt_cpn_min_amt;
        }

        public String getPlan_type() {
            return plan_type;
        }

        public void setPlan_type(String plan_type) {
            this.plan_type = plan_type;
        }

        public String getZs_duo_id() {
            return zs_duo_id;
        }

        public void setZs_duo_id(String zs_duo_id) {
            this.zs_duo_id = zs_duo_id;
        }

        public boolean isOnly_scene_auth() {
            return only_scene_auth;
        }

        public void setOnly_scene_auth(boolean only_scene_auth) {
            this.only_scene_auth = only_scene_auth;
        }

}

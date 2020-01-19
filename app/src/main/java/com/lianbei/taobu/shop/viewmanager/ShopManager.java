package com.lianbei.taobu.shop.viewmanager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Fail;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.api.PddParam;
import com.lianbei.taobu.application.GlobalApplication;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.shop.model.GoodDetailBean;
import com.lianbei.taobu.shop.model.MainGoodsBean;
import com.lianbei.taobu.shop.model.OrderBean;
import com.lianbei.taobu.shop.model.PromotionUrlInfo;
import com.lianbei.taobu.shop.model.ShopBean;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.model.UnitUrlnfo;
import com.lianbei.taobu.utils.MGson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopManager {
    private static Context mContext;
    private List<ShopBean> beanList;
    private static volatile ShopManager instance = null;

    private ShopManager(){
    }

    public static ShopManager getInstance(Context context) {
        // if already inited, no need to get lock everytime
        mContext = context;
        if (instance == null) {
            synchronized (ShopManager.class) {
                if (instance == null) {
                    instance = new ShopManager();
                }
            }
        }
        return instance;
    }

    public ShopManager(Context mContext){
        this.mContext = mContext;
    }

    public  void  getShopListContent(RequestCompletion completion){
        new HttpUtil.Builder ( APIs.NEWS_URL )
                .Params ( new HashMap <> (  ) )
                .Tag ( mContext )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {

                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();
    }
    //查询商品标签列表
    public void opt_get(RequestCompletion completion,String parent_opt_id){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_OPT_GET);
        map.put ("parent_opt_id",parent_opt_id );
      ///  map.put ("parent_cat_id","0" );
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson  = new MGson (  ).Gson ();
                        MainGoodsBean mainGoodsBean  = gson.fromJson (model,MainGoodsBean.class);
                        if(mainGoodsBean.getGoods_opt_get_response () != null){
                            if(completion != null){
                                completion.Success ( mainGoodsBean.getGoods_opt_get_response ().getGoods_opt_list (),"" );
                            }
                        }else{
                            if(completion != null){
                                completion.Fail ( "失败" );
                            }
                        }


                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();
    }
    //获取拼多多多多进宝主题列表查询
    public void cats_get(RequestCompletion completion,String id){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_THEME_LIST_GET);
        map.put ("page_size","100");
        map.put ("page","1" );
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson  = new MGson (  ).Gson ();
                        MainGoodsBean mainGoodsBean  = gson.fromJson (model,MainGoodsBean.class);
                        if(mainGoodsBean.getTheme_list_get_response () != null){
                            if(completion != null){
                                completion.Success ( mainGoodsBean.getTheme_list_get_response().getTheme_list(),id );
                            }
                        }else{
                            if(completion != null){
                                completion.Fail ( "失败" );
                            }
                        }


                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();
    }

    //多多进宝主题商品查询
    public void Theme_Goods_list(String theme_id,RequestCompletion completion,String id){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_THEME_GOODS_SEARCH);
        map.put ("theme_id",theme_id);
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .isShowLoadProgess(true,false)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        Gson gson  = new MGson (  ).Gson ();
                        MainGoodsBean mainGoodsBean  = gson.fromJson (model,MainGoodsBean.class);
                        if(mainGoodsBean.getTheme_list_get_response () != null){
                            if(completion != null){
                                completion.Success ( mainGoodsBean.getTheme_list_get_response ().getGoods_list(),id );
                            }
                        }else{
                            if(completion != null){
                                completion.Fail ( "失败" );
                            }
                        }


                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();
    }

    /**
     * 热销商品列表
     */
   public void  top_goods(RequestCompletion completion,int offset, int limit , String str){
       Map<String,Object> map  =new HashMap <> (  );
       map.put ("type", APIs.PDD_TOP_GOODS);
       map.put ("p_id","" );
       map.put ("offset",offset+"" );
       map.put ("sort_type","" );
       map.put ("limit",limit+"" );//请求数量；默认值 ： 400
       Map param = PddParam.getParam (map );
       new HttpUtil.Builder ()
               .Params( param)
               .Tag ( mContext )
               .isShowLoadProgess ( true,false )
               .Url ( APIs.PDD_BASE_URL)
               .Success ( new Success ( ) {
                   @Override
                   public void Success(String model) {
                       try {
                           Gson gson = new MGson (  ).Gson ();
                           MainGoodsBean mainGoodsBean  = gson.fromJson (model, MainGoodsBean.class );
                           if(completion != null){
                               completion.Success ( mainGoodsBean.getTop_goods_list_get_response ().getList (),str );
                           }
                       } catch (Exception e) {
                           e.printStackTrace ( );
                       } finally {
                       }
                   }
               } ).Fail ( new Fail ( ) {
           @Override
           public void Fail(String model) {

           }
       } ).post ();

   }



    /**
     * 获取商品基本信息接口
     */
    public void  basic_info_get(RequestCompletion completion,ArrayList<String> id_list,String str){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_BASIC_INFO_GET);
        map.put ("goods_id_list",String.valueOf(id_list));
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .isShowLoadProgess ( true,false )
                .Url ( APIs.PDD_BASE_URL)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean  = gson.fromJson (model, MainGoodsBean.class );
                            if(completion != null){
                                completion.Success ( mainGoodsBean.getGoods_basic_detail_response ().getGoods_list(),str );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();

    }

    /**
     * 商品详情
     */

    public void  goods_Detail(RequestCompletion completion,String goods_id ){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_GOODS_DETAIL);
        map.put ("goods_id_list",goods_id);
        map.put ("pid","" );   //推广位id
        map.put ("custom_parameters","" ); //自定义参数
        map.put ("zs_duo_id","" );//招商多多客ID
        map.put ("plan_type","" );//佣金优惠券对应推广类型，3：专属 4：招商
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean = gson.fromJson (model, MainGoodsBean.class );
                            if(completion != null){
                                GoodDetailBean goodDetailBean = mainGoodsBean.getGoods_detail_response ().getGoods_details ().get ( 0 );
                                completion.Success ( (goodDetailBean),"" );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();

    }
    /**
     * 创建多多进宝推广位
     */

    public void  Pid_Generate(RequestCompletion completion, String number,String namelist ){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_PID_GENERATE);
        map.put ("number",number); //要生成的推广位数量，默认为10，范围为：1~100
        map.put ("p_id_name_list", "");   //推广位id
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean = gson.fromJson (model, MainGoodsBean.class );
                            if(completion != null){
                                GoodDetailBean goodDetailBean = mainGoodsBean.getGoods_detail_response ().getGoods_details ().get ( 0 );
                                completion.Success ( (goodDetailBean),"" );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();
    }

    /**
     * 多多进宝推广链接生成
     */
    public void  PDD_Url_Generate(String goods_id_list,boolean isbuy,RequestCompletion completions,String tag){
        Log.e("000request:",PddParam.getCustom_parameters(isbuy));
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_URL_GENERATE);
        map.put ("p_id", Constant.PDD_PID_HOT); //推广位ID
        map.put ("goods_id_list",goods_id_list );   //商品ID，仅支持单个查询
        map.put ("generate_short_url","true" );   //是否生成短链接，true-是，false-否
        map.put ("multi_group","false");   //true--生成多人团推广链接 false--生成单人团推广链接（默认false）1、单人团推广链接：用户访问单人团推广链接，可直接购买商品无需拼团。2、多人团推广链接：用户访问双人团推广链接开团，若用户分享给他人参团，则开团者和参团者的佣金均结算给推手
        map.put ("custom_parameters",PddParam.getCustom_parameters(isbuy));   //自定义参数，为链接打上自定义标签。自定义参数最长限制64个字节。
        map.put ("generate_weapp_webview","true" );   //是否生成唤起微信客户端链接，true-是，false-否，默认false
        map.put ("zs_duo_id","" );   //招商多多客ID
        map.put ("generate_we_app","true" );   //是否生成小程序推广
        map.put ("generate_weiboapp_webview","true" );   //是否生成微博推广链接
        map.put ("generate_mall_collect_coupon","" );   //是否生成店铺收藏券推广链接
        map.put ("generate_schema_url","true" );   //是否返回 schema URL
        map.put ("generate_qq_app","true" );   //是否生成qq小程序
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .isShowLoadProgess(true,false)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean = gson.fromJson (model, MainGoodsBean.class );
                            if(completions != null){
                                PromotionUrlInfo promotionUrlInfo = mainGoodsBean.getGoods_promotion_url_generate_response ().getGoods_promotion_url_list ().get ( 0 );
                                completions.Success (promotionUrlInfo,tag );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();
    }


    /**
     * 多多进宝转链接口
     */
    public void  PDD_ZS_UNIT_URL_GEN(String source_url,RequestCompletion completions){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_UNIT_URL_GEN);
        map.put ("p_id", Constant.PDD_PID_HOT); //推广位ID
        map.put ("source_url",source_url );   //商品ID，仅支持单个查询
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .isShowLoadProgess(true,false)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean = gson.fromJson (model, MainGoodsBean.class );
                            if(completions != null){
                                UnitUrlnfo unitUrlnfo = mainGoodsBean.getGoods_zs_unit_generate_response();
                                completions.Success (unitUrlnfo,"" );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();
    }

    public  void SearchGoods(String page,String page_size, String goods_id_list,RequestCompletion completion, String tag){
        getGoodsSearch("","","",page,page_size,"","",false,false,goods_id_list,completion,tag);

    }

    public  void SearchGoods(String keyword, String opt_id,String cat_id,String page,String page_size,String sort_type,String range_list,boolean with_coupon,boolean is_brand_goods , RequestCompletion completion, String tag){
        getGoodsSearch(keyword,opt_id,cat_id,page,page_size,sort_type,range_list,with_coupon,is_brand_goods,"",completion,tag);
    }


    /**
     *多多进宝商品查询
     * @param opt_id
     * @param page
     * @param page_size
     * @param sort_type 排序方式:0-综合排序;1-按佣金比率升序;2-按佣金比例降序;3-按价格升序;4-按价格降序;5-按销量升序;6-按销量降序;7-优惠券金额排序升序;8-优惠券金额排序降序;9-券后价升序排序;10-券后价降序排序;11-按照加入多多进宝时间升序;12-按照加入多多进宝时间降序;13-按佣金金额升序排序;14-按佣金金额降序排序;15-店铺描述评分升序;16-店铺描述评分降序;17-店铺物流评分升序;18-店铺物流评分降序;19-店铺服务评分升序;20-店铺服务评分降序;27-描述评分击败同类店铺百分比升序，28-描述评分击败同类店铺百分比降序，29-物流评分击败同类店铺百分比升序，30-物流评分击败同类店铺百分比降序，31-服务评分击败同类店铺百分比升序，32-服务评分击败同类店铺百分比降序
     */

    public  void getGoodsSearch(String keyword, String opt_id,String cat_id,String page,String page_size,String sort_type,String range_list,boolean with_coupon,boolean is_brand_goods ,String goods_id_list, RequestCompletion completion, String tag){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_GOODS_SEARCH);
        map.put ("keyword", keyword+""); //商品关键词，与opt_id字段选填一个或全部填写
        map.put ("opt_id",opt_id );   //商品标签类目ID，使用pdd.goods.opt.get获取
        map.put ("page",page );   //是否生成短链接，true-是，false-否
        map.put ("page_size",page_size );   //
        map.put ("sort_type",sort_type );   //
        map.put ("with_coupon",with_coupon+"");   //是否只返回优惠券的商品，false返回所有商品，true只返回有优惠券的商品
        map.put ("range_list",range_list);   //筛选范围列表 样例：[{"range_id":0,"range_from":1,"range_to":1500},{"range_id":1,"range_from":1,"range_to":1500}] range_id枚举及描述
        map.put ("cat_id",cat_id+"");   //商品类目ID，使用pdd.goods.cats.get接口获取
        map.put ("goods_id_list","" );   //商品ID列表。例如：[123456,123]，当入参带有goods_id_list字段，将不会以opt_id、 cat_id、keyword维度筛选商品
        map.put ("merchant_type","" );   //店铺类型，1-个人，2-企业，3-旗舰店，4-专卖店，5-专营店，6-普通店（未传为全部）
        map.put ("pid","" );   //推广位id
        map.put ("custom_parameters","" );   //自定义参数
        map.put ("merchant_type_list","" );   //店铺类型数组
        map.put ("is_brand_goods",is_brand_goods+"" );   //是否为品牌商品
        map.put ("activity_tags","" );   //商品标记数组，1-表示双十一商品
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .isShowLoadProgess(true,false)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean = gson.fromJson (model, MainGoodsBean.class );
                            if(completion != null){
                                List<TopGoodsBean> topGoodsBeans = mainGoodsBean.getGoods_search_response ().getGoods_list ();
                                completion.Success (topGoodsBeans,tag );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                completion.Fail(tag);
            }
        } ).Error(new Error() {
            @Override
            public void Error(Object... values) {
                completion.Error(tag);
            }
        }).post ();

    }
    /**
     *生成多多进宝频道推广
     */

    public  void get_resource_url(String keyword, String opt_id,String page,String page_size,String sort_type,String range_list,boolean with_coupon,boolean is_brand_goods, RequestCompletion completion, String tag){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_RESOURCE_URL_GEN);
        map.put ("custom_parameters", UserInfo.getUserInfo ( GlobalApplication.getContext ( ) ).getUser_id ( ) + "" );
        map.put ("generate_we_app","false");
        map.put ("pid","false");
        map.put ("resource_type","false");
        map.put ("url","false");
        map.put ("generate_schema_url","false");
        map.put ("generate_qq_app","false");
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .isShowLoadProgess(true,false)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean = gson.fromJson (model, MainGoodsBean.class );
                            if(completion != null){
                                List<TopGoodsBean> topGoodsBeans = mainGoodsBean.getGoods_search_response ().getGoods_list ();
                                completion.Success (topGoodsBeans,tag );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                completion.Fail(tag);
            }
        } ).Error(new Error() {
            @Override
            public void Error(Object... values) {
                completion.Error(tag);
            }
        }).post ();
    }


    /**
     *商品标准类目接口
     */

    public  void get_cats_url(String parent_cat_id, RequestCompletion completion, String tag){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_CATS_GET);
        map.put ("parent_cat_id",parent_cat_id );
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params( param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .isShowLoadProgess(true,false)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean = gson.fromJson (model, MainGoodsBean.class );
                            if(completion != null){
                              //  List<TopGoodsBean> topGoodsBeans = mainGoodsBean.getGoods_search_response ().getGoods_list ();
                              //  completion.Success (topGoodsBeans,tag );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {
                completion.Fail(tag);
            }
        } ).Error(new Error() {
            @Override
            public void Error(Object... values) {
                completion.Error(tag);
            }
        }).post ();
    }


    /**
     *用时间段查询推广订单接口
     * @param start_time//支付起始时间，如：2019-05-05 00:00:00
     * @param last_order_id  上一次的迭代器id(第一次不填)
     * @param page_size //每次请求多少条，建议300
     * @param end_time //支付结束时间，如：2019-05-05 00:00:00
     * @param completion
     */

    public  void getOrderListRange(String start_time,String last_order_id,String page_size,String end_time,RequestCompletion completion){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_ORDERLIST_RANGE);//
        map.put ("start_time", start_time);//
        map.put ("last_order_id", last_order_id);
        map.put ("page_size",page_size ); //
        map.put ("end_time",end_time );   //是否生成短链接，true-是，false-否
        Map param = PddParam.getParam (map );
        new HttpUtil.Builder ()
                .Params(param)
                .Tag ( mContext )
                .Url ( APIs.PDD_BASE_URL)
                .isShowLoadProgess(true,false)
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {
                        try {
                            Gson gson = new MGson (  ).Gson ();
                            MainGoodsBean mainGoodsBean = gson.fromJson (model, MainGoodsBean.class );
                            if(completion != null){
                                List<OrderBean> orderlist = mainGoodsBean.getOrder_list_get_response ().getOrder_list ();
                                completion.Success (orderlist,"" );
                            }
                        } catch (Exception e) {
                            e.printStackTrace ( );
                        } finally {
                        }
                    }
                } ).Fail ( new Fail ( ) {
            @Override
            public void Fail(String model) {

            }
        } ).post ();

    }
}

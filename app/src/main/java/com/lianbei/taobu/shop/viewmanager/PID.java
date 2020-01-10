package com.lianbei.taobu.shop.viewmanager;

import android.content.Context;

import com.google.gson.Gson;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Fail;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.api.PddParam;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.model.GoodDetailBean;
import com.lianbei.taobu.shop.model.MainGoodsBean;
import com.lianbei.taobu.utils.MGson;

import java.util.HashMap;
import java.util.Map;

public class PID {
    Context mContext;

    public PID(Context mContext){
        this.mContext =mContext;
    }



    /**
     * 创建多多进宝推广位
     */

    public void  Pid_Generate(RequestCompletion completion, String goods_id ){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_PID_GENERATE);
        map.put ("number",goods_id); //要生成的推广位数量，默认为10，范围为：1~100
        map.put ("p_id_name_list","" );   //推广位id
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

    public void  PDD_Url_Generate(RequestCompletion completion,String goods_id_list, String goods_id ){
        Map<String,Object> map  =new HashMap <> (  );
        map.put ("type", APIs.PDD_URL_GENERATE);
        map.put ("p_id", Constant.PDD_PID_HOT); //推广位ID
        map.put ("goods_id_list",goods_id_list );   //商品ID，仅支持单个查询
        map.put ("generate_short_url",true );   //是否生成短链接，true-是，false-否
        map.put ("multi_group","" );   //true--生成多人团推广链接 false--生成单人团推广链接（默认false）1、单人团推广链接：用户访问单人团推广链接，可直接购买商品无需拼团。2、多人团推广链接：用户访问双人团推广链接开团，若用户分享给他人参团，则开团者和参团者的佣金均结算给推手
        map.put ("custom_parameters","" );   //自定义参数，为链接打上自定义标签。自定义参数最长限制64个字节。
        map.put ("generate_weapp_webview","" );   //是否生成唤起微信客户端链接，true-是，false-否，默认false
        map.put ("zs_duo_id","" );   //招商多多客ID
        map.put ("generate_we_app","" );   //是否生成小程序推广
        map.put ("generate_weiboapp_webview","" );   //是否生成微博推广链接
        map.put ("generate_mall_collect_coupon","" );   //是否生成店铺收藏券推广链接
        map.put ("generate_schema_url","" );   //是否返回 schema URL
        map.put ("generate_qq_app","" );   //是否生成qq小程序
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
}

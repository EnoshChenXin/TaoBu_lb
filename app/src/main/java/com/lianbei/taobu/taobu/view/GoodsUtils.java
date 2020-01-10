package com.lianbei.taobu.taobu.view;

import android.util.Log;

import com.lianbei.taobu.shop.model.GoodsOptBean;

import java.util.ArrayList;
import java.util.List;

public class GoodsUtils {

    public  interface  myInterface{
        void result(Object o);
    }

    static String[] str = new String[]{"女装","百货","男装","美妆", "鞋包", "内衣", "水果", "母婴", "美食", "电器",
            "运动", "汽车",  "家访",  "家装","家具","手机","电脑" };
    static List<GoodsOptBean> goodsOptBeans =new ArrayList <> (  );
    public static  void disposeopt(List<GoodsOptBean> formerdata, myInterface completion){
        try {
            for (int i = 0; i <formerdata.size() ; i++) {
               // Log.e(i + "wwww频道---:", formerdata.get(i).getOpt_name() + "---" + formerdata.get(i).getOpt_id() + "");
            }
            if(formerdata!= null && formerdata.size ()>0 ){
                GoodsOptBean mygood0OP= new GoodsOptBean ();
                mygood0OP.setOpt_name ( "精选" );
                mygood0OP.setOpt_id ( "8569" );
                goodsOptBeans.add ( mygood0OP );
                for (String stropt: str) {
                    for (int i = 0; i < formerdata.size (); i++) {
                        if(stropt.equals (formerdata.get ( i ).getOpt_name ()  ) ){
                            goodsOptBeans.add( formerdata.get ( i ));
                            break;
                        }

                    }

                }
                if(completion != null){
                    completion.result ( goodsOptBeans );
                }
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }
//排序方式:0-综合排序;1-按佣金比率升序;2-按佣金比例降序;3-按价格升序;4-按价格降序;5-按销量升序;6-按销量降序;7-优惠券金额排序升序;8-优惠券金额排序降序;9-券后价升序排序;10-券后价降序排序;11-按照加入多多进宝时间升序;12-按照加入多多进宝时间降序;13-按佣金金额升序排序;14-按佣金金额降序排序;15-店铺描述评分升序;16-店铺描述评分降序;17-店铺物流评分升序;18-店铺物流评分降序;19-店铺服务评分升序;20-店铺服务评分降序;27-描述评分击败同类店铺百分比升序，28-描述评分击败同类店铺百分比降序，29-物流评分击败同类店铺百分比升序，30-物流评分击败同类店铺百分比降序，31-服务评分击败同类店铺百分比升序，32-服务评分击败同类店铺百分比降序
//     */
    public static String  GoodsSortType(String type){
        switch (type){
            case "综合":
               return "0";//0-综合排序;
            case "佣金":
                return "2";//1-按佣金比率升序;2-按佣金比例降序
            case "价格":
                return "4";//3-按价格升序;4-按价格降序
            case "销量":
                return "6";//5-按销量升序;6-按销量降序

        }
    return "0";
    }

}

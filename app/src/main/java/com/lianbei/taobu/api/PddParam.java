package com.lianbei.taobu.api;

import android.content.Context;
import android.util.Log;

import com.lianbei.taobu.R;
import com.lianbei.taobu.application.GlobalApplication;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.utils.TimeUtils;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.utils.secureity.MD5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 构建pdd参数
 */
public class PddParam {

	static List<String> stringList = new ArrayList <> (  );;
	static StringBuffer sb = new StringBuffer (  );

	public static Map getParam(Map<String,Object> map) {
		stringList.clear ();
		sb.setLength(0);
		Map <String, Object> param = new HashMap <> ( );
		param.putAll (map);
		param.put ( "client_id", Constant.client_id );
		//  param.put ( "access_token", "" );
		param.put ( "timestamp", TimeUtils.getNowTimeStamp ( ) + "" );
		param.put ( "data_type", "JSON" );
		//将所有的key取出来重排
		for(String s1:param.keySet()){//遍历map的键
			stringList.add ( s1 );
		}
		Collections.sort(stringList);
		//拼装 sing
		sb.append (Constant.client_secret );
		for (String keyStr :stringList ){
			sb.append ( keyStr+param.get ( keyStr ) );
		}
		sb.append (Constant.client_secret );
		param.put("sign",MD5.stringToMD5( sb.toString ()).toUpperCase ());
		Log.e ("sign", sb.toString () );
		return param;
	}

	/**
	 * 用户获取佣金金额
	 * @param nowprice 当前最小价格
	 * @param promotion 佣金比例  千分比
	 * @return
	 */

	public static long  getCommission(long nowprice,long promotion){

		double money =nowprice * ((promotion)/(double)1000)* Constant.MAKE_MONEY_RATE;
		long lmoney = new Double(money).longValue();

		return lmoney;
	}


	public static String  getCustom_parameters(boolean isbug){

		String Custom = "cate:"+Constant.CATE+
				        "|"+"uid:"+ UserInfo.getUserInfo ( GlobalApplication.getContext ( ) ).getUser_id ( ) + ""+
				        "|"+"sid:"+isbug +
				        "|"+"actid:"+Constant.ACTID ;
		return Custom;
	}

	public  static void setCustom_parameters(Context context,String title,String opt){
            if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__ZLXMD))){//助力享免单
				Constant.ACTID ="-1";
			}else if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__GXMD))){ //购享免单
				Constant.ACTID ="-8";
			}else if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__BKZQ))){//爆款专区
				Constant.ACTID ="-3";
			}else if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__ZTZQ))){
				Constant.ACTID ="-4";
			}else if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__TDHW))){//糖豆换物
				Constant.ACTID ="-2";
			}else if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__YHJ))){//优惠卷
				Constant.ACTID ="-5";
			}else if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__YXHH))){//优选好货
				Constant.ACTID ="-6";
			}else if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__GYBD))){//高佣榜单
				Constant.ACTID ="-7";
			}else if(title.equals(context.getResources().getString(R.string.NAVIGATION_TITLE__99BY))){//9.9包邮
				Constant.ACTID ="-9";
			}else{
            	if(!Validator.isStrNotEmpty(title)){
					Constant.ACTID =opt;
				}

			}



	}


}

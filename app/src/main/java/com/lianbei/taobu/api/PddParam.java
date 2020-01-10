package com.lianbei.taobu.api;

import android.util.Log;

import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.utils.TimeUtils;
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
}

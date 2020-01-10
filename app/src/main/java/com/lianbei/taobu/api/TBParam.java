package com.lianbei.taobu.api;

import android.util.Log;

import com.lianbei.taobu.application.GlobalApplication;
import com.lianbei.taobu.mine.model.UserInfo;
import java.util.HashMap;
import java.util.Map;

/**
 * 构建淘步参数
 */
public class TBParam {
    @SuppressWarnings("unchecked")
    public static Map MapParam(Map <String, Object> map) {
        Map <String, Object>  param= new HashMap <> ( );
        param.put ( "user_id", UserInfo.getUserInfo ( GlobalApplication.getContext ( ) ).getUser_id ( ) + "" );
        if (map != null) {
            param.putAll ( map );
            return param;
        }else{
            return param;
        }
    }
}
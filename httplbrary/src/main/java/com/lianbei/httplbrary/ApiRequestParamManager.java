package com.lianbei.httplbrary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HASEE on 2017/3/3.
 */

public class ApiRequestParamManager {

    private static Map<String, String> requestparams = null;

    public static void apiPublicParams(Map<String, String> params) {
        if (requestparams == null) {
            requestparams = new HashMap<>();
            requestparams.putAll(params);
        } else
            requestparams.putAll(params);
    }

    public static Map<String, String> getApiRequestparams() {
        return requestparams;
    }

    public static void setApiRequestParam() {
        Map<String, String> map = new HashMap<>();
        map.put("Integer", "");//1	会员id	Integer	userId	是	1
        map.put("token", "");
        map.put("appId", "");
        ApiRequestParamManager.apiPublicParams(map);
        apiPublicParams(map);
    }
}

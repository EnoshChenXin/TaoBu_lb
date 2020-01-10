package com.lianbei.httplbrary.utils;

import java.util.Map;

/**
 * Created by HASEE on 2017/3/8.
 */

public interface ApiRequestParamInterface {

    public String  RequestParam();
    public Map<String,String> parammap();
    public void result(Object object);
}

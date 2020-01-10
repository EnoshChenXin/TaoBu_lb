package com.lianbei.taobu.listener;

/**
 * Created by chenyxin
 */

public interface RequestCompletion {

    void Success(Object value, String tag);
    void Fail(Object value);//业务上的400
    void Error(Object... values);//网络问题、超时、连不上服务器 404等
}

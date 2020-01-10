package com.lianbei.taobu.listener;

/**
 * QQ分享请求成功
 */

public interface ShareRequestCompletion {

    void QQShareSuccess(Object value);
    void WXShareSuccess(Object value);//业务上的400
    void WBShareSuccess(Object... values);//网络问题、超时、连不上服务器 404等
}

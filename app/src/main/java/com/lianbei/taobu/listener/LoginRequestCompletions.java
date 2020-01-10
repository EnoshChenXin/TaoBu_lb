package com.lianbei.taobu.listener;

public interface LoginRequestCompletions {
    void WXLoginSuccess(Object value);//业务上的400
    void WXLoginFail(Object value);//业务上的400
}

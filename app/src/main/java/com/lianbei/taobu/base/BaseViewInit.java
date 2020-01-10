package com.lianbei.taobu.base;

/**
 * Created by SilenceDut on 16/10/25.
 */


public interface BaseViewInit {
    int getContentViewId();
    void initViews();
    void initData();
    void initListener();
    void initBeforeView();
}

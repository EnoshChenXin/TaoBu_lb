package com.lianbei.taobu.shop.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.shop.model.ShopBean;
import com.lianbei.taobu.shop.adapter.itemview.ShopItemProvider;

import java.util.List;

public class ShopListAdatper extends MultipleItemRvAdapter <ShopBean,BaseViewHolder> {
    private String mChannelCode;
    /**
     * 纯文字布局(文章、广告)
     */
    public static final int TEXT_NEWS = 100;

    public ShopListAdatper(String channelCode, @Nullable List<ShopBean> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }


    public ShopListAdatper(@Nullable List <ShopBean> data) {
        super ( data );
    }

    @Override
    protected int getViewType(ShopBean shopBean) {
        return TEXT_NEWS;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new ShopItemProvider (mChannelCode) );
    }

}

package com.lianbei.taobu.shop.adapter.itemview;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.lianbei.taobu.shop.model.ShopBean;

public abstract class BaseShopItemProvider extends BaseItemProvider <ShopBean, BaseViewHolder> {

    private String mChannelCode;

    public BaseShopItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder, ShopBean shopBean, int i) {
        setData(baseViewHolder,shopBean);
    }
    protected abstract void setData(BaseViewHolder helper, ShopBean s);
}

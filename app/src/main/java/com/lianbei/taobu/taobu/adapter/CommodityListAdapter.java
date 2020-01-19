package com.lianbei.taobu.taobu.adapter;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.taobu.model.CommodityBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CommodityListAdapter extends MultipleItemRvAdapter <CommodityBean, BaseViewHolder> {

    private String mChannelCode;
    public CommodityListAdapter(String channelCode, @Nullable List<CommodityBean> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }
    @Override
    protected int getViewType(CommodityBean commodityBean) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new CommodityItemProvider (mChannelCode) );
    }
}

package com.lianbei.taobu.taobu.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.taobu.adapter.itemprovider.DynamicItemProvider;
import com.lianbei.taobu.taobu.model.DynamicBean;

import java.util.List;

public class DynamicListAdapter extends MultipleItemRvAdapter <DynamicBean.DynamicList, BaseViewHolder> {

    private String mChannelCode;
    public DynamicListAdapter(String channelCode, @Nullable List<DynamicBean.DynamicList> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }
    @Override
    protected int getViewType(DynamicBean.DynamicList commodityBean) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new DynamicItemProvider (mChannelCode) );
    }
}

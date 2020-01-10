package com.lianbei.taobu.circle.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.circle.adapter.itemprovider.GroupManagerItemProvider;
import com.lianbei.taobu.circle.model.GroupPoperBean;
import com.lianbei.taobu.circle.model.lineListBean;

import java.util.List;

public class GroupManagerAdapter extends MultipleItemRvAdapter <lineListBean.ListBean, BaseViewHolder> {

    private String mChannelCode;
    public GroupManagerAdapter(String channelCode, @Nullable List<lineListBean.ListBean> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }
    @Override
    protected int getViewType(lineListBean.ListBean groupPoperBean) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new GroupManagerItemProvider (mChannelCode) );
    }
}

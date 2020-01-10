package com.lianbei.taobu.circle.adapter.itemprovider;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.circle.model.GroupPoperBean;
import com.lianbei.taobu.circle.model.lineListBean;

public abstract class BaseGroupManagerItemProvider extends BaseItemProvider <lineListBean.ListBean, BaseViewHolder> {

    private String mChannelCode;

    public BaseGroupManagerItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder, lineListBean.ListBean groupPoperBean, int i) {
        setData(baseViewHolder,groupPoperBean);
    }
    protected abstract void setData(BaseViewHolder helper, lineListBean.ListBean groupPoperBean);
}

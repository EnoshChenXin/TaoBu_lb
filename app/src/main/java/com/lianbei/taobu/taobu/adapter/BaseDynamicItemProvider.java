package com.lianbei.taobu.taobu.adapter;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.lianbei.taobu.R;
import com.lianbei.taobu.taobu.model.CommodityBean;
import com.lianbei.taobu.taobu.model.DynamicBean;
import com.lianbei.taobu.utils.GlideUtils;

public abstract class BaseDynamicItemProvider extends BaseItemProvider <DynamicBean.DynamicList, BaseViewHolder> {

    private String mChannelCode;

    public BaseDynamicItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder, DynamicBean.DynamicList dynamicBean, int i) {
        GlideUtils.getInstance ().load(mContext,dynamicBean.getHeadimgurl (), baseViewHolder.getView( R.id.headImageView));
        baseViewHolder.setText( R.id.nickName, dynamicBean.getNickname ());
        baseViewHolder.setText( R.id.creatTime, dynamicBean.getCreate_time ());
        setData(baseViewHolder,dynamicBean);
    }
    protected abstract void setData(BaseViewHolder helper, DynamicBean.DynamicList dynamicBean);
}

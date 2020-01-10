package com.lianbei.taobu.taobu.adapter;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.lianbei.taobu.R;
import com.lianbei.taobu.taobu.model.CommodityBean;

public abstract class BaseCommodityItemProvider extends BaseItemProvider <CommodityBean, BaseViewHolder> {

    private String mChannelCode;

    public BaseCommodityItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder, CommodityBean commodityBean, int i) {
        baseViewHolder.setText( R.id.goods_name, commodityBean.getTitle ());
        setData(baseViewHolder,commodityBean);
    }
    protected abstract void setData(BaseViewHolder helper, CommodityBean commodityBean);
}

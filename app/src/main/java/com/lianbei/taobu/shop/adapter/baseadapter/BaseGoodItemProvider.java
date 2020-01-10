package com.lianbei.taobu.shop.adapter.baseadapter;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.lianbei.taobu.R;
import com.lianbei.taobu.shop.model.TopGoodsBean;

public abstract class BaseGoodItemProvider extends BaseItemProvider <TopGoodsBean, BaseViewHolder> {

    private String mChannelCode;

    public BaseGoodItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder, TopGoodsBean shopBean, int i) {
        baseViewHolder.setText( R.id.goods_name, shopBean.getGoods_name ());
        setData(baseViewHolder,shopBean);
    }
    protected abstract void setData(BaseViewHolder helper, TopGoodsBean s);
}

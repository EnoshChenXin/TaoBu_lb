package com.lianbei.taobu.taobu.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.taobu.model.CommodityBean;

public class CommodityItemProvider extends BaseCommodityItemProvider {
    public CommodityItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return 0;
    }

    @Override
    public int layout() {
        return R.layout.item_commodity_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, CommodityBean commodityBean) {

        helper.setText ( R.id.describe,commodityBean.getDescribe ()+"" );
        helper.setText ( R.id.price,commodityBean.getPrice ()+"" );
    }
}

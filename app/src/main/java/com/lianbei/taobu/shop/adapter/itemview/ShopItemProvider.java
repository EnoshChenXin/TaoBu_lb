package com.lianbei.taobu.shop.adapter.itemview;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.shop.adapter.ShopListAdatper;
import com.lianbei.taobu.shop.model.ShopBean;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.views.PriceView;

public class ShopItemProvider extends BaseShopItemProvider {
    public ShopItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return ShopListAdatper.TEXT_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_commodity_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, ShopBean shopBean) {
        helper.setText( R.id.goods_name, shopBean.getTitle ());
        PriceView priceView = helper.getView ( R.id.oldprice );
        priceView.setText ("54.3" );
        GlideUtils.getInstance ().load(mContext, shopBean.getImgurl ().replace("list/300x196", "large"), helper.getView(R.id.iv_img));

    }
}

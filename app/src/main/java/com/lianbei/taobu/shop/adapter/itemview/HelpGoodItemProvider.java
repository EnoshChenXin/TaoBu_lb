package com.lianbei.taobu.shop.adapter.itemview;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodItemProvider;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodListAdatper;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.utils.GlideUtils;

public class HelpGoodItemProvider extends BaseGoodItemProvider {
    public HelpGoodItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return BaseGoodListAdatper.FREE_GOOD;
    }

    @Override
    public int layout() {
        return R.layout.item_free_good_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, TopGoodsBean shopBean) {
        try {
           // helper.addOnClickListener (  R.id.free_btn);
            helper.setText ( R.id.sales_tip, "已送出"+shopBean.getSales_tip ()+"件" );
            GlideUtils.getInstance ().load(mContext, shopBean.getGoods_thumbnail_url().replace("list/300x300", "large"), helper.getView(R.id.iv_img));
        } catch (Exception e) {
            e.printStackTrace ( );
        }


    }
}

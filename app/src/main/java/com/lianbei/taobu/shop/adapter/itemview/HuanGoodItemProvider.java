package com.lianbei.taobu.shop.adapter.itemview;

import android.util.Log;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodItemProvider;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodListAdatper;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.utils.AmountUtils;
import com.lianbei.taobu.utils.GlideUtils;

public class HuanGoodItemProvider extends BaseGoodItemProvider {
    public HuanGoodItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return BaseGoodListAdatper.HUAN_GOOD;
    }

    @Override
    public int layout() {
        return R.layout.item_help_good_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, TopGoodsBean shopBean) {
        try {
           // helper.addOnClickListener (  R.id.free_btn);
            double TD_NUM =   (Double.parseDouble(AmountUtils.changeF2Y2(shopBean.getMin_group_price()))) * Constant.MAKE_TANDOU_RATE;
            long lmoney = new Double(TD_NUM).longValue();
            helper.setText ( R.id.tangdou_tip, String.valueOf(lmoney)+"");
            GlideUtils.getInstance ().load(mContext, shopBean.getGoods_pic ().replace("list/300x300", "large"), helper.getView(R.id.iv_img));
        } catch (Exception e) {
            e.printStackTrace ( );
        }


    }
}

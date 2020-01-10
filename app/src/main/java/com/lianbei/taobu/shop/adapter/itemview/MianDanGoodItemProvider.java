package com.lianbei.taobu.shop.adapter.itemview;

import android.graphics.Typeface;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.api.PddParam;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodItemProvider;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodListAdatper;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.utils.AmountUtils;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.views.PriceView;

public class MianDanGoodItemProvider extends BaseGoodItemProvider {
    public MianDanGoodItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return BaseGoodListAdatper.MAIN_DAN_GOOD;
    }

    @Override
    public int layout() {
        return R.layout.item_commodity_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, TopGoodsBean shopBean) {
        try {
            helper.setText ( R.id.coupon_discount, AmountUtils.changeF2Y2 (shopBean.getCoupon_discount ())+"元卷" );
            if(Validator.isStrNotEmpty(shopBean.getSales_tip ())) {
                helper.setText(R.id.describe, "已售" + shopBean.getSales_tip() + "单");
            }

            //现价
            long nowprice =shopBean.getMin_group_price ()-shopBean.getCoupon_discount ();
            PriceView priceView = helper.getView ( R.id.nowprice);
            priceView.setTypeface(Typeface.DEFAULT_BOLD);
            priceView.setText (AmountUtils.changeF2Y2 (nowprice)+"" );
            //佣金
//            double money =nowprice * ((shopBean.getPromotion_rate ())/(double)1000)* Constant.MAKE_MONEY_RATE;
//            long lmoney = new Double(money).longValue();
            long lmoney =  PddParam.getCommission(nowprice,shopBean.getPromotion_rate ());
            PriceView makemoney = helper.getView ( R.id.makeMoney);
            makemoney.setText (AmountUtils.changeF2Y2 (lmoney)+"");
            //原价
            PriceView oldprice = helper.getView ( R.id.oldprice);
            oldprice.setText ( AmountUtils.changeF2Y2 (shopBean.getMin_group_price ())+"" );
            GlideUtils.getInstance ().load200(mContext, shopBean.getGoods_thumbnail_url(), helper.getView(R.id.iv_img));
        } catch (Exception e) {
            e.printStackTrace ( );
        }

    }
}

package com.lianbei.taobu.mine.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.R;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.shop.model.OrderBean;
import com.lianbei.taobu.utils.AmountUtils;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.TimeUtils;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.views.CustomRoundAngleImageView;
import com.lianbei.taobu.views.PriceView;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 动态详情
 */
public class OrderListAdapter extends MultipleItemRvAdapter <OrderBean, BaseViewHolder> {

    private String mChannelCode;
    public OrderListAdapter(String channelCode, @Nullable List<OrderBean> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }

    @Override
    protected int getViewType(OrderBean orderBean) {
            return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new DynamicDetailLisetTiemProvider (mChannelCode) );
    }

    public  class DynamicDetailLisetTiemProvider extends BaseItemProvider <OrderBean, BaseViewHolder> {

        private String mChannelCode;

        public DynamicDetailLisetTiemProvider(String channelCode) {
            mChannelCode = channelCode;
        }
        @Override
        public int viewType() {
            return 0;
        }

        @Override
        public int layout() {
            return R.layout.item_order_layout;
        }

        @Override
        public void convert(BaseViewHolder baseViewHolder, OrderBean orderBean, int i) {
            CustomRoundAngleImageView imageView = baseViewHolder.getView ( R.id.iv_img );
            GlideUtils.getInstance ().load ( mContext,orderBean.getGoods_thumbnail_url (), imageView);
            baseViewHolder.setText ( R.id.goods_name, orderBean.getGoods_name ()+"");
            baseViewHolder.setText ( R.id.order_sn, orderBean.getOrder_sn ()+"");
            PriceView order_amount = baseViewHolder.getView(R.id.order_amount);
            PriceView promotion_amount = baseViewHolder.getView(R.id.promotion_amount);
            order_amount.setText(AmountUtils.changeF2Y2 (orderBean.getOrder_amount ())+"");//实际支付金额

            double money =(orderBean.getPromotion_amount ())* Constant.MAKE_MONEY_RATE;
            long lmoney = new Double(money).longValue();
            promotion_amount.setText(AmountUtils.changeF2Y2 (lmoney)+"");//佣金
            baseViewHolder.setText ( R.id.order_create_time,  TimeUtils.getUnixTransferTime(orderBean.getOrder_group_success_time(),"yyyy-MM-dd HH:mm:ss"));
            baseViewHolder.setText ( R.id.order_status_desc, orderBean.getOrder_status_desc()+"");

            baseViewHolder.getView(R.id.btn_copy_order_sn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager)mContext.getSystemService ( Context.CLIPBOARD_SERVICE );
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText ( "Label", orderBean.getOrder_sn ()+"" );
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip ( mClipData );
                    ToastUtil.showShort(mContext,"订单号已复制");
                }
            });

        }
    }

}

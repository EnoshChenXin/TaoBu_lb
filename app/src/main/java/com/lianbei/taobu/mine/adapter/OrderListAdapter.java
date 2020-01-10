package com.lianbei.taobu.mine.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.R;
import com.lianbei.taobu.shop.model.OrderBean;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.TimeUtils;
import com.lianbei.taobu.views.CustomRoundAngleImageView;

import java.util.List;

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
            baseViewHolder.setText ( R.id.order_create_time,  TimeUtils.getUnixTransferTime(orderBean.getOrder_create_time(),"yyyy-MM-dd hh:mm:ss"));
            baseViewHolder.setText ( R.id.order_status_desc, orderBean.getOrder_status_desc()+"");
        }
    }

}

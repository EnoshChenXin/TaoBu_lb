package com.lianbei.taobu.shop.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.shop.model.ShopBean;

import java.util.List;

import androidx.annotation.Nullable;

public class ShopRecommendListAdapter extends BaseQuickAdapter <ShopBean, BaseViewHolder> {

    public ShopRecommendListAdapter(int layoutResId, @Nullable List <ShopBean> data) {
        super ( layoutResId, data );
    }

    public ShopRecommendListAdapter(@Nullable List <ShopBean> data) {
        super(R.layout.item_shop_recommend_list, data);
    }

    public ShopRecommendListAdapter(int layoutResId) {
        super ( layoutResId );
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ShopBean shopRecommendBean) {

    }
}

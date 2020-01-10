package com.lianbei.taobu.mine.view.addresspickerview;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;

import java.util.List;

/**
 */
public class CityAdapter extends BaseQuickAdapter<AddressDistrictBean.CityBean, BaseViewHolder> {
    public CityAdapter(int layoutResId, @Nullable List<AddressDistrictBean.CityBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressDistrictBean.CityBean item) {
        helper.setText(R.id.textview, item.getLabel());
        helper.setTextColor(R.id.textview, item.isStatus() ? Color.parseColor("#65C15C") : Color.parseColor("#444444"));
    }
}

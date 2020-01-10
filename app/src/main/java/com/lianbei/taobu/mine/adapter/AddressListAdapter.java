package com.lianbei.taobu.mine.adapter;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.R;
import com.lianbei.taobu.mine.model.AddressBean;
import com.lianbei.taobu.views.CustomRoundAngleImageView;

import java.util.List;

/**
 * 动态详情
 */
public class AddressListAdapter extends MultipleItemRvAdapter <AddressBean, BaseViewHolder> {

    private String mChannelCode;
    public AddressListAdapter(String channelCode, @Nullable List<AddressBean> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }

    @Override
    protected int getViewType(AddressBean addressBean) {
            return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new AddressItemProvider (mChannelCode) );
    }

    public  class AddressItemProvider extends BaseItemProvider <AddressBean, BaseViewHolder> {

        private String mChannelCode;

        public AddressItemProvider(String channelCode) {
            mChannelCode = channelCode;
        }
        @Override
        public int viewType() {
            return 0;
        }

        @Override
        public int layout() {
            return R.layout.item_address_layout;
        }

        @Override
        public void convert(BaseViewHolder baseViewHolder, AddressBean addressBean, int i) {
            baseViewHolder.setText(R.id.name,addressBean.getName()+"");
            baseViewHolder.setText(R.id.phone,addressBean.getPhone()+"");
            baseViewHolder.setText(R.id.district_address,addressBean.getDistrict()+"  "+addressBean.getAddress()+"");
            baseViewHolder.addOnClickListener(R.id.edit_address);
            ImageView imageView = baseViewHolder.getView(R.id.radiobtn);
            TextView choose_tv =  baseViewHolder.getView(R.id.choose_tv);
            if(addressBean.isIschoose()){
                imageView.setBackgroundResource(R.drawable.radio_choose);
                choose_tv.setTextColor(mContext.getResources().getColor(R.color.main_color));
            }else{
                imageView.setBackground(mContext.getResources().getDrawable(R.drawable.radio_choose_defaul));
                choose_tv.setTextColor(mContext.getResources().getColor(R.color.gray_deep2));
            }
        }
    }

}

package com.lianbei.taobu.shop.adapter.baseadapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.shop.adapter.itemview.HelpGoodItemProvider;
import com.lianbei.taobu.shop.adapter.itemview.HuanGoodItemProvider;
import com.lianbei.taobu.shop.adapter.itemview.SearchGoodItemProvider;
import com.lianbei.taobu.shop.adapter.itemview.MianDanGoodItemProvider;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.utils.Validator;

import java.util.List;

public class BaseGoodListAdatper extends MultipleItemRvAdapter <TopGoodsBean,BaseViewHolder> {
    private String mChannelCode;
    private int vierType;
    /**
     * 暴热商品
     */
    public static final int MAIN_DAN_GOOD = 100;//
    /**
     * 免费领取
     */
    public static final int FREE_GOOD = 200;//
    /**
     * 普通商品
     */
    public static final int COMMON_GOOD = 300;//
    /**
     * 糖豆換物
     */
    public static final int HUAN_GOOD = 400;//

    /**
     * other
     */
    public static final int OTHHER = 1000;//

    public BaseGoodListAdatper(String channelCode, int vierType, @Nullable List<TopGoodsBean> data) {
        super(data);
        mChannelCode = channelCode;
        this.vierType = vierType;
        finishInitialize();
    }
    public void addNewData(List<TopGoodsBean> data) {
        if (Validator.isListNotEmpty ( data )) {
            mData.addAll(0, data);
            notifyDataSetChanged();
        }
    }

    public BaseGoodListAdatper(@Nullable List <TopGoodsBean> data) {
        super ( data );
    }

    @Override
    protected int getViewType(TopGoodsBean shopBean) {


        return vierType;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
      //  mProviderDelegate.registerProvider ( new ShopItemProvider (mChannelCode) );
        mProviderDelegate.registerProvider ( new HelpGoodItemProvider (mChannelCode) );///BaseGoodListAdatper.FREE_GOOD
        mProviderDelegate.registerProvider ( new MianDanGoodItemProvider(mChannelCode) );//BaseGoodListAdatper.TOP_GOOD
        mProviderDelegate.registerProvider ( new SearchGoodItemProvider (mChannelCode) );//BaseGoodListAdatper.COMMON_GOOD
        mProviderDelegate.registerProvider ( new HuanGoodItemProvider(mChannelCode) );//BaseGoodListAdatper.HUAN_GOOD
    }

}

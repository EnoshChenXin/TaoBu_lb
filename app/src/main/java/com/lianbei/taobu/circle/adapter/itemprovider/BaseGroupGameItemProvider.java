package com.lianbei.taobu.circle.adapter.itemprovider;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.taobu.model.CommodityBean;

public abstract class BaseGroupGameItemProvider extends BaseItemProvider <GameBean.Gamelist, BaseViewHolder> {

    private String mChannelCode;

    public BaseGroupGameItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder, GameBean.Gamelist commodityBean, int i) {
        if(mChannelCode.equals ( Constant.SEARCH_GROUP )){
            baseViewHolder.setVisible ( R.id.add_group,true);
            baseViewHolder.addOnClickListener (  R.id. add_group );
        }else{
            baseViewHolder.setGone ( R.id.add_group,true );
        }

        setData(baseViewHolder,commodityBean);
    }
    protected abstract void setData(BaseViewHolder helper, GameBean.Gamelist news);
}

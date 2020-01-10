package com.lianbei.taobu.circle.adapter.base;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.model.ItemDymlist;
import com.lianbei.taobu.circle.model.ItemInfoMsg;
import com.lianbei.taobu.taobu.model.DynamicBean;
import com.lianbei.taobu.utils.GlideUtils;

public abstract class BaseDisscussItemProvider extends BaseItemProvider <ItemInfoMsg.msglist, BaseViewHolder> {

    private String mChannelCode;

    public BaseDisscussItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder,ItemInfoMsg.msglist msglist, int i) {
        GlideUtils.getInstance ().load(mContext,msglist.getHeadimgurl (), baseViewHolder.getView( R.id.headImageView));
        baseViewHolder.setText( R.id.nickName, msglist.getNickname ());
        baseViewHolder.setText( R.id.creatTime, msglist.getCreate_time ());
        setData(baseViewHolder,msglist);
    }
    protected abstract void setData(BaseViewHolder helper, ItemInfoMsg.msglist msglist);
}

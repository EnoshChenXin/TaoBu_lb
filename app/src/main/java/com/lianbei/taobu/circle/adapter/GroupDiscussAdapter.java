package com.lianbei.taobu.circle.adapter;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.circle.adapter.itemprovider.DiscussItemProvider;
import com.lianbei.taobu.circle.model.ItemInfoMsg;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 群组讨论内容
 */
public class GroupDiscussAdapter extends MultipleItemRvAdapter <ItemInfoMsg.msglist, BaseViewHolder> {


    private String mChannelCode;
    public GroupDiscussAdapter(String channelCode, @Nullable List<ItemInfoMsg.msglist> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }
    @Override
    protected int getViewType(ItemInfoMsg.msglist commodityBean) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new DiscussItemProvider (mChannelCode) );
    }
}

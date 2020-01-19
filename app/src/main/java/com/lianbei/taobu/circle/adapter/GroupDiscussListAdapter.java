package com.lianbei.taobu.circle.adapter;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.circle.adapter.base.BaseDisscussListProvider;
import com.lianbei.taobu.circle.model.ItemInfoMsg;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 群组留言回复列表
 */
public class GroupDiscussListAdapter extends MultipleItemRvAdapter <ItemInfoMsg.msglist.childMsgList, BaseViewHolder> {


    private String mChannelCode;
    public GroupDiscussListAdapter(String channelCode, @Nullable List<ItemInfoMsg.msglist.childMsgList> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }
    @Override
    protected int getViewType(ItemInfoMsg.msglist.childMsgList commodityBean) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new BaseDisscussListProvider (mChannelCode) );
    }
}

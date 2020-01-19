package com.lianbei.taobu.circle.adapter;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.taobu.adapter.GroupGameItemProvider;

import java.util.List;

import androidx.annotation.Nullable;

public class GroupGameListAdapter extends MultipleItemRvAdapter <GameBean.Gamelist, BaseViewHolder> {

    private String mChannelCode;
    public GroupGameListAdapter(String channelCode, @Nullable List<GameBean.Gamelist> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }
    @Override
    protected int getViewType(GameBean.Gamelist gamelist) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new GroupGameItemProvider (mChannelCode) );
    }
}

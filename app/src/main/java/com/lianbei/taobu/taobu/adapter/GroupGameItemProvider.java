package com.lianbei.taobu.taobu.adapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.adapter.itemprovider.BaseGroupGameItemProvider;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.utils.GlideUtils;

public class GroupGameItemProvider extends BaseGroupGameItemProvider {
    private String mChannelCode;
    public GroupGameItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return 0;
    }

    @Override
    public int layout() {
        return R.layout.item_group_game_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, GameBean.Gamelist gamelist) {
        GlideUtils.getInstance ().load(mContext, gamelist.getIcon ().replace("list/300x196", "large"), helper.getView(R.id.headImageView));
        helper.setText ( R.id.game_title,gamelist.getTitle ()+"" );
        helper.setText ( R.id.describe,gamelist.getRemark ()+"" );
    }
}

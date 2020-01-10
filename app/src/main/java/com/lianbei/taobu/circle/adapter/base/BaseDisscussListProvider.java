package com.lianbei.taobu.circle.adapter.base;


import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.model.ItemInfoMsg;
import com.lianbei.taobu.utils.GlideUtils;

public  class BaseDisscussListProvider extends BaseItemProvider <ItemInfoMsg.msglist.childMsgList, BaseViewHolder> {

    private String mChannelCode;

    public BaseDisscussListProvider(String channelCode) {
        mChannelCode = channelCode;
    }


    @Override
    public int viewType() {
        return 0;
    }

    @Override
    public int layout() {
        return R.layout.item_discuss_list_layout;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder,ItemInfoMsg.msglist.childMsgList msglist, int i) {
        GlideUtils.getInstance ().loadRound (mContext,msglist.getHeadimgurl (), baseViewHolder.getView( R.id.child_headImageView));
        baseViewHolder.setText( R.id.child_title,msglist.getNickname ());
        baseViewHolder.setText( R.id.child_content, msglist.getContent ());
    }
}

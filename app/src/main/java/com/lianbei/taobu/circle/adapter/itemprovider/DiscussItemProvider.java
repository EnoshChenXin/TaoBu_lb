package com.lianbei.taobu.circle.adapter.itemprovider;


import com.chad.library.adapter.base.BaseViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.adapter.base.BaseDisscussItemProvider;
import com.lianbei.taobu.circle.model.ItemInfoMsg;
import com.lianbei.taobu.utils.Validator;

/**
 * 群组列表讨论 -- 非详情页
 */
public class DiscussItemProvider extends BaseDisscussItemProvider {

    public DiscussItemProvider(String channelCode) {
        super ( channelCode );
    }

    @Override
    public int viewType() {
        return 0;
    }

    @Override
    public int layout() {
        return R.layout.item_discuss_layout;
    }

    @Override
    protected void setData(BaseViewHolder helper, ItemInfoMsg.msglist dynamicBean) {
        if (Validator.isStrNotEmpty ( dynamicBean.getContent ( ) )) {
            helper.setVisible ( R.id.content_lin, true );
            helper.setGone ( R.id.clocNnum, false );
            helper.setText ( R.id.content, dynamicBean.getContent ( ) + "" );
        }
        if (false) {
            helper.setBackgroundRes ( R.id.like_btn, R.mipmap.like2 );
            helper.setTextColor ( R.id.like_num, this.mContext.getResources ( ).getColor ( R.color.gary ) );
        } else {
            helper.setBackgroundRes ( R.id.like_btn, R.mipmap.like );
            helper.setTextColor ( R.id.like_num, this.mContext.getResources ( ).getColor ( R.color.text_grey ) );
        }
//        helper.setText ( R.id.message2_num, dynamicBean.getNum ( ) + "" );
//        helper.setText ( R.id.like_num, dynamicBean.getLike ( ) + "" );
        helper.addOnClickListener ( R.id.like_lin );
        helper.addOnClickListener ( R.id.like_btn );
        helper.addOnClickListener ( R.id.message2_lin );
        helper.addOnClickListener ( R.id.message2_btn );

    }
}

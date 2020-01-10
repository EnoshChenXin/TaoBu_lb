package com.lianbei.taobu.taobu.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.R;
import com.lianbei.taobu.taobu.model.SignReplyBean;
import com.lianbei.taobu.utils.GlideUtils;
import com.lianbei.taobu.utils.Validator;

import java.util.List;

/**
 * 动态详情
 */
public class DynamicDetailLisetAdapter extends MultipleItemRvAdapter <SignReplyBean.ReplyList, BaseViewHolder> {


    private String mChannelCode;
    public DynamicDetailLisetAdapter(String channelCode, @Nullable List<SignReplyBean.ReplyList> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }

    @Override
    protected int getViewType(SignReplyBean.ReplyList signReplyBean) {
        if(!Validator.isStrNotEmpty (signReplyBean.getContentImagelist ())){
            return 0;
        }else{
            return 1;
        }

    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new DynamicDetailLisetTiemProvider (mChannelCode) );
        mProviderDelegate.registerProvider ( new DynamicDetailImageLisetTiemProvider (mChannelCode) );
    }


    public  class DynamicDetailLisetTiemProvider extends BaseItemProvider <SignReplyBean.ReplyList, BaseViewHolder> {

        private String mChannelCode;

        public DynamicDetailLisetTiemProvider(String channelCode) {
            mChannelCode = channelCode;
        }
        @Override
        public int viewType() {
            return 0;
        }

        @Override
        public int layout() {
            return R.layout.sign_discuss_item_layout;
        }

        @Override
        public void convert(BaseViewHolder baseViewHolder, SignReplyBean.ReplyList childMsgList, int i) {
            baseViewHolder.setText ( R.id.child_title, childMsgList.getNickname ()+"");
            baseViewHolder.setText ( R.id.child_content, childMsgList.getContent ()+"");
            baseViewHolder.setText ( R.id.child_create_time, childMsgList.getCreate_time ()+"22");
        }
    }

    public  class DynamicDetailImageLisetTiemProvider extends BaseItemProvider <SignReplyBean.ReplyList, BaseViewHolder> {

        private String mChannelCode;

        public DynamicDetailImageLisetTiemProvider(String channelCode) {
            mChannelCode = channelCode;
        }
        @Override
        public int viewType() {
            return 1;
        }

        @Override
        public int layout() {
            return R.layout.sign_discuss_image_item_layout;
        }

        @Override
        public void convert(BaseViewHolder baseViewHolder, SignReplyBean.ReplyList childMsgList, int i) {
            ImageView imageView = baseViewHolder.getView ( R.id.contentImagelist );
            GlideUtils.getInstance ().load( mContext, childMsgList.getContentImagelist (), imageView);
        }
    }
}

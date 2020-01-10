package com.lianbei.taobu.shop.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.lianbei.taobu.R;
import com.lianbei.taobu.circle.adapter.itemprovider.BaseGroupGameItemProvider;
import com.lianbei.taobu.circle.model.GameBean;
import com.lianbei.taobu.shop.model.SearchRecord;
import com.lianbei.taobu.taobu.adapter.GroupGameItemProvider;
import com.lianbei.taobu.utils.GlideUtils;

import java.util.List;

public class SecordRecordListAdapter extends MultipleItemRvAdapter <SearchRecord, BaseViewHolder> {

    private String mChannelCode;
    public SecordRecordListAdapter(String channelCode, @Nullable List<SearchRecord> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }
    public SecordRecordListAdapter( @Nullable List<SearchRecord> data) {
        super(data);
        finishInitialize();
    }
    @Override
    protected int getViewType(SearchRecord gamelist) {
        return 0;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider ( new SecordItemProvider () );
    }
    public class SecordItemProvider extends BaseItemProvider<SearchRecord, BaseViewHolder> {

        @Override
        public int viewType() {
            return 0;
        }

        @Override
        public int layout() {
            return R.layout.item_search_record_layout;
        }

        @Override
        public void convert(BaseViewHolder baseViewHolder, SearchRecord searchRecord, int i) {
            baseViewHolder.setText(R.id.tv_secord,searchRecord.getKeywordRecord());


        }


    }


}
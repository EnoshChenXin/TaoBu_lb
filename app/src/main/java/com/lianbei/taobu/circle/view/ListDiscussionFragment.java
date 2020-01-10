package com.lianbei.taobu.circle.view;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.circle.model.ItemInfoMsg;
import com.lianbei.taobu.circle.adapter.GroupDiscussAdapter;
import com.lianbei.taobu.circle.model.ItmeInfo;
import com.lianbei.taobu.taobu.viewmanager.TaoBuManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 群组赛留言 activity
 */
public class ListDiscussionFragment extends BaseFragment {
    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    private String mChannelCode = "0";
    protected BaseQuickAdapter groupListAdapter;
    private List <ItemInfoMsg.msglist> msglists = new ArrayList <> ( );
    // TODO: Rename and change types of parameters
    private ItmeInfo itmeInfo;
    private String mParam2;


    public ItmeInfo getItmeInfo() {
        return itmeInfo;
    }

    public void setItmeInfo(ItmeInfo itmeInfo) {
        this.itmeInfo = itmeInfo;
        msglists.addAll (itmeInfo.getMsg ().getList ());
        groupListAdapter.notifyDataSetChanged ();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_discussion;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

        //TaoBuManager.getInstance ().getDynamicContent (this.getContext (),questCompletion,"1");
    }

    @Override
    public void initListener() {
        groupListAdapter = new GroupDiscussAdapter ( mChannelCode, msglists );
        mRvNews.setAdapter ( groupListAdapter );
        groupListAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent (ListDiscussionFragment.this.getContext (), GameDiscussionDetailActivity.class  );
                intent.putExtra ( "msglists",msglists.get ( i ));
                intent.putExtra ( "match_id",msglists.get ( i ).getMatch_id ());
                startActivity ( intent );
            }
        } );
        groupListAdapter.setOnItemChildClickListener ( new BaseQuickAdapter.OnItemChildClickListener ( ) {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                int viewId= view.getId ();

            }
        } );
    }
    TaoBuManager.RequestCompletion questCompletion = new TaoBuManager.RequestCompletion ( ) {
        @Override
        public void Success(Object value, String tag) {
            try {
                if(value != null){
//                    dynamicBeanList.addAll ( ((DynamicBean)value).getList () );
//                    dynamicListAdapter.notifyDataSetChanged ();
                }
            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }

        @Override
        public void Fail(Object value) {

        }

        @Override
        public void Error(Object... values) {

        }
    };

    @Override
    public void fetchData() {

    }
}

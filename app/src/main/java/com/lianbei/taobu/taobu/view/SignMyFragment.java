package com.lianbei.taobu.taobu.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.taobu.adapter.DynamicListAdapter;
import com.lianbei.taobu.taobu.model.DynamicBean;
import com.lianbei.taobu.taobu.viewmanager.TaoBuManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 签到 ->我的动态
 */

public class SignMyFragment extends BaseFragment implements   TaoBuManager.RequestCompletion {
    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    private String mChannelCode = "0";
    protected BaseQuickAdapter dynamicListAdapter;
    private List <DynamicBean.DynamicList> dynamicBeanList = new ArrayList <> ( );
    public SignMyFragment() {
        // Required empty public constructor
    }
    public static SignMyFragment newInstance(String param1, String param2) {
        SignMyFragment fragment = new SignMyFragment ( );
        Bundle args = new Bundle ( );
        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_my_sign;
    }

    @Override
    public void initViews() {
        setListView();
    }

    private void setListView(){
        mRvNews.setLayoutManager ( new GridLayoutManager ( mActivity, 1 ) );
        mRvNews.setNestedScrollingEnabled ( false );//解决滑动卡顿

    }
    @Override
    public void initData() {
        TaoBuManager.getInstance ().getDynamicContent ( this.getContext (), this,"2");
    }

    @Override
    public void initListener() {
        dynamicListAdapter = new DynamicListAdapter ( mChannelCode, dynamicBeanList );
        mRvNews.setAdapter ( dynamicListAdapter );
        dynamicListAdapter.setOnItemClickListener ( new BaseQuickAdapter.OnItemClickListener ( ) {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent (SignMyFragment.this.getContext (), SignDynamicDetailActivity.class  );
                intent.putExtra ( "dynamicBean",dynamicBeanList.get ( i ) );
                startActivity ( intent );

            }
        } );
        dynamicListAdapter.setOnItemChildClickListener ( new BaseQuickAdapter.OnItemChildClickListener ( ) {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                int viewId= view.getId ();
                if(viewId == R.id.like_lin || viewId == R.id.like_btn){
                    int num  =Integer.parseInt ( dynamicBeanList.get ( i ).getNum () );
                    int like  =Integer.parseInt(dynamicBeanList.get ( i ).getNum ());
//                    if (dynamicBeanList.get ( i ).getLike ()){
//                       // view.setBackgroundResource ( R.mipmap.like );
//                        dynamicBeanList.get ( i ).setIslike ( false );
//                        dynamicBeanList.get ( i ).setLikenum ( -1 );
//                    }else{//喜欢
//                       // view.setBackgroundResource ( R.mipmap.like2 );
//                        dynamicBeanList.get ( i ).setIslike ( true );
//                        dynamicBeanList.get ( i ).setLikenum ( num+1 );
//                    }
                    dynamicListAdapter.notifyDataSetChanged ();
                }else if(viewId == R.id.message2_lin || viewId == R.id.message2_btn){
                    Intent intent = new Intent (SignMyFragment.this.getContext (), SignDynamicDetailActivity.class  );
                    intent.putExtra ( "dynamicBean",dynamicBeanList.get ( i ) );
                    startActivity ( intent );
                }

            }
        } );
    }

    @Override
    public void Success(Object value, String tag) {
        try {
            if(value != null){
                dynamicBeanList.addAll ( ((DynamicBean)value).getList () );
                dynamicListAdapter.notifyDataSetChanged ();
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

    @Override
    public void fetchData() {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

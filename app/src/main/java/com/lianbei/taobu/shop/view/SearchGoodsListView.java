package com.lianbei.taobu.shop.view;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodListAdatper;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchGoodsListView extends BaseFragment implements RequestCompletion {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    private ShopManager shopManager = null;
    private String mChannelCode;
    private List<TopGoodsBean> goodsSearchBeanList = new ArrayList <> (  );
    protected BaseQuickAdapter mShopListAdapter;
    @Override
    public int getContentViewId() {
        return  R.layout.fragment_search_goods_list;
    }

    @Override
    public void initViews() {
        mRvNews.setLayoutManager(new GridLayoutManager (mActivity, 1));
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(mActivity, false);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.color_F3F5F4);//背景色
        refreshViewHolder.setPullDownRefreshText( UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
    }

    @Override
    public void initData() {
        if(shopManager == null){
            shopManager =new ShopManager ( this.getContext ());
        }
        //shopManager.getGoodsSearch (mChannelCode,"1","10","0",this  );
    }

    @Override
    public void initListener() {
        mShopListAdapter = new BaseGoodListAdatper (mChannelCode,BaseGoodListAdatper.COMMON_GOOD,goodsSearchBeanList);
        mRvNews.setAdapter(mShopListAdapter);
        mShopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent ( SearchGoodsListView.this.getContext (),ActivityGoodsDetail.class  );
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra ( "goods_id",goodsSearchBeanList.get ( position ).getGoods_id ());
                startActivity ( intent );

            }
        });
    }


    @Override
    public void Success(Object value, String tag) {
        try {
            if(value != null){
                goodsSearchBeanList.addAll ( (List <TopGoodsBean>)value) ;
                mShopListAdapter.notifyDataSetChanged ();
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
}

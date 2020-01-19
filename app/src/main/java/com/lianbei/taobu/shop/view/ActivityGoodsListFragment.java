package com.lianbei.taobu.shop.view;

import android.content.Intent;
import android.view.View;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.base.MessageEvent;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodListAdatper;
import com.lianbei.taobu.shop.model.ShopBean;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.taobu.view.TaobuFragment;
import com.lianbei.taobu.utils.ThreadUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class ActivityGoodsListFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate, RequestCompletion, View.OnClickListener {

    @BindView(R.id.m_RvNews)
    PowerfulRecyclerView mRvNews;
    private ShopManager shopManager = null;
    private String mChannelCode;
    protected BaseQuickAdapter topgoodlistadatper;
    private List<ShopBean> mShopList = new ArrayList<>();
    private List<TopGoodsBean> toplist = new ArrayList<>();
    private int offset = 0;
    private int limit = 30;
    ArrayList<String> arrayList;
    String param1;
      int tag;
    public ActivityGoodsListFragment() {
        // Required empty public constructor
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_activity_goods_list;
    }

    @Override
    public void initViews() {
        //注册事件
        arrayList = new ArrayList();
        arrayList.add("54680882186");
        arrayList.add("7440289");
        arrayList.add("2338511");
        arrayList.add("50226994697");
        arrayList.add("3870377813");
    }
    public void setParam(String param){
        this.param1 = param;
        if(param1.equals(Constant.MIANDAN)){
            tag =BaseGoodListAdatper.MAIN_DAN_GOOD;
        }else if(param1.equals(Constant.ZHULI)){
            tag =BaseGoodListAdatper.FREE_GOOD;
        }
    }

    @Override
    public void initData() {
        if (toplist != null && toplist.size() > 0)
            return;
        ShopManager.getInstance (this.getContext()).SearchGoods ("1", "20", String.valueOf(arrayList),this, "0");
        //ShopManager.getInstance(this.getContext()).basic_info_get(this, arrayList, "0");
    }

    @Override
    public void initListener() {
        mRvNews.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        topgoodlistadatper = new BaseGoodListAdatper(mChannelCode, tag, toplist);
        mRvNews.setAdapter(topgoodlistadatper);
        topgoodlistadatper.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                if(param1.equals(Constant.MIANDAN)){
                    tag =BaseGoodListAdatper.MAIN_DAN_GOOD;
                    intent.setClass(ActivityGoodsListFragment.this.getContext(), ActivityGoodsDetail.class);
                }else if(param1.equals(Constant.ZHULI)){
                    tag =BaseGoodListAdatper.FREE_GOOD;
                    intent.setClass(ActivityGoodsListFragment.this.getContext(), HelpGoodsDetail.class);
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("goods_id", toplist.get(position).getGoods_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void Success(Object value, String tag) {
        if (value != null) {
            ThreadUtil.runInUIThread(new Runnable() {
                @Override
                public void run() {
                    if (tag.equals("0")) {
                        toplist.clear();
                        toplist.addAll((List<TopGoodsBean>) value);
                        ((ActivityGoodsMain) getActivity()).endRefreshing();
                    } else {
                        toplist.addAll((List<TopGoodsBean>) value);
                        ((ActivityGoodsMain) getActivity()).endLoadingMore();
                    }
                    topgoodlistadatper.notifyDataSetChanged();
                }
            });

        }
    }


    @Override
    public void Fail(Object value) {

    }

    @Override
    public void Error(Object... values) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消事件注册
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        offset = 0;
        ShopManager.getInstance (this.getContext()).SearchGoods ("1", "20", String.valueOf(arrayList),this, "0");
        //ShopManager.getInstance(this.getContext()).basic_info_get(this, arrayList, "0");
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        offset = toplist.size();
       // ShopManager.getInstance(this.getContext()).top_goods(this, offset, limit, "1");
        return false;
    }
}

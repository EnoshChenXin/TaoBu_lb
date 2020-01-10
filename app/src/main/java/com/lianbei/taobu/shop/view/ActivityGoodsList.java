package com.lianbei.taobu.shop.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chaychan.uikit.TipView;
import com.chaychan.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.chaychan.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.chaychan.uikit.refreshlayout.BGARefreshLayout;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.MessageEvent;
import com.lianbei.taobu.base.NavigationView;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.shop.adapter.baseadapter.BaseGoodListAdatper;
import com.lianbei.taobu.shop.model.ShopBean;
import com.lianbei.taobu.shop.model.TopGoodsBean;
import com.lianbei.taobu.shop.viewmanager.ShopManager;
import com.lianbei.taobu.utils.ThreadUtil;
import com.lianbei.taobu.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ActivityGoodsList extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.RequestLoadMoreListener, RequestCompletion, View.OnClickListener {

    @BindView(R.id.tip_view)
    TipView mTipView;

    @BindView(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;

    @BindView(R.id.navigation_view)
    NavigationView navigation_view;


    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    protected BaseQuickAdapter topgoodlistadatper;
    private List<ShopBean> mShopList = new ArrayList<>();
    private List<TopGoodsBean> toplist = new ArrayList<>();
    private int page = 1;
    private int pageSize = 20;
    private int mChannelCode = -1;//精选8569
    private String keyword="";
    private int channel_type = 0;
    private boolean with_coupon; //是否只返回优惠券的商品，false返回所有商品，true只返回有优惠券的商品
    private boolean is_brand_goods;  //是否为品牌商品
    JSONArray jsonArray;
    String pagertype;
    String param1;

    @Override
    public int getContentViewId() {
        return R.layout.activity_goods;
    }

    @Override
    public void initViews() {
        //注册事件
        createNavigationView(R.id.navigation_view);
        mRefreshLayout.setDelegate(this);
        mRvNews.setLayoutManager(new GridLayoutManager(this, 1));
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, false);
        // 设置下拉刷新
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.color_F3F5F4);//背景色
        refreshViewHolder.setPullDownRefreshText(UIUtils.getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(UIUtils.getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(UIUtils.getString(R.string.refresh_ing_text));//刷新中的提示文字
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mRvNews);
    }

    @Override
    public void initData() {
        try {
            pagertype = getIntent().getStringExtra("pagertype");
            param1 = getIntent().getStringExtra("param1");
            jsonArray = new JSONArray();
            if (pagertype.equals(Constant.JIUBAOYOU)) {
                navigation_view.setTitleText("9.9包邮");
                JSONObject js = new JSONObject();
                js.put("range_id", "1");
                js.put("range_from", "0");
                js.put("range_to", "1000");
                jsonArray.put(js);
                mChannelCode = 8569;//精选8569
                channel_type = 0;
                with_coupon = true;
                is_brand_goods = true;
            } else if (pagertype.equals(Constant.HAOHUO)) {
                navigation_view.setTitleText("优品推荐");
//                JSONObject js1 = new JSONObject();
//                js1.put("range_id", "1");
//                js1.put("range_from", "0");
//                js1.put("range_to", "1000");
//                jsonArray.put(js1);
                JSONObject js13 = new JSONObject();//商品分
                js13.put("range_id", 13);
                js13.put("range_from", 4.6);
                js13.put("range_to", 5);
                jsonArray.put(js13);
//                JSONObject js7 = new JSONObject();//店铺描述分
//                js7.put("range_id", 7);
//                js7.put("range_from", 4.6);
//                js7.put("range_to", 5.0);
//                jsonArray.put(js7);
//                JSONObject js8 = new JSONObject();//店铺物流分
//                js8.put("range_id", 8);
//                js8.put("range_from", 4.6);
//                js8.put("range_to", 5.0);
//                jsonArray.put(js8);
//                JSONObject js9 = new JSONObject();//店铺服务分
//                js9.put("range_id", 9);
//                js9.put("range_from",4.6);
//                js9.put("range_to", 5.0);
//                jsonArray.put(js9);
//                JSONObject js10 = new JSONObject();//店铺服务分
//                js10.put("range_id", "10");
//                js10.put("range_from", "50%");
//                js10.put("range_to", "100%");
//                jsonArray.put(js10);
                mChannelCode = 8569;//精选8569
                channel_type = 0;//综合
                with_coupon = false;
                is_brand_goods = false;
                Log.e("jsonArray",jsonArray.toString());
            }else if(pagertype.equals(Constant.YONGJIN)){
                navigation_view.setTitleText("高佣商品");
                mChannelCode = 8569;//精选8569
                channel_type = 2;//按佣金金额降序排序
                with_coupon = false;
                is_brand_goods = false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(pagertype.equals(Constant.BAOKUAN)) {
            navigation_view.setTitleText("爆款专区");
            ShopManager.getInstance(this).top_goods(this, page, pageSize, "-1");
        }else if(pagertype.equals(Constant.ZHUTI)){
            navigation_view.setTitleText("主题专区");
            ShopManager.getInstance(this).Theme_Goods_list(param1, this, "-1");
        }else{
            ShopManager.getInstance(this).SearchGoods(keyword,mChannelCode + "", "",page + "", pageSize + "", channel_type + "", jsonArray.toString(), with_coupon, is_brand_goods, this, "-1");
        }



    }

    @Override
    public void initListener() {
        topgoodlistadatper = new BaseGoodListAdatper(mChannelCode + "", BaseGoodListAdatper.COMMON_GOOD, toplist);
        mRvNews.setAdapter(topgoodlistadatper);
        topgoodlistadatper.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ActivityGoodsList.this, ActivityGoodsDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("goods_id", toplist.get(position).getGoods_id());
                startActivity(intent);
                // ShopBean news = mShopList.get(position);
            }
        });

        topgoodlistadatper.setEnableLoadMore(true);
        topgoodlistadatper.setOnLoadMoreListener(this, mRvNews);
    }


    @Override
    public void onLeftClick() {
        finish();

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 上拉加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        page++;
        if(pagertype.equals(Constant.BAOKUAN)) {
            ShopManager.getInstance(this).top_goods(this, page, pageSize, "1");
        }else if(pagertype.equals(Constant.ZHUTI)){
            ShopManager.getInstance(this).Theme_Goods_list(param1, this, "1");
        }else{
            ShopManager.getInstance(this).SearchGoods(keyword,mChannelCode + "", "",page + "", pageSize + "", channel_type + "", jsonArray.toString(), with_coupon, is_brand_goods, this, "1");
        }

    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        channel_type++;
        if(pagertype.equals(Constant.BAOKUAN)){
            ShopManager.getInstance(this).top_goods ( this,page,pageSize,"0");
        }else if(pagertype.equals(Constant.ZHUTI)){
            ShopManager.getInstance(this).Theme_Goods_list(param1, this, "0");
        }else{
            ShopManager.getInstance(this).SearchGoods(keyword,mChannelCode + "", "",page + "", pageSize + "", channel_type + "", jsonArray.toString(), with_coupon, is_brand_goods, this, "0");
        }

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void Success(Object value, String tag) {
        try {
            if (value != null) {
                ThreadUtil.runInUIThread(new Runnable() {
                    @Override
                    public void run() {
                        if (tag.equals("0")) {
                            if (toplist != null) {
                                toplist.clear();
                                toplist.addAll((List<TopGoodsBean>) value);
                                mRefreshLayout.endRefreshing();
                            }
                        } else if (tag.equals("1")) {
                            toplist.addAll((List<TopGoodsBean>) value);
                            mRefreshLayout.endLoadingMore();
                        } else if (tag.equals("-1")) {
                            if (toplist != null) {
                                toplist.clear();
                                toplist.addAll((List<TopGoodsBean>) value);
                            }
                        }
                        topgoodlistadatper.notifyDataSetChanged();
                        //
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (value != null) {
                if (value != null)
                    toplist.addAll((List<TopGoodsBean>) value);
                topgoodlistadatper.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Fail(Object value) {

    }

    @Override
    public void Error(Object... values) {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMessageEvent(MessageEvent messageEvent) {
        Log.e("Message", messageEvent.getMessage());
    }
}
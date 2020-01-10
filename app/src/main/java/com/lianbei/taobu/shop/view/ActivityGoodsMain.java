package com.lianbei.taobu.shop.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.NavigationView;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.shop.adapter.SnapUpPagerAdapter;
import com.lianbei.taobu.shop.model.BannerImgInfo;
import com.lianbei.taobu.shop.model.TabModel;
import com.lianbei.taobu.views.bannerview.bean.IBanner;
import com.lianbei.taobu.views.bannerview.lib.CycleViewPager;
import com.lianbei.taobu.views.bannerview.ui.ADInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

public class ActivityGoodsMain extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    @BindView(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.recommend_view)
    LinearLayout recommend_view;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager mContentVp;
    ArrayList <Fragment> list = new ArrayList <Fragment> ( );
    private List <BannerImgInfo> imgBannerArray;
    private ADInfo info;
    private List <ADInfo> infos = new ArrayList <ADInfo> ( );
    private CycleViewPager cycleViewPager;
    public static List<Map<String,String>> tab_title = new ArrayList <> (  );
    public static int[] TAB_TITLES = new int[]{R.string.TAB_TEXT_FREE_ORDER, R.string.TAB_TEXT_NEXT_ORDER};
    public ArrayList<TabModel> tabModelArrayList;
    private String mChannelCode;
    private String param1;

    @BindView(R.id.navigation_id)
    NavigationView navigationView;
    @Override
    public int getContentViewId() {
        return R.layout.activity_goods_main;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_id );
        param1 =this.getIntent().getStringExtra("param1");
        if(param1.equals(Constant.MIANDAN)){
            navigationView.setTitleText("购享免单");
        }else if(param1.equals(Constant.ZHULI)){
            navigationView.setTitleText("助力享免单");
        }

       // GlideUtils.getInstance ().loadRoundRadius ( this.getContext (), APIs.MYBASESET_ZHUANPAN,lottery_gen,120 );
      //  GlideUtils.getInstance ().loadRoundRadius ( this.getContext (), APIs.MYBASESET_BAOYOU,image2,120 );
        initBanner();
        initviewtab();
        intViwePager ( );
    }

    private void initviewtab(){
        tabModelArrayList= new ArrayList<TabModel>();
        tabModelArrayList.add ( new TabModel ("00:00","抢购中"));
        tabModelArrayList.add ( new TabModel ("07:00","即将开抢"));
        tabModelArrayList.add ( new TabModel ("09:00","即将开抢"));
        tabModelArrayList.add ( new TabModel ("13:00","即将开抢"));
        tabModelArrayList.add ( new TabModel ("15:00","即将开抢"));
    }

    private void intViwePager() {
        if (list != null && list.size ( ) == 0 && tabModelArrayList != null) {
            for (TabModel channel : tabModelArrayList) {
                ActivityGoodsListFragment activityGoodsListFragment = new ActivityGoodsListFragment ( );
                activityGoodsListFragment.setParam(param1);
                Bundle bundle = new Bundle ( );
//              Bundle bundle = new Bundle();
//              bundle.putString(Constant.CHANNEL_CODE,TAB_TITLES[0]);
                list.add ( activityGoodsListFragment );
            }
        }
        if (list == null || list.size ( ) == 0)
            return;
        tabs.setTabMode ( TabLayout.MODE_FIXED );//设置tab模式，当前为系统默认模式
        SnapUpPagerAdapter sectionsPagerAdapter = new SnapUpPagerAdapter ( this, getSupportFragmentManager (), list ,tabModelArrayList);
        mContentVp.setAdapter ( sectionsPagerAdapter );
        // tv_item_one.setBackgroundColor( Color.RED);//被选中就为红色
        //  mIndicator.initData(0, mContentVp);
        tabs.setupWithViewPager ( mContentVp );
        tabs.setTabIndicatorFullWidth ( false );
        tabs.setSelectedTabIndicatorHeight(0);
        mContentVp.setCurrentItem ( 0 );  //初始化显示第一个页面
        tabs.getTabAt ( 0 ).select ( );
        SnapUpTabLayoutAddOnClickHelper.AddOnClick ( this, tabs,tabModelArrayList, onTouchListener );
    }

    @Override
    protected void initDataObserver() {
        super.initDataObserver ( );
        mRefreshLayout.setRefreshViewHolder ( new BGANormalRefreshViewHolder ( ActivityGoodsMain.this, true ) );
    }



    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mRefreshLayout.setDelegate ( this );
        mContentVp.addOnPageChangeListener ( new ViewPager.OnPageChangeListener ( ) {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                SnapUpTabLayoutAddOnClickHelper.refreshTab ( ActivityGoodsMain.this, tabs,i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        } );

    }

    @Override
    public void onLeftClick() {
        finish ();

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        int item = mContentVp.getCurrentItem();
        ((ActivityGoodsListFragment) list.get ( item )).onBGARefreshLayoutBeginRefreshing ( refreshLayout );

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        int item = mContentVp.getCurrentItem();
        return ((ActivityGoodsListFragment) list.get ( item )).onBGARefreshLayoutBeginLoadingMore ( refreshLayout );
    }
    public void endRefreshing() {
        tabs.getTabAt ( 0 ).setText ( "已结束" );
        mRefreshLayout.endRefreshing ( );
    }

    public void endLoadingMore() {
        tabs.getTabAt ( 0 ).setText ( "已加载" );
        mRefreshLayout.endLoadingMore ( );
    }

    private void initBanner() {
        imgBannerArray = new ArrayList <> ( );
        BannerImgInfo bannerImgInfo = new BannerImgInfo ( );
        bannerImgInfo.setImgUrl ( APIs.MYBASESET+"miandan.png" );
        imgBannerArray.add ( bannerImgInfo );
        cycleViewPager = (CycleViewPager) getSupportFragmentManager ( )
                .findFragmentById ( R.id.fragment_cycle_viewpager_content );
        for (int i = 0; i < imgBannerArray.size ( ); i++) {
            info = new ADInfo ( );
            info.url = imgBannerArray.get ( i ).getImgUrl ( );
            info.photoDesc = "图片-->" + i;
            info.type = "act";
            infos.add ( info );
        }

        cycleViewPager.setData ( infos, mAdCycleViewListener );
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime ( 4000 );
//		cycleViewPager.setWheel(true);
        //设置圆点指示图标组居中显示，默认靠右
//		cycleViewPager.setIndicatorCenter();

//		// 将最后一个ImageView添加进来
//		views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1).getUrl()));
//		for (int i = 0; i < infos.size(); i++) {
//			views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
//		}
//		// 将第一个ImageView添加进来
//		views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));
//
//		// 设置循环，在调用setData方法前调用
//		cycleViewPager.setCycle(true);
//
//		// 在加载数据前设置是否循环
//
//		//设置轮播
//		cycleViewPager.setWheel(true);
//
//	    // 设置轮播时间，默认5000ms
//		cycleViewPager.setTime(2000);
//		//设置圆点指示图标组居中显示，默认靠右
//		cycleViewPager.setIndicatorCenter();

    }
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener ( ) {

        @Override
        public void onImageClick(IBanner info, int position, View imageView) {
            if (cycleViewPager.isCycle ( )) {
//                position = position - 1;
//                Toast.makeText ( ActivityGoodsMain.this,
//                        "position-->" + info.getPhotoDesc ( ), Toast.LENGTH_SHORT ).show ( );
            }
        }
    };

    View.OnTouchListener onTouchListener =new View.OnTouchListener ( ) {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            try {
                int pos = (int) view.getTag();
                SnapUpTabLayoutAddOnClickHelper.refreshTab ( ActivityGoodsMain.this, tabs,pos);
                mContentVp.setCurrentItem(pos);
                return  true;
            } catch (Exception e) {
                e.printStackTrace ( );
                return false;
            }


        }
    };
}

package com.lianbei.taobu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.animation.Animation;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.chaychan.uikit.NoScrollViewPager;
import com.lianbei.taobu.adapter.MainTabAdapter;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.circle.view.CircleFragment;
import com.lianbei.taobu.mine.view.MineFragment;
import com.lianbei.taobu.shop.view.ShopFragment;
import com.lianbei.taobu.taobu.view.TaobuFragment;
import com.lianbei.taobu.utils.SPUtils;
import com.lianbei.taobu.utils.StatusBarUtil;
import com.lianbei.taobu.utils.UIUtils;
import com.lianbei.taobu.utils.flyn.Eyes;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity1 extends BaseActivity {
    /***
     *
     * 测试
     */
    private TaobuFragment taobuFragment;
    private CircleFragment circleFragment;
    private ShopFragment shopFragment;
    private MineFragment mineFragment;
    /**
     *
     */
    @BindView(R.id.vp_content)
    NoScrollViewPager mVpContent;

    @BindView(R.id.bottom_bar)
    BottomBarLayout mBottomBarLayout;

    private MyReceiver myReceiver;

    private List <BaseFragment> mFragments;
    private MainTabAdapter mTabAdapter;

    private int[] mStatusColors = new int[]{
            R.color.color_D33D3C,
            R.color.white,
            R.color.color_BDBDBD,
            R.color.white,
    };
    @Override
    public boolean enableSlideClose() {
        return false;
    }
    @Override
    public int getContentViewId() {
        return R.layout.activity_main1;
    }

    @Override
    public void initBeforeView() {
        super.initBeforeView();
    }

    @Override
    public void initViews() {
        //添加
        boolean isFirst = (boolean) SPUtils.get("isFirst", true);
        if (isFirst){
            SPUtils.put("isFirst",false);
        }
        //注册广播任务
        myReceiver=new MyReceiver ();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.action.circle");
        registerReceiver(myReceiver,intentFilter);
    }

    @Override
    public void initData() {
        mFragments = new ArrayList <> ( 4 );
        mFragments.add ( new TaobuFragment ( ) );
        mFragments.add ( new CircleFragment ( ) );
        mFragments.add ( new ShopFragment ( ) );
        mFragments.add ( new MineFragment ( ) );

    }

    @Override
    public void initListener() {
        mTabAdapter = new MainTabAdapter ( mFragments, getSupportFragmentManager ( ) );
        mVpContent.setAdapter ( mTabAdapter );
        mVpContent.setOffscreenPageLimit ( mFragments.size ( ) );
        mBottomBarLayout.setViewPager ( mVpContent );
        setStatusBarColor (0);
        //设置条目点击的监听
        mBottomBarLayout.setOnItemSelectedListener ( new BottomBarLayout.OnItemSelectedListener ( ) {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int position) {
               setStatusBarColor ( position );//设置状态栏颜色

                //JCVideoPlayer.releaseAllVideos();//底部页签切换或者是下拉刷新，释放资源

                if (position == 0 || position == 1) {
                    //如果点击的是首页
                    if (mBottomBarLayout.getCurrentItem ( ) == position) {
                        //如果当前页码和点击的页码一致,进行下拉刷新
                        String channelCode = "";
                        if (position == 0) {
                            //  channelCode = ((HomeFragment) mFragments.get(0)).getCurrentChannelCode();//获取到首页当前显示的fragment的频道
                        } else {
                            //   channelCode = ((VideoFragment) mFragments.get(1)).getCurrentChannelCode();//获取到视频当前显示的fragment的频道
                        }
                        //  postTabRefreshEvent(bottomBarItem, position, channelCode);//发送下拉刷新的事件
                    }
                    return;
                }

                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem ( 0 );
                bottomItem.setIconSelectedResourceId ( R.mipmap.home_icon_select );//更换为原来的图标
                cancelTabLoading ( bottomItem );//停止旋转动画
            }
        } );
    }

    private void setStatusBarColor(int position) {
        StatusBarUtil.setStatusBarMode ( this, true, R.color.transparent );
        if (position == 0 || position == 3) {
            //如果是我的页面，状态栏设置为透明状态栏
          //  Eyes.translucentStatusBar ( MainActivity1.this, true );
        } else {
           // Eyes.setStatusBarLightMode(MainActivity1.this, R.color.black);
          //  StatusBarUtil.setStatusBarMode ( this, true, R.color.black );
           //Eyes.translucentStatusBar ( MainActivity1.this, true );
           // Eyes.setStatusBarColor ( MainActivity1.this, UIUtils.getColor ( mStatusColors[position] ) );
        }
    }

//    private void postTabRefreshEvent(BottomBarItem bottomBarItem, int position, String channelCode) {
//        TabRefreshEvent event = new TabRefreshEvent();
//        event.setChannelCode(channelCode);
//        event.setBottomBarItem(bottomBarItem);
//        event.setHomeTab(position == 0);
//        EventBus.getDefault().post(event);//发送下拉刷新事件
//    }

    /**
     * 停止首页页签的旋转动画
     */
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView ( ).getAnimation ( );
        if (animation != null) {
            animation.cancel ( );
        }
    }


//    @Subst (threadMode = ThreadMode.MAIN)
//    public void onRefreshCompletedEvent(TabRefreshCompletedEvent event) {
//        //接收到刷新完成的事件，取消旋转动画，更换底部首页页签图标
//        BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
//
//        cancelTabLoading(bottomItem);//停止旋转动画
//
//        bottomItem.setIconSelectedResourceId(R.mipmap.home_icon_select);//更换成首页原来图标
//        bottomItem.setStatus(true);//刷新图标
//    }





    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction ( );
            if (action.equals ( "com.action.circle" )) {
                mBottomBarLayout.setCurrentItem ( 1 );
            } else if (action.equals ( "" )) {

            }
        }
    }
}

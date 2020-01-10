package com.lianbei.taobu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.view.CircleFragment;
import com.lianbei.taobu.constants.Constant;
import com.lianbei.taobu.mine.view.MineFragment;
import com.lianbei.taobu.shop.view.ShopFragment;
import com.lianbei.taobu.taobu.view.TaobuFragment;
import com.lianbei.taobu.utils.SPUtils;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{
    private TaobuFragment taobuFragment;
    private CircleFragment circleFragment;
    private ShopFragment shopFragment;
    private MineFragment mineFragment;

    @BindView(R.id.bar)
    public BottomNavigationBar bar;
    private MyReceiver myReceiver;
    private FragmentManager fragmentManager;
    private Fragment currentFra=new Fragment();


    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }
    @Override
    public boolean enableSlideClose() {
        return false;
    }
    @Override
    public void initViews() {
        //添加
        boolean isFirst = (boolean) SPUtils.get("isFirst", true);
        if (isFirst){
            SPUtils.put("isFirst",false);
        }
        //注册广播任务
        myReceiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.action.circle");
        registerReceiver(myReceiver,intentFilter);


        //  ButterKnife.bind(this);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
            taobuFragment=new TaobuFragment ();
            AddOrShowFra(ft,taobuFragment);
        bar.setMode( BottomNavigationBar.MODE_FIXED);
        bar.setInActiveColor("#808080");
        bar.setActiveColor("#ff4742");
/*
        bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bar.addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.choosenews_icon),"首页").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.choosevideo_icon),"视频").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.redpack_icon),"邀请").setActiveColorResource(R.color.main_red))
                .addItem(new BottomNavigationItem(getResources().getDrawable(R.mipmap.nonemy_icon),"我的").setActiveColorResource(R.color.main_red))
                .setFirstSelectedPosition(0).initialise();
*/
        bar.addItem(new BottomNavigationItem (R.mipmap.home_icon_select,"首页")
                .setInactiveIcon( ContextCompat.getDrawable(MainActivity.this,R.mipmap.home_icon)))
                .addItem(new BottomNavigationItem(R.mipmap.found_icon_select,"发现")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.found_icon)))
                .addItem(new BottomNavigationItem(R.mipmap.shop_icon_select,"商城")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.shop_icon)))
                .addItem(new BottomNavigationItem(R.mipmap.me_icon_select,"我的")
                        .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this,R.mipmap.me_icon)))
                .setFirstSelectedPosition( Constant.FirstSelectedPosition)//设置默认选择的按钮
                .initialise();//所有的设置需在调用该方法前完成
        bar.setTabSelectedListener(this);

    }

    /***
     * 显示隐藏Fragment
     *
     * @param ft
     * @param Fra
     */
    private void AddOrShowFra(FragmentTransaction ft, Fragment Fra) {
        if (currentFra == Fra) {
            return;
        }
        if (!Fra.isAdded()) {
            ft.hide(currentFra).add(R.id.main_switch, Fra).commitAllowingStateLoss();

        } else {
            ft.hide(currentFra).show(Fra).commitAllowingStateLoss();

        }
        currentFra = Fra;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                if (taobuFragment == null) {
                    taobuFragment = new TaobuFragment ( );
                }
                FragmentTransaction ft_news = fragmentManager.beginTransaction ( );
                AddOrShowFra ( ft_news, taobuFragment );
                break;
            case 1:
                if (circleFragment == null) {
                    circleFragment = new CircleFragment ( );
                }
                FragmentTransaction ft_video = fragmentManager.beginTransaction ( );
                AddOrShowFra ( ft_video, circleFragment );
                break;
            case 2:
                if (shopFragment == null) {
                    shopFragment = new ShopFragment ( );
                }
                FragmentTransaction ft_invitation = fragmentManager.beginTransaction ( );
                AddOrShowFra ( ft_invitation, shopFragment );
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment ( );
                }
                FragmentTransaction ft_mine = fragmentManager.beginTransaction ( );
                AddOrShowFra ( ft_mine, mineFragment );
                break;
            default:
                break;
        }
    }

        @Override
        public void onTabUnselected ( int position){

        }

        @Override
        public void onTabReselected ( int position){

        }


        @Override
        public void initData () {

        }

        @Override
        public void initListener () {

        }

        @Override
        public void onLeftClick () {

        }

        @Override
        public void onRightClick () {

        }

        @Override
        public void onRefreshClick () {

        }
        class MyReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction ( );
                if (action.equals ( "com.action.circle" )) {
                    if(circleFragment ==null){
                        circleFragment =new CircleFragment ();
                    }
                    bar.setFirstSelectedPosition(1).initialise();
                    FragmentTransaction ft_invitation=fragmentManager.beginTransaction();
                    AddOrShowFra(ft_invitation, circleFragment );
                } else if (action.equals ( "" )) {

                }
            }
    }
}

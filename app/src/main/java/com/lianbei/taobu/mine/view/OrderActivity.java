package com.lianbei.taobu.mine.view;


import com.google.android.material.tabs.TabLayout;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.taobu.adapter.SectionsPagerAdapter;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class OrderActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    ArrayList <Fragment> list = new ArrayList <Fragment> ( );
    private int[] TAB_TITLES = new int[]{R.string.TAB_TEXT_ALL_ORDER, R.string.TAB_TEXT_PAY_ORDER, R.string.TAB_TEXT_COMPLETED_ORDER,R.string.TAB_TEXT_OK_ORDER,R.string.TAB_TEXT_FAILURE_ORDER} ;

    @Override
    public int getContentViewId() {
        return R.layout.activity_order;
    }

    @Override
    public void initViews() {
        createNavigationView(R.id.navigation_id);
        for (int i = 0; i <TAB_TITLES.length ; i++) {
            list.add ( new OrderListFragment ());
        }
        tabs.setTabMode( TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tabs.setTabIndicatorFullWidth(false);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter (this,getSupportFragmentManager (),list ,TAB_TITLES);
        viewPager.setAdapter ( sectionsPagerAdapter );
        viewPager.setOffscreenPageLimit ( 1 );
        tabs.setupWithViewPager ( viewPager );
        tabs.getTabAt(0).select();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

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
}

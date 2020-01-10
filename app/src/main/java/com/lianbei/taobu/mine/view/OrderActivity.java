package com.lianbei.taobu.mine.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.circle.view.ArticleSelectFragment;
import com.lianbei.taobu.circle.view.GameEventFragment;
import com.lianbei.taobu.circle.view.GroupGameFragment;
import com.lianbei.taobu.taobu.adapter.SectionsPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class OrderActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    ArrayList <Fragment> list = new ArrayList <Fragment> ( );
    private int[] TAB_TITLES = new int[]{R.string.TAB_TEXT_ALL_ORDER, R.string.TAB_TEXT_PAY_ORDER, R.string.TAB_TEXT_COMPLETED_ORDER,R.string.TAB_TEXT_FAILURE_ORDER} ;

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

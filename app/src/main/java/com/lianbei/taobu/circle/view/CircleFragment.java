package com.lianbei.taobu.circle.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseFragment;
import com.lianbei.taobu.taobu.adapter.SectionsPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class CircleFragment extends BaseFragment {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    ArrayList <Fragment> list = new ArrayList <Fragment> ( );

    private int[] TAB_TITLES = new int[]{R.string.TAB_TEXT_GAMEEVENT, R.string.TAB_TEXT_GROUPGAME, R.string.TAB_TEXT_ARTICLESELECT} ;
    public static int TabTtNum =0;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_circle;
    }

    @Override
    public void initViews() {
        list.add ( new GameEventFragment ());
        list.add ( new GroupGameFragment ());
        list.add ( new ArticleSelectFragment ());
        tabs.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter (this.getContext (),getChildFragmentManager (),list ,TAB_TITLES);
        viewPager.setAdapter ( sectionsPagerAdapter );
        tabs.setupWithViewPager ( viewPager );
        tabs.getTabAt(TabTtNum).select();


    }

    @Override
    public void onResume() {
        super.onResume ( );

    }

    @Override
    public void onPause() {
        super.onPause ( );
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged ( hidden );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint ( isVisibleToUser );
        try{
            if(getUserVisibleHint()){//界面可见时
                if(tabs != null){
                    tabs.getTabAt(TabTtNum).select();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void fetchData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        if(tabs != null){
            tabs.addOnTabSelectedListener ( new TabLayout.BaseOnTabSelectedListener ( ) {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    TabTtNum =tab.getPosition ();

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            } );
        }
    }
}

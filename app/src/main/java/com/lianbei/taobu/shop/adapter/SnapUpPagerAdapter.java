package com.lianbei.taobu.shop.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lianbei.taobu.shop.model.TabModel;

import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SnapUpPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;

    private List<TabModel> tabModelList;
    private final Context mContext;
    public SnapUpPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments, List<TabModel> tabModelList) {
        super ( fm );
        mContext = context;
        this.fragments= fragments;
        this.tabModelList = tabModelList;
    }
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return fragments.get ( position );
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  tabModelList.get ( position ).getTitle ();
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return fragments.size ();
    }

}
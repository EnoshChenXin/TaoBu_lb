package com.lianbei.taobu.circle.adapter;

import android.content.Context;

import com.lianbei.taobu.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class GroupGameDatailAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    List<String> titlelist =  new ArrayList <> (  );;
    @StringRes
    private static  int[] TAB_TITLES = new int[]{R.string.TAB_TEXT_DISCUSSION, R.string.TAB_TEXT_TODAY_LIST, R.string.TAB_TEXT_YESTERDAY_LIST,R.string.TAB_TEXT_MONTH_LIST} ;
    private final Context mContext;

    public GroupGameDatailAdapter(Context context, FragmentManager fm, List<Fragment> fragments, List<String> titlelist) {
        super ( fm );
        mContext = context;
        this.fragments = fragments;
        this.titlelist = titlelist;

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
        return mContext.getResources ( ).getString ( TAB_TITLES[position] );
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return fragments.size ();
    }


}
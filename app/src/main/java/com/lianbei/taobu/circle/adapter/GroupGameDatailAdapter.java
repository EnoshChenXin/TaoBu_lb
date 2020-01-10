package com.lianbei.taobu.circle.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbei.taobu.R;

import java.util.ArrayList;
import java.util.List;

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
package com.lianbei.taobu.taobu.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
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
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments;

    @StringRes
    private static  int[] TAB_TITLES ;
    private final Context mContext;
    private int mChildCount = 0;
    private FragmentManager fm;
    public SectionsPagerAdapter(Context context, FragmentManager fm, List<Fragment> fragments, int[] tab_titles) {
        super ( fm );
        this.fm = fm;
        mContext = context;
        this.fragments= fragments;
        this.TAB_TITLES = tab_titles;
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
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setFragments(List<Fragment> fragments) {
        if(this.fragments != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(Fragment f:this.fragments){
                ft.remove(f);
            }
            ft.commit();
            ft=null;
            fm.executePendingTransactions();
        }
        this.fragments = fragments;
        notifyDataSetChanged();
    }
}
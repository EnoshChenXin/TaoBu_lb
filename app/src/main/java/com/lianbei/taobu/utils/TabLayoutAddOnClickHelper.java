package com.lianbei.taobu.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lianbei.taobu.R;
import com.lianbei.taobu.shop.model.Channel;
import com.lianbei.taobu.shop.view.ShopListFragment;

import java.lang.reflect.Field;
import java.util.List;


public class TabLayoutAddOnClickHelper {
    public static void AddOnClick(Context context, TabLayout tabLayout, View.OnTouchListener listener, View.OnClickListener onClickListener) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(R.layout.text);
            View view = getTabView(tabLayout, i);
            TextView tab = view.findViewById(R.id.tab_tv);
            tab.setText(context.getResources().getString(ShopListFragment.TAB_TITLES[i]));
            ImageButton imageButton = view.findViewById(R.id.rb_tab);
            if (i == 0) {
                imageButton.setVisibility(View.GONE);
            }

            if (view == null) continue;
            view.setTag(i);
            view.setOnClickListener(onClickListener);
            view.setOnTouchListener(listener);

        }
    }

    public static void TopTabOnClick(Context context, List<Channel> channelList, TabLayout tabLayout, View.OnTouchListener listener, View.OnClickListener onClickListener) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(R.layout.top_layout_tab);
            View view = getTabView(tabLayout, i);
            TextView tab = view.findViewById(R.id.tab_item_textview);
            tab.setText(channelList.get(i).title);
            tab.setTextColor(context.getResources().getColor(R.color.white));
            if (i == 0) {
                TextPaint tp = tab.getPaint();
                tp.setFakeBoldText(true);
            }

            if (view == null) continue;
            view.setTag(i);
            view.setOnClickListener(onClickListener);
            view.setOnTouchListener(listener);

        }
    }

    public static void setTopTablestate(TabLayout.Tab tabIndex, Context context, TabLayout tabLayout) {
        try {
            if (tabIndex.getPosition() == 0) {//精选
                tabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.white));
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    View view = getTabView(tabLayout, i);
                    TextView tab = view.findViewById(R.id.tab_item_textview);
                    tab.setTextColor(context.getResources().getColor(R.color.white));
                    TextPaint tp = tab.getPaint();
                    tp.setFakeBoldText(false);
                }
                TextView selectTextView = tabIndex.getCustomView().findViewById(R.id.tab_item_textview);
                TextPaint tp = selectTextView.getPaint();
                tp.setFakeBoldText(true);
            } else {
                tabLayout.setSelectedTabIndicatorColor(context.getResources().getColor(R.color.main_font));
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    View view = getTabView(tabLayout, i);
                    TextView tab = view.findViewById(R.id.tab_item_textview);
                    tab.setTextColor(context.getResources().getColor(R.color.gray_deep));
                    TextPaint tp = tab.getPaint();
                    tp.setFakeBoldText(false);
                }
                TextView selectTextView = tabIndex.getCustomView().findViewById(R.id.tab_item_textview);
                TextPaint tp = selectTextView.getPaint();
                selectTextView.setTextColor(context.getResources().getColor(R.color.main_font));
                tp.setFakeBoldText(true);
            }
//
//            for (int i = 0; i < tabLayout.getTabCount(); i++) {
//                View view = getTabView(tabLayout, i);
//                TextView tab = view.findViewById(R.id.tab_item_textview);
//                if (tabIndex.getPosition() == 0) { //精选
//                    tab.setTextColor(context.getResources().getColor(R.color.white));
//                } else {
//                    tab.setTextColor(context.getResources().getColor(R.color.gray_deep));
//                }
//                TextPaint tp = tab.getPaint();
//                tp.setFakeBoldText(false);
//            }
//            TextView selectTextView = tabIndex.getCustomView().findViewById(R.id.tab_item_textview);
//            TextPaint tp = selectTextView.getPaint();
//            tp.setFakeBoldText(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void seTTablestate(Context context, TabLayout tabLayout) {
        try {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                View view = getTabView(tabLayout, i);
                TextView tab = view.findViewById(R.id.tab_tv);
                ImageButton imageButton = view.findViewById(R.id.rb_tab);
                if (i == 0) {
                    imageButton.setVisibility(View.GONE);
                } else {
                    Drawable backgroundDrawable = context.getResources().getDrawable(R.drawable.nomal);
                    imageButton.setBackground(null);
                    imageButton.setBackground(backgroundDrawable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 获取tabview
    private static View getTabView(TabLayout tabLayout, int index) {
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        if (tab == null) return null;
        View tabView = null;
        Field view = null;
        try {
            view = TabLayout.Tab.class.getDeclaredField("view");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        view.setAccessible(true);
        try {
            tabView = (View) view.get(tab);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return tabView;
    }
}

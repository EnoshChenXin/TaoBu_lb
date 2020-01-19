package com.lianbei.taobu.shop.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.lianbei.taobu.R;
import com.lianbei.taobu.shop.model.TabModel;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class SnapUpTabLayoutAddOnClickHelper {
    public static void AddOnClick(Context context, TabLayout tabLayout, ArrayList <TabModel> modelArrayList, View.OnTouchListener listener){
        for (int i=0;i<tabLayout.getTabCount();i++) {
            tabLayout.getTabAt ( i ).setCustomView ( R.layout.tab_text );
            View view = getTabView(tabLayout,i);
            TextView title =view.findViewById ( R.id.tab_title );
            TextView describe =view.findViewById ( R.id.tab_describe );
            title.setText (modelArrayList.get (i  ).getTitle ());
            describe.setText (modelArrayList.get ( i ).getDescribe ());
            if(i == 0){
                title.setTextColor ( context.getResources ().getColor ( R.color.white ) );
                describe.setTextColor (context.getResources ().getColor ( R.color.white ) );
            }
            if (view == null) continue;
            view.setTag(i);
            view.setOnTouchListener(listener);
        }
    }
    public  static void refreshTab(Context context,TabLayout tabLayout,int index){
        for (int i=0;i<tabLayout.getTabCount();i++) {
            View view = getTabView(tabLayout,i);
            TextView title =view.findViewById ( R.id.tab_title );
            TextView describe =view.findViewById ( R.id.tab_describe );
            if(i == index){
                title.setTextColor ( context.getResources ().getColor ( R.color.white ) );
                describe.setTextColor (context.getResources ().getColor ( R.color.white ) );
            }else{
                title.setTextColor ( context.getResources ().getColor ( R.color.gray_86FFFFFF ) );
                describe.setTextColor (context.getResources ().getColor ( R.color.gray_86FFFFFF ) );
            }
        }
    }

    public  static void refreshTab2(Context context,TabLayout tabLayout,int index){
        for (int i=0;i<tabLayout.getTabCount();i++) {
            View view = getTabView(tabLayout,i);
            TextView title =view.findViewById ( R.id.tab_title );
            TextView describe =view.findViewById ( R.id.tab_describe );
            if(i == index){
                title.setTextColor ( context.getResources ().getColor ( R.color.white ) );
                describe.setTextColor (context.getResources ().getColor ( R.color.white ) );
            }else{
                title.setTextColor ( context.getResources ().getColor ( R.color.gray_86FFFFFF ) );
                describe.setTextColor (context.getResources ().getColor ( R.color.gray_86FFFFFF ) );
            }
        }
    }


    // 获取tabview
    private static View getTabView(TabLayout tabLayout, int index){
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

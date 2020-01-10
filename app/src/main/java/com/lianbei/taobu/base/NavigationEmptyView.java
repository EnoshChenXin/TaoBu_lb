package com.lianbei.taobu.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.lianbei.taobu.R;

/**
 * 自定义页面空布局样式，可在该页面定义布局文件
 * Created by HASEE on 2017/5/11.
 */
public class NavigationEmptyView extends RelativeLayout {
    private Context context;

    public NavigationEmptyView(Context context) {
        super(context);
        this.context = context;
    }

    public NavigationEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public NavigationEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    public void setContentView(int View) {
        android.view.View layout = LayoutInflater.from(context).inflate( R.layout.layout_toolbar, this, true);
    }


    private void initView(final Context context) {

    }

}

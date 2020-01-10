package com.lianbei.taobu.shop.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class PostView extends LinearLayout {
    private Handler mHandler;
    public PostView(Context context) {
        super(context);
    }

    public PostView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PostView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public boolean post(Runnable action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && action!= null && !isAttachedToWindow()) {
            mHandler = new Handler();
            return mHandler.post(action);
        }
        return super.post(action);
    }

    @Override
    public boolean removeCallbacks(Runnable action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && action != null && !isAttachedToWindow()&& mHandler != null) {
            mHandler.removeCallbacks(action);
            return true;
        }
        return super.removeCallbacks(action);
    }
}

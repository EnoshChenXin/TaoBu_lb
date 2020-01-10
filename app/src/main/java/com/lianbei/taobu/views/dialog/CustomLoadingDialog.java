package com.lianbei.taobu.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.lianbei.taobu.R;


/**
 **/
public class CustomLoadingDialog extends Dialog {

    private ImageView mImageView;
    private AnimationDrawable mAnimation;
    public CustomLoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
        /** 设置触摸外面的取消为false */

        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.common_custom_loading_dialog);
        mImageView = (ImageView) findViewById( R.id.loadingIv);
       // mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
           //     mAnimation.start();
            }
        });
    }


}

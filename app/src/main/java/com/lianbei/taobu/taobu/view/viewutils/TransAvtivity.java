package com.lianbei.taobu.taobu.view.viewutils;

import android.content.Intent;
import android.os.Bundle;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.utils.StatusBarUtil;
import com.lidong.photopicker.intent.PhotoPreviewIntent;

import java.util.ArrayList;

/**
 * 照片查看器跳转页面
 */
public class TransAvtivity extends BaseActivity {
    public static final String EXTRA_PHOTOS = "extra_photos";
    public static final String EXTRA_CURRENT_ITEM = "extra_current_item";
    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    private ArrayList<String> paths;
    private int currentItem = 0;
    @Override
    public int getContentViewId() {
        return R.layout.activity_trans_avtivity;
    }

    @Override
    public void initBeforeView() {
        super.initBeforeView ( );
        StatusBarUtil.setStatusBarModeNoTitle ( this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
    }
    @Override
    public void initViews() {
        paths = new ArrayList <> ();
        ArrayList<String> pathArr = getIntent().getStringArrayListExtra(EXTRA_PHOTOS);
        if(pathArr != null){
            paths.addAll(pathArr);
        }
        currentItem = getIntent().getIntExtra(EXTRA_CURRENT_ITEM, 0);
        PhotoPreviewIntent intent = new PhotoPreviewIntent( this);
        intent.setCurrentItem(currentItem);
        intent.setCurrentType (-1);
        intent.setPhotoPaths(paths);
        startActivity (intent  );
        finish ();
       // startActivityForResult(intent, REQUEST_PREVIEW_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 预览
                case REQUEST_PREVIEW_CODE:
                    finish ();
                    break;
            }
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }
}

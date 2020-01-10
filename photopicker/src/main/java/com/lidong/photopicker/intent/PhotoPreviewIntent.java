package com.lidong.photopicker.intent;

import android.content.Context;
import android.content.Intent;

import com.lidong.photopicker.PhotoPreviewActivity;

import java.util.ArrayList;

/**
 * 预览照片
 * Created by foamtrace on 2015/8/25.
 */
public class PhotoPreviewIntent extends Intent{

    public PhotoPreviewIntent(Context packageContext) {
        super(packageContext, PhotoPreviewActivity.class);
    }

    /**
     * 照片地址
     * @param paths
     */
    public void setPhotoPaths(ArrayList<String> paths){
        this.putStringArrayListExtra(PhotoPreviewActivity.EXTRA_PHOTOS, paths);
    }

    /**
     * 当前照片的下标
     * @param currentItem
     */
    public void setCurrentItem(int currentItem){
        this.putExtra(PhotoPreviewActivity.EXTRA_CURRENT_ITEM, currentItem);
    }
    /**
     * 当前照片处理展示类型 删除/纯粹预览
     * @param currentType
     */
    public void setCurrentType(int currentType){
        this.putExtra(PhotoPreviewActivity.EXTRA_CURRENT_TYPE, currentType);
    }
}

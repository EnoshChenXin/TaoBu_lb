package com.lianbei.taobu.views.bannerview.bean;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 轮播图父接口
 *
 * @author Mars
 *
 */
public interface IBanner {


    public String getType();
    /**
     *
     */
    public View getView();

    /**
     * 图片控件背景图，只有需要显示两张图片的时候才使用（第二章是覆盖在第一张上面的，同时为了保证图片不失真，建议把作为背景的图片进行裁剪到合适尺寸）
     *
     * @return
     */
    public Bitmap getImgBg();

    /**
     * 网络图片路径
     *
     * @return
     */
    public String getUrl();

    /**
     * 图片点击跳转类型
     *
     * @return
     */
    public String getClickType();

    /**
     * 图片点击跳转id
     *
     * @return
     */
    public String getClickID();

    /**
     * 图片点击跳转Url
     *
     * @return
     */
    public String getClickUrl();

    /**
     * 图片点击跳转TITLE
     *
     */
    public String getClickUrlTitle();


    /**
     * 本地图片路径
     *
     * @return
     */
    public int getResource();

    /**
     * 获取图片文字描述
     */
    public String getPhotoDesc();

    /**
     * 获取链接类型
     */
    public String getUrlType();
}

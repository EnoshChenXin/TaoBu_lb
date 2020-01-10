package com.lianbei.taobu.views.bannerview.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.utils.GlideUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * ImageView创建工厂
 */
public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 *
	 * @param url
	 * @return
	 */
	public static ImageView getImageView(Context context, String url, String tag, View view) {
		if(tag.equals ( "adv" )){
			/*View relativeLayout = (RelativeLayout)LayoutInflater.from(context).inflate(
					R.layout.view_banneradv, null);
			if (view.getParent ( ) != null) {
				((ViewGroup)view.getParent ( )).removeView ( view );
			}
			relativeLayout.addView (view);*/
			return null;
		}else{
			ImageView imageView;
			if(tag.equals ( "banner" )){
				imageView= (ImageView)LayoutInflater.from(context).inflate(
						R.layout.view_banner, null);
				GlideUtils.getInstance ().load(context, url.replace("list/300x196", "large"),imageView);
			}else if(tag.equals ( "banner2" )){
				imageView = (ImageView)LayoutInflater.from(context).inflate(
						R.layout.view_banner2, null);
				GlideUtils.getInstance ().load_Circular_Cache (context,url,imageView);
			}else{
				imageView = (ImageView)LayoutInflater.from(context).inflate(
						R.layout.view_banner, null);
				GlideUtils.getInstance ().load(context, url.replace("list/300x196", "large"),imageView);
			}

			//ImageLoader.getInstance().displayImage(url, imageView);
			//GlideUtils.load(context, url.replace("list/300x196", "large"),imageView);
			return imageView;
		}
	}

}

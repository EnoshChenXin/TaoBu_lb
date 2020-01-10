package com.lianbei.taobu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.mine.model.UserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.File;
import java.io.IOException;

public class DirectoryHelp {

	public enum ImageType {
		// 意见反馈
		SUGGESS,
		// 下单预定页面 自主选车
		SELECT_CAR,
		// 出行记录
		TRAVERECORD
	}

	public static String CACHE_PATH;

	public static String USER_PHOTO_CLICP_NAME;

	public static String USER_PHOTO_CLICP_NAME_TMP;

	public static String CACHE_LOG_PATH;
	public static boolean httpUrl = false;

	public static void clear() {
		try {
			File file = new File (CACHE_PATH);
			if (file.exists())
				file.deleteOnExit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void renameTmpPhoto() {
		File file = new File (USER_PHOTO_CLICP_NAME_TMP);
		File renameFile = new File (USER_PHOTO_CLICP_NAME);
		file.renameTo(renameFile);
	}

	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}



	public static boolean isSDCardAvailable() {
		return Environment.MEDIA_MOUNTED.equals( Environment
				.getExternalStorageState());
	}

	public static String getTmpPath(Context context) {
		File file;
		if (false && Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			file = new File ( Environment.getExternalStorageDirectory()
					+ "/bima_tmp");
		} else {
			file = new File (context.getCacheDir().getAbsolutePath()
					+ "/bima_tmp");
		}
		if (!file.exists())
			file.mkdirs();
		return file.getAbsolutePath();
	}

	public static String setHttpUrl(String httpUrl) {
           if(!Validator.isStrNotEmpty(httpUrl)){
			   return "";
		   }else if(!httpUrl.startsWith("http")){
			   return APIs.BASE_URL+httpUrl;
		   }else {
			   return httpUrl;
		   }
	}

	/**
	 * 用户头像加载
	 */
	public static DisplayImageOptions optionsUserAvatar = new DisplayImageOptions.Builder()
			.showStubImage( R.mipmap.icon3)
			// 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.mipmap.icon3)
			// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.mipmap.icon3)
			// 设置图片加载或解码过程中发生错误显示的图片
			// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
			.cacheOnDisc(false).cacheInMemory(true)
			.bitmapConfig( Bitmap.Config.ARGB_8888).build();

	/**
	 * 运营商logo加载
	 */
	public static DisplayImageOptions optionslogo = new DisplayImageOptions.Builder()
			.showStubImage(R.mipmap.icon3)
			// 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.mipmap.icon3)
			// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.mipmap.icon3)
			// 设置图片加载或解码过程中发生错误显示的图片
			// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
			.cacheOnDisc(false).cacheInMemory(true)
			.bitmapConfig( Bitmap.Config.ARGB_8888).build();


	public static void receiveAvataChanged(Context context,
                                           final ImageView imageView) {
		Bitmap bt = null;
		try {
			bt = BitmapFactory.decodeFile(USER_PHOTO_CLICP_NAME);
			imageView.setImageBitmap(bt);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private static void setImageBitmap(ImageView iv, Bitmap bt) {
		if (iv != null)
			iv.setImageBitmap(bt);
	}

	private static void setImageResource(ImageView iv, int resId) {
		if (iv != null)
			iv.setImageResource(resId);
	}

	public static String genCameraPhoto(Context context) {

		// String path =
		// CACHE_PATH+"/avatar_camara_cache/"+ShareUtils.getUserId(context);
		if (CACHE_PATH == null) {
			initCachePath(context);
		}
		File file = new File (CACHE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		File outputImage = new File (CACHE_PATH, "bima_photo_1.jpeg");
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = outputImage.getAbsolutePath();// Uri.fromFile(outputImage);
		// 将File对象转换为Uri并启动照相程序
		return url;
	}
	public static void initCachePath(Context context) {
		if (UserInfo.getUserInfo (context) != null) {
			if (isSDCardAvailable()) {
				CACHE_PATH = Environment.getExternalStorageDirectory()
						+ "/taobu/cache/" + "7";
			} else {
				CACHE_PATH = context.getCacheDir().getAbsolutePath() + "/"
						+ ShareUtils.getUserId(context);
			}
			File file = new File (CACHE_PATH);
			if (!file.exists()) {
				CACHE_PATH = context.getFilesDir().getAbsolutePath() + "/"
						+ ShareUtils.getUserId(context);
			}
			CACHE_PATH = CACHE_PATH + File.separator;

			USER_PHOTO_CLICP_NAME_TMP = CACHE_PATH + "photo_clip_tmp.png";
			USER_PHOTO_CLICP_NAME = CACHE_PATH + "photo_clip.png";
			CACHE_LOG_PATH = CACHE_PATH + "sdcard_taobu_log";
			;

			try {
				file = new File (USER_PHOTO_CLICP_NAME_TMP);
				if (!file.exists())
					file.createNewFile();

				file = new File (CACHE_LOG_PATH);
				if (!file.exists())
					file.mkdirs();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

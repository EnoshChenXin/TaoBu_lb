package com.lianbei.taobu.utils.clip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.lianbei.taobu.utils.GLog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;


/**
 * Tools for handler picture
 * 
 * @author Ryan.Tang
 * 
 */
public final class ImageTools {
	

	public static boolean savePhotoToSDCard(Context context, Bitmap bm, String path){
			GLog.d("savePhotoToSDCard...path..."+path);
			try{
		     File file = new File (path);
		     if(!file.getParentFile().exists()){    
		    	 	file.getParentFile().mkdirs(); 
		     }    
			// FileOutputStream fos=context.openFileOutput(path, Context.MODE_WORLD_WRITEABLE);
		     BufferedOutputStream bos = new BufferedOutputStream (new FileOutputStream (file));
		     bm.compress( Bitmap.CompressFormat.PNG, 80, bos);
		     bos.flush();    
		     bos.close();   
		     return true;
		} catch (Exception e) {
			e.printStackTrace();
			GLog.d("savePhotoToSDCard error "+e.getMessage());
			return false;
			}
	}
	
	/**
	 * 根据路径加载bitmap
	 * 
	 * @param path
	 *            路径
	 * @param w
	 *            宽
	 * @param h
	 *            长
	 * @return
	 */
	public static final Bitmap convertToBitmap(String path, int w, int h) {
		try {
			GLog.b("convert bitmap path="+path);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			// 设置为ture只获取图片大小
			opts.inJustDecodeBounds = true;
			opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
			// 返回为空
			BitmapFactory.decodeFile(path, opts);
			int width = opts.outWidth;
			int height = opts.outHeight;
			float scaleWidth = 0.f, scaleHeight = 0.f;
			if (width > w || height > h) {
				// 缩放
				scaleWidth = ((float) width) / w;
				scaleHeight = ((float) height) / h;
			}
			opts.inJustDecodeBounds = false;
			float scale = Math.max(scaleWidth, scaleHeight);
			opts.inSampleSize = (int) scale;
			File filePath = new File (path);
			GLog.b("file size="+filePath.length());
			WeakReference <Bitmap> weak = new WeakReference <Bitmap> ( BitmapFactory.decodeFile(path, opts));
			Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);
			if (bMapRotate != null) {
				return bMapRotate;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

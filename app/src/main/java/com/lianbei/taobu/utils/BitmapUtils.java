package com.lianbei.taobu.utils;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {

	// 可接受的最小150K 最大 3M
	private static int MIN_SIZE = 150;
	private static int MAX_SIZE = 3 * 1024;
	public static String compressImage(String path, String tmpPath, int availableSize) {
		Bitmap image = compressImageFromFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		image.compress( Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		int size = availableSize;
		if (availableSize > MAX_SIZE) {
			size = MAX_SIZE;
		} else if (availableSize < MIN_SIZE) {
			size = MIN_SIZE;
		}
		while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于可接受的大小,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10%
			if (options > 0) {
				image.compress( Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			} else {
				break;
			}
		}
		File save = new File (tmpPath);
		BitmapUtils.saveBitmap(image, save);
		Utils.D("compressFile", "compress file size is " + save.length() / 1024
				+ "KB");
		if (image.isRecycled()) {
			image.recycle();
			image = null;
		}
		baos.reset();
		try {
			baos.close();
			baos = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.gc();
		return path;
	}

	public static String compressImage(String path, int availableSize) {
		Bitmap image = compressImageFromFile(path);
		ByteArrayOutputStream baos = new ByteArrayOutputStream ();
		image.compress( Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		int size = availableSize;
		if (availableSize > MAX_SIZE) {
			size = MAX_SIZE;
		} else if (availableSize < MIN_SIZE) {
			size = MIN_SIZE;
		}
		while (baos.toByteArray().length / 1024 > size) { // 循环判断如果压缩后图片是否大于可接受的大小,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10%
			if (options > 0) {
				image.compress( Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			} else {
				break;
			}
		}
		File save = new File (path);
		BitmapUtils.saveBitmap(image, save);
		Utils.D("compressFile", "compress file size is " + save.length() / 1024
				+ "KB");
		if (image.isRecycled()) {
			image.recycle();
			image = null;
		}
		baos.reset();
		try {
			baos.close();
			baos = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.gc();
		return path;
	}
	
	private static Bitmap compressImageFromFile(String path) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 854f;//
		float ww = 480f;//
		int be = 1;
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置采样率
		newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收
		bitmap = BitmapFactory.decodeFile(path, newOpts);
		return bitmap;
	}

	/** 保存方法 */
	public static void saveBitmap(Bitmap bm, File dest) {
		if (dest.exists()) {
			dest.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream (dest);
			bm.compress( Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas (output);
        final int color = 0xff424242; 
        final Rect rect = new Rect (0, 0, bitmap.getWidth(), bitmap.getHeight());
        Paint paint =new Paint ();
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color); 
        int x = bitmap.getWidth(); 
        canvas.drawCircle(x / 2, x / 2, x / 2, paint);  
        paint.setXfermode(new PorterDuffXfermode ( Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint); 
        return output;
	}
	/**
	 * uri 转 File
	 * @param uri
	 * @return
	 */
	public File uriTurnFile(Uri uri, Activity activity){

		if(uri == null){
			return null;
		}

		File file = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = activity.managedQuery(uri, proj, null,
				null, null);
		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		String img_path = actualimagecursor
				.getString(actual_image_column_index);
		file = new File(img_path);
		return file;
	}
	private String getPath(Uri uri, Activity activity) {
		String[] projection = {MediaStore.Video.Media.DATA};
		Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
}

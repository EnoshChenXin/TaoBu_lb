package com.lianbei.taobu.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lianbei.taobu.R;

import java.io.File;
import java.math.BigDecimal;


/**
 * @author ChayChan
 * @description: 对glide进行封装的工具类
 * @date 2017/6/19  20:43
 */

public class GlideUtils {
    private static GlideUtils mInstance;

    public static GlideUtils getInstance() {
        if (mInstance == null) {
            synchronized (GlideUtils.class) {
                if (mInstance == null) {
                    mInstance = new GlideUtils ( );
                }
            }
        }
        return mInstance;
    }

    RequestOptions options = new RequestOptions ( )
            .placeholder ( R.mipmap.icon3 )
            .error ( R.mipmap.icon3 )                    //加载错误之后的错误图

            //.override ( 400, 400 )                                //指定图片的尺寸
            //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
           // .fitCenter ( )
            //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
            //.centerCrop ( )
           // .circleCrop ( )//指定图片的缩放类型为centerCrop （圆形）
            .skipMemoryCache ( true )                           //跳过内存缓存
           // .diskCacheStrategy ( DiskCacheStrategy.ALL )        //缓存所有版本的图像
            .diskCacheStrategy ( DiskCacheStrategy.NONE ) ;       //跳过磁盘缓存
           // .diskCacheStrategy ( DiskCacheStrategy.DATA )        //只缓存原来分辨率的图片
          //  .diskCacheStrategy ( DiskCacheStrategy.RESOURCE );    //只缓存最终的图片

    public void load(Context context, String url, ImageView iv) {
        Glide.with ( context )
                .load ( url )
                .apply ( options )
                .into ( iv );
    }

    /**
     * banner 直角缓存
     * @param context
     * @param url
     */
    public void loadright_AngleCache(Context context, String url, SimpleTarget<Drawable> D) {
        RequestOptions options = new RequestOptions ( )
                //加载错误之后的错误图

                //.override ( 400, 400 )                                //指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                // .fitCenter ( )
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                //.centerCrop ( )
                // .circleCrop ( )//指定图片的缩放类型为centerCrop （圆形）
                .skipMemoryCache ( true )                       //跳过内存缓存
                // .diskCacheStrategy ( DiskCacheStrategy.ALL )        //缓存所有版本的图像
                .diskCacheStrategy ( DiskCacheStrategy.NONE ) ;       //跳过磁盘缓存
             // .diskCacheStrategy ( DiskCacheStrategy.DATA )        //只缓存原来分辨率的图片
             //  .diskCacheStrategy ( DiskCacheStrategy.RESOURCE );    //只缓存最终的图片
        Glide.with ( context )
                .load ( url )
                .apply ( options )
                .into (D);
    }
    /**
     * banner 圆角缓存
     * @param context
     * @param url
     */
    public void load_Circular_Cache(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions ( )
                .bitmapTransform(new RoundedCorners(25))
                //加载错误之后的错误图

                //.override ( 400, 400 )                                //指定图片的尺寸
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                // .fitCenter ( )
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                //.centerCrop ( )
                // .circleCrop ( )//指定图片的缩放类型为centerCrop （圆形）
                .skipMemoryCache ( false )                           //跳过内存缓存
                // .diskCacheStrategy ( DiskCacheStrategy.ALL )        //缓存所有版本的图像
                .diskCacheStrategy ( DiskCacheStrategy.NONE ) ;       //跳过磁盘缓存
        // .diskCacheStrategy ( DiskCacheStrategy.DATA )        //只缓存原来分辨率的图片
        //  .diskCacheStrategy ( DiskCacheStrategy.RESOURCE );    //只缓存最终的图片
        Glide.with ( context )
                .load ( url )
                .apply ( options )
                .into (iv);
    }
    public void loadpauseRequests(Context context) {
         Glide.with ( context ).pauseRequests();
    }
    public void load200(Context context, String url, ImageView iv) {
        Glide.with ( context )
                .load ( url )
                .apply ( options200 )
                .into ( iv );
    }
    RequestOptions options200 = new RequestOptions ( )
            .placeholder ( R.mipmap.icon3 )
            .error ( R.mipmap.icon3 )                    //加载错误之后的错误图
            .override ( 300, 300 )                                //指定图片的尺寸
           // .bitmapTransform(new RoundedCorners(10))
            //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
             .fitCenter ( )
            //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
            //.centerCrop ( )
            // .circleCrop ( )//指定图片的缩放类型为centerCrop （圆形）
            .skipMemoryCache ( false )                           //跳过内存缓存
            // .diskCacheStrategy ( DiskCacheStrategy.ALL )        //缓存所有版本的图像
            .diskCacheStrategy ( DiskCacheStrategy.NONE ) ;       //跳过磁盘缓存
    // .diskCacheStrategy ( DiskCacheStrategy.DATA )        //只缓存原来分辨率的图片
    //  .diskCacheStrategy ( DiskCacheStrategy.RESOURCE );    //只缓存最终的图片
    public void load(Context context, String url, SimpleTarget<Drawable> D) {
        Glide.with ( context )
                .load ( url )
                .apply ( options )
                .into (D);
    }



    public void load(Context context, String url, ImageView iv, int placeHolderResId) {
        if (placeHolderResId == -1) {
            Glide.with ( context )
                    .load ( url )
                    .apply ( options )
                    .into ( iv );
        }
    }

    public void loadRound(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions ( );
        options.placeholder ( R.mipmap.icon3 )
                .centerCrop ( )
                .circleCrop ( );

        Glide.with ( context )//
                .load ( url )//
                .apply ( options )//
                .into ( iv );
    }
    public void loadthumbnail(Context context, String url, ImageView iv) {
        Glide.with ( context )
                .load ( url )
                .apply ( options2 )
                .into ( iv );
    }




    /**
     * 清除图片磁盘缓存
     */
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper ( ) == Looper.getMainLooper ( )) {
                new Thread ( new Runnable ( ) {
                    @Override
                    public void run() {
                        Glide.get ( context ).clearDiskCache ( );
// BusUtil.getBus().post(new GlideCacheClearSuccessEvent());
                    }
                } ).start ( );
            } else {
                Glide.get ( context ).clearDiskCache ( );
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }

    /**
     * 清除图片内存缓存
     */
    public void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper ( ) == Looper.getMainLooper ( )) { //只能在主线程执行
                Glide.get ( context ).clearMemory ( );
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }

    /**
     * 清除图片所有缓存
     */
    public void clearImageAllCache(Context context) {
        clearImageDiskCache ( context );
        clearImageMemoryCache ( context );
        String ImageExternalCatchDir = context.getExternalCacheDir ( ) + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile ( ImageExternalCatchDir, true );
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public String getCacheSize(Context context) {
        try {
            return getFormatSize ( getFolderSize ( new File ( context.getCacheDir ( ) + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR ) ) );
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles ( );
            for (File aFileList : fileList) {
                if (aFileList.isDirectory ( )) {
                    size = size + getFolderSize ( aFileList );
                } else {
                    size = size + aFileList.length ( );
                }
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty ( filePath )) {
            try {
                File file = new File ( filePath );
                if (file.isDirectory ( )) {
                    File files[] = file.listFiles ( );
                    for (File file1 : files) {
                        deleteFolderFile ( file1.getAbsolutePath ( ), true );
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory ( )) {
                        file.delete ( );
                    } else {
                        if (file.listFiles ( ).length == 0) {
                            file.delete ( );
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }
    RequestOptions options2 = new RequestOptions ( )
            .placeholder ( R.mipmap.icon3 )
            .error ( R.mipmap.icon3 )                    //加载错误之后的错误图
            .override ( 300, 300 )                                //指定图片的尺寸
            //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
            // .fitCenter ( )
            //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
            //.centerCrop ( )
            // .circleCrop ( )//指定图片的缩放类型为centerCrop （圆形）
            .skipMemoryCache ( true )                           //跳过内存缓存
            // .diskCacheStrategy ( DiskCacheStrategy.ALL )        //缓存所有版本的图像
            .diskCacheStrategy ( DiskCacheStrategy.NONE );  //跳过磁盘缓存
    // .diskCacheStrategy ( DiskCacheStrategy.DATA )        //只缓存原来分辨率的图片
    //  .diskCacheStrategy ( DiskCacheStrategy.RESOURCE );    //只缓存最终的图片
    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal ( Double.toString ( kiloByte ) );
            return result1.setScale ( 2, BigDecimal.ROUND_HALF_UP ).toPlainString ( ) + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal ( Double.toString ( megaByte ) );
            return result2.setScale ( 2, BigDecimal.ROUND_HALF_UP ).toPlainString ( ) + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal ( Double.toString ( gigaByte ) );
            return result3.setScale ( 2, BigDecimal.ROUND_HALF_UP ).toPlainString ( ) + "GB";
        }
        BigDecimal result4 = new BigDecimal ( teraBytes );

        return result4.setScale ( 2, BigDecimal.ROUND_HALF_UP ).toPlainString ( ) + "TB";
    }
}

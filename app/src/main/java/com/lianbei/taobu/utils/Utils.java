package com.lianbei.taobu.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Toast;

import com.lianbei.taobu.application.GlobalApplication;
import com.lianbei.taobu.mine.login.WXAccessTokenInfo;
import com.lianbei.taobu.utils.secureity.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("NewApi")
public class Utils {

    public static boolean isDebug = true;

    public static int getScreenWidth() {
        return GlobalApplication.getApplication ().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return GlobalApplication.getApplication().getResources().getDisplayMetrics().heightPixels;
    }

    public static void showToast(String str) {
        Toast.makeText(GlobalApplication.getApplication(), str, Toast.LENGTH_SHORT).show();
    }

    public static void D(String TAG, String str) {
        if (isDebug) {
            Log.d(TAG, "-----------------" + str + "-----------------");
        }
    }

    public static void I(String TAG, String str) {
        if (isDebug) {
            Log.i(TAG, "-----------------" + str + "-----------------");
        }
    }

    public static void E(String TAG, String str) {
        if (isDebug) {
            Log.e(TAG, "-----------------" + str + "-----------------");
        }
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @SuppressLint("NewApi")
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, imageUri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(imageUri)) {
                final String docId = DocumentsContract.getDocumentId(imageUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(imageUri)) {

                final String id = DocumentsContract.getDocumentId(imageUri);
                final Uri contentUri = ContentUris.withAppendedId( Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(imageUri)) {
                final String docId = DocumentsContract.getDocumentId(imageUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();

            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }

        return null;
    }

    /*
     * 判断选择图片格式问题是否为png jpg jpeg
     */
    public static boolean decideImageType(String filePath) {

        String value = getImageType(filePath);

        if (value.equals("jpg")) {
            return true;// jpg
        } else if (value.equals("gif")) {
            return false; // gif
        } else if (value.equals("bmp")) {
            return false;// bmp
        } else if (value.equals("jpeg")) {
            return true;// jpeg
        } else if (value.equals("png")) {
            return true;// png
        } else {
            return false;
        }
    }

    /*
     * 获取图片格式
     */
    public static String getImageType(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream (filePath);
            byte[] b = new byte[3];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

        if (pictureType(filePath).equals("jpg") && "FFD8FF".contains(value)) {
            return "jpg";// jpg
        } else if (pictureType(filePath).equals("gif") && "47494638".contains(value)) {
            return "gif"; // gif
        } else if (pictureType(filePath).equals("bmp") && "424D".contains(value)) {
            return "bmp";// bmp
        } else if (pictureType(filePath).equals("jpeg") && "424D3A".contains(value)) {
            return "jpeg";// jpeg
        } else if (pictureType(filePath).equals("png") && "89504E47".contains(value)) {
            return "png";// png
        } else {
            return "other";
        }
    }

      private static String pictureType(String filePath){
      return  filePath.substring(filePath.lastIndexOf(".")+1);
    }

    /*
     * // mFileTypes.put("FFD8FF", "jpg"); // mFileTypes.put("89504E47", "png");
     * // mFileTypes.put("47494638", "gif"); // mFileTypes.put("49492A00",
     * "tif"); // mFileTypes.put("424D", "bmp"); // // //
     * mFileTypes.put("41433130", "dwg"); //CAD // mFileTypes.put("38425053",
     * "psd"); // mFileTypes.put("7B5C727466", "rtf"); //日记本 //
     * mFileTypes.put("3C3F786D6C", "xml"); // mFileTypes.put("68746D6C3E",
     * "html"); // mFileTypes.put("44656C69766572792D646174653A", "eml"); //邮件
     * // mFileTypes.put("D0CF11E0", "doc"); //
     * mFileTypes.put("5374616E64617264204A", "mdb"); //
     * mFileTypes.put("252150532D41646F6265", "ps"); //
     * mFileTypes.put("255044462D312E", "pdf"); // mFileTypes.put("504B0304",
     * "zip"); // mFileTypes.put("52617221", "rar"); //
     * mFileTypes.put("57415645", "wav"); // mFileTypes.put("41564920", "avi");
     * // mFileTypes.put("2E524D46", "rm"); // mFileTypes.put("000001BA",
     * "mpg"); // mFileTypes.put("000001B3", "mpg"); // mFileTypes.put("424D3A",
     * "jpeg");
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder ();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    /**
     * 根据URI获取data路径
     *
     * @param context
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {

            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static void showPhoto(Activity act, String path) {
        Intent it = new Intent ( Intent.ACTION_VIEW);
        Uri mUri = Uri.fromFile(new File (path));
        it.setDataAndType(mUri, "image/*");
        act.startActivity(it);
    }

    public static void setMinHeapSize(long size) {
        try {
            Class <?> cls = Class.forName("dalvik.system.VMRuntime");
            Method getRuntime = cls.getMethod("getRuntime");
            Object obj = getRuntime.invoke(null);// obj就是Runtime
            if (obj == null) {
                System.err.println("obj is null");
            } else {
                System.out.println(obj.getClass().getName());
                Class <?> runtimeClass = obj.getClass();
                Method setMinimumHeapSize = runtimeClass.getMethod("setMinimumHeapSize", long.class);

                setMinimumHeapSize.invoke(obj, size);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络是否连接
     *
     * @return
     */
    public static boolean checkNetWork() {
        ConnectivityManager manager = (ConnectivityManager) GlobalApplication.getApplication()
                .getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo( ConnectivityManager.TYPE_WIFI);
        if (mobile.isConnected() || wifi.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将View 转换成Bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure( MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static void copyAssets2SDcard(String fileName) {
        try {
            File obj = new File ( FileUtils.getImgPath() + fileName);
            if (obj.exists()) {
                return;
            } else {
                Bitmap bm = BitmapFactory.decodeStream(GlobalApplication.getApplication().getAssets().open(fileName));
                BitmapUtils.saveBitmap(bm, obj);
                if (!bm.isRecycled()) {
                    bm.recycle();
                    bm = null;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void changeTime() {

    }
    //保存微信的accessToken
    public static boolean saveAccessInfotoLocation(WXAccessTokenInfo tokenInfo, Context context){
        SharedPreferences preferences = context.getSharedPreferences("tokenInfo",MODE_PRIVATE);
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(tokenInfo);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String( Base64.encode (baos.toByteArray ()));
            Log.i("xxx","save的字符串=="+oAuth_Base64);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Info", oAuth_Base64);

            editor.commit();
            Log.i("xxx","tokenInfo存储成功");
            return true;
        } catch (IOException e) {

            Log.i("xxx","tokenInfo存储失败"+e);
            return false;
        }

    }

    /**
     * 距离单位
     * @param distance
     * @return
     */
    public static String mOrKmunit(String distance){
        float dist = Float.valueOf(distance);
        if(dist > 1000){
            return "km";
        }else{
            return "m";
        }
    }
    /**
     * m 距离转换成km
     * @param distance
     */
    public static  double destanceUtils(String distance) {
         float dist = Float.valueOf(distance);
         double dis = 0;
         if(dist >1000) {
             dis = Math.round(dist / 1000);
             return dis;
         }else{
            return  Math.round(dist) / 100d;
        }
    }

    public static String transformNull(String string) {
        if (Validator.isStrNotEmpty(string)){
            return string;
        }else {
            return "";
        }
    }
}

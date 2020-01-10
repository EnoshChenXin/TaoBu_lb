package com.lianbei.taobu.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.text.format.Formatter;
import android.util.Log;

import com.lianbei.taobu.application.GlobalApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {
    static Context mContext;

    // SD卡路径
    public static final String SDCARD_PATH = Environment
            .getExternalStorageDirectory().toString() + "/";


    // 文件夹名称
    public static final String MAIN = GlobalApplication.getApplication().getPackageName(); // 主文件夹
    private static final String DATA = "data"; // 资源
    private static final String UPDATE = "update"; // 更新
    private static final String IMG = "img"; // 图片资源
    private static final String LOG = "log"; // 运行日志
    // 创建该文件使文件夹不被android媒体库扫描
     private static final String NO_MEDIA = ".nomedia/";
  //  private static final String NO_MEDIA = "s";

    /**
     * 主目录
     */
    private static String MAIN_PATH = SDCARD_PATH + MAIN + "/";

    /**
     * 数据资源文件路径
     */
    //private static String DATA_PATH = MAIN_PATH + DATA + "/";
     private static String DATA_PATH = MAIN_PATH + DATA + "/" + NO_MEDIA;

    /**
     * 下载更新文件夹
     */
    private static String UPDATE_PATH = MAIN_PATH + UPDATE + "/";
    /**
     * 图片资源地址
     */
    private static String IMG_PATH = DATA_PATH + IMG + "/";
    /**
     * LOG日志位置
     */
    private static String LOG_PATH = MAIN_PATH + LOG + "/";


    private static String CACHE_PATH = "s";

    /**
     * SD卡是否存在
     *
     * @return
     */
    public static boolean hasSDcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 内部空间总大小(MB)
     */
    public static String getTotalRom() {
        File root = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();

        return Formatter.formatFileSize(GlobalApplication.getApplication(), blockSize
                * blockCount);
    }

    /**
     * 内部空间可用大小(MB)
     */
    public static String getAvailableRom() {
        File root = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();

        return Formatter.formatFileSize(GlobalApplication.getApplication(), blockSize
                * availCount);
    }

    /**
     * 文件大小格式转换byte转成KB MB GB
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        return Formatter.formatFileSize(GlobalApplication.getApplication(), size);
    }

    /**
     * 创建所有文件路径
     *
     * @throws IOException
     */
    public static void createAllDirectory(Context context) {
        mContext = context;
        if (!hasSDcard()) {
            String SDCARD_PATHs = mContext.getCacheDir().getAbsolutePath() + "/";
            MAIN_PATH = SDCARD_PATHs + MAIN;
        }
        File main = new File(MAIN_PATH);
        if (!main.exists()) {
            main.mkdirs();
        }
        File update = new File(UPDATE_PATH);
        if (!update.exists()) {
            update.mkdirs();
        }
        File data = new File(DATA_PATH);
        if (!data.exists()) {
            data.mkdirs();
        }
        File img = new File(IMG_PATH);
        if (!img.exists()) {
            img.mkdirs();
        }
        File log = new File(LOG_PATH);
        if (!log.exists()) {
            log.mkdirs();
        }
    }

    public static String getMainPath() {
        return MAIN_PATH;
    }

    public static String getDataPath() {
        return DATA_PATH;
    }

    public static String getImgPath() {
        return IMG_PATH;
    }

    public static String getUpdatePath() {
        return UPDATE_PATH;
    }

    /**
     * @param path 文件路径
     * @return void 返回类型
     * @Title: deleteFile
     * @Description: TODO(删除附件)
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * @return 是否删除成功
     * @Title: deleteFilesInImagePath
     * @Description: 删除IMG_PATH下所有的图片
     * 5
     */
    public static boolean deleteFilesInImagePath(String filePath) {
        boolean flag = false;
        //判断文件是否存在
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        //检查目录是否存在
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                deleteFile(files[i].getAbsolutePath());
            } else {
                //删除子目录
                flag = deleteFilesInImagePath(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        Log.e("IMAGE", "路径：" + filePath + "包含文件数：" + dirFile.listFiles().length);
        if (filePath.equals(getImgPath())) {
            //IMG_PATH不能被删除
            if (dirFile.listFiles().length > 0) {
                //没有删除完全
                return false;
            } else {
                return true;
            }
        } else {
            //删除当前空目录
            return dirFile.delete();
        }
    }

    /**
     * 拷贝一个文件,srcFile源文件，destFile目标文件
     *
     * @throws IOException
     */
    public static void copyFileTo(File srcFile, File destFile)
            throws IOException {
        FileInputStream fis = new FileInputStream(srcFile);
        FileUtils.copyFileTo(fis, destFile);
    }

    /**
     * 拷贝一个文件,srcFile源文件，destFile目标文件
     *
     * @throws IOException
     */
    public static void copyFileTo(InputStream ios, File destFile)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(destFile);
        int readLen = 0;
        byte[] buf = new byte[4096];
        while ((readLen = ios.read(buf)) != -1) {
            fos.write(buf, 0, readLen);
        }
        fos.flush();
        fos.close();
        ios.close();
    }

    /**
     * 保存流到目标文件
     *
     * @throws IOException
     */
    public static void saveFile(String data, String dest) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(dest));
        fos.write(data.getBytes("utf-8"));
        fos.flush();
        fos.close();
    }

    public static void pickAlbum(Activity act, int reqCode) {
        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openAlbumIntent.setType("image/*");
        openAlbumIntent.addCategory(Intent.CATEGORY_OPENABLE);
        act.startActivityForResult(openAlbumIntent, reqCode);
    }

    public static String onActivityResult(Context contxt, int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return null;
        }
        Uri uri = null;
        if (data == null) {
            return null;
        }
        Uri mImageCaptureUri = data.getData();
        String path = FileUtils.getRealPathFromURI(contxt, mImageCaptureUri); //from Gallery
        if (path == null)
            path = mImageCaptureUri.getPath(); //from File Manager
        return path;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        String filePath = null;
        if (android.os.Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context, contentUri)) {
            String wholeID = DocumentsContract.getDocumentId(contentUri);
            String id = wholeID.split(":")[1];
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column,
                    sel, new String[]{id}, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(contentUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            filePath = cursor.getString(column_index);
            cursor.close();
        }
        return filePath;
    }
}

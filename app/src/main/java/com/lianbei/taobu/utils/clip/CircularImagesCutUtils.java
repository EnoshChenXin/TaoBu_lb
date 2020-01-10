package com.lianbei.taobu.utils.clip;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lianbei.taobu.R;
import com.lianbei.taobu.global.GlobalConstant;
import com.lianbei.taobu.utils.DirectoryHelp;
import com.lianbei.taobu.utils.GLog;
import com.lianbei.taobu.utils.PhotoUtils;
import com.lianbei.taobu.utils.Utils;

import java.io.File;

import retrofit2.http.Url;

/**
 * 拍照 剪切图片 ----》 图片是圆形
 * Created by HASEE on 2017/3/28.
 */

public class CircularImagesCutUtils {
    private TextView photograph, albums;
    private LinearLayout cancel;
    private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private String path;// 图片全路径
    private String path1;// 图片裁剪后路径
    private View view;
    private Activity context;
    private Uri imageUri;
    private Uri cropImageUri;
    private File fileUri = new File ( Environment.getExternalStorageDirectory ( ).getPath ( ) + "/photo.jpg" );
    private File fileCropUri = new File ( Environment.getExternalStorageDirectory ( ).getPath ( ) + "/crop_photo.jpg" );
    private volatile static CircularImagesCutUtils singleton;

    public static CircularImagesCutUtils getSingleton() {
        if (singleton == null) {
            synchronized (CircularImagesCutUtils.class) {
                if (singleton == null) {
                    singleton = new CircularImagesCutUtils ( );
                }
            }
        }

        return singleton;
    }

    public void setshowPopupWindow(Activity context, View view) {
        this.context = context;
        this.view = view;
        layoutInflater = (LayoutInflater) context.getSystemService ( Context.LAYOUT_INFLATER_SERVICE );
        showPopupWindow ( );
    }

    private void showPopupWindow() {
        if (popWindow == null) {
            View view = layoutInflater.inflate ( R.layout.pop_select_photo, null );
            popWindow = new PopupWindow ( view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, true );
            initPop ( view );
        }
        // popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popWindow.setFocusable ( true );
        popWindow.setOutsideTouchable ( true );
        popWindow.setBackgroundDrawable ( new BitmapDrawable ( ) );
        // popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation ( view, Gravity.CENTER, 0, 0 );
    }

    public void initPop(View view) {
        photograph = (TextView) view.findViewById ( R.id.photograph );// 拍照
        albums = (TextView) view.findViewById ( R.id.albums );// 相册
        cancel = (LinearLayout) view.findViewById ( R.id.cancel_pic );// 取消
        photograph.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss ( );
                if (makePhotoOrPickAlbum != null) {
                    if (makePhotoOrPickAlbum.isMakePhoto ()) {
                        makePhotoAndSave ( );
                    }
                }

            }
        } );
        albums.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss ( );
                if(makePhotoOrPickAlbum  != null){
                    if(makePhotoOrPickAlbum.ispickAlbumPhoto ()){
                        pickAlbumPhoto ( );
                    }

                }

            }
        } );
        cancel.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View arg0) {
                popWindow.dismiss ( );

            }
        } );
    }


    public interface MakePhotoOrPickAlbum {
        boolean isMakePhoto();
        boolean ispickAlbumPhoto();

    }

    public MakePhotoOrPickAlbum makePhotoOrPickAlbum = null;

    public void setMakePhotoOrpickAlbumClick(MakePhotoOrPickAlbum m) {
        makePhotoOrPickAlbum = m;
    }


    /**
     * 拍照
     */
    private void makePhotoAndSave() {

      //  path = DirectoryHelp.genCameraPhoto ( context );

        if (hasSdcard ( )) {
            imageUri = Uri.fromFile ( fileUri );
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                //通过FileProvider创建一个content类型的Uri
                imageUri = FileProvider.getUriForFile ( context, "com.lianbei.taobu.fileProvider", fileUri );
        } else {
            Toast.makeText ( context, "设备没有SD卡！", Toast.LENGTH_SHORT ).show ( );
            Log.e ( "asd", "设备没有SD卡" );
            return;
        }
        Intent openCameraIntent = new Intent ( );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            openCameraIntent.addFlags ( Intent.FLAG_GRANT_READ_URI_PERMISSION ); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        openCameraIntent.setAction ( MediaStore.ACTION_IMAGE_CAPTURE );
        openCameraIntent.putExtra ( MediaStore.Images.Media.ORIENTATION,0 );
        openCameraIntent.putExtra ( MediaStore.EXTRA_OUTPUT, imageUri );
        context.startActivityForResult ( openCameraIntent, GlobalConstant.ACTIVITY_FLAG_PHOTO );

    }


    /**
     * 相册
     */
    private void pickAlbumPhoto() {
        //path = DirectoryHelp.genCameraPhoto ( context );
        Intent openAlbumIntent = new Intent ( Intent.ACTION_GET_CONTENT );
        openAlbumIntent.setType ( "image/*" );
        context.startActivityForResult ( openAlbumIntent, GlobalConstant.ACTIVITY_FLAG_ALBUM );

    }

    public String getRealPathFromURI(Uri contentUri) {
        String filePath = null;
        if (android.os.Build.VERSION.SDK_INT >= 19
                && DocumentsContract.isDocumentUri ( context, contentUri )) {
            String wholeID = DocumentsContract.getDocumentId ( contentUri );
            String id = wholeID.split ( ":" )[1];
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = context.getContentResolver ( ).query (
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
                    new String[]{id}, null );
            int columnIndex = cursor.getColumnIndex ( column[0] );
            if (cursor.moveToFirst ( )) {
                filePath = cursor.getString ( columnIndex );
            }
            cursor.close ( );
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver ( ).query ( contentUri,
                    projection, null, null, null );
            int column_index = cursor
                    .getColumnIndexOrThrow ( MediaStore.Images.Media.DATA );
            cursor.moveToFirst ( );
            filePath = cursor.getString ( column_index );
            cursor.close ( );
        }
        return filePath;
    }

    /**
     * 4.3或以下,选了图片之后,根据Uri来做处理,很多帖子都有了,我就不详细说了.
     * 主要是4.4,如果使用上面pick的原生方法来选图,返回的uri还是正常的,
     * 但如果用ACTION_GET_CONTENT的方法,返回的uri跟4.3是完全不一样的,
     * 4.3返回的是带文件路径的,
     * 而4.4返回的却是content://com.Android.providers.media.documents/document/image:3951这样的,
     * 没有路径,只有图片编号的uri.这就导致接下来无法根据图片路径来裁剪的步骤了.
     *
     * @param
     * @param uri
     * @return
     */
    public String getPath(Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri ( context, uri )) {
            // ExternalStorageProvider
            if (isExternalStorageDocument ( uri )) {
                final String docId = DocumentsContract.getDocumentId ( uri );
                final String[] split = docId.split ( ":" );
                final String type = split[0];

                if ("primary".equalsIgnoreCase ( type )) {
                    return Environment.getExternalStorageDirectory ( ) + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument ( uri )) {

                final String id = DocumentsContract.getDocumentId ( uri );
                final Uri contentUri = ContentUris.withAppendedId (
                        Uri.parse ( "content://downloads/public_downloads" ), Long.valueOf ( id ) );

                return getDataColumn ( context, contentUri, null, null );
            }
            // MediaProvider
            else if (isMediaDocument ( uri )) {
                final String docId = DocumentsContract.getDocumentId ( uri );
                final String[] split = docId.split ( ":" );
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals ( type )) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals ( type )) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals ( type )) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn ( context, contentUri, selection, selectionArgs );
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase ( uri.getScheme ( ) )) {

            // Return the remote address
            if (isGooglePhotosUri ( uri ))
                return uri.getLastPathSegment ( );

            return getDataColumn ( context, uri, null, null );
        }
        // File
        else if ("file".equalsIgnoreCase ( uri.getScheme ( ) )) {
            return uri.getPath ( );
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver ( ).query ( uri, projection, selection, selectionArgs,
                    null );
            if (cursor != null && cursor.moveToFirst ( )) {
                final int index = cursor.getColumnIndexOrThrow ( column );
                return cursor.getString ( index );
            }
        } finally {
            if (cursor != null)
                cursor.close ( );
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals ( uri.getAuthority ( ) );
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals ( uri.getAuthority ( ) );
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals ( uri.getAuthority ( ) );
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals ( uri.getAuthority ( ) );
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int output_X = 480, output_Y = 480;
        switch (requestCode) {
            case GlobalConstant.ACTIVITY_FLAG_ALBUM:// 相册
                Log.e ( "tag", "photozoom enter" );
                if (data == null) {
                    return;
                }
                if (hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(context, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        newUri = FileProvider.getUriForFile(context, "com.lianbei.taobu.fileProvider", new File(newUri.getPath()));
                    PhotoUtils.cropImageUri(context, newUri, cropImageUri, 0, 0, output_X, output_Y, GlobalConstant.IMAGE_COMPLETE );
                } else {
                    Toast.makeText(context, "设备没有SD卡!", Toast.LENGTH_SHORT).show();
                }

                break;
            case GlobalConstant.ACTIVITY_FLAG_PHOTO:// 拍照
                if (data != null) {
                    Log.e ( "tag", " callback  data " + data.getStringExtra ( "path" ) );
                }

                cropImageUri = Uri.fromFile ( fileCropUri );
                PhotoUtils.cropImageUri ( context, imageUri, cropImageUri, 0, 0, output_X, output_Y, GlobalConstant.IMAGE_COMPLETE );

               /* Intent intent2 = new Intent (context,
                        ClipActivity.class);
                GLog.bi(" callback path =" + path);
                intent2.putExtra("path", path);
                context.startActivityForResult(intent2, GlobalConstant.IMAGE_COMPLETE);*/
                break;
            case GlobalConstant.IMAGE_COMPLETE: //剪切结果回调
                Bitmap bitmap = PhotoUtils.getBitmapFromUri ( cropImageUri, context );
                if (bitmap != null) {
                    if (mListener != null) {
                        mListener.CularImageInterface ( cropImageUri );
                        mListener.CularImageInterface ( bitmap );
                    }
                }
                if( 0 == 1){
                    path1 = data.getStringExtra ( "path" );
                    Log.e ( "tag", "image_comp enter path1=" + path1 );
                    if (mListener != null) {
                        mListener.CularImageInterface ( cropImageUri );
                    }
                    if (Utils.checkNetWork ( )) {
                        Log.e ( "tag", "check network" );
                        //  uploadFile(path1);
                        Log.e ( "path---------:", path );
                        if (mListener != null) {
                            mListener.CularImageInterface ( cropImageUri );
                        }

                    } else {
                        Toast.makeText ( context, "网络已断开，请连接后再试", Toast.LENGTH_LONG )
                                .show ( );
                    }
                }
           break;
            default:
                break;
        }
    }

    Handler handler = new Handler ( ) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage ( msg );
            if (msg.what == 0) {

            }
        }
    };
    /**
     * 单击事件监听器
     */
    public CularImageInterface mListener = null;

    public void OnCularImageListener(CularImageInterface listener) {
        mListener = listener;
    }

    public interface CularImageInterface {
        void CularImageInterface(Uri uri);
        void CularImageInterface(Bitmap bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState ( );
        return state.equals ( Environment.MEDIA_MOUNTED );
    }
}

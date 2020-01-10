package com.lianbei.taobu.ShareInvitation;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.base.share.SharePopView;
import com.lianbei.taobu.base.share.ShareViewManager;
import com.lianbei.taobu.listener.RequestCompletion;
import com.lianbei.taobu.listener.ShareRequestCompletion;
import com.lianbei.taobu.utils.ViewGenerationImage;
import com.lianbei.taobu.views.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class ShareStepsActivity extends BaseActivity implements View.OnClickListener , EasyPermissions.PermissionCallbacks{
    private static final int REQUEST_CODE_SAVE_IMG = 10;
    private static final String TAG = "ShareStepsActivity";
    @BindView(R.id.ll_share)
    RelativeLayout ll_share;
    @BindView(R.id.step_bg_share)
    LinearLayout step_bg_share;

    @BindView(R.id.save_img)
    ImageView saveImg;

    @BindView(R.id.close_img)
    ImageView closeImg;

    @BindView(R.id.share_img)
    ImageView shareImg;

    InvitationViewManager invitationViewManager;
    Bitmap bitmap;
    @Override
    public int getContentViewId() {
        return R.layout.activity_share_steps;
    }

    @Override
    public void initViews() {

    }
    @Override
    public void initData() {
        invitationViewManager = new InvitationViewManager ( this);
    }

    ShareViewManager svm;

    //微信//QQ//微博分享
    private void showShare() {
        bitmap =  new ViewGenerationImage ().viewConversionBitmap ( step_bg_share );
        SharePopView shareView = new SharePopView ( this, 1 );
        shareView.showSharePop ( ll_share );
        svm= new ShareViewManager ( this,shareCompletionListener );
        svm.isWXShareURLSessionAndTimeLine ( false,true );
        svm.title ( this.getResources ().getString (R.string.share_common_title ) );//
        svm.description ( this.getResources ().getString ( R.string.share_daily_description )+"");
        svm.ResourceBitmap ( bitmap );
        svm.Resourceimgurl (R.mipmap.plan_preview_header_bg); //本地图片
        // svm.imgurl ( APIs.QQ_SHARE_LOGO );//网络图片
        //  svm.webpageUrl ( APIs.DOWN_SHARE_URL);
        shareView.shareViewManager ( svm );
    }

    private ShareRequestCompletion shareCompletionListener = new ShareRequestCompletion ( ) {
        @Override
        public void QQShareSuccess(Object value) {
            shareAfter();
        }

        @Override
        public void WXShareSuccess(Object value) {
            shareAfter();
        }

        @Override
        public void WBShareSuccess(Object... values) {
            shareAfter();
        }
    };


    /**
     * 分享成功之后调用该方法
     */
    private void shareAfter(){
        invitationViewManager.shareDailyShareAfter ( new RequestCompletion ( ) {
            @Override
            public void Success(Object value, String tag) {

            }

            @Override
            public void Fail(Object value) {

            }

            @Override
            public void Error(Object... values) {

            }
        } );
    }
    @OnClick({R.id.save_img,R.id.close_img,R.id.share_img})
    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.save_img:
                bitmap =  new ViewGenerationImage ().viewConversionBitmap ( step_bg_share );
                requestPermission();
                break;
            case R.id.close_img:
                finish ();
                break;
            case R.id.share_img:
                showShare();
                break;
        }

    }
    @Override
    public void initListener() {
    }

    @Override
    public void onLeftClick() {

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    /**
     * 请求读取sd卡的权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //读取sd卡的权限
            String[] mPermissionList = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (EasyPermissions.hasPermissions(this, mPermissionList)) {
                //已经同意过
                saveImage();
            } else {
                //未同意过,或者说是拒绝了，再次申请权限
                EasyPermissions.requestPermissions(
                        this,  //上下文
                        "保存图片需要读取sd卡的权限", //提示文言
                        REQUEST_CODE_SAVE_IMG, //请求码
                        mPermissionList //权限列表
                );
            }
        } else {
            saveImage();
        }
    }
    //保存图片
    private void saveImage() {
        boolean isSaveSuccess = ImageUtils.saveImageToGallery(this, bitmap);
        if (isSaveSuccess) {
            Toast.makeText(this, "保存图片成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "保存图片失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }
    //授权结果，分发下去
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        //跳转到onPermissionsGranted或者onPermissionsDenied去回调授权结果
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    //同意授权
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List <String> perms) {
        Log.i(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
        saveImage();
    }
    //拒绝授权
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List <String> perms) {
        Log.i(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //打开系统设置，手动授权
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //拒绝授权后，从系统设置了授权后，返回APP进行相应的操作
            Log.i(TAG, "onPermissionsDenied:------>自定义设置授权后返回APP");
            saveImage();
        }
    }
}

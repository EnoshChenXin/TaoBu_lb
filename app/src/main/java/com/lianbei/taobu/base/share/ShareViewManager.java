package com.lianbei.taobu.base.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.listener.ShareRequestCompletion;
import com.lianbei.taobu.listener.WXShareResuleInterface;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.utils.Validator;
import com.lianbei.taobu.utils.WXUtil;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.statistic.WBAgent;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.HashMap;
import java.util.Map;

public class ShareViewManager implements WbShareCallback {

    public static final int WXSceneSession = 0; //好友
    public static final int WXSceneTimeline = 1; //朋友圈
    public static final int WXSceneFavorite = 2;//收藏

    private IWXAPI mWxApi;
    private Tencent mTencent;
    private WbShareHandler shareHandler;
    private Activity mActivity;
    private int mScene  = 0;  //0:微信   1：朋友圈
    boolean URLSession = true;//true:分享网页   false:分享图片
    boolean URLTimeLine = true;//true:分享网页   false:分享图片
    private String imaurl;
    private String title;
    private String Content;
    private String webpageUrl;
    private String description;
    private String imgurl;
    private Bitmap resourceBitmap;
    private int resourceimgurl = -1;
    ShareRequestCompletion completion;
    enum WXScene{
        WXSceneSession,WXSceneTimeline,WXSceneFavorite
    }


    /**
     *
     * @param activity
     *
     */
  public ShareViewManager(Activity activity, ShareRequestCompletion completion){
      this.mActivity = activity;
      this.completion = completion;
      mTencent = Tencent.createInstance( APIs.QQShare_APP_ID,mActivity.getApplicationContext ());
      mWxApi = WXAPIFactory.createWXAPI(mActivity, APIs.APP_ID, false);
      // 将该app注册到微信
      mWxApi.registerApp( APIs.APP_ID);
      WbSdk.install(mActivity, new AuthInfo (mActivity, APIs.APP_KEY_WEIBO, APIs.REDIRECT_URL, APIs.SCOPE));
  }

    /**
     * 微信分享到好友和朋友圈类型
     * @param URLSession true:分享网页  false :分享图片
     * @param URLTimeLine true:分享网页  false :分享图片
     */
    //分享类型   true:分享页面链接   false:分享图片
    public void isWXShareURLSessionAndTimeLine(boolean URLSession,boolean URLTimeLine){
        this.URLSession =URLSession;
        this.URLTimeLine =URLTimeLine;
    }

    // WXSceneSession 分享好友   WXSceneTimeline朋友圈
    public void mScene(int mScene){
        this.mScene = mScene;
    }

    //页面URL
    public void  webpageUrl(String webpageUrl){
        this.webpageUrl = webpageUrl;
    }
   //分享内容标题
    public void title(String title){
      this.title = title;
    }
    //分享内容描述
    public void description(String description){
      this.description = description;
    }
    //分享图片链接
    public void imgurl(String imgurl){
      this.imgurl =imgurl;
    }
    //图片资源
    public void Resourceimgurl(int resourceimgurl){
      this.resourceimgurl = resourceimgurl;
    }
    //图片资源 直接传如图片
    public void ResourceBitmap(Bitmap resourceBitmap){
      this.resourceBitmap = resourceBitmap;
    }
    //配置耗时图片资源
    public ResourceBitmaponReqest resourceBitmaponReqest;

    public  interface  ResourceBitmaponReqest{
        public Bitmap bitmap();
    }

    public void setResourceBitmaponReqest(ResourceBitmaponReqest resourceBitmaponReqest){
        this.resourceBitmaponReqest = resourceBitmaponReqest;
    }



    /**
     * 分享到微信 好友/朋友圈
     */
  public void sendReq(){
      if(mScene ==WXSceneSession){  //好友
          if(URLSession){  //分享网页
              WXShareWebpage();
          }else{ //分享图片
              if(resourceBitmap !=null)
                WXSharePicture();
              else {
                  resourceBitmaponReqest.bitmap();

              }
          }
      }else{
          if(URLTimeLine){  //朋友圈
              WXShareWebpage();
          }else{ //分享图片
              WXSharePicture();
          }
      }
  }
    /**
     *
     * 分享到微信小程序
     */
    public void ShareMiniObject(){
        shareMiniProgram();
    }

    /**
     * 分享到QQ
     */
  public void sendReqtoQQ(){
      shareQQ();
  }
    /**
     * 分享到微博
     */
    public void  shareWeiBo(){
        shareWB();
    }

     //微信网页链接类型分享
    private void WXShareWebpage() {
        WXUtil.getImage (getShare_imgurl ( ), new WXUtil.HttpCallBackListener ( ) {
            @Override
            public void onFinish(Bitmap bitmap) {
                final Bitmap bitmap1=bitmap;
                mActivity.runOnUiThread ( new Runnable ( ) {
                    @Override
                    public void run() {
                        try {
                            WXWebpageObject webpage = new WXWebpageObject ();
                            webpage.webpageUrl = getwebpageUrl();
                            WXMediaMessage msg = new WXMediaMessage (webpage);
                            msg.title = getTitle();
                            msg.description =getDescription ();
                            msg.setThumbImage(bitmap);
                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("webpage");
                            req.message = msg;
                            //req.scene = sendtype==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
                            if(mScene == 0){
                                req.scene = SendMessageToWX.Req.WXSceneSession;
                            }else{
                                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                            }
                            mWxApi.sendReq(req);
                            ShareWx();
                        }catch (Exception e){
                        }
                    }
                } );
            }

            @Override
            public void onError(Exception e) {

            }
        } );
    }


    //微信图片类型分享
    private void WXSharePicture() {//R.drawable.share_img_friend_circle
        Bitmap bitmap = null;
        if(resourceBitmap != null){
             bitmap = resourceBitmap;
        }else if(resourceimgurl != -1){
             bitmap = BitmapFactory.decodeResource(mActivity.getResources(),resourceimgurl);
        }else{
             bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.icon3);
        }

        //初始化WXImageObject 和WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject (bitmap);
        WXMediaMessage msg = new WXMediaMessage ();
        msg.mediaObject = imgObj;
        //设置缩略图
        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(bitmap, 100, 146,  true);
        bitmap.recycle();
        msg.thumbData = WXUtil.bmpToByteArray(thumbBitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        if(mScene == 0){
            req.scene = SendMessageToWX.Req.WXSceneSession;;
        }else{
            req.scene = SendMessageToWX.Req.WXSceneTimeline;;
        }

        mWxApi.sendReq(req);
        ShareWx();
    }

    private void shareMiniProgram(){
        Bitmap bitmap = null;
       if(resourceimgurl != -1){
            bitmap = BitmapFactory.decodeResource(mActivity.getResources(),resourceimgurl);
        }else{
            bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.icon3);
        }


        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_b2d20c4a9dd7";     // 小程序原始id
        miniProgramObj.path = "/pages/media";            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage();
        msg.title = "今天几小步的努力，都会明天的你引以为傲";                    // 小程序消息title
        msg.description = "小程序消息Desc";               // 小程序消息desc
        msg.mediaObject =miniProgramObj;
        //设置缩略图
        //初始化WXImageObject 和WXMediaMessage 对象

        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(bitmap, 100, 200,  true);
        bitmap.recycle();
        msg.thumbData = WXUtil.bmpToByteArray(thumbBitmap, true); // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        mWxApi.sendReq(req);
    }


    //QQ分享
    private void shareQQ() {
        try{
            final Bundle params = new Bundle();
            params.putInt( QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
            params.putString(QQShare.SHARE_TO_QQ_TITLE, getTitle());//分享标题
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY,getDescription ());//要分享的内容摘要
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,getwebpageUrl());//内容地址
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,getShare_imgurl ()); //分享的图片URL
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "每日速报");//应用名称
            mTencent.shareToQQ(mActivity, params, new ShareUiListener ());
        }catch (Exception e){

        }
    }
    /**
     * QQ自定义监听器实现IUiListener，需要3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class ShareUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
          Toast.makeText ( mActivity, "QQ分享成功", Toast.LENGTH_SHORT ).show ();
          if(APIs.SHARE_TAG.equals ( "newsShare" )){
              newsShareSuccess();
          }else if(APIs.SHARE_TAG.equals ( "newsShare" ))

            Log.e ( "QQonComplete" ,response+"分享成功");

          if(completion != null){
              completion.QQShareSuccess ( response );
          }
        }
        @Override
        public void onError(UiError uiError) {
            Toast.makeText ( mActivity, "QQ分享失败", Toast.LENGTH_SHORT ).show ();
            Log.e ( "QQonError" ,uiError+"分享失败");
            //分享失败
        }
        @Override
        public void onCancel() {
            Toast.makeText ( mActivity, "QQ分享取消", Toast.LENGTH_SHORT ).show ();
            Log.e ( "QQonCancel" ,"分享取消");
            //分享取消
        }
    }


    //分享到微博
    private void shareWB() {
        initLog();
        //startActivity(new Intent(getActivity(), WBAuthActivity.class));
        shareHandler = new WbShareHandler (mActivity);
        shareHandler.registerApp();
        // sendMessage(true, true);
        shareWebPage();
    }
    private void shareWebPage() {
        try {
            WXUtil.getImage ( getShare_imgurl ( ), new WXUtil.HttpCallBackListener ( ) {
                @Override
                public void onFinish(Bitmap bitmap) {
                    mActivity.runOnUiThread ( new Runnable ( ) {
                        @Override
                        public void run() {
                            WebpageObject mediaObject = new WebpageObject();
                            mediaObject.identify = Utility.generateGUID();
                            mediaObject.title = getTitle ();
                            mediaObject.description = getDescription ();
                            // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
                            mediaObject.setThumbImage(bitmap);
                            mediaObject.actionUrl = getwebpageUrl ();
                            mediaObject.defaultText = getDescription ();
                            WeiboMultiMessage message = new WeiboMultiMessage();
                            message.mediaObject = mediaObject;
                            shareHandler.shareMessage(message, false);
                        }
                    } );

                }

                @Override
                public void onError(Exception e) {

                }
            } );
        }catch (Exception e){

        }

    }
    private void initLog() {
        WBAgent.setAppKey( APIs.APP_KEY_WEIBO);
        WBAgent.setChannel("weibo"); //这个是统计这个app 是从哪一个平台down下来的  百度手机助手
        WBAgent.openActivityDurationTrack(false);
        try {
            //设置发送时间间隔 需大于90s小于8小时
            WBAgent.setUploadInterval(91000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @Override
    public void onWbShareSuccess() {
        newsShareSuccess();
        Log.e ( "onWbShareSuccess","onWbShareSuccess" );
    }

    @Override
    public void onWbShareCancel() {
        Log.e ( "onWbShareCancel","onWbShareCancel" );
    }

    @Override
    public void onWbShareFail() {
        Log.e ( "onWbShareFail","onWbShareFail" );
    }
    //新闻分享成功
    private void newsShareSuccess() {
        if(UserInfo.UserInfoIsEmpty ( mActivity ))
            return;
        Map<String,String> params=new HashMap<> ();
        params.put("fo","N");
        new HttpUtil.Builder (APIs. SHARE_AFTER )
                .Tag (mActivity  )
                .Params ( params )
                .Success ( new Success ( ) {
                    @Override
                    public void Success(String model) {

                    }
                } ).Error ( new Error ( ) {
            @Override
            public void Error(Object... values) {

            }
        } ).post ();
    }
    private String  getShare_imgurl(){
        return TextUtils.isEmpty(imgurl)? APIs.QQ_SHARE_LOGO : imgurl;
    }
    private String getDescription(){
        return TextUtils.isEmpty(description)?title:description;

    }
    private String getTitle(){
        return TextUtils.isEmpty(title)?"每日速报":title;
    }

    private String getwebpageUrl(){
        return TextUtils.isEmpty(webpageUrl)?"":webpageUrl;

    }
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
         // 官方文档没这句代码, 但是很很很重要, 不然不会回调!
        Tencent.onActivityResultData(requestCode, resultCode, data, new ShareUiListener());
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE ||
                    resultCode == Constants.REQUEST_QZONE_SHARE ||
                    resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, new ShareUiListener());
            }
        }
    }
    //分享图片到朋友圈
    public void sharePictureFriendCircle(Activity activity,int id) {
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), id);
        //初始化WXImageObject 和WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject (bitmap);
        WXMediaMessage msg = new WXMediaMessage ();
        msg.mediaObject = imgObj;
        //设置缩略图
        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(bitmap, 100, 200,  true);
        bitmap.recycle();
        msg.thumbData = WXUtil.bmpToByteArray(thumbBitmap, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;;
        mWxApi.sendReq(req);
    }

   private void  ShareWx(){
        WXShareResuleInterface.getInstance ().wxExecuteMessage ( new ShareRequestCompletion ( ) {
            @Override
            public void QQShareSuccess(Object value) {

            }

            @Override
            public void WXShareSuccess(Object value) {
                if(completion != null){
                    completion.WXShareSuccess ( value );
                }
            }


            @Override
            public void WBShareSuccess(Object... values) {

            }
        } );
    }
}

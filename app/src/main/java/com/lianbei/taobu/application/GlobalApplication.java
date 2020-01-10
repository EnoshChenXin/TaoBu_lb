package com.lianbei.taobu.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.webkit.WebView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.ParamsInterceptor;
import com.lianbei.taobu.BuildConfig;
import com.lianbei.taobu.R;
import com.lianbei.taobu.api.APIs;
import com.lianbei.taobu.mine.model.UserInfo;
import com.lianbei.taobu.utils.DirectoryHelp;
import com.lianbei.taobu.utils.DisplayUtil;
import com.lianbei.taobu.utils.FileUtils;
import com.lianbei.taobu.utils.Utils;
import com.lianbei.taobu.utils.log.KLog;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

/**
 * Created by NEUNB on 2018/3/19.
 */

public class GlobalApplication extends Application {
    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static GlobalApplication globalApplication;
    public static Context sContext;
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler
    public DisplayImageOptions options;// 默认图片为头像
    public ImageLoader imageLoader;
    @Override
    public void onCreate() {
        super.onCreate ( );
        if (globalApplication == null) {
            globalApplication = this;
        }
        //对全局属性赋值
        KLog.init( BuildConfig.DEBUG);//初始化KLog
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();
        initDisplayOpinion();
        //创建文件/夹
        DirectoryHelp.initCachePath(this);
        //初始化Fresco --->com.facebook.fresco:fresco:0.12.0
        Fresco.initialize ( this );
        //腾讯bugly 测试阶段建议设置成true，发布时设置为false
        CrashReport.initCrashReport ( getApplicationContext ( ), "7813899225", false );
        //百度
        try {
            // API 19（4.4）, only for debug
            if (Build.VERSION.SDK_INT >= 19) {
                WebView.class.getMethod ( "setWebContentsDebuggingEnabled", boolean.class ).invoke ( null, true );
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }
         new HttpUtil.SingletonBuilder(this)
         .baseUrl( APIs.BASE_URL)//URL请求前缀地址。必传
         //                .versionApi("")//API版本，不传不可以追加接口版本号
         //                .addServerUrl("")//备份服务器ip地址，可多次调用传递
         //                .addCallFactory()//不传默认StringConverterFactory
         //                .addConverterFactory()//不传默认RxJavaCallAdapterFactory
         //                .client()//OkHttpClient,不传默认OkHttp3
         .paramsInterceptor(mParamsInterceptor)//不传不进行参数统一处理
         //.headersInterceptor(mHeadersInterceptor)//不传不进行headers统一处理
         .build();
        registToWX();
        initApp();
    }

    private void initApp() {
        // 创建应用文件夹
        FileUtils.createAllDirectory(this);
        // 申请16M的虚拟机内存
        Utils.setMinHeapSize(16 * 1024 * 1024);
// 初始化图片loader
        inituniversalimageloader();
    }


    private void inituniversalimageloader() {
        // 初始化图片缓存
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator ())
                .threadPoolSize(15)
                .tasksProcessingOrder( QueueProcessingType.LIFO);

        ImageLoader.getInstance().init(builder.build());
        options = new DisplayImageOptions.Builder()
                .showStubImage( R.mipmap.icon3)
                .showImageForEmptyUri( R.mipmap.icon3)
                .showImageOnFail( R.mipmap.icon3).cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig( Bitmap.Config.ARGB_8888)
                .build();
        imageLoader = ImageLoader.getInstance();

    }
    ParamsInterceptor mParamsInterceptor = new ParamsInterceptor ( ) {
        @Override
        public Map checkParams(Map params) {
           //  String userid = (String) SPUtils.get ("user_id", "" );
           // params.put ("user_id", UserInfo.getUserInfo (getContext ()).getUser_id ()+"");
           // params.put("token", Security.encrypt ( TimeUtils.getTime ())+"");
            return params;
        }
    };

    /**
     * 全程作用域
     *
     * @return
     */
    public static GlobalApplication getApplication() {
        return globalApplication;
    }

    /***
     * 注册微信
     */
    private void registToWX() {
        final IWXAPI msgApi = WXAPIFactory.createWXAPI(this,APIs.APP_ID, false);
     // 将该app注册到微信   AppID:步骤二申请到的AppID
        msgApi.registerApp(APIs.APP_ID);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext ( base );
        MultiDex.install ( this );
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources ( ).getDisplayMetrics ( );
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip ( getApplicationContext ( ), dm.widthPixels );
        DisplayUtil.screenHightDip = DisplayUtil.px2dip ( getApplicationContext ( ), dm.heightPixels );
    }
    /**
     * 重启当前应用
     */
    public static void restart() {
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        GlobalApplication.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        GlobalApplication.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        GlobalApplication.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        GlobalApplication.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        GlobalApplication.mHandler = mHandler;
    }

}

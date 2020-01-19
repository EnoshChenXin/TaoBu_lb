package com.lianbei.taobu.views.h5;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.lianbei.taobu.R;

import androidx.viewpager.widget.ViewPager;

/**
 * @author admin
 * 带进度条的WebView
 */
public class ProgressWebView extends WebView {

    public Context context ;
    public ProgressBar progressbar ;
    public OnWebCallBack onWebCallBack ;   //回调
    private View mErrorView;
    public ProgressWebView(Context context) {
        this( context , null ) ;
        init() ;
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this( context , attrs , android.R.attr.webTextViewStyle ) ;
        init() ;
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
        super( context , attrs , defStyle ) ;
        this.context = context ;
        init() ;
        setWebViewClient( new  MyWebViewClient() ) ;
        setWebChromeClient( new WebChromeClient() ) ;
    }

    /**
     * 设置ProgressBar
     */
    void init(){

        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setJavaScriptEnabled(true);

        getSettings().setDomStorageEnabled(true);
        // getSettings().setPluginsEnabled(true);
        requestFocus();

        //以下两句和硬件加速有关
        getSettings().setPluginState( WebSettings.PluginState.ON);
        getSettings().setAllowFileAccess(true);

        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setCacheMode( WebSettings.LOAD_NO_CACHE);

        //mWebView.loadUrl("http://www.cdmetro.cn/");
        // mWebView.setWebViewClient(new VideoFragment.TestWebViewClient ());


        getSettings().setBlockNetworkImage(false);//解决图片不显示
        progressbar = new ProgressBar( context , null , android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams( new LayoutParams(LayoutParams.MATCH_PARENT, 4 , 0, 0 ));
        addView( progressbar ) ;
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                progressbar.setVisibility( VISIBLE ) ;
                progressbar.setProgress(newProgress);
            }
            if( onWebCallBack != null ){  //获取标题
                onWebCallBack.onProgressChanged( view, newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }


        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if( onWebCallBack != null ){  //获取标题
                onWebCallBack.getTitle( title ) ;
            }
            Log.e ( "onReceivedTitle",title+"");
        }

    }

    /**
     * 不重写的话，会跳到手机浏览器中
     * @author admin
     */
    public class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) { // Handle the
//            hideErrorPage(view);
            //        showErrorPage(view);//显示错误页面
            if( onWebCallBack != null ){ //获得WebView的地址
                onWebCallBack.onReceivedError (view, errorCode,description,failingUrl );
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String utls = url;
            if(url.contains ("pinduoduo://com.xunmeng.pinduoduo")){
                view.goBack();
            }else{
                view.loadUrl(utls);
            }
            if( onWebCallBack != null ){ //获得WebView的地址
                onWebCallBack.shouldOverrideUrlLoading (view, utls );
            }
            return  true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if( onWebCallBack != null ){ //获得WebView的地址
                onWebCallBack.onPageFinished (view, url );
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if( onWebCallBack != null ){ //获得WebView的地址
                onWebCallBack.getUrl( url ) ;
                onWebCallBack.onPageStarted ( view,  url,  favicon);
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 设置WebView的回掉器
     * @param onWebCallBack
     */
    public void  setOnWebCallBack( OnWebCallBack onWebCallBack ){
        this.onWebCallBack = onWebCallBack ;
    }

    public  interface OnWebCallBack{
        /**
         * 获取标题
         * @param title
         */
        void getTitle(String title) ;

        /**
         * 获得WebView的地址
         * @param url
         */
        void getUrl(String url) ;

        void onPageFinished(WebView view, String url);

        void shouldOverrideUrlLoading(WebView view, String url);
        void onPageStarted (WebView view, String url, Bitmap favicon);
        void onProgressChanged(WebView view, int newProgress);
        void onReceivedError(WebView view, int errorCode,String description, String failingUrl);
    }

    boolean mIsErrorPage;
    protected void showErrorPage(WebView view) {
        LinearLayout webParentView = (LinearLayout)this.getParent();
        initErrorPage(view);//初始化自定义页面
        while (webParentView.getChildCount() > 1) {
            webParentView.removeViewAt(0);
        }
        @SuppressWarnings("deprecation")
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( ViewPager.LayoutParams.FILL_PARENT, ViewPager.LayoutParams.FILL_PARENT);
        webParentView.addView(mErrorView, 0, lp);
        mIsErrorPage = true;
    }
    /****
     * 把系统自身请求失败时的网页隐藏
     */
    protected void hideErrorPage(WebView view) {
        LinearLayout webParentView = (LinearLayout)view.getParent();
        mIsErrorPage = false;
        while (webParentView.getChildCount() > 1) {
            webParentView.removeViewAt(0);
        }
    }
    /***
     * 显示加载失败时自定义的网页
     */
    protected void initErrorPage(WebView view) {
        if (mErrorView == null) {
            mErrorView = View.inflate(view.getContext (), R.layout.activity_error, null);
            RelativeLayout layout = (RelativeLayout)mErrorView.findViewById(R.id.online_error_btn_retry);
            layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    view.reload();
                }
            });
            mErrorView.setOnClickListener(null);
        }
    }
}
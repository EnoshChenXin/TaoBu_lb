package com.lianbei.taobu.views.h5;


import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.lianbei.httplbrary.HttpUtil;
import com.lianbei.httplbrary.interfaces.Error;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.taobu.R;
import com.lianbei.taobu.base.BaseActivity;
import com.lianbei.taobu.mine.model.WXUserInfo;
import com.lianbei.taobu.utils.ToastUtil;
import com.lianbei.taobu.utils.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class H5PublicActivity extends BaseActivity {
    @BindView ( R.id.web )
    ProgressWebView webView;
    @Autowired(name = "type")
    String type;
    @Autowired(name = "api")
    String api;
    @Autowired(name = "title")
    String title;
    @Autowired(name = "url")
    String url;
    @Autowired(name = "data")
    String data;

    @Override
    public void initBeforeView() {
        ARouter.getInstance().inject(this);
        super.initBeforeView ( );
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_h5_public;
    }

    @Override
    public void initViews() {
        createNavigationView ( R.id.navigation_view );
        url =this.getIntent ().getStringExtra ( "url" );
        title =this.getIntent ().getStringExtra ( "title" );

    }

    @Override
    public void initData() {
        if (Validator.isStrNotEmpty (title)) {
            navigationView.setTitleText (title);
        }else{
            navigationView.setTitleText ("");
        }
        JSuserinfo jSuserinfo = new JSuserinfo ();
        jSuserinfo.setUserID ( WXUserInfo.getWXUserInfo ( this ).getOpenid ()) ;
        webView.addJavascriptInterface (jSuserinfo,"jsUserinfo");
        if(url != null){
            if (url.contains ( "http" ) || url.contains ( "https" )) {
            } else {
                url = "http://" + url;
            }
            loadUrl (url);
        }else if(Validator.isStrNotEmpty ( data )){
            loadDataWithBaseURL(data);
        }else if(Validator.isStrNotEmpty ( type ) && Validator.isStrNotEmpty ( api )){
            loadData (type);
        }else{
            ToastUtil.showShort ( this,"参数异常" );
        }

    }

    @Override
    public void initListener() {
        webView.setOnWebCallBack(new ProgressWebView.OnWebCallBack() {
            @Override
            public void getTitle(String title) {
                navigationView.setTitleText (title);
            }

            @Override
            public void getUrl(String url) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void shouldOverrideUrlLoading(WebView view, String url) {


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }
        });

    }

    @Override
    public void onLeftClick() {
        finish ();

    }

    @Override
    public void onRightClick() {

    }

    @Override
    public void onRefreshClick() {

    }

    /**
     * 加载html
     * @param data
     */
    private void loadDataWithBaseURL(String data){
        webView.loadDataWithBaseURL(null, data, "text/html" , "utf-8", null);
    }

    /**
     * 加载url
     * @param url
     */
    private void loadUrl(String url) {
        webView.loadUrl(url.trim());
    }

    /**
     *获取webVIEW Html数据
     * @param type
     */
    private void loadData(String type){
        if(Validator.isStrNotEmpty (type)){
            Map<String ,String> map = new HashMap<> (  );
            map.put("type",type);
            new HttpUtil.Builder (api)
                    .Tag ( H5PublicActivity.this )
                    .Params ( map )
                    .Success ( new Success ( ) {
                        @Override
                        public void Success(String model) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        JSONObject jsonObject=new JSONObject(model);
                                        String return_code = jsonObject.getString("return_code");
                                        String data = jsonObject.getString("data");
                                        if (return_code.equals("SUCCESS")){
                                            loadDataWithBaseURL(data);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace ( );
                                    }
                                }
                            });
                        }
                    } ).Error ( new Error ( ) {
                @Override
                public void Error(Object... values) {

                }
            } ).post ();
        }
    }
}

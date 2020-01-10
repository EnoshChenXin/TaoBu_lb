package com.lianbei.httplbrary;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lianbei.commomview.loadprogress.LoadProgress;
import com.lianbei.httplbrary.converter.StringConverterFactory;
import com.lianbei.httplbrary.interfaces.Fail;
import com.lianbei.httplbrary.interfaces.HeadersInterceptor;
import com.lianbei.httplbrary.interfaces.ParamsInterceptor;
import com.lianbei.httplbrary.interfaces.Progress;
import com.lianbei.httplbrary.interfaces.Success;
import com.lianbei.httplbrary.utils.ApiRequestParamInterface;
import com.lianbei.httplbrary.utils.NetworkAvailableUtils;
import com.lianbei.httplbrary.utils.OkhttpProvidede;
import com.lianbei.httplbrary.utils.WriteFileUtil;
import com.lianbei.httplbrary.interfaces.Error;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by chen_yxin Modify on 2017/3/2.
 */
public class HttpUtil {
    private static volatile HttpUtil mInstance;
    private static volatile RetrofitHttpService mService;
    private static String mVersionApi;
    private ParamsInterceptor mParamsInterceptor;
    private HeadersInterceptor mHeadersInterceptor;
    // private static ApiRequestParamInterface apiRequestParamInterface = null;
    private static String requestParam;
    static boolean ischeckparam = true; //是否启用统一处理公共参数


    /**
     * public static void setCallFun(ApiRequestParamInterface apiRequestParamInterface) {
     * HttpUtil.apiRequestParamInterface = apiRequestParamInterface;
     * }
     * <p>
     * public static String getRequestParam() {
     * return apiRequestParamInterface.RequestParam();
     * }
     **/
    //构造函数私有，不允许外部调用
    private HttpUtil(RetrofitHttpService mService, String mVersionApi, ParamsInterceptor mParamsInterceptor, HeadersInterceptor mHeadersInterceptor) {
        this.mService = mService;
        this.mVersionApi = mVersionApi;
        this.mParamsInterceptor = mParamsInterceptor;
        this.mHeadersInterceptor = mHeadersInterceptor;
    }

    public static RetrofitHttpService getService() {
        if (mInstance == null) {
            throw new NullPointerException("HttpUtil has not be initialized");
        }
        return mService;
    }

    public static class SingletonBuilder {
        private Context appliactionContext;
        private String baseUrl;
        private List<String> servers = new ArrayList<>();
        private String versionApi;
        private ParamsInterceptor paramsInterceptor;
        private HeadersInterceptor headersInterceptor;
        private List<Converter.Factory> converterFactories = new ArrayList<>();
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
        OkHttpClient client;

        public SingletonBuilder(Context context) {
            try {//防止传入的是activity的上下文
                Activity activity = (Activity) context;
                appliactionContext = context.getApplicationContext();
            } catch (Exception e) {
                e.printStackTrace();
                appliactionContext = context;
            }
        }

        public SingletonBuilder client(OkHttpClient client) {
            this.client = client;
            return this;
        }

        public SingletonBuilder versionApi(String versionApi) {
            this.versionApi = versionApi;
            return this;
        }

        public SingletonBuilder paramsInterceptor(ParamsInterceptor interceptor) {
            this.paramsInterceptor = interceptor;
            return this;
        }

        public SingletonBuilder headersInterceptor(HeadersInterceptor headersInterceptor) {
            this.headersInterceptor = headersInterceptor;
            return this;
        }

        public SingletonBuilder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public SingletonBuilder addServerUrl(String ipUrl) {
            this.servers.add(ipUrl);
            return this;
        }

        public SingletonBuilder serverUrls(List<String> servers) {
            this.servers = servers;
            return this;
        }

        public SingletonBuilder addConverterFactory(Converter.Factory factory) {
            this.converterFactories.add(factory);
            return this;
        }

        public SingletonBuilder addCallFactory(CallAdapter.Factory factory) {
            this.adapterFactories.add(factory);
            return this;
        }

        public HttpUtil build() {
            if (checkNULL(this.baseUrl)) {
                throw new NullPointerException("BASE_URL can not be null");
            }
            if (converterFactories.size() == 0) {
                converterFactories.add( StringConverterFactory.create());
            }
            if (adapterFactories.size() == 0) {
                adapterFactories.add(RxJavaCallAdapterFactory.create());
            }
            if (client == null) {
                client = OkhttpProvidede.okHttpClient(appliactionContext, baseUrl, servers);
            }
            Retrofit.Builder builder = new Retrofit.Builder();

            for (Converter.Factory converterFactory : converterFactories) {
                builder.addConverterFactory(converterFactory);
            }
            for (CallAdapter.Factory adapterFactory : adapterFactories) {
                builder.addCallAdapterFactory(adapterFactory);
            }
            Retrofit retrofit = builder
                    .baseUrl(baseUrl + "/")
                    .client(client).build();
            RetrofitHttpService retrofitHttpService =
                    retrofit.create(RetrofitHttpService.class);
            mInstance = new HttpUtil(retrofitHttpService, versionApi, paramsInterceptor, headersInterceptor);
            return mInstance;
        }
    }


    public static String V(String url) {
        if (checkNULL(mVersionApi)) {
            throw new NullPointerException("can not add VersionApi ,because of VersionApi is null");
        }
        if (!url.contains(mVersionApi)) {
            return mVersionApi + url;
        }
        return url;
    }


    public static Map<String, String> checkParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        if (mInstance.mParamsInterceptor != null && ischeckparam) {
            params = mInstance.mParamsInterceptor.checkParams(params);
        }
        //retrofit的params的值不能为null，此处做下校验，防止出错
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() == null) {
                params.put(entry.getKey(), "");
            }
        }
        return params;
    }

    public static Map<String, String> checkHeaders(Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        if (mInstance.mHeadersInterceptor != null) {
            headers = mInstance.mHeadersInterceptor.checkHeaders(headers);
        }
        //retrofit的headers的值不能为null，此处做下校验，防止出错
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if (entry.getValue() == null) {
                headers.put(entry.getKey(), "");
            }
        }
        return headers;
    }

    // 判断是否NULL
    public static boolean checkNULL(String str) {
        return str == null || "null".equals(str) || "".equals(str);

    }

    // 判断是否NULL
    public static void Error(Context context, String msg) {
        if (checkNULL(msg)) {
            msg = "未知异常";
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String message(String mes) {
        if (checkNULL(mes)) {
            mes = "似乎已断开与互联网连接";
        }

        if (mes.equals("timeout") || mes.equals("SSL handshake timed out")) {
            return "网络请求超时";
        } else {
            return mes;
        }

    }

    final static Map<String, Call> CALL_MAP = new HashMap<>();

    /*
     *添加某个请求
     *@author Administrator
     *@date 2016/10/12 11:00
     */
    private static synchronized void putCall(Object tag, String url, Call call) {
        if (tag == null)
            return;
        synchronized (CALL_MAP) {
            CALL_MAP.put(tag.toString() + url, call);
        }
    }

    /*
     *取消某个界面都所有请求，或者是取消某个tag的所有请求
     * 如果要取消某个tag单独请求，tag需要转入tag+url
     *@author Administrator
     *@date 2016/10/12 10:57
     */
    public static synchronized void cancel(Object tag) {
        if (tag == null)
            return;
        List<String> list = new ArrayList<>();
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.startsWith(tag.toString())) {
                    CALL_MAP.get(key).cancel();
                    list.add(key);
                }
            }
        }
        for (String s : list) {
            removeCall(s);
        }

    }

    /*
     *移除某个请求
     *@author Administrator
     *@date 2016/10/12 10:58
     */
    private static synchronized void removeCall(String url) {
        synchronized (CALL_MAP) {
            for (String key : CALL_MAP.keySet()) {
                if (key.contains(url)) {
                    url = key;
                    break;
                }
            }
            CALL_MAP.remove(url);
        }
    }

    public static class Builder {
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        String url;
        String path;
        Error mErrorCallBack;
        Success mSuccessCallBack;
        Fail mFailCallBack;
        Progress mProgressCallBack;
        boolean addVersion = false;
        Object tag;
        File file;//上传文件
        Context context;
        String fileKey;
        boolean isShowLoadProgess = false;//是否展示请求加载动画，true 是 ，false 否
        boolean isNotificationRsult = false;
        StringBuffer resultMsg = new StringBuffer(); //网络请求成功或者失败信息


        public Builder ResultMsg(StringBuffer resultMsg) {
            this.resultMsg = resultMsg;
            return this;
        }

        public Builder isShowLoadProgess(boolean isShowLoadProgess, boolean isNotificationRsult) {
            this.isShowLoadProgess = isShowLoadProgess;
            this.isNotificationRsult = isNotificationRsult;
            return this;
        }


        public Builder File(String fileKey, File file) {
            this.file = file;
            this.fileKey = fileKey;
            return this;
        }

        public Builder CacheTime(String time) {
            headers.put("Cache-Time", time);
            return this;
        }

        public Builder Url(String url) {
            this.url = url;
            return this;
        }


        public Builder SavePath(String path) {
            this.path = path;
            return this;
        }

        public Builder Tag(Context tag) {
            this.context = tag;
            this.tag = (Object) tag;
            return this;
        }

        public Builder Params(Map<String, String> params) {
            if (ApiRequestParamManager.getApiRequestparams() != null) {
                params.putAll(ApiRequestParamManager.getApiRequestparams());
            }
            //加入请求公共参数
            this.params.putAll(params);
            return this;
        }
        public Builder Params2(Map<String, String> params) {
            if (ApiRequestParamManager.getApiRequestparams() != null) {
                params.putAll(ApiRequestParamManager.getApiRequestparams());
            }
            //加入请求公共参数
            //this.params.putAll(params);
            return this;
        }

        public Builder Params(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        public Builder Headers(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public Builder Headers(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder Success(Success success) {
            this.mSuccessCallBack = success;
            return this;
        }

        public Builder Fail(Fail fail) {
            this.mFailCallBack = fail;
            return this;
        }

        public Builder Progress(Progress progress) {
            this.mProgressCallBack = progress;
            return this;
        }

        public Builder Version() {
            this.addVersion = true;
            return this;
        }

        public Builder Error(Error error) {
            this.mErrorCallBack = error;
            return this;
        }

        public Builder() {
            this.setParams();
        }

        public Builder(String url) {
            this.setParams(url);
        }


        private void setParams() {
            this.setParams(null);
        }

        private void setParams(String url) {
            if (mInstance == null) {
                throw new NullPointerException("HttpUtil has not be initialized");
            }
            this.url = url;
            this.params = new HashMap<>();
            this.mErrorCallBack = c -> {
            };
            this.mSuccessCallBack = c -> {
            };
            this.mProgressCallBack = c -> {
            };
            this.mFailCallBack = c -> {
            };
        }

        private String checkUrl(String url) {
            if (checkNULL(url)) {
                throw new NullPointerException("absolute url can not be empty");
            }
            if (addVersion) {
                url = mInstance.V(url);
            }
            return url;
        }

        String result;

        public void get() {
            startLoadProgressDialog( LoadProgress.DialogStatus.lOAD, "加载数据");
            Call call = mService.get(checkHeaders(headers), checkUrl(this.url), checkParams(params));
            putCall(tag, url, call);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        Gson gson = new GsonBuilder().create();
                        result = response.body().toString();
                        ApiResultModel apiResultModel = gson.fromJson(result, ApiResultModel.class);
                        Log.e("公共数据模型", apiResultModel.toString());
                        if (ApiResultManager.apiResult(apiResultModel, result, (ApiRequestParamInterface) tag)) {
                            // if (ApiResultManager.apiResult(apiResultModel)){
                            if (resultMsg.toString().length() == 0)
                                resultMsg.append("完成");
                            stopLoadProgressDialog(LoadProgress.DialogStatus.SUCCESS);
                            // mSuccessCallBack.Success(result);
                        } else {
                            if (resultMsg.toString().length() == 0)
                                resultMsg.append("失败");
                            mFailCallBack.Fail(result);
                            stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                        }
                    } else {
                        if (resultMsg.toString().length() == 0)
                            resultMsg.append("失败");
                        mErrorCallBack.Error(response.code(), message(response.message()), null);
                        Log.e("Error***", message(response.message()));
                        stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                    }
                    if (tag != null)
                        removeCall(url);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    resultMsg.append("失败");
                    mErrorCallBack.Error(200, message(t.getMessage()), t);
                    if (tag != null)
                        removeCall(url);
                    ErrorShowToast();
                    Log.e("onFailure***", message(t.getMessage()));
                    stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                }
            });

        }

        public void post() {
            startLoadProgressDialog(LoadProgress.DialogStatus.lOAD, "加载数据");
            Call call = mService.post(checkHeaders(headers), checkUrl(this.url), checkParams(params));
            putCall(tag, url, call);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        Gson gson = new GsonBuilder().create();
                        result = response.body().toString();
                        ApiResultModel apiResultModel = gson.fromJson(result, ApiResultModel.class);
                        if (ApiResultManager.apiResult(apiResultModel, result, (ApiRequestParamInterface) tag)) {
                            if (resultMsg.toString().length() == 0)
                                resultMsg.append("完成");
                            stopLoadProgressDialog(LoadProgress.DialogStatus.SUCCESS);
                           // mSuccessCallBack.Success(result);
                        } else {
                            if (resultMsg.toString().length() == 0)
                                resultMsg.append("失败");
                                mFailCallBack.Fail(result);
                            stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                        }
                    } else {
                        if (resultMsg.toString().length() == 0)
                            resultMsg.append("失败");
                        mErrorCallBack.Error(response.code(), message(response.message()), null);
                        Log.e("Error***", message(response.message()));
                        stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                    }
                    if (tag != null)
                        removeCall(url);
                }

                @Override
                    public void onFailure(Call<String> call, Throwable t) {
                    resultMsg.append("失败");
                    mErrorCallBack.Error(200, message(t.getMessage()), t);
                    ErrorShowToast();
                    Log.e("onFailure***", message(t.getMessage()));
                    stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                    if (tag != null)
                        removeCall(url);
                }
            });
        }

        public Observable<String> Obget() {
            this.url = checkUrl(this.url);
            this.params = checkParams(this.params);
            return mService.Obget(checkHeaders(headers), url, checkParams(params));
        }


        public Observable<String> Obpost() {
            this.url = checkUrl(this.url);
            this.params = checkParams(this.params);
            return mService.Obpost(checkHeaders(headers), url, checkParams(params));
        }

        public Observable<ResponseBody> Obdownload() {
            this.url = checkUrl(this.url);
            this.params = checkParams(this.params);
            this.headers.put( Constant.DOWNLOAD, Constant.DOWNLOAD);
            this.headers.put(Constant.DOWNLOAD_URL, this.url);
            return mService.Obdownload(checkHeaders(headers), url, checkParams(params));
        }

        //下载
        public void download() {
            this.url = checkUrl(this.url);
            this.params = checkParams(this.params);
            this.headers.put(Constant.DOWNLOAD, Constant.DOWNLOAD);
            this.headers.put(Constant.DOWNLOAD_URL, this.url);
            Call call = mService.download(checkHeaders(headers), url, checkParams(params));
            putCall(tag, url, call);
            Observable<ResponseBody> observable = Observable.create(subscriber -> {
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                subscriber.onNext(response.body());
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                mErrorCallBack.Error(200, message(t.getMessage()), t);
                            }
                        });
                    }
            );
            observable.observeOn(Schedulers.io())
                    .subscribe(body -> WriteFileUtil.writeFile(body, path, mProgressCallBack, mSuccessCallBack, mErrorCallBack), t -> {
                                mErrorCallBack.Error(t);
                            }
                    );
        }

        //文件上传
        public void upload() {
            startLoadProgressDialog(LoadProgress.DialogStatus.lOAD, "加载数据");
            //构建要上传的文件  必須參數  .Params(map).ImageKey("image") .File(uploadfile)  .Tag(this)
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("application/otcet-stream"), file);
            MultipartBody.Part mbody =
                    MultipartBody.Part.createFormData(fileKey, file.getName(), requestFile);
            String descriptionString = "This is a description";
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), descriptionString);
            Call<ResponseBody> call = mService.upload(checkHeaders(headers), checkUrl(this.url), checkParams(params), description, mbody);
            putCall(tag, url, call);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        try {
                            result = response.body().string();
                            Gson gson = new GsonBuilder().create();
                            //String result = response.body().toString();
                            ApiResultModel apiResultModel = gson.fromJson(result, ApiResultModel.class);
                            Log.e("公共数据模型", apiResultModel.toString());
                            if (ApiResultManager.apiResult(apiResultModel, result, (ApiRequestParamInterface) tag)) {
                                // if (ApiResultManager.apiResult(apiResultModel)){
                                if (resultMsg.toString().length() == 0)
                                    resultMsg.append("完成");
                                stopLoadProgressDialog(LoadProgress.DialogStatus.SUCCESS);
                            } else {
                                if (resultMsg.toString().length() == 0)
                                    resultMsg.append("失败");
                                mFailCallBack.Fail(result);
                                stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            mFailCallBack.Fail(result);
                            if (resultMsg.toString().length() == 0)
                                resultMsg.append("失败");
                            stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                        }
                    } else {
                        mErrorCallBack.Error(response.code(), message(response.message()), null);
                        if (resultMsg.toString().length() == 0)
                            resultMsg.append("失败");
                        stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                    }
                    if (tag != null)
                        removeCall(url);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mErrorCallBack.Error(200, message(t.getMessage()), t);
                    if (tag != null)
                        removeCall(url);
                    if (resultMsg.toString().length() == 0)
                        resultMsg.append("失败");
                    stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                }
            });
        }

        /**
         * 普通自定义Base请求
         */

        public void getRequest(){
            startLoadProgressDialog( LoadProgress.DialogStatus.lOAD, "加载数据");
            Call call = mService.get(checkHeaders(headers), checkUrl(this.url), checkParams(params));
            putCall(tag, url, call);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {
                        result = response.body().toString();
                        stopLoadProgressDialog(LoadProgress.DialogStatus.SUCCESS);
                    } else {
                        stopLoadProgressDialog(LoadProgress.DialogStatus.FAIL);
                        mErrorCallBack.Error(response.code(), message(response.message()), null);
                    }
                    if (tag != null)
                        removeCall(url);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    mErrorCallBack.Error(200, message(t.getMessage()), t);
                    if (tag != null)
                        removeCall(url);
                }
            });
        }

        /**
         * 判断网络是否正常  true   异常    false  正常
         *
         * @return
         */
        private boolean NetworkError() {
            if (!NetworkAvailableUtils.isWifiEnabled(context)) {
                Toast.makeText(context, NetworkAvailableUtils.GET_DATA_ERR, Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        private void ErrorShowToast() {
            Toast.makeText(context, NetworkAvailableUtils.NETWORK_TIMEOUT, Toast.LENGTH_SHORT).show();
        }

        /**
         * 开始请求数据
         *
         * @param msg 请求描述
         */
        private void startLoadProgressDialog(LoadProgress.DialogStatus status, String msg) {
            if (isShowLoadProgess) {
                LoadProgress.getInstance().startProgressDialog(status, msg, (Activity) context);
            }
        }

        /**
         * 请求结果,
         *
         * @param status
         */
        private void stopLoadProgressDialog(LoadProgress.DialogStatus status) {
            if (isShowLoadProgess) {
                LoadProgress.getInstance().stopProgressDialog(0);
            }
            if (status == LoadProgress.DialogStatus.SUCCESS) {//请求结果成功后弹出提醒框，通知页面
                if (isNotificationRsult) {
                    String msg = ApiResultManager.toastMsg(resultMsg);
                    startLoadProgressDialog(status, msg);
                    //  android消息处理
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //execute the task
                            LoadProgress.getInstance().stopProgressDialog(1);
                            mSuccessCallBack.Success(result);
                        }
                    }, 500);
                } else {
                    mSuccessCallBack.Success(result);
                }
            }else{

            }
        }

    }


}

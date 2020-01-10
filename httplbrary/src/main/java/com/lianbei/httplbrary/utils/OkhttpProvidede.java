package com.lianbei.httplbrary.utils;

import android.content.Context;

import com.lianbei.httplbrary.BuildConfig;
import com.lianbei.httplbrary.cacahe.CacheProvide;
import com.lianbei.httplbrary.interceptor.CacheInterceptor;
import com.lianbei.httplbrary.interceptor.DownLoadInterceptor;
import com.lianbei.httplbrary.interceptor.HttpLoggingInterceptor;
import com.lianbei.httplbrary.interceptor.RetryAndChangeIpInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 耿 on 2016/8/27.
 */
public class OkhttpProvidede {
    static OkHttpClient okHttpClient;

    public static OkHttpClient okHttpClient(final Context context, String BASE_URL, List<String> SERVERS) {
        if (okHttpClient == null) {
            synchronized (OkhttpProvidede.class) {
                if (okHttpClient == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addInterceptor(new DownLoadInterceptor (BASE_URL))
                            .addInterceptor(new RetryAndChangeIpInterceptor (BASE_URL, SERVERS))
                            .addNetworkInterceptor(new CacheInterceptor ())
                            .cache(new CacheProvide (context).provideCache())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(2, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .build();
                    if (BuildConfig.DEBUG) {//printf logs while  debug
                        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                        client = client.newBuilder().addInterceptor(logging).build();
                    }
                    okHttpClient = client;
                }

            }

        }
        return okHttpClient;

    }
}

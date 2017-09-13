package com.example.retrofitcache.http.get;

import android.content.Context;

import com.example.retrofitcache.MyApplication;
import com.example.retrofitcache.constants.HttpConstants;
import com.example.retrofitcache.http.HttpService;
import com.example.retrofitcache.http.RequestParams;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/2/20.
 */

public class HttpRequestsForCache {

    private static HttpRequestsForCache instance;

    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 5000;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 5000;
    //写入时长，单位：毫秒
    public static final int WRITE_TIME_OUT = 5000;

    //短缓存0分钟
    public static final int CACHE_AGE_SHORT = 0;
    //长缓存有效期为180
    public static final int CACHE_STALE_LONG = 60 * 3;

    private static Retrofit mRetrofit;
    private static HttpService mHttpService;

    // 是否使用缓存 默认关闭
    private static boolean mUseCache = false;

    public static HttpRequestsForCache getinstance() {
        if (instance == null) {
            synchronized (HttpRequestsForCache.class) {
                if (instance == null) {
                    instance = new HttpRequestsForCache();
                }
            }
        }
        return instance;
    }

    public static RequestParams createRequestParams() {
        //请求参数
        RequestParams localRequestParams = new RequestParams();
//        localRequestParams.put("t", (System.currentTimeMillis() + "").substring(0, 10));
        return localRequestParams;
    }

    public static RequestParams createRequestParams(Context context) {
        //请求参数
        RequestParams localRequestParams = new RequestParams();
//        localRequestParams.put("t", (System.currentTimeMillis() + "").substring(0, 10));
        return localRequestParams;
    }

    private HttpRequestsForCache() {

        File httpCacheDirectory = new File(MyApplication.context.getCacheDir(), "HttpCache8");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
                .cache(cache)
                .addInterceptor(new InterceptorNoNet())
                .addNetworkInterceptor(new InterceptorOnNet())
                .addInterceptor(logInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(HttpConstants.BASE_URL)
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为String的支持
//                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        mHttpService = mRetrofit.create(HttpService.class);
    }

    public HttpService getHttpService() {
        return mHttpService;
    }

}

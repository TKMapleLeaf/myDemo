package com.example.myapplicationdemo.http;

import android.content.Context;
import android.util.Log;

import com.example.myapplicationdemo.MyApplication;
import com.example.myapplicationdemo.utils.NetWorkStateStateUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.myapplicationdemo.HttpConstants.BASE_URL;

/**
 * Created by Administrator on 2017/2/20.
 */

public class HttpRequests {

    private static HttpRequests instance;

    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 5000;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 5000;
    //短缓存1分钟
    public static final int CACHE_AGE_SHORT = 60;
    //长缓存有效期为1天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24;

    private final Retrofit mRetrofit;
    private final HttpService mHttpService;

    public static HttpRequests getinstance() {
        if (instance == null) {
            synchronized (HttpRequests.class) {
                if (instance == null) {
                    instance = new HttpRequests();
                }
            }
        }
        return instance;
    }

    public static HttpRequests getinstance(Context context) {
        if (instance == null) {
            synchronized (HttpRequests.class) {
                if (instance == null) {
                    instance = new HttpRequests(context);
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

    private HttpRequests() {
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
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

    private HttpRequests(Context context) {

//        RetrofitCacheInterceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new RetrofitCacheInterceptor(context);

        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //cache url
        File httpCacheDirectory = new File(MyApplication.context.getCacheDir(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
//                .addInterceptor(logInterceptor)
                .addNetworkInterceptor(logInterceptor)
                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mHttpService = mRetrofit.create(HttpService.class);
    }

    // 响应头拦截器，用来配置缓存策略
    private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!NetWorkStateStateUtils.isNetWorkConnected(MyApplication.context)) {
                Log.e("Interceptor", "request: " + request.toString());
                //没网时只使用缓存
                //自定义请求头，可以在响应头对请求头的header进行拦截，配置不同的缓存策略
                request = request.newBuilder()
                        .header("head-request", request.toString())
                        .cacheControl(CacheControl.FORCE_CACHE)
//                        .cacheControl(cacheControl)
                        .build();
            }
            Response response = chain.proceed(request);
            if (NetWorkStateStateUtils.isNetWorkConnected(MyApplication.context)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                Log.e("Interceptor", "response: " + response.toString());
                //添加头信息，配置Cache-Control
                //removeHeader("Pragma") 使缓存生效
                String cache = request.cacheControl().toString();
                return response.newBuilder()
                        .removeHeader("Pragma")
//                        .header("Cache-Control", "public, max-age=" + CACHE_AGE_SHORT)
                        .header("Cache-Control", cache)
                        .build();
            } else {
                Log.e("Interceptor", "net not connect");
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public,only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .build();
            }
        }
    };


}

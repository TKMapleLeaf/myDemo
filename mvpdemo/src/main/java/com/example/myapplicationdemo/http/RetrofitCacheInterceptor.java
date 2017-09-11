package com.example.myapplicationdemo.http;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.myapplicationdemo.utils.NetWorkStateStateUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * An OkHttp interceptor which cache to Response,
 * Allow your application to have offline caching function
 * ,this cache Valid only for Get
 * 一个用于响应的OkHttp拦截器，
 * 允许应用程序具有离线缓存功能
 * 这个缓存只对Get有效
 */
public class RetrofitCacheInterceptor implements Interceptor {
    private Context context;
    private final String pragma = "pragma";
    private final String cacheControl = "Cache-Control";

    private volatile int maxAge = 60 * 60;
    private volatile int maxStale = 60 * 60 * 24 * 28;
    private String maxStaleString = "public, only-if-cached, max-stale=" + maxStale;
//    private String maxAgeString = "public, only-if-cached, max-stale=" + maxAge;
    private String maxAgeString = "public,max-age="+maxAge;


    public RetrofitCacheInterceptor(Context context) {
        this.context = context;
    }

    /**
     * set this response cache date in Network connection, Units are milliseconds
     * 在网络连接中设置这个响应缓存日期，单位是毫秒
     * <p>
     * default is 60 * 60
     *
     * @param maxAge response cache date in Network connection 网络连接中的响应缓存日期
     * @return this
     */
    public RetrofitCacheInterceptor setMaxAge(int maxAge) {
        this.maxAge = maxAge;
//        maxAgeString = "public, only-if-cached, max-stale=" + maxAge;
        maxAgeString = "public,max-age="+maxAge;
        return this;
    }

    /**
     * set this response cache date in Network not connection, Units are milliseconds
     * 将这个响应缓存日期设置为网络而不是连接，单位是毫秒
     * <p>
     * default is 60 * 60 * 24 * 28 4week,Of course,you can custom a long time
     * 默认是60 60 24 28 4周，当然，你可以定制很长时间
     *
     * @param maxStale response cache date in Network not connection 在网络中不连接的响应缓存日期
     * @return this
     */
    public RetrofitCacheInterceptor setMaxStale(int maxStale) {
        this.maxStale = maxStale;
        maxStaleString = "public, only-if-cached, max-stale=" + maxStale;
        return this;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        if (!NetWorkStateStateUtils.isNetWorkConnected(context)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response response = chain.proceed(request);
        if (NetWorkStateStateUtils.isNetWorkConnected(context)) {
            // read from cache for 1 minute 从缓存中读取1分钟
            response.newBuilder()
                    .removeHeader(pragma)
                    .header(cacheControl, maxAgeString)
                    .build();
        } else {
            // tolerate 4-weeks stale 4周的
            response.newBuilder()
                    .removeHeader(pragma)
                    .header(cacheControl, maxStaleString)
                    .build();
        }
        return response;
    }

}

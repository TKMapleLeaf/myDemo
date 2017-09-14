package com.example.retrofitrxcaceh.rxcache;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 创建者     Maggie
 * 创建时间   2017/8/14 11:08
 * 描述	     TODO 请求拦截器，用于统一设置请求相同参数
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class HttpCommonInterceptor implements Interceptor {
    private Map<String, String> mHeaderParamsMap = new HashMap<>();

    public HttpCommonInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.d("HttpCommonInterceptor", "add common params");
        Request oldRequest = chain.request();
        // 添加新的参数，添加到url 中
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .addQueryParameter("version_chinanews","")
                .addQueryParameter("deviceId_chinanews", "")
                .addQueryParameter("platform_chinanews", "android")
                .addQueryParameter("source", "chinanews")
                .host(oldRequest.url().host());

        // 新的请求
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build());

        Request newRequest = requestBuilder.build();
        //添加公共参数,添加到header中
        //        if (mHeaderParamsMap.size() > 0) {
        //            for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
        //                requestBuilder.header(params.getKey(), params.getValue());
        //            }
        //        }
        return chain.proceed(newRequest);
    }

    public static class Builder {
        HttpCommonInterceptor mHttpCommonInterceptor;

        public Builder() {
            mHttpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value) {
            mHttpCommonInterceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public HttpCommonInterceptor build() {
            return mHttpCommonInterceptor;
        }
    }
}

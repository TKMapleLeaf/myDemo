package com.example.myapplicationdemo.retrofitcachelib.intercept;

import android.text.TextUtils;

import com.example.myapplicationdemo.retrofitcachelib.RetrofitCache;
import com.example.myapplicationdemo.retrofitcachelib.util.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Yale on 2017/6/13.
 */

public class CacheInterceptorOnNet extends BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response  mockResponse = mockResponse(chain);
        if (mockResponse!=null){
            return mockResponse;
        }
        String url = request.url().url().toString();

        String mockPreUrl = request.header(KEY_HEADER_PRE_URL);
        if (!TextUtils.isEmpty(mockPreUrl)){
            url = mockPreUrl;
        }

        Long maxAge = RetrofitCache.getInatance().getCacheTime(url);
        Response response = chain.proceed(request);
        LogUtil.d("get data from net = "+response.code());

        return   response.newBuilder()
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public,max-age="+maxAge)
                .removeHeader("Pragma")
                .build();
    }
}

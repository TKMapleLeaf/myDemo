package com.example.retrofitcache.http.get;

import android.util.Log;

import com.example.retrofitcache.MyApplication;
import com.example.retrofitcache.util.NetWorkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by TK on 2017/9/14.
 */

public class InterceptorOnNet implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (NetWorkUtil.isNetworkAvailable(MyApplication.context)) {//有网时
            Response response = chain.proceed(request);
            String cacheControl = request.cacheControl().toString();

            switch (0) {
                case 0://实时获取在线数据
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + 0)
                            .build();
                case 1://读取缓存数据，缓存时间t（s）之后再去服务器获取在线数据--此处是60s
                    return response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + 60)
                            .build();
            }

        }
        return null;
    }
}

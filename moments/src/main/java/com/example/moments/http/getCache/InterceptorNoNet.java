package com.example.moments.http.getCache;


import com.example.moments.MyApplication;
import com.example.moments.util.NetWorkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static okhttp3.CacheControl.FORCE_CACHE;

/**
 * Created by TK on 2017/9/14.
 */

public class InterceptorNoNet implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetWorkUtil.isNetworkAvailable(MyApplication.applicationContext))  {//无网时
            switch (1) {
                case 0://读取缓存数据，过期时间最大值
                    request = request.newBuilder()
                            .cacheControl(FORCE_CACHE)//系统默认最大值
                            .build();
                    break;
                case 1://设置缓存过期时间为t(s);
                    //读取缓存数据，缓存时间t（s）之后就不能读取缓存数据量，必须联网再去服务器获取在线数据
                    CacheControl FORCE_CACHE1 = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(HttpRequestsCacheForGet.CACHE_STALE_LONG, TimeUnit.SECONDS)//设置缓存过期时间
                            .build();

                    request = request.newBuilder()
                            .cacheControl(FORCE_CACHE1)
                            .build();
                    break;
            }

        }
        Response response = chain.proceed(request);
        //下面注释的部分设置也没有效果，因为在上面已经设置了
        return response.newBuilder()
                .header("Cache-Control", "public, only-if-cached")
                .removeHeader("Pragma")
                .build();
    }
}

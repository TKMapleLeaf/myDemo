package com.example.moments.http.cache;

import android.text.TextUtils;

import com.example.moments.MyApplication;
import com.example.moments.util.LogUtil;
import com.example.moments.util.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CacheCall<T> {

    private Call<T> mCall;
    private Class dataClassName;
    // 是否使用缓存 默认开启
    private boolean mUseCache = true;

    public CacheCall(Call<T> call) {
        this.mCall = call;
    }

    /**
     * Gson反序列化缓存时 需要获取到泛型的class类型
     */
    public CacheCall<T> dataClassName(Class className) {
        dataClassName = className;
        return this;
    }

    /**
     * 是否使用缓存 默认使用
     */
    public CacheCall<T> useCache(boolean useCache) {
        mUseCache = useCache;
        return this;
    }

    public void enqueue(final CacheCallback<T> cacheCallback) {
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                cacheCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (!mUseCache || NetWorkUtil.isNetworkAvailable(MyApplication.applicationContext)) {
                    //不使用缓存 或者网络可用 的情况下直接回调onFailure
                    cacheCallback.onFailure(call, t);
                    return;
                }

                Request request = call.request();
                String url = request.url().toString();
                RequestBody requestBody = request.body();
                Charset charset = Charset.forName("UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append(url);
                if (request.method().equals("POST")) {
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(Charset.forName("UTF-8"));
                    }
                    Buffer buffer = new Buffer();
                    try {
                        requestBody.writeTo(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    sb.append(buffer.readString(charset));
                    buffer.close();
                }

                File path = CacheManager.getInstance().getDiskCachePath(MyApplication.applicationContext, sb.toString());
                boolean isCache = CacheManager.isCacheDataFailure(MyApplication.applicationContext, path);
                LogUtil.e(CacheManager.TAG, " path == " + path + "  iscache == " + isCache);
                if (isCache) {
                   /* String cache = CacheManager.getInstance().getCache(sb.toString());

                    LogUtil.e(CacheManager.TAG, "get cache->" + cache);

                    if (!TextUtils.isEmpty(cache) && dataClassName != null) {
                        Object obj = null;
                        try {
                            obj = new Gson().fromJson(cache, dataClassName);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        if (obj != null) {
                            cacheCallback.onCache((T) obj);
                            return;
                        }
                    }*/

                    CacheManager.getInstance().getCache(sb.toString(), new ThreadCacheCall() {
                        @Override
                        public void onCache(String cache) {
                            LogUtil.e(CacheManager.TAG, "get cache->" + cache);

                            if (!TextUtils.isEmpty(cache) && dataClassName != null) {
                                Object obj = null;
                                try {
                                    obj = new Gson().fromJson(cache, dataClassName);
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                }
                                if (obj != null) {
                                    cacheCallback.onCache((T) obj);
                                    return;
                                }
                            }
                        }
                    });

                }

                cacheCallback.onFailure(call, t);
                LogUtil.e(CacheManager.TAG, "onFailure->" + t.getMessage());
            }
        });
    }
}

package com.example.retrofitrxcaceh.rxcache;

import android.content.Context;

import com.example.retrofitrxcaceh.HttpService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建者     Maggie
 * 创建时间   2017/7/28 16:26
 * 描述	      TODO
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class RetrofitHelper {

    private static RetrofitHelper instance  = null;
    private        Retrofit       mRetrofit = null;

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    private RetrofitHelper(Context mContext) {
        init();
    }

    private void init() {
        resetApp();
    }


    private void resetApp() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(5, TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(5, TimeUnit.SECONDS);//读操作超时时间
        builder.addInterceptor(new HttpCommonInterceptor());

        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();
    }

    public HttpService getServer() {
        return mRetrofit.create(HttpService.class);
    }

}

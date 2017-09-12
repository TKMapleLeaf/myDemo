package com.example.myapplicationdemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplicationdemo.bean.HomeBean;
import com.example.myapplicationdemo.example1.Login1Activity;
import com.example.myapplicationdemo.example2.Login.LoginActivity;
import com.example.myapplicationdemo.example5.Login5Activity;
import com.example.myapplicationdemo.example6.Login6Activity;
import com.example.myapplicationdemo.http.HttpService;
import com.example.myapplicationdemo.http.RetrofitCacheInterceptor;
import com.example.myapplicationdemo.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView mTvCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.example1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login1Activity.class));
            }
        });

        findViewById(R.id.example2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        findViewById(R.id.example3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login5Activity.class));
            }
        });

        findViewById(R.id.example6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login6Activity.class));
            }
        });

        mTvCache = (TextView) findViewById(R.id.tv_cache);

        //方案二
        /*findViewById(R.id.bt_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启Log
                HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

//                OkHttpClient okHttpClient = new OkHttpClient.Builder()
////                        .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时
////                        .readTimeout(10, TimeUnit.SECONDS)//读取超时
////                        .writeTimeout(10, TimeUnit.SECONDS)//写入超时
//                        .addInterceptor(logInterceptor)
//                        .addInterceptor(new EnhancedCacheInterceptor()).build();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.addInterceptor(new EnhancedCacheInterceptor());

        File cacheDir = new File(getCacheDir(), "response");
        //缓存的最大尺寸10m
//        Cache cache = new Cache(cacheDir, 1024 * 1024 * 10);
//        builder.cache(cache);
//        builder.addInterceptor(new CacheInterceptor());

                OkHttpClient client = builder.build();
                Retrofit retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("http://117.48.201.110")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                HttpService httpService = retrofit.create(HttpService.class);
                Call<HomeBean> call = httpService.home();
                //使用我们自己的EnhancedCall 替换Retrofit的Call
                EnhancedCall<HomeBean> enhancedCall = new EnhancedCall<>(call);
                enhancedCall.useCache(true)*//*默认支持缓存 可不设置*//*
                        .dataClassName(HomeBean.class)
                        .enqueue(new EnhancedCallback<HomeBean>() {
                            @Override
                            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                                HomeBean body = response.body();
                                if (body != null) {
                                    LogUtil.e("haha" + "onResponse->" + body.toString());
                                    mTvCache.setText(body.toString());
                                }
                            }

                            @Override
                            public void onFailure(Call<HomeBean> call, Throwable t) {
                                LogUtil.e("haha" + "onFailure ->" + t.getMessage());
                            }

                            @Override
                            public void onGetCache(HomeBean homeBean) {
                                LogUtil.e("haha" + "onGetCache->" + homeBean.toString());
                                mTvCache.setText(homeBean.toString());
                            }
                        });
            }
        });*/
               findViewById(R.id.bt_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                initRetrofit();
//                requestAPI();
//                HttpService service = HttpRequests.getinstance(getApplicationContext()).getHttpService();
//                Call<HomeBean> call = service.home();
//                call.enqueue(new Callback<HomeBean>() {
//                    @Override
//                    public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
//
//                        if (response.raw().cacheResponse() != null) {
//                            // true: response was served from cache
//                            LogUtil.e("true: response was served from cache");
//                        }
//
//                        if (response.raw().networkResponse() != null) {
//                            // true: response was served from network/server
//                            LogUtil.e("true: response was served from network/server");
//                            okhttp3.Response networkResponse = response.raw().networkResponse();
//                            ResponseBody body = networkResponse.body();
//                        }
//
//                        boolean successful = response.isSuccessful();
//                        HomeBean body = response.body();
//                        LogUtil.e(successful + "" + body);
//                        if (body != null){
//                            List<HomeBean.HomeTopicBean> list = body.getHomeTopic();
//                            LogUtil.e(body.toString());
//                            mTvCache.setText("state: " + successful + ",   body:  " + body.toString());
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<HomeBean> call, Throwable t) {
//                        LogUtil.e(t.getMessage() + "网络错误");
//                    }
//                });

                File httpCacheDirectory = new File(getCacheDir(), "HttpCache");
                int cacheSize = 10 * 1024 * 1024; // 10 MiB
                Cache cache = new Cache(httpCacheDirectory, cacheSize);

                //开启Log
                HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)//设置连接超时
                        .readTimeout(10, TimeUnit.SECONDS)//读取超时
                        .writeTimeout(10, TimeUnit.SECONDS)//写入超时
                        .addInterceptor(logInterceptor)
                        .cache(cache)
//                        .addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR)
                        .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR) //方案一
//                        .addInterceptor(new CacheForceInterceptorNoNet()) //方案三
//                        .addNetworkInterceptor(new CacheInterceptorOnNet()) //方案三
//                        .addInterceptor(new Interceptor() {
//                            @Override
//                            public okhttp3.Response intercept(Chain chain) throws IOException {
//
////                                Request request = chain.request();
////                                if (!isNetworkReachable(getApplicationContext())) {
////                                    request = request.newBuilder()
////                                            .cacheControl(CacheControl.FORCE_CACHE)
////                                            .build();
////                                    LogUtil.e("true: no network");
////                                }
////
////                                okhttp3.Response response = chain.proceed(request);
////
////                                if (isNetworkReachable(getApplicationContext())) {
////                                    int maxAge = 0 * 60; // 有网络时 设置缓存超时时间0个小时
////                                    LogUtil.e("has network maxAge="+maxAge);
////                                    response.newBuilder()
////                                            .header("Cache-Control", "public, max-age=" + maxAge)
////                                            .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
////                                            .build();
////                                } else {
////                                    LogUtil.e("network error");
////                                    int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周
////                                    LogUtil.e("has maxStale="+maxStale);
////                                    response.newBuilder()
////                                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
////                                            .removeHeader("Pragma")
////                                            .build();
////                                    LogUtil.e("response build maxStale="+maxStale);
////                                }
////                                return response;
//
////
//                                Request request = chain.request();
//                                okhttp3.Response response = chain.proceed(request);
//
//                                // DANGER! We're overwriting an existing header. Use caution.
//                                okhttp3.Response hackedResponse = response
//                                        .newBuilder()
//                                        .header("Cache-Control", "max-age=3600")
//                                        .build();
//                                LogUtil.e("true: ====000==" +isNetworkAvailable(getApplicationContext()));
//                                if (!isNetworkAvailable(getApplicationContext())) {
//                                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale (in seconds)
//                                    request = request
//                                            .newBuilder()
//                                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                            .build();
//                                }
//
////                                if (isNetworkAvailable(getApplicationContext())) {
////                                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
////                                } else {
////                                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
////                                }
////                                return hackedResponse;
//                                return chain.proceed(request);
//                            }
//                        })
                        .build();

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://117.48.201.110")
                        .client(okHttpClient)
//                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//方案三
                        .addConverterFactory(GsonConverterFactory.create());

                Retrofit retrofit = builder.build();
//                RetrofitCache.getInatance().addRetrofit(retrofit);//方案三
                api = retrofit.create(HttpService.class);
                requestAPI();

            }


        });

    }

    private HttpService api;

    /**
     * set cacheInterceptor to Retrofit
     */
    private void initRetrofit() {

        //create a cache
        File cacheDir = Environment.getExternalStorageDirectory();
        Cache cache = new Cache(cacheDir, 20 * 1024 * 1024);

        //create

        RetrofitCacheInterceptor retrofitCacheInterceptor =
                new RetrofitCacheInterceptor(getApplicationContext());
        //this is not needful


        //set httpClint cache and
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(retrofitCacheInterceptor)
                .cache(cache)
                .build();


        Retrofit build = new Retrofit
                .Builder()
                .baseUrl("http://117.48.201.110")
                //set client
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        api = build.create(HttpService.class);
    }

    private void requestAPI() {
        Call<HomeBean> call = api.home();
        call.enqueue(new Callback<HomeBean>() {
            @Override
            public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                HomeBean body = response.body();
                if (body != null) {
                    mTvCache.setText(body.toString());
                }


                if (response.raw().cacheResponse() != null) {
                    // true: response was served from cache
                    LogUtil.e("true: response was served from cache");
                }

                if (response.raw().networkResponse() != null) {
                    // true: response was served from network/server
                    LogUtil.e("true: response was served from network/server");
                    okhttp3.Response networkResponse = response.raw().networkResponse();
                    ResponseBody body1 = networkResponse.body();
                }

            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {

            }
        });
    }


    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            LogUtil.e("true: ====111==" + isNetworkAvailable(getApplicationContext()));
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                LogUtil.e("true: ===222===" + isNetworkAvailable(getApplicationContext()));
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 3600)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };

    private final Interceptor REWRITE_RESPONSE_INTERCEPTOR_OFFLINE = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isNetworkAvailable(MainActivity.this)) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        }
    };


    //方案一
    private final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            if (isNetworkAvailable(getApplicationContext())) {
                int maxAge = 60; // read from cache for 1 minute
                Log.e("Interceptor", "response: " + originalResponse.toString());
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                Log.e("Interceptor", "net not connect");
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")//清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
}

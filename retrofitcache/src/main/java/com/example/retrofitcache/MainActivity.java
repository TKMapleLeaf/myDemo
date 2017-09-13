package com.example.retrofitcache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.retrofitcache.bean.HomeBean;
import com.example.retrofitcache.bean.LoginBean;
import com.example.retrofitcache.http.HttpService;
import com.example.retrofitcache.http.cache.EnhancedCacheInterceptor;
import com.example.retrofitcache.http.cache.EnhancedCall;
import com.example.retrofitcache.http.cache.EnhancedCallback;
import com.example.retrofitcache.http.retrofitcachelib.RetrofitCache;
import com.example.retrofitcache.http.retrofitcachelib.intercept.CacheForceInterceptorNoNet;
import com.example.retrofitcache.http.retrofitcachelib.intercept.CacheInterceptorOnNet;
import com.example.retrofitcache.util.LogUtil;
import com.example.retrofitcache.util.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.retrofitcache.util.NetWorkUtil.isNetworkAvailable;

public class MainActivity extends AppCompatActivity {

    private TextView mTvCache;
    private HttpService api;
    private HttpService mHttpService;
    private EditText mEt_name;
    private EditText mEt_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTvCache = (TextView) findViewById(R.id.content);
        mEt_name = (EditText) findViewById(R.id.et_name);
        mEt_pwd = (EditText) findViewById(R.id.et_pwd);
        findViewById(R.id.get1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get1();
            }
        });
        findViewById(R.id.get2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get2();
            }
        });
        findViewById(R.id.get3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get3();
            }
        });
        findViewById(R.id.get5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get5();
            }
        });
        findViewById(R.id.get6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get6();
            }
        });
        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post();
            }
        });
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvCache.setText("");
            }
        });
    }

    public void get1() {
//        api = HttpRequestsForCache.HttpRequestsForCache();
//        api = RetrofitNetUtil.requestData();
        api = com.example.retrofitcache.http.get.HttpRequestsForCache.getinstance().getHttpService();
        requestAPI();
    }


    public void get2() {

        File httpCacheDirectory = new File(MyApplication.context.getCacheDir(), "HttpCache2");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .addInterceptor(new CacheForceInterceptorNoNet())
                .addNetworkInterceptor(new CacheInterceptorOnNet())
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://117.48.201.110")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RetrofitCache.getInatance().addRetrofit(retrofit);
        api = retrofit.create(HttpService.class);
        requestAPI();
    }

    public void get3() {

        initRetrofit();
        Call<HomeBean> call = mHttpService.home();

        //使用我们自己的EnhancedCall 替换Retrofit的Call
        EnhancedCall<HomeBean> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.useCache(true)//默认支持缓存 可不设置
                .dataClassName(HomeBean.class)
                .enqueue(new EnhancedCallback<HomeBean>() {
                    @Override
                    public void onResponse(Call<HomeBean> call, Response<HomeBean> response) {
                        HomeBean body = response.body();
                        if (body != null) {
                            LogUtil.e("get3" + "onResponse->" + body.toString());
                            mTvCache.setText(body.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeBean> call, Throwable t) {
                        LogUtil.e("get3" + "onFailure ->" + t.getMessage());
                    }

                    @Override
                    public void onGetCache(HomeBean homeBean) {
                        LogUtil.e("get3" + "onGetCache->" + homeBean.toString());
                        mTvCache.setText(homeBean.toString());
                    }
                });
    }


    public void post() {
        initRetrofit();
        Map<String,String> params = new HashMap<>();
        params.put("username",mEt_name.getText().toString().trim());
        params.put("password",mEt_pwd.getText().toString().trim());

        Call<LoginBean> call = mHttpService.login(params);
        EnhancedCall<LoginBean> enhancedCall = new EnhancedCall<>(call);
        enhancedCall.useCache(true)
                .dataClassName(LoginBean.class)
                .enqueue(new EnhancedCallback<LoginBean>() {
                    @Override
                    public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                        LoginBean body = response.body();
                        if (body != null) {
                            LogUtil.e("post" + "onResponse->" + body.toString());
                            mTvCache.setText(body.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginBean> call, Throwable t) {
                        LogUtil.e("post" + "onFailure ->" + t.getMessage());
                    }

                    @Override
                    public void onGetCache(LoginBean loginBean) {
                        LogUtil.e("post" + "onGetCache->" + loginBean.toString());
                        mTvCache.setText(loginBean.toString());
                    }
                });
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
                    LogUtil.e("Interceptor: response was served from cache");
                }

                if (response.raw().networkResponse() != null) {
                    // true: response was served from network/server
                    LogUtil.e("Interceptor: response was served from network/server");
                    okhttp3.Response networkResponse = response.raw().networkResponse();
                    ResponseBody body1 = networkResponse.body();
                }

            }

            @Override
            public void onFailure(Call<HomeBean> call, Throwable t) {
                LogUtil.e("Interceptor" + "onFailure ->" + t.getMessage());
            }
        });
    }

    private void initRetrofit(){
        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .addInterceptor(new EnhancedCacheInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://117.48.201.110")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mHttpService = retrofit.create(HttpService.class);
    }

    private void get5(){

        File httpCacheDirectory = new File(MyApplication.context.getCacheDir(), "HttpCache5");
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        okhttp3.Response response = chain.proceed(request);
                        Log.e("Interceptor", "request: " + NetWorkUtil.isNetworkAvailable(MyApplication.context));
                        // DANGER! We're overwriting an existing header. Use caution.
                        okhttp3.Response hackedResponse = response
                                .newBuilder()
                                .removeHeader("Pragma")
                                .header("Cache-Control", "max-age=3600")
                                .build();

                        return hackedResponse;
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://117.48.201.110")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = builder.build();
        HttpService httpService = retrofit.create(HttpService.class);

        Call<ResponseBody> call =  httpService.getHome();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // success, server (or cache) responded
                        if (response.raw().cacheResponse() != null) {
                            // true: response was served from cache
                            LogUtil.e("Interceptor: response was served from cache");
                        }

                        if (response.raw().networkResponse() != null) {
                            // true: response was served from network/server
                            LogUtil.e("Interceptor: response was served from network/server");
                        }
                        ResponseBody body = response.body();
                        if (body != null){
                            try {
                                mTvCache.setText(body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        // failure, no network connection or conversion threw error
                        LogUtil.e("Interceptor" + "onFailure ->" + t.getMessage());
                    }
                });
    }


    private void get6(){

        File httpCacheDirectory = new File(MyApplication.context.getCacheDir(), "HttpCache6");
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        //开启Log
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .addNetworkInterceptor(new Interceptor() {
                    @Override public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.e("Interceptor", "request: " + NetWorkUtil.isNetworkAvailable(MyApplication.context));
                        if (NetWorkUtil.isNetworkAvailable(MyApplication.context)) {
                            Log.e("Interceptor", "request: " + request.toString());
                            //没网时只使用缓存
                            //自定义请求头，可以在响应头对请求头的header进行拦截，配置不同的缓存策略
                            request = request.newBuilder()
                                    .header("head-request", request.toString())
                                    .cacheControl(CacheControl.FORCE_CACHE)
                                    .build();
                        }
                        if (isNetworkAvailable(getApplicationContext())) {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale (in seconds)
                            request = request
                                    .newBuilder()
                                    .removeHeader("Pragma")
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }

                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://117.48.201.110")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        HttpService httpService = retrofit.create(HttpService.class);

        Call<ResponseBody> call =  httpService.getHome();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // success, server (or cache) responded
                if (response.raw().cacheResponse() != null) {
                    // true: response was served from cache
                    LogUtil.e("Interceptor: response was served from cache");
                }

                if (response.raw().networkResponse() != null) {
                    // true: response was served from network/server
                    LogUtil.e("Interceptor: response was served from network/server");
                }
                ResponseBody body = response.body();
                if (body != null){
                    try {
                        mTvCache.setText(body.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // failure, no network connection or conversion threw error
                LogUtil.e("Interceptor" + "onFailure ->" + t.getMessage());
            }
        });
    }
}

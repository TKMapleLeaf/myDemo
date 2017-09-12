package com.example.myapplicationdemo.retrofitcachelib;

import android.content.Context;
import android.text.TextUtils;

import com.example.myapplicationdemo.retrofitcachelib.anno.Cache;
import com.example.myapplicationdemo.retrofitcachelib.anno.Mock;
import com.example.myapplicationdemo.retrofitcachelib.util.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import retrofit2.Retrofit;

/**
 * Created by Yale on 2017/6/13.
 */
public class RetrofitCache {


    private static volatile RetrofitCache mRetrofit;
    private Vector<Map> mVector;
    private Map<String,Long> mUrlMap;

    private Context mContext;
    private Long mDefaultTime = 0L;
    private TimeUnit mDefaultTimeUnit =TimeUnit.SECONDS;

    private Map mUrlAragsMap =null;

    private RetrofitCache(){
        clear();
        mUrlAragsMap = new HashMap();
    }
    public Context getContext(){
        return mContext;
    }


    public static RetrofitCache getInatance(){
        if (mRetrofit == null){
            synchronized (RetrofitCache.class){
                if (mRetrofit == null){
                    mRetrofit = new RetrofitCache();
                }
            }
        }
        return mRetrofit;
    }
    public RetrofitCache init(Context context){
        mContext = context.getApplicationContext();
        return this;
    }

    public void addMethodInfo(Object serviceMethod,Object[] args){
        String url = "";
        try {
            url =  buildRequestUrl(serviceMethod,args);
        } catch (Exception e) {
            LogUtil.l(e);
        }
        if (!TextUtils.isEmpty(url)){
            if (!mUrlAragsMap.containsKey(url)){
                mUrlAragsMap.put(url,args);
            }
        }
    }

    private String buildRequestUrl(Object serviceMethod,Object[] args) throws Exception{
        Class   clsServiceMethod =  Class.forName("retrofit2.ServiceMethod");
        Method toRequestMethod =  clsServiceMethod.getDeclaredMethod("toRequest", Object[].class );
        toRequestMethod.setAccessible(true);
        try {
            Request request = (Request) toRequestMethod.invoke(serviceMethod,new Object[]{args});
            return request.url().toString();
        }catch (Exception e){
            LogUtil.l(e);
        }
        return "";
    }
    public RetrofitCache setDefaultTime(long time){
        mDefaultTime = time;
        return this;
    }
    public RetrofitCache setDefaultTimeUnit(TimeUnit timeUnit){
        mDefaultTimeUnit = timeUnit;
        return this;
    }
    public long getDaultTime(){
        return mDefaultTime;
    }
    public TimeUnit getDefaultTimeUnit(){
        return mDefaultTimeUnit;
    }

    public Mock getMockObject(String url){
        for (Map serviceMethodCache:mVector) {

            for (Object entry:serviceMethodCache.keySet()){
                Object o = serviceMethodCache.get(entry);
                try {

                    if (mUrlAragsMap.containsKey(url)){
                        Object[] args = (Object[]) mUrlAragsMap.get(url);
                        String reqUrl =  buildRequestUrl(o,args);
                        if (reqUrl.equals(url)){
                            Method m = (Method) entry;
                            Mock mock =  m.getAnnotation(Mock.class);
                            if (mock!=null){
                                return  mock;
                            }
                            return null;
                        }
                    }
                } catch (Exception e) {
                    LogUtil.l(e);
                }
            }
        }

        return null;
    }
    public String getMockUrl(Mock mock){
        if (mock!=null){
            if (!TextUtils.isEmpty(mock.url())){
                return mock.url();
            }
        }
        return null;
    }
    public String getMockData(Mock mock){
        if (mock!=null){
            if (!TextUtils.isEmpty(mock.value())){
                return mock.value();
            }
        }
        return null;
    }
    public String getMockData(String url){
        Mock mock =  getMockObject(url);
        if (mock!=null){
            if (!TextUtils.isEmpty(mock.value())){
                return mock.value();
            }
        }
        return null;
    }

    public Long getCacheTime(String url){
        if (mUrlMap!=null){
            Long type = mUrlMap.get(url);
            if (type!=null){
                return type;
            }
        }
        for (Map serviceMethodCache:mVector) {

            for (Object entry:serviceMethodCache.keySet()){
                Object o = serviceMethodCache.get(entry);
                try {

                    if (mUrlAragsMap.containsKey(url)){
                        Object[] args = (Object[]) mUrlAragsMap.get(url);
                        String reqUrl =  buildRequestUrl(o,args);
                        if (reqUrl.equals(url)){
                            Method m = (Method) entry;
                            Cache cache =  m.getAnnotation(Cache.class);
                            if (cache!=null){
                                TimeUnit timeUnit =  mDefaultTimeUnit;
                                if (cache.timeUnit() != TimeUnit.NANOSECONDS){
                                    timeUnit = cache.timeUnit();
                                }
                                long t = mDefaultTime;
                                if (cache.time() != -1){
                                    t = cache.time();
                                }
                                long tm =  timeUnit.toSeconds(t);
                                getUrlMap().put(url, tm);
                                return tm;
                            }else{
                                getUrlMap().put(url, 0L);
                                return 0L;
                            }
                        }
                    }
                } catch (Exception e) {
                    LogUtil.l(e);
                }
            }
        }
        getUrlMap().put(url, 0L);
        return 0L;
    }
    private Map getUrlMap(){
        if (mUrlMap==null){
            mUrlMap = new HashMap<String, Long>();
        }
        return  mUrlMap;
    }

    public RetrofitCache addRetrofit(Retrofit retrofit){
        try {

            Class cls = retrofit.getClass();
            Field field =  cls.getDeclaredField("serviceMethodCache");
            field.setAccessible(true);
            if (mVector == null){
                mVector = new Vector<Map>();
            }
            Map m = (Map) field.get(retrofit);
            mVector.add(m);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public void clear(){
        if (mVector!=null){
            mVector.clear();
            mVector =null;
        }
        if (mUrlMap!=null){
            mUrlMap.clear();
            mUrlMap =null;
        }
        if (mUrlAragsMap!=null){
            mUrlAragsMap.clear();
            mUrlAragsMap =null;
        }
        mRetrofit = null;

    }

}

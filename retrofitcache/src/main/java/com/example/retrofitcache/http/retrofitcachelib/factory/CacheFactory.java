package com.example.retrofitcache.http.retrofitcachelib.factory;

import android.support.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * Created by Yale on 2017/6/14.
 */

public class CacheFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return null;
    }
//    @Override
//    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
//        return null;
//    }
}

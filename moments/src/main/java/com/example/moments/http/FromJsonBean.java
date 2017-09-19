package com.example.moments.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */

public class FromJsonBean<T> {

    private static Gson mGson;
    private Class<T> mClass;//bean类


    private FromJsonBean() {
    }

    public FromJsonBean(Class<T> classz) {
        mClass = classz;
    }

    private Gson getGson() {
        if (mGson == null) {
            synchronized (FromJsonBean.class) {
                mGson = new Gson();
            }
        }
        return mGson;
    }

    public T parseJson(String result) {
        T parsedResult = null;
        try {
            parsedResult = getGson().fromJson(result, mClass);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return parsedResult;
    }

    public T fromJson(String data) {
        mGson = getGson();
        T bean = null;
        Type type = new TypeToken<T>() {
        }.getType();
        if (data != null) {
            bean = mGson.fromJson(data, type);
        }
        return bean;
    }

    public List<T> fromJsonList(String data) {
        mGson = getGson();
        List<T> bean = null;
        Type type = new TypeToken<List<T>>() {
        }.getType();
        if (data != null) {
            bean = mGson.fromJson(data, type);
        }
        return bean;
    }

}

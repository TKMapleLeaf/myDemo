package com.example.notify;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author luhh on 2017/12/14.
 *         desc
 */

public class FromJson2Bean<T> {

    private Class<T> mClazz;
    private static Gson mGson;
    private T t;

    public FromJson2Bean() {
    }

    public FromJson2Bean(Class<T> clazz) {
        mClazz = clazz;
    }

    private Gson getGson() {
        if (mGson == null) {
            synchronized (FromJson2Bean.class) {
                if (mGson == null) {
                    mGson = new Gson();
                }
            }
        }
        return mGson;
    }

    public T parseJson(String json) {

        T t = null;
        try {
            t = getGson().fromJson(json, mClazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return t;
    }

    public T fromJson(String data) {

        T bean = null;
        Type type = new TypeToken<T>() {
        }.getType();
//        Type genType = getClass().getGenericSuperclass();
//        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
//        Type type = params[0];
        if (data != null) {
            bean = getGson().fromJson(data, type);
        }
        return bean;
    }

    public List<T> fromJsonList(String data) {
        List<T> bean = null;
//        Type type = new TypeToken<List<T>>() {}.getType();
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        if (data != null) {
            bean = getGson().fromJson(data, type);
        }
        return bean;
    }

}

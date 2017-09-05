package com.example.myapplicationdemo.example6;

/**
 * Created by Administrator on 2017/9/5.
 */

public interface MvpCallBack<T> {

    void onSuccess(T t);

    void onError(String err);

}

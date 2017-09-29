package com.example.moments.basemvp;

/**
 * Created by Administrator on 2017/9/5.
 *
 * 普通的MVP回调
 */

public interface MvpCommonCallBack<T> {

    void onSuccess(T t);

    void onError(String err);

}

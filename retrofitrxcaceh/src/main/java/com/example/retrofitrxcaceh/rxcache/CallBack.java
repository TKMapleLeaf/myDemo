package com.example.retrofitrxcaceh.rxcache;

/**
 * 创建者     Maggie
 * 创建时间   2017/8/1 9:08
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public interface CallBack<T> {
    void onSuccess(T result);
    void onError(String erroMsg);
    void onStart();
}

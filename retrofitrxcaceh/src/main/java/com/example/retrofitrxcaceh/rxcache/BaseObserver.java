package com.example.retrofitrxcaceh.rxcache;


import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * 创建者     Maggie
 * 创建时间   2017/9/4 13:35
 * 描述	     TODO
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class BaseObserver<T> extends DisposableObserver<BaseEntity<T>> {
    private BaseService service;
    private CallBack<T> callBack;

    public BaseObserver(BaseService service, CallBack<T> callBack) {
        this.service = service;
        this.callBack = callBack;
    }

    @Override
    public void onNext(@NonNull BaseEntity<T> tBaseEntity) {
        if (service.getCache()) {
            //缓存
            service.getCacheKey();
        }
        if (callBack != null) {
            callBack.onSuccess(tBaseEntity.data);
        }
    }


    @Override
    public void onError(@NonNull Throwable e) {
        if (callBack != null) {
            callBack.onError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }


}

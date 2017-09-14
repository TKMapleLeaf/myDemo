package com.example.retrofitrxcaceh.rxcache;

import android.content.Context;

import com.example.retrofitrxcaceh.HttpService;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


/**o
 * 创建者     Maggie
 * 创建时间   2017/8/1 9:18
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public abstract class BaseService {
    public HttpService mRetrofitService;
    private boolean cache = false;
    private String cacheKey;
    private boolean canCancle = true;
    protected Context mContext;

    public BaseService(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
        this.mContext = context;
    }

    public void resetRetrofitConfig() {
    }

    public BaseService setCacheAble(boolean cache) {
        this.cache = cache;
        return this;
    }

    public boolean getCache() {
        return this.cache;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public BaseService setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return this;
    }

    void doHttp(Observable observable, CallBack callBack) {
        observable.compose(new ObservableTransformer<BaseEntity,BaseEntity>() {

            @Override
            public ObservableSource<BaseEntity> apply(@NonNull Observable<BaseEntity> upstream) {
              /*  if (mContext instanceof RxAppCompatActivity) {
                    upstream.compose(((RxAppCompatActivity) mContext).bindUntilEvent(ActivityEvent.DESTROY));
                }else if (mContext instanceof RxFragmentActivity){
                    upstream.compose(((RxFragmentActivity) mContext).bindUntilEvent(ActivityEvent.DESTROY));
                }else if (mContext instanceof RxActivity){
                    upstream.compose(((RxActivity) mContext).bindUntilEvent(ActivityEvent.DESTROY));
                }*/
                return null;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<>(this, callBack));
    }
}
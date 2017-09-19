package com.example.moments.model;


import com.example.moments.basemvp.MvpBaseModel;
import com.example.moments.basemvp.MvpCommonCallBack;
import com.example.moments.bean.DynamicBean;
import com.example.moments.http.cache.CacheCall;
import com.example.moments.http.cache.CacheCallback;
import com.example.moments.http.cache.HttpRequestsForCache;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/9/14.
 */

public class DynamicModel extends MvpBaseModel {

    private Call<DynamicBean> mCall;

    public void dynamic(String page,final MvpCommonCallBack<DynamicBean> callBack){

//        mCall = HttpRequestsCacheForGet.getinstance().getHttpService().dynamic();

        Map<String, String> params = new HashMap<>();
        params.put("page",page);
        mCall = HttpRequestsForCache.getinstance().getHttpService().getDynamic(params);

      /*  mCall.enqueue(new Callback<DynamicBean>() {
            @Override
            public void onResponse(Call<DynamicBean> call, Response<DynamicBean> response) {
                if (response != null && response.isSuccessful()) {
                    DynamicBean dynamicBean = response.body();
                    if (dynamicBean != null){
                        callBack.onSuccess(dynamicBean);
                        return;
                    }
                }
                onFailure(call,new Throwable());
            }

            @Override
            public void onFailure(Call<DynamicBean> call, Throwable t) {
                callBack.onError(MyApplication.applicationContext.getString(R.string.net_err));
            }
        });*/

        //使用我们自己的CacheCall 替换Retrofit的Call
        CacheCall<DynamicBean> cacheCall = new CacheCall<>(mCall);
        cacheCall
                .useCache(true)
                .dataClassName(DynamicBean.class)
                .enqueue(new CacheCallback<DynamicBean>() {
                    @Override
                    public void onResponse(Call<DynamicBean> call, Response<DynamicBean> response) {
                        if (response != null && response.isSuccessful()) {
                            DynamicBean dynamicBean = response.body();
                            if (dynamicBean != null){
                                callBack.onSuccess(dynamicBean);
                                return;
                            }
                        }
                        onFailure(call,new Throwable());
                    }

                    @Override
                    public void onFailure(Call<DynamicBean> call, Throwable t) {
                        callBack.onError("Network error");
                    }

                    @Override
                    public void onCache(DynamicBean dynamicBean) {
                        if (dynamicBean != null){
                            callBack.onSuccess(dynamicBean);
                        }
                    }
                });


    }

    @Override
    public void cancelRequests() {
        if (mCall != null){
            mCall.cancel();
        }
    }

}

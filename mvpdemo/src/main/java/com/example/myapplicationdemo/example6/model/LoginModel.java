package com.example.myapplicationdemo.example6.model;

import com.example.myapplicationdemo.bean.LoginBean;
import com.example.myapplicationdemo.example6.MvpCallBack;
import com.example.myapplicationdemo.http.HttpRequests;
import com.example.myapplicationdemo.http.RequestParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/9/5.
 */

public class LoginModel {

    public void login(String username, String passwore, final MvpCallBack<LoginBean> callBack){

        RequestParams params = HttpRequests.createRequestParams();
        params.put("username", username);
        params.put("passwore", passwore);

        Call<LoginBean> call = HttpRequests.getinstance().getHttpService().getLogin(username,passwore);

        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response != null && response.isSuccessful()) {
                    LoginBean LoginBean = response.body();
                    if (LoginBean != null){
                        callBack.onSuccess(LoginBean);
                        return;
                    }
                }
                onFailure(call,new Throwable());
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                callBack.onError("请求失败");
            }
        });
    }
}

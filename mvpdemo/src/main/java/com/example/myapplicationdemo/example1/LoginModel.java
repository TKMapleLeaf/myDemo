package com.example.myapplicationdemo.example1;


import android.os.Handler;

import com.example.myapplicationdemo.utils.HttpTask;
import com.example.myapplicationdemo.utils.HttpUtils;

/**
 * 作者: Dream on 2017/9/4 21:18
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//M层
public class LoginModel {


    public void login(String username, String password, final HttpUtils.OnHttpResultListener onHttpResultListener) {

        HttpTask httpTask = new HttpTask(new HttpUtils.OnHttpResultListener() {
            @Override
            public void onResult(String result) {
                onHttpResultListener.onResult(result);
            }
        });
        httpTask.execute("http://192.168.57.1:8080/Dream_6_19_LoginServer/LoginServlet", username, password);
    }

    public void login(final String username, final String password, final MvpCallBack<String> callBack) {

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                if ("abc".equals(username) && "123".equals(password)) {
                    callBack.onSuccess();
                } else {
                    callBack.onError();
                }
            }
        }, 2000);
    }

}

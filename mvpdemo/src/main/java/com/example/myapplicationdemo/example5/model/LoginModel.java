package com.example.myapplicationdemo.example5.model;

import android.os.Handler;

import com.example.myapplicationdemo.example5.MvpCallBack;

/**
 * Created by Administrator on 2017/9/5.
 */

public class LoginModel {
//
//    public interface OnLoginFinishedListener {
//
//        void onUsernameError();
//
//        void onPasswordError();
//
//        void onSuccess();
//
//        void onFailure();
//    }
//
//    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
//        // Mock login. I'm creating a handler to delay the answer a couple of seconds
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (TextUtils.isEmpty(username)) {
//                    listener.onUsernameError();
//                    return;
//                }
//                if (TextUtils.isEmpty(password)) {
//                    listener.onPasswordError();
//                    return;
//                }
//
//                if ("abc".equals(username) && "123".equals(password)) {
//                    listener.onSuccess();
//                } else {
//                    listener.onFailure();
//                }
//            }
//        }, 2000);
//    }

    public void login(final String username, final String password, final MvpCallBack callBack) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if ("abc".equals(username) && "123".equals(password)) {
                    callBack.onSuccess();
                } else {
                    callBack.onError();
                }
            }
        }, 2000);
    }
}

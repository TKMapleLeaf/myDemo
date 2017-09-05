package com.example.myapplicationdemo.example5.presenter;

import com.example.myapplicationdemo.example5.MvpCallBack;
import com.example.myapplicationdemo.example5.model.LoginModel;
import com.example.myapplicationdemo.example5.view.LoginView;

/**
 * Created by Administrator on 2017/9/5.
 */

public class LoginPresenter {

    private LoginView mView;
    private LoginModel mLoginModel;

    public LoginPresenter(LoginView view) {
        mView = view;
        mLoginModel = new LoginModel();
    }

    public void login(final String username, final String password){

        mLoginModel.login(username, password, new MvpCallBack() {
            @Override
            public void onSuccess() {
                mView.success();
            }

            @Override
            public void onError() {
                mView.failure();
            }
        });
    }
//    public void login(final String username, final String password){
//
//        mLoginModel.login(username, password, new LoginModel.OnLoginFinishedListener() {
//            @Override
//            public void onUsernameError() {
//                mView.setUsernameError();
//            }
//
//            @Override
//            public void onPasswordError() {
//                mView.setPasswordError();
//            }
//
//            @Override
//            public void onSuccess() {
//                mView.success();
//            }
//
//            @Override
//            public void onFailure() {
//                mView.failure();
//            }
//        });
//    }

    public void onDestroy() {
        mView = null;
    }
}

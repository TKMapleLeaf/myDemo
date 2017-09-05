package com.example.myapplicationdemo.example6.presenter;

import com.example.myapplicationdemo.bean.LoginBean;
import com.example.myapplicationdemo.example6.MvpCallBack;
import com.example.myapplicationdemo.example6.base.BasePresenter;
import com.example.myapplicationdemo.example6.model.LoginModel;
import com.example.myapplicationdemo.example6.view.LoginView;

/**
 * Created by Administrator on 2017/9/5.
 */

public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel mLoginModel;

    public LoginPresenter() {
        mLoginModel = new LoginModel();
    }

    public void login(String username,String password){

        mLoginModel.login(username, password, new MvpCallBack<LoginBean>() {
            @Override
            public void onSuccess(LoginBean userBean) {
                if (getView() != null){
                    getView().success(userBean);
                }
            }

            @Override
            public void onError(String err) {
                if (getView() != null){
                    getView().failure(err);
                }
            }
        });
    }
}

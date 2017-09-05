package com.example.myapplicationdemo.example1;


import com.example.myapplicationdemo.example1.base.BasePresenter;

/**
 * 作者: Dream on 2017/9/4 21:20
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

//P层
//特点：持有UI层和数据层引用
public class LoginPresenter extends BasePresenter<LoginView> {

    //持有M层引用
    private LoginModel loginModel;

    //构造方法绑定UI层
    public LoginPresenter() {
        this.loginModel = new LoginModel();
    }

    public void login(String username, String password) {
//
//        this.loginModel.login(username, password, new HttpUtils.OnHttpResultListener() {
//            @Override
//            public void onResult(String result) {
//                //回调UI层->和UI进行交互
//                if (getView() != null) {
//                    getView().onLoginResult(result);
//                }
//            }
//        });
//
        this.loginModel.login(username, password, new MvpCallBack<String>() {
            @Override
            public void onSuccess() {
                //回调UI层->和UI进行交互
                if (getView() != null) {
                    getView().onLoginResult("登录成功");
                }
            }

            @Override
            public void onError() {
                if (getView() != null) {
                    getView().failure();
                }
            }
        });
    }

}

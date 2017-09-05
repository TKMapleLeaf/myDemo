package com.example.myapplicationdemo.example1;


import com.example.myapplicationdemo.example1.base.BaseView;

/**
 * 作者: Dream on 2017/9/4 21:23
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public interface LoginView extends BaseView {

    void onLoginResult(String result);

    void failure();

}

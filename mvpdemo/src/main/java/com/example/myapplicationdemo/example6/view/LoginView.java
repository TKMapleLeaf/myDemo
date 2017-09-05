package com.example.myapplicationdemo.example6.view;

import com.example.myapplicationdemo.example6.base.BaseView;

/**
 * Created by Administrator on 2017/9/5.
 */

public interface LoginView extends BaseView {

    void success(Object t);

    void failure(String err);

}

package com.example.myapplicationdemo.example6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplicationdemo.example6.base.BaseFragment;
import com.example.myapplicationdemo.example6.presenter.LoginPresenter;
import com.example.myapplicationdemo.example6.view.LoginView;

/**
 * Created by Administrator on 2017/9/5.
 */

public class LoginFragment extends BaseFragment<LoginView,LoginPresenter> implements LoginView {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getPresenter().login("abc", "123456");
    }

    @Override
    public LoginPresenter createPresneter() {
        return new LoginPresenter();
    }

    @Override
    public LoginView createView() {
        return this;
    }

    @Override
    public void success(Object o) {

    }

    @Override
    public void failure(String err) {

    }
}

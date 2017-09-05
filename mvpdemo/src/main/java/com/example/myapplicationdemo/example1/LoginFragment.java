package com.example.myapplicationdemo.example1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplicationdemo.example1.base.BaseFragment;

/**
 * 作者: Dream on 2017/9/4 22:03
 * QQ:510278658
 * E-mail:510278658@qq.com
 */

public class LoginFragment extends BaseFragment<LoginView, LoginPresenter> implements LoginView {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresneter().login("Dream", "123456");

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
    public void onLoginResult(String result) {
        Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
    }

    @Override
    public void failure() {

    }

}

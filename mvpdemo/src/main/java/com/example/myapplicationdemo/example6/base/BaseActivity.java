package com.example.myapplicationdemo.example6.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/9/5.
 *
 * activity基类 绑定和解除绑定UI接口view
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity {

    private V view;

    private P presenter;

    public P getPresenter(){
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.presenter == null){
            this.presenter = createPresneter();
        }

        if (this.view == null){
            this.view = createView();
        }

        if (this.presenter == null){
            throw new NullPointerException("presneter不能够为空");
        }

        if (this.view == null){
            throw new NullPointerException("view不能够为空");
        }

        //绑定
        if (this.presenter != null) {
            this.presenter.attachView(this.view);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.presenter != null){
            this.presenter.detachView();
        }
    }

    //并不知道具体的P是哪一个实现类，由他的子类决定(BaseActivity子类决定具体类型)
    public abstract P createPresneter();

    //并不知道具体的V是哪一个实现类，由他的子类决定(BaseActivity子类决定具体类型)
    public abstract V createView();
}

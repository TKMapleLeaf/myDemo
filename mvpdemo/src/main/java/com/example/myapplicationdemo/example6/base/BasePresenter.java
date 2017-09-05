package com.example.myapplicationdemo.example6.base;

/**
 * Created by Administrator on 2017/9/5.
 */

public class BasePresenter<V extends BaseView> {

    private V view;

    public V getView() {
        return view;
    }

    public void attachView(V view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }
}

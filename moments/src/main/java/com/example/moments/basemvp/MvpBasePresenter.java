package com.example.moments.basemvp;

/**
 * Created by Administrator on 2017/9/5.
 */

public abstract class MvpBasePresenter<V extends MvpBaseView> {

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

    public abstract void cancelRequests();
}

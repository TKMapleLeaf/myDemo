package com.example.moments.basemvp;

/**
 * Created by Administrator on 2017/9/5.
 */

public interface CommonView extends MvpBaseView {

    void showProgress();

    void hideProgress();

    void success(Object o);

    void failure(String err);

}

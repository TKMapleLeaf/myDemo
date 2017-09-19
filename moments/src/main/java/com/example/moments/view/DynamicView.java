package com.example.moments.view;


import com.example.moments.basemvp.MvpBaseView;

/**
 * Created by Administrator on 2017/9/14.
 */

public interface DynamicView extends MvpBaseView {

    void success(Object o);

    void failure(String err);
}

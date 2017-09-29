package com.example.moments;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/9/19.
 */

public class MyApplication extends Application {

    public static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }
}

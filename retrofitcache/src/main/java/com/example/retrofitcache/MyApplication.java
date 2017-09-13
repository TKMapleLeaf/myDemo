package com.example.retrofitcache;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/9/13.
 */

public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}

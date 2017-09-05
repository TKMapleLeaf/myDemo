package com.example.myapplicationdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by tk on 17-9-3.
 */

public class MyApplication extends Application {
    public static Context context;
    public static Context applictionCOntext;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        applictionCOntext = getApplicationContext();

    }
}

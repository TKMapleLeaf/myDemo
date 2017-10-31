package com.example.xeb;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/9/13.
 */

public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        initX5();
        context = this;
    }

    private void initX5() {
        Intent intent = new Intent(this, PreLoadX5Service.class);
        startService(intent);
    }

}

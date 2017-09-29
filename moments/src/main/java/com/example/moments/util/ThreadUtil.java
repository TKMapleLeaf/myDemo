package com.example.moments.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ThreadUtil {

    private static Handler sHandler = new Handler(Looper.getMainLooper());

//    private static Executor sExecutor = Executors.newSingleThreadExecutor();
    private static Executor sExecutor = Executors.newFixedThreadPool(5);

    //子线程中执行任务
    public static void runOnSubThread(Runnable runnable){
        sExecutor.execute(runnable);
    }

    //在主线程中
    public static void runOnMainThread(Runnable runnable){
        sHandler.post(runnable);

    }
}

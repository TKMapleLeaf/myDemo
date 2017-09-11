package com.example.myapplicationdemo.utils;

/**
 * Created by Administrator on 2017/9/11.
 */

public class LogUtil {

    private static String TAG="retrofitcache";

    private static final  boolean DEBUG = false;

    public static void d(String text){
        android.util.Log.d(TAG,text);
    }

    public static void e(String text){
        android.util.Log.e(TAG,text);
    }

    public static void w(String text){
        android.util.Log.w(TAG,text);
    }

    public static void l(Exception e){
        if (DEBUG){
            e.printStackTrace();
        }else{
            android.util.Log.w(TAG,e.toString());
        }
    }
}

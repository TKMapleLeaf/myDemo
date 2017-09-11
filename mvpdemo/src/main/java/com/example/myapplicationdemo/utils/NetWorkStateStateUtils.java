package com.example.myapplicationdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/9/11.
 */

public class NetWorkStateStateUtils {

    public synchronized static Boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = cm.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    public synchronized static Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        return current != null && (current.isAvailable());
    }

//5种状态：
//在WLAN设置界面
//1，显示连接已保存，但标题栏没有，即没有实质连接上，       输出为：not connect， available
//2，显示连接已保存，标题栏也有已连接上的图标，            输出为：connect， available
//3，选择不保存后                                    输出为：not connect， available
//4，选择连接，在正在获取IP地址时                       输出为：not connect， not available
//5，连接上后                                       输出为：connect， available
}

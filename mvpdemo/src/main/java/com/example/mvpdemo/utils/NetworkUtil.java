package com.example.mvpdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @ClassName: NetworkUtil
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/5 11:51
 */
public class NetworkUtil {
    /**
     *      * 检查是否连接网络
     *      *
     *      * @param context
     *      * @return
     *      
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mobNetInfo != null && mobNetInfo.isConnected()) {
            return true;
        }
        if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
            return true;
        }
        return false;
    }
}

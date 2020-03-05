package com.example.mvpdemo.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

/**
 * @ClassName: AppUtil
 * @Description:存放整个应用会用到的东西
 * @Author: Administrator
 * @CreateDate: 2020/2/28 11:08
 */
public class AppUtil {
    public static String initialUrl = "http://gank.io/api/data/";

    public static String generateUrl(String dataCategory, int pageNum) {
        String finalUrl = initialUrl + dataCategory + "/20/" + pageNum;
        return finalUrl;
    }

    static AlertDialog.Builder builder;

    public static AlertDialog.Builder getDialog(Context context) {
        //防止每次都new一个实例出来
        if (builder == null) {
            builder = new AlertDialog.Builder(context);
        }
        return builder;
    }

}

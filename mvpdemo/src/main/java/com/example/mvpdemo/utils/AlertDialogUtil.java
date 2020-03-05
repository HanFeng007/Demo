package com.example.mvpdemo.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

/**
 * @ClassName: AlertDialogUtil
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/4 17:43
 */
public class AlertDialogUtil {

    static AlertDialog.Builder builder;

    public static AlertDialog.Builder getDialog(Context context) {
        //防止每次都new一个实例出来
        if (builder == null) {
            builder = new AlertDialog.Builder(context);
        }
        return builder;
    }

}

package com.example.mvpdemo.utils;

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
}

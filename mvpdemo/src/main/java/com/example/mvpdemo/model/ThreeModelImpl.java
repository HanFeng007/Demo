package com.example.mvpdemo.model;

import android.util.Log;

import com.example.mvpdemo.BuildConfig;
import com.example.mvpdemo.contract.ThirdContract;
import com.example.mvpdemo.utils.AppUtil;
import com.example.mvpdemo.utils.OkHttpUtil;

import java.util.HashMap;

import okhttp3.FormBody;

/**
 * @ClassName: ThreeModelImpl
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/3 14:45
 */
public class ThreeModelImpl implements ThirdContract.IModel {

    @Override
    public void requestData(HashMap<String, String> requestMap, final ModelCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : requestMap.keySet()) {
            //遍历出hashmap的值
            if (BuildConfig.DEBUG) {
                Log.e("ThreeModelImpl", key + "--->" + requestMap.get(key));
            }
            builder.add(key, requestMap.get(key));
        }
        FormBody formBody = builder.build();
        OkHttpUtil.getInstance().httpPost(AppUtil.JUHE_API + "/toh?", formBody, new OkHttpUtil.ICallback() {
            @Override
            public void invoke(String string) {
                callback.invoke(string);
            }
        });
    }
}

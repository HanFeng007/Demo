package com.example.mvpdemo.model;

import android.util.Log;

import com.example.mvpdemo.contract.HistoryDetailContract;
import com.example.mvpdemo.utils.AppUtil;
import com.example.mvpdemo.utils.OkHttpUtil;

import java.util.HashMap;

import okhttp3.FormBody;

/**
 * @ClassName: HistoryDetailModelImpl
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/6 9:18
 */
public class HistoryDetailModelImpl implements HistoryDetailContract.IModel {
    @Override
    public void requestData(HashMap<String, String> requestMap, final ModelCallback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : requestMap.keySet()) {
            Log.e("HistoryDetailModelImpl", key + "--->" + requestMap.get(key));
            builder.add(key,requestMap.get(key));
        }
        FormBody formBody = builder.build();
        OkHttpUtil.getInstance().httpPost(AppUtil.JUHE_API + "tohdet?", formBody, new OkHttpUtil.ICallback() {
            @Override
            public void invoke(String string) {
                callback.invoke(string);
            }
        });
    }
}

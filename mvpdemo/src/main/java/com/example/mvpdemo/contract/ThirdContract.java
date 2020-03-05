package com.example.mvpdemo.contract;

import com.example.mvpdemo.model.ModelCallback;

import java.util.HashMap;

/**
 * @ClassName: ThirdContract
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/3 14:39
 */
public interface ThirdContract {
    interface IView {

        void responseData(String string);
    }

    interface IModel {
        void requestData(HashMap<String, String> requestMap, ModelCallback callback);
    }
}

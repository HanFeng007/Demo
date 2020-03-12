package com.example.mvpdemo.contract;

import com.example.mvpdemo.model.ModelCallback;

import java.util.HashMap;

/**
 * @ClassName: HistoryDetailContract
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/6 9:12
 */
public interface HistoryDetailContract {
    interface IModel {
        void requestData(HashMap<String, String> requestMap, ModelCallback callback);
    }

    interface IView {

        void recieveData(String string);
    }
}

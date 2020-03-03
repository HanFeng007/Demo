package com.example.mvpdemo.contract;

import com.example.mvpdemo.model.ModelCallback;

/**
 * @ClassName: ThirdContract
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/3 14:39
 */
public interface ThirdContract {
    interface IView{

        void responseData(String string);
    }
    interface IModel{

        void requestData(String category, int pageNum, ModelCallback callback);
    }
}

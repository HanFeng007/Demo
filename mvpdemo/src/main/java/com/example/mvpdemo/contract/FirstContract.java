package com.example.mvpdemo.contract;

import com.example.mvpdemo.model.ModelCallback;

/**
 * @ClassName: FirstContract
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/2 17:52
 */
public interface FirstContract {
    interface IModel {
        void loadData(String category, int pageNum, ModelCallback callback);

    }

    interface IView {

        void recieveData(String string);
    }
}

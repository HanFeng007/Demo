package com.example.mvpdemo.contract;

import com.example.mvpdemo.model.ModelCallback;

/**
 * @ClassName: RetroPracContract
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/12 10:58
 */
public interface RetroPracContract {
    interface IModel {

        void retrofiRequest(ModelCallback callback);

        void retroRxjavaRequest(ModelCallback callback);

    }

    interface IView {
        void recieveData(String desc);

        void retroRxjavaRecieve(String string);
    }
}

package com.example.mvpdemo.contract;

import com.example.mvpdemo.bean.FirstBean;
import com.example.mvpdemo.model.ModelCallback;

import java.util.List;

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

        void recieveData(List<FirstBean.ResultsBean> string);
    }
}

package com.example.mvpdemo.contract;

import com.example.mvpdemo.bean.FirstBean;
import com.example.mvpdemo.model.ModelCallback;

import java.util.List;

/**
 * @ClassName: SecondContract
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/3 13:33
 */
public interface SecondContract {
    interface IModel {
        void requestData(String category, int pageNum, ModelCallback callback);
    }

    interface IView {

        void responseData(List<FirstBean.ResultsBean> string);
    }
}

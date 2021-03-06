package com.example.mvpdemo.presenter;

import com.example.mvpdemo.base.BasePresenter;
import com.example.mvpdemo.bean.FirstBean;
import com.example.mvpdemo.contract.FirstContract;
import com.example.mvpdemo.model.ModelCallback;
import com.example.mvpdemo.model.OneModelImpl;
import com.google.gson.Gson;

import java.util.List;

/**
 * @ClassName: FirstPresenter
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/2 17:52
 */
public class FirstPresenter extends BasePresenter<FirstContract.IView> {

    private OneModelImpl model;

    public FirstPresenter() {
        model = new OneModelImpl();
    }

    public void loadData(String localCategory, int pageNum) {
        model.loadData(localCategory, pageNum, new ModelCallback() {
            @Override
            public void invoke(String string) {
                List<FirstBean.ResultsBean> results = new Gson().fromJson(string, FirstBean.class).getResults();
                getView().recieveData(results);
            }
        });
    }

    @Override
    protected void detachView() {
        super.detachView();
        if (null!=model){
            model = null;
        }
    }
}

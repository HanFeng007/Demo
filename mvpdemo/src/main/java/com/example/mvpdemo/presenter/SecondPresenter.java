package com.example.mvpdemo.presenter;

import com.example.mvpdemo.base.BasePresenter;
import com.example.mvpdemo.bean.FirstBean;
import com.example.mvpdemo.contract.SecondContract;
import com.example.mvpdemo.model.ModelCallback;
import com.example.mvpdemo.model.TwoModelImpl;
import com.google.gson.Gson;

import java.util.List;

/**
 * @ClassName: SecondPresenter
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/3 13:34
 */
public class SecondPresenter extends BasePresenter<SecondContract.IView> {

    private TwoModelImpl model;

    public SecondPresenter() {
        model = new TwoModelImpl();
    }

    public void loadData(String category, int pageNum) {
        model.requestData(category, pageNum, new ModelCallback() {
            @Override
            public void invoke(String string) {
                List<FirstBean.ResultsBean> results = new Gson().fromJson(string, FirstBean.class).getResults();
                getView().responseData(results);
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

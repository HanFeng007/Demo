package com.example.mvpdemo.presenter;

import com.example.mvpdemo.base.BasePresenter;
import com.example.mvpdemo.contract.FirstContract;
import com.example.mvpdemo.model.ModelCallback;
import com.example.mvpdemo.model.ModelImpl;

/**
 * @ClassName: FirstPresenter
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/2 17:52
 */
public class FirstPresenter extends BasePresenter<FirstContract.IView> {

    private ModelImpl model;

    public FirstPresenter() {
        model = new ModelImpl();
    }

    public void loadData(String localCategory, int pageNum) {
        model.loadData(localCategory, pageNum, new ModelCallback() {
            @Override
            public void invoke(String string) {
                getView().recieveData(string);
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

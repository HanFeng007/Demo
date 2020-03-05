package com.example.mvpdemo.presenter;

import com.example.mvpdemo.base.BasePresenter;
import com.example.mvpdemo.contract.ThirdContract;
import com.example.mvpdemo.model.ModelCallback;
import com.example.mvpdemo.model.ThreeModelImpl;

import java.util.HashMap;

/**
 * @ClassName: ThirdPresenter
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/3 14:40
 */
public class ThirdPresenter extends BasePresenter<ThirdContract.IView> {

    private ThreeModelImpl model;

    public ThirdPresenter() {
        model = new ThreeModelImpl();
    }

    @Override
    protected void detachView() {
        super.detachView();
        if (model != null) {
            model = null;
        }
    }

    public void loadData(HashMap<String, String> requestMap) {
        model.requestData(requestMap, new ModelCallback() {
            @Override
            public void invoke(String string) {
                getView().responseData(string);
            }
        });
    }
}

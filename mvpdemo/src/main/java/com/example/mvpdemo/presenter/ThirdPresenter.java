package com.example.mvpdemo.presenter;

import com.example.mvpdemo.base.BasePresenter;
import com.example.mvpdemo.contract.ThirdContract;
import com.example.mvpdemo.model.ModelCallback;
import com.example.mvpdemo.model.ThreeModelImpl;

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

    public void loadData(String category, int pageNum) {
        model.requestData(category, pageNum, new ModelCallback() {
            @Override
            public void invoke(String string) {
                getView().responseData(string);
            }
        });
    }

    @Override
    protected void detachView() {
        super.detachView();
        if (model!=null){
            model = null;
        }
    }
}

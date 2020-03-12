package com.example.mvpdemo.presenter;

import com.example.mvpdemo.base.BasePresenter;
import com.example.mvpdemo.contract.HistoryDetailContract;
import com.example.mvpdemo.model.HistoryDetailModelImpl;
import com.example.mvpdemo.model.ModelCallback;

import java.util.HashMap;

/**
 * @ClassName: HistoryDetailPresenter
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/6 9:12
 */
public class HistoryDetailPresenter extends BasePresenter<HistoryDetailContract.IView> {

    private HistoryDetailModelImpl model;

    public HistoryDetailPresenter() {
        model = new HistoryDetailModelImpl();
    }

    @Override
    protected void detachView() {
        super.detachView();
        if (model!=null){
            model = null;
        }
    }

    public void loadData(HashMap<String, String> requestMap) {
        model.requestData(requestMap, new ModelCallback() {
            @Override
            public void invoke(String string) {
getView().recieveData(string);
            }
        });
    }
}

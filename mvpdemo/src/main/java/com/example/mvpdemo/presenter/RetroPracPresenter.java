package com.example.mvpdemo.presenter;

import android.util.Log;

import com.example.mvpdemo.base.BasePresenter;
import com.example.mvpdemo.contract.RetroPracContract;
import com.example.mvpdemo.model.ModelCallback;
import com.example.mvpdemo.model.RetroPracModelImpl;

/**
 * @ClassName: RetroPracPresenter
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/12 10:59
 */
public class RetroPracPresenter extends BasePresenter<RetroPracContract.IView> {

    private RetroPracModelImpl model;

    public RetroPracPresenter() {
        model = new RetroPracModelImpl();
    }

    @Override
    protected void detachView() {
        super.detachView();
        Log.e("RetroPracPresenter", "detachView!");
        if (null != model) {
            model = null;
        }
    }

    public void retrofiRequest() {
        model.retrofiRequest(new ModelCallback() {
            @Override
            public void invoke(String string) {
                getView().recieveData(string);
            }
        });
    }

    public void retroRxjavaRequest() {
        model.retroRxjavaRequest(new ModelCallback() {
            @Override
            public void invoke(String string) {
                getView().retroRxjavaRecieve(string);
            }
        });
    }
}

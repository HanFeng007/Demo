package com.example.mvpdemo.model;

import com.example.mvpdemo.contract.SecondContract;
import com.example.mvpdemo.utils.AppUtil;
import com.example.mvpdemo.utils.OkHttpUtil;

/**
 * @ClassName: TwoModelImpl
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/3 13:42
 */
public class TwoModelImpl implements SecondContract.IModel {
    @Override
    public void requestData(String category, int pageNum, final ModelCallback callback) {
        OkHttpUtil.getInstance().httpGet(AppUtil.generateUrl(category, pageNum), new OkHttpUtil.ICallback() {
            @Override
            public void invoke(String string) {
                callback.invoke(string);
            }
        });
    }
}

package com.example.mvpdemo.model;

import com.example.mvpdemo.contract.FirstContract;
import com.example.mvpdemo.utils.AppUtil;
import com.example.mvpdemo.utils.OkHttpUtil;

/**
 * @ClassName: OneModelImpl
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/2 20:27
 */
public class OneModelImpl implements FirstContract.IModel {
    @Override
    public void loadData(String category, int pageNum, final ModelCallback callback) {
        OkHttpUtil.getInstance().httpGet(AppUtil.generateUrl(category, pageNum), new OkHttpUtil.ICallback() {
            @Override
            public void invoke(String string) {
                callback.invoke(string);
            }
        });
    }
}

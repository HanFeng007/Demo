package com.example.mvpdemo.base;

import java.lang.ref.SoftReference;

/**
 * @ClassName: BasePresenter
 * @Description:只允许V层注入
 * @Author: Administrator
 * @CreateDate: 2020/2/28 10:19
 */
public class BasePresenter<V> {

    private SoftReference<V> viewSoftReference;

    protected void attachView(V view) {
        viewSoftReference = new SoftReference<>(view);
    }

    protected void detachView() {
        if (null != viewSoftReference) {
            viewSoftReference.clear();
            viewSoftReference = null;
        }
    }

    protected V getView() {
        return viewSoftReference.get();
    }
}

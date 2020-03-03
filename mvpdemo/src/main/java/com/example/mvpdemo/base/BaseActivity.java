package com.example.mvpdemo.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * @ClassName: BaseActivity
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/2/28 10:17
 */
public abstract class BaseActivity<V, P extends BasePresenter<V>> extends FragmentActivity {

    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 填充数据
     */
    protected abstract void initData();

    /**
     * 初始化控件
     */
    protected abstract void initView();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    /**
     * 创建P层
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 加载布局
     *
     * @return
     */
    protected abstract int getLayoutId();
}

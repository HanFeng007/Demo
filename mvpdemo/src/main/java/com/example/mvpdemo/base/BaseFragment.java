package com.example.mvpdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mvpdemo.BuildConfig;

/**
 * @ClassName: BaseFragment
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/2/28 10:25
 * <p>
 * TODO  fragment的懒加载完善
 */
public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {

    protected P mPresenter;
    private boolean hasLoadData;//是否加载过数据
    private boolean isViewPrepared;//视图是否创建完成
    private boolean isVisible;
    protected Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        return view;
    }

    /**
     * 保证了View不为空
     * 进行FindViewById
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragmentView(view);
        isViewPrepared = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (BuildConfig.DEBUG) {
            Log.e(this.getClass().getSimpleName().toString(), "setUserVisibleHint：" + isVisibleToUser);
        }
        isVisible = isVisibleToUser;
        isLoadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(this.getClass().getSimpleName().toString(), "onResume：");
        isLoadData();
    }

    private void isLoadData() {
        if (!hasLoadData && isViewPrepared && isVisible) {
            loadData();
            hasLoadData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasLoadData = false;
        isViewPrepared = false;
    }

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 初始化资源控件
     *
     * @param view
     */
    protected abstract void initFragmentView(View view);

    /**
     * 加载布局
     */
    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
            mPresenter = null;
        }
    }

    /**
     * 创建P层
     */
    protected abstract P createPresenter();
}

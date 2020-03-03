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
 *
 * TODO  fragment的懒加载完善
 */
public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {

    protected P mPresenter;
    private boolean isVisible = false;//界面是否可见
    private boolean isFocus = false;//是否获取到焦点
    private boolean isLoadData = false;//是否获取到焦点
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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (BuildConfig.DEBUG) {
            Log.e(this.getClass().getSimpleName().toString(), "setUserVisibleHint：" + isVisibleToUser);
        }
        isVisible = isVisibleToUser;
        isLoadData();//这里也要设置一个
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Log.e(this.getClass().getSimpleName().toString(), "onResume：");
        }
        isFocus = true;
        isLoadData();
    }

    private void isLoadData() {
        if (isFocus && isVisible&&!isLoadData) {
            loadData();
            isLoadData = true;
        }
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

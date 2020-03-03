package com.example.mvpdemo.ui.frag;


import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mvpdemo.R;
import com.example.mvpdemo.adapter.FirstAdapter;
import com.example.mvpdemo.base.BaseFragment;
import com.example.mvpdemo.bean.FirstBean;
import com.example.mvpdemo.contract.FirstContract;
import com.example.mvpdemo.presenter.FirstPresenter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends BaseFragment<FirstContract.IView, FirstPresenter> implements FirstContract.IView {

    private RecyclerView firstRv;
    private String localCategory;
    private int pageNum = 1;
    Gson gson = new Gson();
    private List<FirstBean.ResultsBean> results;
    private FirstAdapter adapter;
    private boolean isLoad;
    private boolean isFirst;
    private SmartRefreshLayout firstSrt;
    private boolean isRefresh;

    @Override
    protected void loadData() {
        isFirst = true;
        mPresenter.loadData(localCategory, pageNum);
    }

    @Override
    protected void initFragmentView(View view) {
        TabLayout tablayout = getActivity().findViewById(R.id.tablayout);
        localCategory = tablayout.getTabAt(0).getText().toString();
        firstRv = view.findViewById(R.id.firstRv);
        firstSrt = view.findViewById(R.id.firstRefresh);
        adapter = new FirstAdapter(R.layout.list_item, results);
        //设置适配器
        firstRv.setAdapter(adapter);
        firstRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        //动画设置
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(false);
        //上拉加载
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e("FirstFragment", "加载!");
                isLoad = true;
                pageNum += 1;
                mPresenter.loadData(localCategory, pageNum);
            }
        }, firstRv);


        //下拉刷新
        firstSrt.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                isRefresh = true;
                mPresenter.loadData(localCategory, pageNum);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected FirstPresenter createPresenter() {
        return new FirstPresenter();
    }

    @Override
    public void recieveData(final String string) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //解析json,得到数据集
                FirstBean firstBean = gson.fromJson(string, FirstBean.class);
                results = firstBean.getResults();
                if (results.size() != 0) {

                    if (isFirst) {
                        adapter.addData(results);
                        adapter.loadMoreComplete();
                        isFirst = false;
                    }

                    if (isLoad) {
                        adapter.loadMoreComplete();//需要调用刷新完成的方法
                        adapter.addData(results);
                        adapter.loadMoreComplete();
                        isLoad = false;
                    }

                    if (isRefresh){
                        Log.e("FirstFragment", "刷新的响应");
                        firstSrt.finishRefresh(true);
                        adapter.setNewData(results);
                        isRefresh = false;
                    }

                } else {
                    Log.e("FirstFragment", "么得数据咯");
                    isLoad = false;
                    isFirst = false;
                    isRefresh = false;
                    adapter.loadMoreEnd();
                    firstSrt.finishRefresh(false);
                }
            }
        });
    }
}

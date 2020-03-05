package com.example.mvpdemo.ui.frag;


import android.content.Intent;
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
import com.example.mvpdemo.contract.SecondContract;
import com.example.mvpdemo.presenter.SecondPresenter;
import com.example.mvpdemo.ui.act.WebActivity;
import com.example.mvpdemo.utils.LoadingDialogUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * 加载时显示loading
 */
public class SecondFragment extends BaseFragment<SecondContract.IView, SecondPresenter> implements SecondContract.IView {

    private SmartRefreshLayout secondSrl;
    private RecyclerView secondRv;
    private String category;
    private int pageNum = 1;
    Gson gson = new Gson();
    private List<FirstBean.ResultsBean> results;
    private FirstAdapter adapter;
    private boolean isLoad;
    private boolean isRefresh;
    private boolean isFirst;

    @Override
    protected void loadData() {
        isFirst = true;
        mPresenter.loadData(category, pageNum);
        LoadingDialogUtils.show(getActivity());
    }

    @Override
    protected void initFragmentView(View view) {
        secondSrl = view.findViewById(R.id.secondRefresh);
        secondRv = view.findViewById(R.id.secondRv);

        TabLayout tabLayout = getActivity().findViewById(R.id.tablayout);
        category = tabLayout.getTabAt(1).getText().toString();

        adapter = new FirstAdapter(R.layout.list_item, results);
        secondRv.setAdapter(adapter);
        secondRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter.openLoadAnimation();
        adapter.isFirstOnly(false);
        //上拉加载
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum += 1;
                isLoad = true;
                secondSrl.setEnableRefresh(false);//是否启用下拉刷新功能
                mPresenter.loadData(category, pageNum);
            }
        }, secondRv);
        //下拉刷新
        secondSrl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                isRefresh = true;
                adapter.setEnableLoadMore(false);//是否启用上拉加载功能
                mPresenter.loadData(category, pageNum);
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FirstBean.ResultsBean item = (FirstBean.ResultsBean) adapter.getItem(position);
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", item.getUrl());
                startActivity(intent);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    protected SecondPresenter createPresenter() {
        return new SecondPresenter();
    }

    @Override
    public void responseData(final String string) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialogUtils.dismiss();

                FirstBean bean = gson.fromJson(string, FirstBean.class);
                results = bean.getResults();
                if (results.size() != 0) {

                    if (isLoad) {
                        adapter.addData(results);
                        adapter.loadMoreComplete();
                        isLoad = false;
                        secondSrl.setEnableRefresh(true);
                    }

                    if (isRefresh) {
                        adapter.setNewData(results);
                        isRefresh = false;
                        secondSrl.finishRefresh();
                        adapter.setEnableLoadMore(true);
                    }

                    if (isFirst) {
                        adapter.addData(results);
                    }

                } else {//没有数据
                    isLoad = false;
                    isRefresh = false;
                    adapter.loadMoreEnd();
                    secondSrl.finishRefresh();
                    secondSrl.setEnableRefresh(true);
                    adapter.setEnableLoadMore(true);
                }


            }
        });
    }
}

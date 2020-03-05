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
import com.example.mvpdemo.contract.ThirdContract;
import com.example.mvpdemo.presenter.ThirdPresenter;
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
 */
public class ThirdFragment extends BaseFragment<ThirdContract.IView, ThirdPresenter> implements ThirdContract.IView {

    private SmartRefreshLayout thirdRefresh;
    private RecyclerView thirdRv;
    private String category;
    private int pageNum = 1;
    Gson gson = new Gson();
    private List<FirstBean.ResultsBean> results;
    private FirstAdapter adapter;
    private boolean isLoad;
    private boolean isFirst;
    private boolean isRefresh;

    @Override
    protected void loadData() {
        isFirst = true;
        mPresenter.loadData(category, pageNum);
        LoadingDialogUtils.show(getActivity());
    }

    @Override
    protected void initFragmentView(View view) {
        thirdRefresh = view.findViewById(R.id.thirdRefresh);
        thirdRv = view.findViewById(R.id.thirdRv);

        TabLayout tabLayout = getActivity().findViewById(R.id.tablayout);
        category = tabLayout.getTabAt(2).getText().toString();

        adapter = new FirstAdapter(R.layout.list_item, results);
        thirdRv.setAdapter(adapter);
        thirdRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNum += 1;
                isLoad = true;
                mPresenter.loadData(category, pageNum);
            }
        }, thirdRv);

        thirdRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                isRefresh = true;
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
        return R.layout.fragment_third;
    }

    @Override
    protected ThirdPresenter createPresenter() {
        return new ThirdPresenter();
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
                    }

                    if (isFirst) {
                        adapter.addData(results);
                    }

                    if (isRefresh) {
                        adapter.setNewData(results);
                        thirdRefresh.finishRefresh();
                        isRefresh = false;
                    }


                } else {
                    isLoad = false;
                    isRefresh = false;
                    adapter.loadMoreEnd();
                    thirdRefresh.finishRefresh();
                }
            }
        });
    }
}

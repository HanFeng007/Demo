package com.example.mvpdemo.ui.frag;


import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mvpdemo.R;
import com.example.mvpdemo.adapter.HistoryAdapter;
import com.example.mvpdemo.base.BaseFragment;
import com.example.mvpdemo.bean.HistoryBean;
import com.example.mvpdemo.contract.ThirdContract;
import com.example.mvpdemo.presenter.ThirdPresenter;
import com.example.mvpdemo.ui.act.HistoryDetailActivity;
import com.example.mvpdemo.utils.AppUtil;
import com.example.mvpdemo.utils.LoadingDialogUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends BaseFragment<ThirdContract.IView, ThirdPresenter> implements ThirdContract.IView {

    private SmartRefreshLayout thirdRefresh;
    private RecyclerView thirdRv;
    private HashMap<String, String> requestMap = new HashMap<>();
    Gson gson = new Gson();
    private List<HistoryBean.ResultBean> result;
    private HistoryAdapter adapter;

    @Override
    protected void loadData() {
        requestMap.put("key", AppUtil.JUHE_KEY);
        requestMap.put("v", "1.0");
        requestMap.put("month", "10");
        requestMap.put("day", "1");
        mPresenter.loadData(requestMap);
        LoadingDialogUtils.show((Activity) context);
    }

    @Override
    protected void initFragmentView(View view) {
        thirdRefresh = view.findViewById(R.id.thirdRefresh);
        thirdRv = view.findViewById(R.id.thirdRv);
        adapter = new HistoryAdapter(R.layout.list_item, result);
        thirdRv.setAdapter(adapter);
        thirdRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HistoryBean.ResultBean item = (HistoryBean.ResultBean) adapter.getItem(position);
                int id = item.getId();
                Intent intent = new Intent(getActivity(), HistoryDetailActivity.class);
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
                HistoryBean historyBean = gson.fromJson(string, HistoryBean.class);

                if (historyBean.getReason().contains("请求成功")) {
                    result = historyBean.getResult();
                    if (result.size() != 0 && result != null) {
                        adapter.addData(result);
                    }
                }
            }
        });

    }

}

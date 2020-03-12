package com.example.mvpdemo.ui.act;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvpdemo.BuildConfig;
import com.example.mvpdemo.R;
import com.example.mvpdemo.adapter.HistoryAdapter;
import com.example.mvpdemo.base.BaseActivity;
import com.example.mvpdemo.bean.HistoryBean;
import com.example.mvpdemo.contract.HistoryDetailContract;
import com.example.mvpdemo.presenter.HistoryDetailPresenter;
import com.example.mvpdemo.utils.AppUtil;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.List;

public class HistoryDetailActivity extends BaseActivity<HistoryDetailContract.IView, HistoryDetailPresenter> implements HistoryDetailContract.IView {

    private android.view.View viewImmer;
    private com.example.mvpdemo.utils.TitleBar detailTitle;
    private String id;
    private HistoryBean historyDetailBean;
    private RecyclerView detailRv;
    private HistoryAdapter adapter;
    private List<HistoryBean.ResultBean> result;

    @Override
    protected void initData() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("key", AppUtil.JUHE_KEY);
        requestMap.put("v", BuildConfig.VERSION_CODE + "");
        requestMap.put("id", id);
        mPresenter.loadData(requestMap);
    }

    @Override
    protected void initView() {

        viewImmer = findViewById(R.id.viewImmer);
        ImmersionBar.with(this)
                .statusBarView(viewImmer)
                .init();

        detailTitle = findViewById(R.id.detail_title);
        detailTitle.setTitle("详情");

        id = getIntent().getStringExtra("id");

        detailRv = findViewById(R.id.detail_rv);

        adapter = new HistoryAdapter(R.layout.list_item, result);
        detailRv.setAdapter(adapter);
        detailRv.setLayoutManager(new LinearLayoutManager(HistoryDetailActivity.this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected HistoryDetailPresenter createPresenter() {
        return new HistoryDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_detail;
    }

    @Override
    public void recieveData(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                historyDetailBean = new Gson().fromJson(string, HistoryBean.class);


                result = historyDetailBean.getResult();

                for (int i = 0; i < result.size(); i++) {
                    result.get(i).setDetail(true);
                }


                adapter.addData(result);

            }
        });
    }
}

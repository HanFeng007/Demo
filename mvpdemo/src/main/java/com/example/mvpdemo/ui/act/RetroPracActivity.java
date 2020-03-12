package com.example.mvpdemo.ui.act;

import android.view.View;

import com.example.mvpdemo.R;
import com.example.mvpdemo.base.BaseActivity;
import com.example.mvpdemo.contract.RetroPracContract;
import com.example.mvpdemo.presenter.RetroPracPresenter;


public class RetroPracActivity extends BaseActivity<RetroPracContract.IView, RetroPracPresenter> implements RetroPracContract.IView, View.OnClickListener {
    private android.widget.Button retrofiRequest;
    private android.widget.Button retroRxjavaRequest;
    private android.widget.TextView tvRetrofit;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        retrofiRequest = findViewById(R.id.retrofi_request);
        retroRxjavaRequest = findViewById(R.id.retro_rxjava_request);

        retrofiRequest.setOnClickListener(this);
        retroRxjavaRequest.setOnClickListener(this);
        tvRetrofit = findViewById(R.id.tv_retrofit);
    }

    @Override
    protected RetroPracPresenter createPresenter() {
        return new RetroPracPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_retro_prac;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retrofi_request:
                mPresenter.retrofiRequest();
                break;
            case R.id.retro_rxjava_request:
                mPresenter.retroRxjavaRequest();
                break;
            default:
                break;
        }
    }

    @Override
    public void recieveData(String desc) {
        tvRetrofit.setText(desc);
    }

    @Override
    public void retroRxjavaRecieve(String string) {
        tvRetrofit.setText(string);
    }
}

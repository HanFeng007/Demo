package com.example.mvpdemo.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mvpdemo.R;
import com.example.mvpdemo.bean.HistoryBean;

import java.util.List;

/**
 * @ClassName: FirstAdapter
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/2 20:39
 */
public class HistoryAdapter extends BaseQuickAdapter<HistoryBean.ResultBean, BaseViewHolder> {
    public HistoryAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryBean.ResultBean item) {

        helper.getView(R.id.title_tv).setVisibility(View.VISIBLE);
        helper.setText(R.id.title_tv, item.getTitle())
                .setText(R.id.desc_tv, item.getDes());
    }
}
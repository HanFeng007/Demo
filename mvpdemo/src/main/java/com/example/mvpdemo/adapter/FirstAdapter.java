package com.example.mvpdemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mvpdemo.R;
import com.example.mvpdemo.bean.FirstBean;

import java.util.List;

/**
 * @ClassName: FirstAdapter
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/2 20:39
 */
public class FirstAdapter extends BaseQuickAdapter<FirstBean.ResultsBean, BaseViewHolder> {
    public FirstAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FirstBean.ResultsBean item) {
        helper.setText(R.id.desc_tv, item.getDesc())
                .setText(R.id.author_tv, item.getWho());
    }
}
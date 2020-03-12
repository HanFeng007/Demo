package com.example.mvpdemo.ui.frag;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mvpdemo.BuildConfig;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends BaseFragment<ThirdContract.IView, ThirdPresenter> implements ThirdContract.IView, View.OnClickListener {

    //    private SmartRefreshLayout thirdRefresh;
    private RecyclerView thirdRv;
    private HashMap<String, String> requestMap = new HashMap<>();
    Gson gson = new Gson();
    private List<HistoryBean.ResultBean> result;
    private HistoryAdapter adapter;
    private Button chooseDate;

    //得到当前日期
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH);//应该+1
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void loadData() {
        requestMap.put("key", AppUtil.JUHE_KEY);
        requestMap.put("v", BuildConfig.VERSION_CODE + "");
        requestMap.put("month", (currentMonth + 1) + "");
        requestMap.put("day", currentDay + "");
        mPresenter.loadData(requestMap);
        LoadingDialogUtils.show((Activity) context);
    }

    @Override
    protected void initFragmentView(View view) {
//        thirdRefresh = view.findViewById(R.id.thirdRefresh);
        chooseDate = view.findViewById(R.id.btn_chooseDate);
        chooseDate.setText(generateDate(currentYear, (currentMonth + 1), currentDay));
        chooseDate.setOnClickListener(this);


        thirdRv = view.findViewById(R.id.thirdRv);
        adapter = new HistoryAdapter(R.layout.list_item, result);
        thirdRv.setAdapter(adapter);
        thirdRv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HistoryBean.ResultBean item = (HistoryBean.ResultBean) adapter.getItem(position);

                Intent intent = new Intent(getActivity(), HistoryDetailActivity.class);
                intent.putExtra("id", item.get_id());
                startActivity(intent);
            }
        });

    }

    private String generateDate(int currentYear, int currentMonth, int currentDay) {
        return currentYear + "-" + currentMonth + "-" + currentDay;
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
    public void responseData(final HistoryBean historyBean) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LoadingDialogUtils.dismiss();
//                HistoryBean historyBean = gson.fromJson(string, HistoryBean.class);

                if (historyBean.getReason().contains("请求成功")) {
                    result = historyBean.getResult();
                    if (result.size() != 0 && result != null) {
                        adapter.setNewData(result);
                    }
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chooseDate:

                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.e("ThirdFragment设置日期触发", generateDate(year, (month+1), dayOfMonth));//month需要+1

                        chooseDate.setText(generateDate(year, (month+1), dayOfMonth));//按钮赋值

                        if (requestMap != null || requestMap.size() != 0) {
                            requestMap.clear();
                        }

                        requestMap.put("key", AppUtil.JUHE_KEY);
                        requestMap.put("v", BuildConfig.VERSION_CODE + "");
                        requestMap.put("month", (month + 1) + "");
                        requestMap.put("day", dayOfMonth + "");

                        mPresenter.loadData(requestMap);



                    }
                }, currentYear, currentMonth, currentDay).show();

                break;
            default:
                break;
        }
    }
}

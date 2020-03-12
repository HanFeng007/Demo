package com.example.mvpdemo.model;

import com.example.mvpdemo.bean.Gank_And;
import com.example.mvpdemo.bean.Gank_DateBean;
import com.example.mvpdemo.contract.RetroPracContract;
import com.example.mvpdemo.retrofit.ApiService;
import com.example.mvpdemo.utils.AppUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ClassName: RetroPracModelImpl
 * @Description:retrofit简单使用例子
 * @Author: Administrator
 * @CreateDate: 2020/3/12 11:00
 */
public class RetroPracModelImpl implements RetroPracContract.IModel {
    /**
     * 单纯使用Retrofit，不加Rxjava的使用
     */
    @Override
    public void retrofiRequest(final ModelCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofit.create(ApiService.class)
                .getMessage()
                .enqueue(new Callback<Gank_And>() {
                    @Override
                    public void onResponse(Call<Gank_And> call, retrofit2.Response<Gank_And> response) {
                        String desc = response.body().getResults().get(0).getDesc();
                        callback.invoke(desc);
                    }

                    @Override
                    public void onFailure(Call<Gank_And> call, Throwable t) {
                        String messageInfo = t.getMessage();
                        callback.invoke(messageInfo);
                    }
                });
    }

    /**
     * 使用Rxjava后，返回的不是Call<T>而是一个Observable<T>的对象了。
     */
    @Override
    public void retroRxjavaRequest(final ModelCallback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//Rxjava适配器
                .build();
        retrofit.create(ApiService.class)
                .getDateMessge()
                .subscribeOn(Schedulers.io())//io加载数据
                .observeOn(AndroidSchedulers.mainThread())//主线程显示数据
                .subscribe(new Action1<Gank_DateBean>() {
                    @Override
                    public void call(Gank_DateBean gank_dateBean) {
                        String category = gank_dateBean.getCategory().get(0);
                        callback.invoke(category);
                    }
                });
    }
}

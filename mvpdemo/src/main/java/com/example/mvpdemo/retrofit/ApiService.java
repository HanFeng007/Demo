package com.example.mvpdemo.retrofit;

import com.example.mvpdemo.bean.Gank_And;
import com.example.mvpdemo.bean.Gank_DateBean;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @ClassName: ApiService
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/3/12 10:04
 */
public interface ApiService {

    @GET("random/data/Android/20")
    Call<Gank_And> getMessage();

    @GET("day/2015/08/07")
    Observable<Gank_DateBean> getDateMessge();
}

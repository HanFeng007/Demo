package com.example.mvpdemo.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @ClassName: OkHttpUtils
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2020/2/28 11:04
 */
public class OkHttpUtil {
    static OkHttpUtil okHttpUtil;
    private OkHttpClient.Builder builder;
    private Request.Builder requestBuilder;
    private OkHttpClient okHttpClient;

    private OkHttpUtil() {
        builder = new OkHttpClient.Builder();
        okHttpClient = builder.addInterceptor(new RequestLoggerInterceptor())
                .addInterceptor(new ResponseLoggerInterceptor())
                .build();
        requestBuilder = new Request.Builder();//省的每次都new  request操作,直接builder出来,随后需要什么往里加,build出来即可
    }

    public static OkHttpUtil getInstance() {
        if (null == okHttpUtil) {
            synchronized (OkHttpUtil.class) {
                if (null == okHttpUtil) {
                    okHttpUtil = new OkHttpUtil();
                }
            }
        }
        return okHttpUtil;
    }

    public void httpGet(String url, final ICallback callback) {
        Request request = requestBuilder.url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.invoke("数据错误");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callback.invoke(response.body().string());
            }
        });
    }

    public void httpPost(String urlString, FormBody formBody, final ICallback callback) {
        Request request = requestBuilder.url(urlString).method("POST", formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.invoke("数据错误");

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                callback.invoke(response.body().string());
            }
        });
    }

    /**
     * 接口用于回调数据
     */
    public interface ICallback {
        void invoke(String string);
    }

    /**
     * 请求拦截器
     */
    static class RequestLoggerInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Log.e(this.getClass().getSimpleName(), "url    =  : " + request.url());
            Log.e(this.getClass().getSimpleName(), "method =  : " + request.method());
            Log.e(this.getClass().getSimpleName(), "headers=  : " + request.headers());
            Log.e(this.getClass().getSimpleName(), "body   =  : " + request.body());

            return chain.proceed(request);
        }
    }

    /**
     * 响应拦截器
     */
    static class ResponseLoggerInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());

            Log.e(this.getClass().getSimpleName(), "code    =  : " + response.code());
            Log.e(this.getClass().getSimpleName(), "message =  : " + response.message());
            Log.e(this.getClass().getSimpleName(), "protocol=  : " + response.protocol());

            if (response.body() != null && response.body().contentType() != null) {
                MediaType mediaType = response.body().contentType();
                String string = response.body().string();
                Log.e(this.getClass().getSimpleName(), "mediaType=  :  " + mediaType.toString());
                Log.e(this.getClass().getSimpleName(), "string   =  : " + string);
                ResponseBody responseBody = ResponseBody.create(mediaType, string);
                return response.newBuilder().body(responseBody).build();
            } else {
                return response;
            }
        }
    }
}


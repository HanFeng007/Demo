package com.example.mvpdemo.utils;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    private OkHttpClient okHttpClient;
    private static SSLContext sslContext;

    private OkHttpUtil() {
        builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new ResponseLoggerInterceptor());
        }
        okHttpClient = builder.connectTimeout(10, TimeUnit.SECONDS)
                .callTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
//                .sslSocketFactory(getSSLSocketFactory())//配置
//                .hostnameVerifier(getHostnameVerifier())//配置
                .build();
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

    public void httpGet(String url, Context context, final ICallback callback) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        excuteCall(call, callback, context);
    }

    public void httpPost(String urlString, Context context, FormBody formBody, final ICallback callback) {
        Request request = new Request.Builder().url(urlString).method("POST", formBody).build();
        Call call = okHttpClient.newCall(request);
        excuteCall(call, callback, context);
    }

    private void excuteCall(Call call, final ICallback callback, final Context context) {
        LoadingDialogUtil.show((Activity) context);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                LoadingDialogUtil.dismiss();
                final String string = response.body().string();
                final JSONObject jsonObject = JSON.parseObject(string);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int errorCode = jsonObject.getIntValue("errorCode");
                        if (errorCode == 0) {
                            callback.invoke(string);
                        } else {
                            callback.invokeFail("服务错误");
                        }
                    }
                });
            }
        });
    }

    /**
     * 接口用于回调数据
     */
    public interface ICallback {
        void invoke(String string);

        void invokeFail(String string);
    }

    /**
     * 请求响应拦截器
     */
    static class ResponseLoggerInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.e(this.getClass().getSimpleName(), "-------HTTP START------");
            Log.e(this.getClass().getSimpleName(), "url    =  : " + request.url());
            Log.e(this.getClass().getSimpleName(), "method =  : " + request.method());
            Log.e(this.getClass().getSimpleName(), "headers=  : " + request.headers());
            Log.e(this.getClass().getSimpleName(), "body   =  : " + request.body());

            Response response = chain.proceed(chain.request());

            Log.e(this.getClass().getSimpleName(), "code    =  : " + response.code());
            Log.e(this.getClass().getSimpleName(), "message =  : " + response.message());
            Log.e(this.getClass().getSimpleName(), "protocol=  : " + response.protocol());

            if (response.body() != null && response.body().contentType() != null) {
                MediaType mediaType = response.body().contentType();
                String string = response.body().string();
                Log.e(this.getClass().getSimpleName(), "mediaType=  :  " + mediaType.toString());
                Log.e(this.getClass().getSimpleName(), "string   =  : " + string);
                Log.e(this.getClass().getSimpleName(), "-------HTTP END------");
                ResponseBody responseBody = ResponseBody.create(mediaType, string);
                return response.newBuilder().body(responseBody).build();
            } else {
                Log.e(this.getClass().getSimpleName(), "-------HTTP END------");
                return response;
            }
        }
    }


    //获取这个SSLSocketFactory
    private SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }

    //获取HostnameVerifier
    private static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }

}


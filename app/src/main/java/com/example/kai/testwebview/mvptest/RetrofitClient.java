package com.example.kai.testwebview.mvptest;

import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.NewsApi;

/**
 * Created by kai on 2018/4/19.
 */

public class RetrofitClient {

    private static RetrofitClient mInstance;
    private static Retrofit retrofit;
    private static String mBaseUrl;
    private static OkHttpClient okHttpClient;
    private static int DEFAULT_TIMEOUT = 3;

    private RetrofitClient(OkHttpClient okHttpClient, String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(initOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    private OkHttpClient initOkHttpClient(){
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor())
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        return  okHttpClient;
    }


    /**
     * 配置自定义的OkHttpClient
     *
     * @param okHttpClient
     * @return
     */
    public static RetrofitClient initClient_BaseUrl(OkHttpClient okHttpClient, @NonNull String baseUrl) {
        mBaseUrl = baseUrl;
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient(okHttpClient, baseUrl);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取Retrofit的实例
     *
     * @return
     */
    public static RetrofitClient getInstance() {
        if (mBaseUrl == null) {
            throw new RuntimeException("Please initialize Your \"BaseUrl\" in Application before use");
        }
        if (mInstance == null) {
            throw new RuntimeException("Please initialize Your \"RetrofitCoreClient\" in Application before use");
        }
        return mInstance;
    }

    /**
     * 构建请求
     *
     * @param clz 请求接口
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> clz) {
        return retrofit.create(clz);
    }

    public NewsApi api(){
        return getInstance().create(NewsApi.class);
    }

}

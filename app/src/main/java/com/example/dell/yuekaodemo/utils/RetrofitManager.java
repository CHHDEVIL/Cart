package com.example.dell.yuekaodemo.utils;

import android.support.v7.view.menu.MenuWrapperFactory;

import com.example.dell.yuekaodemo.constants.ConstantApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 2018/7/26.
 */

public class RetrofitManager {
    private static String BASE_URL = ConstantApi.BASE_URL;
    private final Retrofit retrofit;

    private static class SingleHolder {
        private static final RetrofitManager _INSTANT = new RetrofitManager(BASE_URL);
    }

    public static RetrofitManager getDefault() {
        return SingleHolder._INSTANT;
    }

    public RetrofitManager(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000,TimeUnit.MILLISECONDS)
                .connectTimeout(5000,TimeUnit.MILLISECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    public <T> T create(Class<T> tClass){
        return retrofit.create(tClass);
    }
}

package com.zmj.mvc.example;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author Zmj
 * @date 2018/10/11
 */
public class MyApplication extends Application {
    OkHttpUtils okHttpUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))   //实现日志记录
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .build();

        okHttpUtils = OkHttpUtils.initClient(okHttpClient);
    }
}

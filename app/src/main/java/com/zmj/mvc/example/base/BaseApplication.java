package com.zmj.mvc.example.base;

import android.app.Application;

import okhttp3.OkHttpClient;

/**
 * @author Zmj
 * @date 2018/10/10
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;

    public static BaseApplication getInstance(){return instance;}

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        initGlide();
    }

    /**
     * 图片加载框架Glide ，使用ok'Http处理网络请求
     */
    private void initGlide() {
//        OkHttpClient okHttpClient =
    }
}

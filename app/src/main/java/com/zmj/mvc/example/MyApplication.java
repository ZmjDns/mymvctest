package com.zmj.mvc.example;

import android.app.Application;
import android.content.SharedPreferences;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.wxlib.util.SysUtil;
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

    public static final String APPKEY = "23015524";// "25243882";




    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))   //实现日志记录
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .build();

        okHttpUtils = OkHttpUtils.initClient(okHttpClient);


        SysUtil.setApplication(this);

        if(SysUtil.isTCMSServiceProcess(this)){
            return;
        }

        if (SysUtil.isMainProcess()){
            YWAPI.init(this,APPKEY);
        }
    }


}

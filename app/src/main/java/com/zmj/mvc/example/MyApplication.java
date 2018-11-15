package com.zmj.mvc.example;

import android.app.Application;
import android.content.SharedPreferences;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.wxlib.util.SysUtil;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.helpdesk.easeui.UIProvider;
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

    public static final String APPKEY ="1429181112068259#kefuchannelapp60284";// "23015524";// "25243882";
    public static final String APPTOKEN = "60284";




    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))   //实现日志记录
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L,TimeUnit.MILLISECONDS)
                .build();

        okHttpUtils = OkHttpUtils.initClient(okHttpClient);


        //阿里百川
        /*SysUtil.setApplication(this);

        if(SysUtil.isTCMSServiceProcess(this)){
            return;
        }

        if (SysUtil.isMainProcess()){
            YWAPI.init(this,APPKEY);
        }*/

        //环信SDK
       /* EMOptions options = new EMOptions();
        //默认添加好友时，是不需要验证，此处修改为需要验证
        options.setAcceptInvitationAlways(false);
        //是否将消息附件上传到环信服务器，默认true是使用环信服务器上传下载，fasl需要自己处理处理附件消息的上传下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);

        //初始化
        EMClient.getInstance().init(getApplicationContext(),options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);*/

        ChatClient.Options options = new ChatClient.Options();
        //options.setAppkey("1102181112107557#zmj-test-kefu-77");
        //options.setTenantId("60284");
        options.setAppkey(APPKEY);
        options.setTenantId(APPTOKEN);
        //客服SDK初始化
        if (!ChatClient.getInstance().init(this,options)){
            return;
        }
        //客服EaseUI的初始化
        UIProvider.getInstance().init(this);
        //也可以设置其他属性
    }


}

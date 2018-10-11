package com.zmj.mvc.example.utils;

import android.content.Context;

import okhttp3.OkHttpClient;

/**
 * @author Zmj
 * @date 2018/10/11
 */
public class OkManager {

    private Context context;
    private OkHttpClient client;
    private OkManager okManager;

    private OkManager (){
        client = new OkHttpClient();
    }
}

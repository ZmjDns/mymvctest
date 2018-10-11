package com.zmj.mvc.example.controller;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Zmj
 * @date 2018/10/11
 * okhttp的拦截器
 */
public class LoggingInterceptor implements Interceptor {

    private final String TAG = "LoggerInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long startTime = System.nanoTime();

        Log.d(TAG, String.format("Sending request %s on %s%n%s",request.url(),chain.connection(),request.headers()));

        Response response = chain.proceed(request);

        long endTime = System.nanoTime();
        Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",response.request().url(),(endTime - startTime) / 1e6d,response.headers()));
        return response;
    }
}

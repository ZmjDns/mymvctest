package com.zmj.mvc.example.model;

import android.util.Log;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * @author Zmj
 * @date 2018/10/30
 */
public class LoginModel {
    private String responseDataJson;
    private String url = "http://222.198.38.53:3000/api/users";

    public void login(final String loginName, final String password, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpUtils
                            .post()
                            .url(url)
                            .addParams("loginUser",loginName)
                            .addParams("password",password)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    if (listener != null){
                                        listener.onError(e);
                                    }
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    if (listener != null){
                                        listener.onfinish(response);
                                    }
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String parseJSONWithJSONObj(String jsonData){
        String loginMark = "";
        try {
            Log.d("RESPONSE", "parseJSONWithJSONObj: " + jsonData);
            JSONObject jsonObject = new JSONObject(jsonData);
            loginMark = jsonObject.getString("loginMark");
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginMark;
    }
}

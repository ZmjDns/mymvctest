package com.zmj.mvc.example.view.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zmj.mvc.example.R;
import com.zmj.mvc.example.entery.User;

import java.io.File;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

public class TestOkhttpAct extends AppCompatActivity implements View.OnClickListener {

    private ImageView pic;
    private String TAG= "OKTEST";
    String url = "http://www.csdn.net/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_okhttp);

        initView();
    }

    private void initView() {
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_post).setOnClickListener(this);
        findViewById(R.id.btn_postJson).setOnClickListener(this);
        findViewById(R.id.btn_postFile).setOnClickListener(this);
        findViewById(R.id.btn_downFile).setOnClickListener(this);
        findViewById(R.id.btn_getPic).setOnClickListener(this);
        pic = findViewById(R.id.img_pic);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get:
                okGet();
                break;
            case R.id.btn_post:
                okPost();
                break;
            case R.id.btn_postJson:
                okPostJsonString();
                break;
            case R.id.btn_postFile:
                okPostFormFile();
                break;
            case R.id.btn_downFile:
                okGetDownloadFile();
                break;
            case R.id.btn_getPic:
                okShowPic();
                break;
            default:
                break;
        }
    }

    private void okGet(){

        OkHttpUtils
                .get()
                .url(url)
                .addParams("username","hyman")
                .addParams("password","123")
                .build()    //execute中传入参数为异步请求
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d(TAG + "_GET", "onError: " + e.getMessage() + "ID:" + id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG + "_GET", "onResponse: " + response + "ID" + id);
                    }
                });
    }

    private void okPost(){
        OkHttpUtils
                .post()
                .url(url)
                .addParams("username","hyman")
                .addParams("password","123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d(TAG + "POST", "onError: " + e.getMessage() + "ID" + id);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG + "POST", "onResponse: " + response + "ID" + id);
                    }
                });
    }

    private void okPostJsonString(){
        //注意：提交一个Gson字符串到服务器，传Json的时候不要通过addHeader去设置contentType，使用Media Type.parse("application/json;charset=utf-8")
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(new User("hyman","123")))
                .mediaType(MediaType.parse("application/json;charset=utf-8"))           //特别注意
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d(TAG + "JSON", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG + "JSON", "onResponse: " + response);
                    }
                });
    }

    private void okPostFormFile(){
        String url = "http://192.168.31.36:8080/collectionBugLogger/?action=upload_bug_logger";
        File file = new File("storage/emulated/0/test.txt");
        OkHttpUtils
                .post()
                .addFile("aa","/test.txt",file)
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d(TAG + "FILE", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d(TAG + "FILE", "onResponse: " + response);
                    }
                });
    }

    private void okGetDownloadFile(){
        String url = "http://192.168.31.36:8080/collectionBugLogger/file/testdownload.txt";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),"newdownload.txt") {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d(TAG + "_DOWNLOADFILE", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Log.d(TAG + "_DOWNLOADFILE", "onResponse: " + response.getAbsolutePath());
                    }
                });
    }

    private void okShowPic(){
        String url = "http://192.168.31.36:8080/collectionBugLogger/file/pic.jpg";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d(TAG + "SHOWPIC", "onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        pic.setImageBitmap(response);
//                        Log.d(TAG + "SHOWPIC", "onResponse: " + );
                    }
                });
    }

    @Override
    protected void onDestroy() {
        //Activity结束时取消请求
//        当请求时用当前activity做为tag时，如：
//              OKHttpUtils.post().url(url).tag(this).build()
//              可以用以下方法统一取消
//        OkHttpUtils.cancelTag(this);//取消以Activity.this作为tag的请求
        super.onDestroy();
    }
}

package com.zmj.mvc.example.AsyncTask;

import android.support.annotation.Nullable;
import android.util.Log;

import com.zmj.mvc.example.controller.LoggingInterceptor;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

/**
 * @author Zmj
 * @date 2018/10/10
 */
public class OkhttpTest {
    //Get异步请求
    public void asyncGet(){
        String url = "http://www.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()  //默认请求
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    //post提交String
    public static void asyncPostString(){
        MediaType mediaType = MediaType.parse("text/x-markdown;charset=utf-8");
        String requestBody = "I am Tester";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType,requestBody))
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("OKHTTPTEST", response.protocol() + "//" + response.code() + "//" + response.message());
                Headers headers  = response.headers();
                for (int i = 0; i <headers.size(); i++ ){
                    Log.d("OKHTTPTESTHEADER", headers.name(i) + "--->" + headers.value(i));
                }
                Log.d("OKHTTPTESTRESPONSE", "onResponse:" + response.body().string());
            }
        });
    }
    //Post提交流
    public static void asynPostStream(){
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse("text/x-markdown;charset=utf-8");
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("I am Tester.");
            }
        };

        final Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TESTSTREAM", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TESTSTREAM", response.protocol() + "//" + response.code() + "//" + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++){
                    Log.d("TESTSTREAMHEADERS", headers.name(i) + "-->" + headers.value(i));
                }
                Log.d("TESTSTREAM", "onResponse: " + response.body().string());

            }
        });
    }
    //Post提交文件
    public static void asynPostFile(){
        MediaType mediaType = MediaType.parse("text/x-markdown;charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File("/storage/emulated/0/test.txt");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(mediaType,file))
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TESTFILE", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TESTFILE", response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d("TESTFILE", headers.name(i) + ":" + headers.value(i));
                }
                Log.d("TESTFILE", "onResponse: " + response.body().string());
            }
        });
    }
    //Post提交表单
    public static void asynPostForm(){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("srarch","Tester Park")
                .build();

        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TESTFORM", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TESTFORM", response.protocol() + "//" + response.code() + "//" + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++){
                    Log.d("TESTFORMHEADER", headers.name(i) + headers.value(i));
                }
                Log.d("TESTFORM", "onResponse: " + response.body().string());
            }
        });
    }

    public static final String IMGUR_CLIENT_ID = "...";
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    //Post提交分块请求
    public static void asynPostMultipartBody(){
        OkHttpClient okHttpClient = new OkHttpClient();

        MultipartBody multipartBody = new MultipartBody.Builder("Aab03x")
                .setType(MultipartBody.FORM)
                .addPart(
                        Headers.of("Content-Disposition","form-data;name=\"title\""),
                        RequestBody.create(null,"Square Logo")
                ).addPart(
                        Headers.of("Content-Disposition","form-data;name=\"image\""),
                        RequestBody.create(MEDIA_TYPE_PNG,new File("storage/emulated/0/51.jpg"))   //"website/static/logo-square.png"
                ).build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(multipartBody)
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TESTMULTIPART", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TESTMULTIPART", "onResponse: " + response.body().string());
            }
        });
    }
    //拦截器
    public static void asynInterceptor(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())       //添加拦截器
                .build();

        Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")      //http://www.publicobject.com/helloworld.txt  //http://123.206.55.106:8088/collectionBugLogger/file/text.txt
                .header("User-Agent","Okhttp Example")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("INTERCEPTORFFF", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (responseBody != null){
                    Log.d("INTERCEPTOR", responseBody.string().trim());
                    responseBody.close();
                }
            }
        });
    }
}

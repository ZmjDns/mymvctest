package com.zmj.mvc.example;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zmj.mvc.example.AsyncTask.MyHandler;
import com.zmj.mvc.example.AsyncTask.OkhttpTest;
import com.zmj.mvc.example.AsyncTask.WeakAsyncTask;
import com.zmj.mvc.example.base.BaseDrawerActivity;
import com.zmj.mvc.example.sendmsgmvp.SendMsgFragment;
import com.zmj.mvc.example.utils.Permissions;
import com.zmj.mvc.example.view.activity.SortDataAct;
import com.zmj.mvc.example.view.activity.TestOkhttpAct;
import com.zmj.mvc.example.view.fragment.MyFragment;
import com.zmj.mvc.example.view.fragment.RecongiseFragment;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;

public class MainActivity extends BaseDrawerActivity {

    private RecongiseFragment recongiseFragment;
    private SendMsgFragment sendMsgFragment;
    private MyFragment myFragment;

    private TextView tv_recongise,tv_second,tv_thrid,tv_my;

    private static int REQ_STORAGE = 110;
    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View convertView) {
//        findViewById(R.id.tv_first).setOnClickListener(this);
//        findViewById(R.id.tv_second).setOnClickListener(this);
//        findViewById(R.id.tv_thrid).setOnClickListener(this);
//        findViewById(R.id.tv_fourth).setOnClickListener(this);

        tv_recongise =  findViewById(R.id.tv_first);
        tv_second = findViewById(R.id.tv_second);
        tv_thrid = findViewById(R.id.tv_thrid);
        tv_my = findViewById(R.id.tv_fourth);

        tv_recongise.setOnClickListener(this);
        tv_second.setOnClickListener(this);
        tv_thrid.setOnClickListener(this);
        tv_my.setOnClickListener(this);

        //
        initUserId();

        requestPermission();

        tv_recongise.performClick();
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()){
            case R.id.tv_first:
//                firstClick(view);
                setSelectedFragment(0);
                setBottomDefault();
                tv_recongise.setTextColor(Color.parseColor("#ffff00"));
                break;
            case R.id.tv_second:
//                secondClick(view);
                setSelectedFragment(1);
                setBottomDefault();
                tv_second.setTextColor(Color.parseColor("#ffff00"));
                break;
            case R.id.tv_thrid:
//                thridClick(view);
                setSelectedFragment(2);
                setBottomDefault();
                tv_thrid.setTextColor(Color.parseColor("#ffff00"));
                break;
            case R.id.tv_fourth:
//                fourthClick(view);
                setSelectedFragment(3);
                setBottomDefault();
                tv_my.setTextColor(Color.parseColor("#ffff00"));
                break;
            default:
                break;
        }
    }

    private void firstClick(View view){
        Toast.makeText(this,"我是首页！",Toast.LENGTH_SHORT).show();
//        OkhttpTest.asyncPostString();
        startActivity(new Intent(this, TestOkhttpAct.class));
    }

    private void secondClick(View view){
        Toast.makeText(this,"我是次页！",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, SortDataAct.class));
//        OkhttpTest.asynPostStream();
    }

    private void thridClick(View view){
        Toast.makeText(this,"提交复杂文件",Toast.LENGTH_SHORT).show();
//
        try{
            OkhttpTest.asynPostMultipartBody();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void fourthClick(View view){
        Toast.makeText(this,"提交FIle",Toast.LENGTH_SHORT).show();
//        OkhttpTest.asynPostForm();
//        OkhttpTest.asynPostFile();
        OkhttpTest.asynInterceptor();
    }


    //AsyncTask的弱引用
    AsyncTask asyncTask = new MscheduleTasks(this).execute();

    private static class MscheduleTasks extends WeakAsyncTask<Void,Void,String,MainActivity>{

        public MscheduleTasks(MainActivity target) {
            super(target);
        }

        @Override
        protected void onPreExecute(MainActivity target) {
            //获取Context，执行一些操作
            Context context = target;
        }

        @Override
        protected String doInBackground(MainActivity target, Void... voids) {
            //进行耗时操作
            return null;
        }

        @Override
        protected void onPostExecute(MainActivity target, String s) {
            //更新UI线程
        }
    }

    //Handler的弱引用
    final MainActHandler mainActHandler = new MainActHandler(this);
    private static class MainActHandler extends MyHandler{

        public MainActHandler(Context context) {
            super(context);
        }

        @Override
        protected void handleMessage(Context context, Message msg) {
            //获取context 进行操作

        }
    }

    @TargetApi(23)
    private void requestPermission(){
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(Permissions.STORAGE_PERMISSION,REQ_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在Activity结束时回收弱引用
        if (asyncTask != null){
            asyncTask.cancel(true);
        }

        if (mainActHandler != null){
            mainActHandler.removeCallbacksAndMessages(null);
        }
    }

    //fragmnet change method
    private void setSelectedFragment(int i){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        hideFragment(ft);

        switch (i){
            case 0:
                if (recongiseFragment == null){
                    recongiseFragment = new RecongiseFragment();

                    ft.add(R.id.fragment_content,recongiseFragment);
                }
                ft.show(recongiseFragment);
                break;
            case 1:
                if (sendMsgFragment == null){
//                    recongiseFragment = new RecongiseFragment();
                    sendMsgFragment = new SendMsgFragment();
                    ft.add(R.id.fragment_content,sendMsgFragment);
                }
                ft.show(sendMsgFragment);
                break;
            case 2:
                if (myFragment == null){
                    myFragment = new MyFragment();

                    ft.add(R.id.fragment_content,myFragment);
                }
                ft.show(myFragment);
            case 3:
                if (myFragment == null){
                    myFragment = new MyFragment();

                    ft.add(R.id.fragment_content,myFragment);
                }
                ft.show(myFragment);
        }

        ft.commit();
    }

    private void hideFragment(FragmentTransaction ft){
        if (recongiseFragment != null){
            ft.hide(recongiseFragment);
        }
        if (sendMsgFragment != null){
            ft.hide(sendMsgFragment);
        }
        if (myFragment != null){
            ft.hide(myFragment);
        }
    }

    private void setBottomDefault(){
        tv_recongise.setTextColor(Color.parseColor("#cfcfc2"));
        tv_second.setTextColor(Color.parseColor("#cfcfc2"));
        tv_thrid.setTextColor(Color.parseColor("#cfcfc2"));
        tv_my.setTextColor(Color.parseColor("#cfcfc2"));
    }

    //
    private void initUserId(){
        SharedPreferences sp = getSharedPreferences("usinfo",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("BCuserID","18302451883");
        editor.commit();
    }

}

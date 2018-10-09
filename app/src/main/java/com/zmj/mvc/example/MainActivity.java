package com.zmj.mvc.example;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.zmj.mvc.example.AsyncTask.MyHandler;
import com.zmj.mvc.example.AsyncTask.WeakAsyncTask;
import com.zmj.mvc.example.base.BaseDrawerActivity;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

public class MainActivity extends BaseDrawerActivity {


    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View convertView) {
        TextView tv_first,tv_second,tv_thrid,tv_fourth;
        tv_first = findViewById(R.id.tv_first);
        tv_second = findViewById(R.id.tv_second);
        tv_thrid = findViewById(R.id.tv_thrid);
        tv_fourth = findViewById(R.id.tv_fourth);

        tv_first.setOnClickListener(this);
        tv_second.setOnClickListener(this);
        tv_thrid.setOnClickListener(this);
        tv_fourth.setOnClickListener(this);

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()){
            case R.id.tv_first:

        }
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
}

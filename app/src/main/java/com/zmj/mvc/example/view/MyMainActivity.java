package com.zmj.mvc.example.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zmj.mvc.example.R;
import com.zmj.mvc.example.base.BaseActivity;

public class MyMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    public void initData(@NonNull Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void initView(Bundle savedInstanceState, View convertView) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }

    public void sendMessage(View view){

    }
}

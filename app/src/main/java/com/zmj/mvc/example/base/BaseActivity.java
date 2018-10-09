package com.zmj.mvc.example.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * @author Zmj
 * @date 2018/9/28
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    protected View mContentView;
    protected Activity mActivity;

    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        setContentView(bindLayout());
        initView(savedInstanceState,mContentView);
        doBusiness();
//        isNetWorkAvaliable(getApplicationContext());
    }

    @SuppressLint("ResourceType")
    protected void setBaseView(@LayoutRes int layoutId){
        if (layoutId <= 0) return;
        setContentView(mContentView = LayoutInflater.from(this).inflate(layoutId,null));
    }

    protected void isNetWorkAvilable(Context context){
        if (false){
            Toast.makeText(this,"当前网络不可用",Toast.LENGTH_SHORT);
        }
    }

    /**
     * 判断是否快速点击
     *
     * @return true 是   false 否
     */
    private Boolean isFastClick(){
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200){
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(final View view) {
        if (!isFastClick()){
            onWidgetClick(view);
        }
    }
}

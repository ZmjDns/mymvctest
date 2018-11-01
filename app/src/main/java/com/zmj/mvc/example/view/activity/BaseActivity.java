package com.zmj.mvc.example.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zmj.mvc.example.presenter.BasePersenter;

/**
 * @author Zmj
 * @date 2018/10/31
 */
public abstract class BaseActivity<V,T extends BasePersenter<V>> extends AppCompatActivity {
    //表示层persenter的引用

    public T girPersenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //让子类Act在初始化的时候就进行选择Persenter
        girPersenter = createPersenter();
        girPersenter.onAttachView((V)this);
    }

    protected abstract T createPersenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        girPersenter.deachView();
    }
}

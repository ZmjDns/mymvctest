package com.zmj.mvc.example.presenter;

import java.lang.ref.WeakReference;

/**
 * @author Zmj
 * @date 2018/10/31
 */
public class BasePersenter<T> {

    //1.view的引用
//    IGirlView girlView;

    WeakReference<T> mViewRef;

    //进行绑定
    public void onAttachView(T view){
        mViewRef = new WeakReference<>(view);
    }

    //进行解绑
    public void deachView(){
        mViewRef.clear();
    }

}

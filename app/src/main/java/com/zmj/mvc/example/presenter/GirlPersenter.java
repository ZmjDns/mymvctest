package com.zmj.mvc.example.presenter;

import android.view.View;

import com.zmj.mvc.example.entery.Girl;
import com.zmj.mvc.example.model.GirlModel;
import com.zmj.mvc.example.model.IGirlModel;
import com.zmj.mvc.example.view.IGirlView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Zmj
 * @date 2018/10/30
 * 表示层
 */
public class GirlPersenter<T extends IGirlView> extends BasePersenter<T> {
//    //1.view的引用
////    IGirlView girlView;
//
//    WeakReference<T> mViewRef;

    //2.model的引用
    GirlModel girlModel = new GirlModel();

    //构造方法
    public GirlPersenter(/*T view*/) {
//        this.girlView = view;
//        this.mViewRef = new WeakReference<>(view);
    }

//    //进行绑定
//    public void onAttachView(T view){
//        mViewRef = new WeakReference<>(view);
//    }
//
//    //进行解绑
//    public void deachView(){
//        mViewRef.clear();
//    }


    //4.执行操作
    public void fetch(){
        if (mViewRef.get() != null){//将原来的girlView 替换为弱引用的mViewRef.get()
            mViewRef.get().showLoading();
            if (girlModel != null){
                girlModel.loadGirl(new IGirlModel.GirlOnLoadListener() {
                    @Override
                    public void onComplete(List<Girl> girls) {
                        if (mViewRef.get() != null){
                            mViewRef.get().showGirls(girls);
                        }
                    }
                });
            }
        }
    }
}

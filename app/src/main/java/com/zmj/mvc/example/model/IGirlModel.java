package com.zmj.mvc.example.model;

import com.zmj.mvc.example.entery.Girl;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/10/30
 * 加载数据
 */
public interface IGirlModel {
    void loadGirl(GirlOnLoadListener listener);
    //设计一个内部回调接口
    interface GirlOnLoadListener{
        void onComplete(List<Girl> girls);
    }
}

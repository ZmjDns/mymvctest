package com.zmj.mvc.example.view;

import com.zmj.mvc.example.entery.Girl;

import java.util.List;

/**
 * @author Zmj
 * @date 2018/10/30
 * 定义所有的逻辑
 */
public interface IGirlView {
    void showLoading();

    //显示ListView中的数据（使用回调的方式返回数据）
    void showGirls(List<Girl> girls); //通过参数将结果回调到Act中
    //此处不使用方法返回是为了防止线程卡死
}

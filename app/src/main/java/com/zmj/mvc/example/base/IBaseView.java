package com.zmj.mvc.example.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @author Zmj
 * @date 2018/9/28
 */
public interface IBaseView extends View.OnClickListener {

    /**
     * 初始化数据
     * @param bundle 传递过来的Bundle
     */
    void initData(@NonNull final Bundle bundle);

    /**
     * 绑定布局
     * @return 布局ID
     */
    int bindLayout();

    /**
     * 初始化View
     * @param savedInstanceState
     * @param convertView
     */
    void initView(final Bundle savedInstanceState,final View convertView);

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * 视图点击操作
     * @param view 视图
     */
    void onWidgetClick(final View view);

    /**
     * 检查网络是否可用
     * @param context
     */
//    void isNetWorkAvaliable(Context context);
}

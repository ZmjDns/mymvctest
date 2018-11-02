package com.zmj.mvc.example.httpprocess;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Zmj
 * @date 2018/11/1
 */
public abstract class HttpCallBack<Result> implements ICallBack {
    @Override
    public void onSuccess(String result) {  //将获取的String转化为Result类型
        Gson gson = new Gson();
        Class<?> clz = analysisClassInfo(this);
    }

    public abstract void onSuccess(Result result);
    //反射
    public static Class<?> analysisClassInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)(genType)).getActualTypeArguments();
        return (Class<?>) params[0];
    }

}

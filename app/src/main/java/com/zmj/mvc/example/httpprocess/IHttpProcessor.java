package com.zmj.mvc.example.httpprocess;

import java.util.Map;

/**
 * @author Zmj
 * @date 2018/11/1
 */
public interface IHttpProcessor {
    //网络访问：个体，post，get，Del,put
    void post(String url, Map<String,Object> params,ICallBack callBack);

    void get(String url,Map<String,Object> params,ICallBack callBack);
}

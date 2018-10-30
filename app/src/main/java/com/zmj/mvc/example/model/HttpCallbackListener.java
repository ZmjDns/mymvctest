package com.zmj.mvc.example.model;

/**
 * @author Zmj
 * @date 2018/10/30
 */
public interface HttpCallbackListener {

    void onfinish(String response);

    void onError(Exception e);
}

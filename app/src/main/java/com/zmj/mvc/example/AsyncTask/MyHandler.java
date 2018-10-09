package com.zmj.mvc.example.AsyncTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author Zmj
 * @date 2018/10/8
 */
public abstract class MyHandler extends Handler {

    private final WeakReference<Context> targetAct;

    public MyHandler(Context context){
        targetAct= new WeakReference<Context>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        Context taget = targetAct.get();
        if (taget != null){
            this.handleMessage(taget,msg);
        }
    }

    protected abstract void handleMessage(Context context,Message msg);
}

package com.zmj.mvc.example.AsyncTask;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * @author Zmj
 * @date 2018/10/8
 */
public abstract class WeakAsyncTask<Params,Progress,Result,WeakTarget> extends AsyncTask<Params,Progress,Result> {

    //外部类弱引用
    protected final WeakReference<WeakTarget> mTarget;

    public WeakAsyncTask(WeakTarget target) {
        mTarget = new WeakReference<WeakTarget>(target);
    }

    @Override
    protected void onPreExecute() {
        final WeakTarget target = mTarget.get();
        if (target != null){
            this.onPreExecute(target);
        }
    }

    @Override
    protected Result doInBackground(Params... params) {
        final WeakTarget target = mTarget.get();
        if (target != null){
            return this.doInBackground(target,params);
        }else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        final WeakTarget target = mTarget.get();
        if (target != null){
            this.onPostExecute(target,result);
        }
    }

    protected abstract void onPreExecute(WeakTarget target);

    protected abstract Result doInBackground(WeakTarget target,Params...params);

    protected abstract void onPostExecute(WeakTarget target,Result result);
}

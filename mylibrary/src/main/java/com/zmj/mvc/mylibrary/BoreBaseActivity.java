package com.zmj.mvc.mylibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.zmj.mvc.mylibrary.utils.DialogUtils;
import com.zmj.mvc.mylibrary.utils.ToastUtils;

/**
 * @author Zmj
 * @date 2018/10/10
 */
public class BoreBaseActivity extends AppCompatActivity {

    protected String TAG;
    private Dialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        init();
    }

    private void init() {
        TAG = getClass().getSimpleName();
        progressDialog = DialogUtils.createProgressDialog(this);
    }


    public void intent2Activity(Class<? extends Activity> targetActivity){
        Intent intent = new Intent(this,targetActivity);
        startActivity(intent);
    }

    public void showToast(String message){
        ToastUtils.showToast(this,message, Toast.LENGTH_SHORT);
    }

    public void showLog(String msg){
        Log.i(TAG, msg);
    }

    public void shoeProgressDialog(){
        progressDialog.show();
    }
}

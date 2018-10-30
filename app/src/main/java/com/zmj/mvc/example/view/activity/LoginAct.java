package com.zmj.mvc.example.view.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.mvc.example.R;
import com.zmj.mvc.example.model.HttpCallbackListener;
import com.zmj.mvc.example.presenter.LoginPresenter;
import com.zmj.mvc.example.view.viewinterface.LoginUser;

public class LoginAct extends AppCompatActivity implements LoginUser{

    private Button login;
    private EditText name;
    private EditText password;
    private LoginPresenter loginPresenter;
    private TextView loginMessage;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ("success".equals(msg.obj.toString())){
                Toast.makeText(LoginAct.this,"cuccess",Toast.LENGTH_LONG).show();
            }else {
                loginMessage.setText("filed:" + msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView(){
        login = findViewById(R.id.btn_login);
        name = findViewById(R.id.et_name);
        password = findViewById(R.id.et_password);
        loginMessage = findViewById(R.id.tv_loginMessage);
        loginPresenter = new LoginPresenter(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(new HttpCallbackListener() {
                    @Override
                    public void onfinish(String response) {
                        Message message = new Message();
                        message.obj = response;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(Exception e) {
//                        e.printStackTrace();
                        Message message = new Message();
                        message.obj = e.toString();
                        mHandler.sendMessage(message);
                    }
                });
            }
        });
    }



    @Override
    public String getLoginName() {
        return name.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }
}

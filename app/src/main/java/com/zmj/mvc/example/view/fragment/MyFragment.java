package com.zmj.mvc.example.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.ChatManager;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.zmj.mvc.example.R;
import com.zmj.mvc.example.view.EaseUIAct;

import java.io.InputStream;
import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/1
 */
public class MyFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MyFragment";

    private Button btn_login,btn_testkefu1,btn_admin,btn_kefu_user1;
    private EditText ed_accountid,ed_password;

    public MyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment,container,false);

        ed_accountid = view.findViewById(R.id.ed_accountid);
        ed_password = view.findViewById(R.id.ed_password);
        btn_login = view.findViewById(R.id.btn_login);

        btn_testkefu1 = view.findViewById(R.id.btn_testkefu1);
        btn_admin = view.findViewById(R.id.btn_admin);
        btn_kefu_user1 = view.findViewById(R.id.btn_kefu_user1);

        btn_login.setOnClickListener(this);
        btn_testkefu1.setOnClickListener(this);
        btn_admin.setOnClickListener(this);
        btn_kefu_user1.setOnClickListener(this);

        /*btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接转人工客服
//                Intent intent = new IntentBuilder(getActivity())
//                        .setServiceIMNumber("Admin")
//                        .setScheduleAgent(ContentFactory.createAgentIdentityInfo("292570927@qq.com"))
//                        .build();
//                startActivity(intent);
                //登陆后转人工客服
                *//*ChatClient.getInstance().login("user2", "123456", new Callback() {
                    @Override
                    public void onSuccess() {
                        if (ChatClient.getInstance().isLoggedInBefore()){
                            //已经登陆，可以进入会话界面
                            Intent intent = new IntentBuilder(getActivity())
                                    .setServiceIMNumber("kefu1")
                                    .build();
                            startActivity(intent);
                        }else {
                            //没有登陆需要登陆

                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.d(TAG, "onError: 登陆出错：" +  i + "s" + s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });*//*
                //指定客服
                *//*Intent intent = new IntentBuilder(getActivity())
                        .setServiceIMNumber("kefu_robot")
                        .setScheduleAgent(ContentFactory.createAgentIdentityInfo("kefu_robot@qq.com"))
                        .build();
                startActivity(intent);*//*
            }
        });*/


        return view;
    }

    //判断是否连接服务器
    private void isConnection(){
        ChatClient.getInstance().addConnectionListener(new ChatClient.ConnectionListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected(int i) {
                Log.d(TAG, "onDisconnected: 没有连接服务器");
            }
        });
    }
    //添加消息监听
    private void addInfoListener(){
        ChatClient.getInstance().chatManager().addMessageListener(new ChatManager.MessageListener() {
            @Override
            public void onMessage(List<Message> list) {
                //普通消息
            }

            @Override
            public void onCmdMessage(List<Message> list) {
                //收到命令消息，命令消息不存数据库，一般用来作为系统通知，例如留言评论更新，
                //会话被客服接入，被转接，被关闭提醒

            }

            @Override
            public void onMessageStatusUpdate() {
                //消息的状态修改，一般可以用来刷新列表，显示最新的状态
            }

            @Override
            public void onMessageSent() {
                //发送消息后，会调用，可以在此刷新列表，显示最新的消息
            }
        });
    }


    @Override
    public void onDestroy() {
        //结束时退出登陆
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: 退出成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: 退出失败");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                //注册
                register();
                break;
            case R.id.btn_testkefu1:
                //登陆
                login();

                /*ChatClient.getInstance().login("zmj", "123456", new Callback() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new IntentBuilder(getActivity()).setServiceIMNumber("kefuchannelimid_853236").build();
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });*/
                break;
            case R.id.btn_admin:
                //客服会话
                openChating();
                /*ChatClient.getInstance().login("zmj", "123456", new Callback() {
                    @Override
                    public void onSuccess() {
                        //直接访问客服的IM服务账号，会先有机器人接待，需要转人工的时候，直接转到该IM服务账号下
                        Intent intent1 = new IntentBuilder(getActivity())
                                .setServiceIMNumber("kefuchannelimid_853236")
//                                .setScheduleQueue(ContentFactory.createQueueIdentityInfo("app组")) //需填写正确的技能组名称
                                .build();
                        startActivity(intent1);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });*/
                break;
            case R.id.btn_kefu_user1:
                //自定义界面
                openDIYChatAct();
                break;
            default:
                break;
        }
    }

    private void register(){
        ChatClient.getInstance().register(ed_accountid.getText().toString(), ed_password.getText().toString(), new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: 注册成功");
                Toast.makeText(getActivity(),"注册成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: 注册失败 code:" + i + "message:" + s );
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private void login(){
        ChatClient.getInstance().login(ed_accountid.getText().toString(), ed_password.getText().toString(), new Callback() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: 登陆成功");
//                Toast.makeText(getActivity(),"登陆成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: 登陆失败 Code：" + i + "message:" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    private void openChating(){
        if (ChatClient.getInstance().isLoggedInBefore()){
            //已经登陆，直接进入会话
            Intent chatIntent = new IntentBuilder(getActivity()).setTargetClass(EaseUIAct.class)
                    .setServiceIMNumber("kefuchannelimid_853236")
                    .build();
            startActivity(chatIntent);

        }else {
            login();
            openChating();
        }
    }

    private void openDIYChatAct(){
        Toast.makeText(getActivity(),"稍等",Toast.LENGTH_SHORT).show();
    }
}

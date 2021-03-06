package com.zmj.mvc.example.view;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.mvc.example.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpSocketAct extends AppCompatActivity implements Runnable {
    private TextView tv_msg;
    private EditText ed_msg;
    private Button btn_send;
    private static final String HOST = "192.168.18.83";
    private static final int PORT = 8888;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;

    //接收线程发送过来的消息，并用TextView醉驾显示
    public Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String message = msg.obj.toString();
            tv_msg.setText(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_socket);

        tv_msg = findViewById(R.id.tv_msg);
        ed_msg = findViewById(R.id.et_msg);
        btn_send = findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = ed_msg.getText().toString().trim();
                if (socket.isConnected()){//服务器连接
                    if (!socket.isOutputShutdown()){//socket的输出流没有关闭
                        out.println(msg);//点击按钮发送消息
                        ed_msg.setText("");//清空编辑框
                    }
                }
            }
        });

        //启动线程，连接服务器，并用死循环守候，接收服务器发送来的消息
        new Thread(this).start();
    }

    //连接服务器
    private void connection(){
        try {
            socket = new Socket(HOST,PORT);//连接服务器
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            in = new BufferedReader(inputStreamReader);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//接收消息的对象流
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);//发送消息的对象流

        }catch (Exception e){
            showDialog("连接服务器失败" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showDialog(String msg){
        new AlertDialog
                .Builder(this)
                .setTitle("通知")
                .setMessage(msg)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    //读取服务器发来的信息
    @Override
    public void run() {
        connection();//连接到服务器
        try {
            while (true){
                if (!socket.isClosed()){    //socked没有关闭
                    if (socket.isConnected()){  //连接正常
                        if (!socket.isInputShutdown()){ //输入流没有断开
                            String getLine;
                            if ((getLine = in.readLine()) != null){
                                getLine += "\n";
                                Message message = new Message();
                                message.obj = getLine;
                                mHandler.sendMessage(message);//通知UI更新
                            }else {
                                showDialog("没有获取到服务器传来的数据");
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

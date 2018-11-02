package com.zmj.mvc.example.sendmsgmvp;

import android.util.Log;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 * @author Zmj
 * @date 2018/11/2
 */
public class SendMsgModel implements ISendModel {

    private static final String TAG = "SendMsgModel";
    Socket socket;

    @Override
    public void connection(final ConnectionListener connectionListener) {
        new Thread(){
            @Override
            public void run() {
                //建立连接
                try {
                    if (socket == null){
                        socket = new Socket("192.168.31.36",9999);
                    }
                    Log.d(TAG, "connection: 与服务器建立连接：" + socket);
                    //成功
                    connectionListener.onConnSuccess(socket);

                }catch (Exception e){
                    //失败
                    connectionListener.onConnFailed(e);
                }
            }
        }.start();
    }


    //在persenter中，获取到要发送的数据时调用此方法来发送信息
    public void sendmsg(final String msg, final SendMsgListener sendMsgListener) {
        new Thread(){
            @Override
            public void run() {
                //发送逻辑
                try {
                   DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
                   writer.writeUTF(msg);    //将msg转化为utf-8格式

                    Log.d(TAG, "run: 发送的消息：" + msg);
                    sendMsgListener.onSendMsgSuccess("发送成功" + msg);
                }catch (Exception e){
                    Log.d(TAG, "run: 发送失败：" + e);
                }
            }
        }.start();
    }

}

package com.zmj.mvc.example.sendmsgmvp;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.net.Socket;

/**
 * @author Zmj
 * @date 2018/11/2
 */
public class SendMasgPersenter<T extends ISendMsgView>{

    private static String TAG = "SendMasgPersenter";

    WeakReference<T> mRefView;

    //持有Mod引用
    SendMsgModel sendMsgModel = new SendMsgModel();

    public SendMasgPersenter() {
    }

    public void onAttach(T view){
        mRefView = new WeakReference<>(view);
    }

    //操作数据
    public void dealWithMsg(){
        if (mRefView.get() != null){
            sendMsgModel.connection(new ISendModel.ConnectionListener() {
                @Override
                public void onConnSuccess(Socket socket) {
                    if (mRefView.get().getSendMsg().length() < 1) return;
                    sendMsgModel.sendmsg(mRefView.get().getSendMsg(), new ISendModel.SendMsgListener() {
                        @Override
                        public void onSendMsgSuccess(String str) {
                            Log.d(TAG, "onSendMsgSuccess: 发送消息成功" + str);
                        }

                        @Override
                        public void onSendMsgFailed(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }

                @Override
                public void onConnFailed(Exception e) {

                }
            });
        }
    }


    public void onDeath(){
        mRefView.clear();
    }
}

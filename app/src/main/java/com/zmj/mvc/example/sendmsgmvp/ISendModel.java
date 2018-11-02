package com.zmj.mvc.example.sendmsgmvp;

import java.net.Socket;

/**
 * @author Zmj
 * @date 2018/11/2
 */
public interface ISendModel {
    void connection(ConnectionListener connectionListener);

    interface ConnectionListener{
        void onConnSuccess(Socket socket);

        void onConnFailed(Exception e);
    }



    interface SendMsgListener{

        void onSendMsgSuccess(String str);

        void onSendMsgFailed(Exception e);
    }



}

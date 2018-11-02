package com.zmj.mvc.example.sendmsgmvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.mvc.example.R;

/**
 * @author Zmj
 * @date 2018/11/2
 */
public class SendMsgFragment extends Fragment implements ISendMsgView ,View.OnClickListener{


    private TextView tv_sendedMsg;
    private EditText et_msg;
    private Button btn_sendMsg;

    private SendMasgPersenter sendMasgPersenter;

    public SendMsgFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.send_msg_fragment,container,false);
        tv_sendedMsg = view.findViewById(R.id.tv_sendedMsg);
        et_msg = view.findViewById(R.id.et_msg);
        btn_sendMsg = view.findViewById(R.id.btn_sendMsg);

        btn_sendMsg.setOnClickListener(this);

        sendMasgPersenter = new SendMasgPersenter();
        sendMasgPersenter.onAttach(this);
        return view;
    }


    @Override
    public String getSendMsg() {
        String msg = et_msg.getText().toString().trim();
        if (msg != null && msg.length() > 0){
            return msg;
        }else {
            Toast.makeText(getActivity(),"发送数据为空",Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sendMsg:
                if (getSendMsg() == null){
                    //Toast.makeText(getActivity(),"发送数据为空",Toast.LENGTH_SHORT).show();
                    break;
                }else {
                    sendMasgPersenter.dealWithMsg();
                }
                break;
        }
    }

    @Override
    public void receiveMsg(String s) {

    }
}

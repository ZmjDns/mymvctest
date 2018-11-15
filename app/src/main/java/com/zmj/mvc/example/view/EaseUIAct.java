package com.zmj.mvc.example.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.zmj.mvc.example.Config;
import com.zmj.mvc.example.R;
import com.zmj.mvc.example.view.fragment.MyChatFragment;

public class EaseUIAct extends AppCompatActivity {

    private ChatFragment chatFragment;
//    private FrameLayout fl_chatContent;

    private FragmentManager fragmentManager;

    private FragmentTransaction transaction;

    String toChatUserName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ease_ui);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //IM服务号
            toChatUserName = bundle.getString(com.hyphenate.helpdesk.easeui.util.Config.EXTRA_SERVICE_IM_NUMBER);

            //
            String chatFragmentTAG = "chatFragment";
            chatFragment = (ChatFragment)getSupportFragmentManager().findFragmentByTag(chatFragmentTAG);
            if (chatFragment == null){
                chatFragment = new MyChatFragment();//ChatFragment();
//                Bundle bundle1 = new Bundle();
//                bundle.putString(com.hyphenate.helpdesk.easeui.util.Config.EXTRA_SERVICE_IM_NUMBER,toChatUserName);
                chatFragment.setArguments(getIntent().getExtras());
            }

            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();


            transaction.add(R.id.fl_chatContent,chatFragment);
            transaction.show(chatFragment);
            transaction.commit();
        }

//        fl_chatContent = findViewById(R.id.fl_chatContent);


    }


}

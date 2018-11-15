package com.zmj.mvc.example.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.provider.CustomChatRowProvider;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.hyphenate.helpdesk.easeui.widget.EaseChatInputMenu;
import com.zmj.mvc.example.R;

/**
 * @author Zmj
 * @date 2018/11/15
 */
public class MyChatFragment extends ChatFragment implements ChatFragment.EaseChatFragmentListener {

    private static final int LEAVE_MSG = 12;//留言viewID

    public EaseChatInputMenu inputMenu;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setUpView() {
        //新添加的扩展点击事件
        setChatFragmentListener(this);
        super.setUpView();
        //此处可以设置titleBar(标题栏)属性
        titleBar.setBackgroundColor(getResources().getColor(android.support.v4.R.color.secondary_text_default_material_dark));

    }

    @Override
    protected void registerExtendMenuItem() {
        //这里不覆盖基类已经注册的item, item点击listener沿用基类的
        super.registerExtendMenuItem();
        //增加扩展的item
        //inputMenu.registerExtendMenuItem("留言",R.drawable.ic_vector_menu_github,LEAVE_MSG,R.id.chat_menu_leave_msg,extendMenuItemClickListener);
    }

    @Override
    public void onAvatarClick(String username) {

    }

    @Override
    public boolean onMessageBubbleClick(Message message) {
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(Message message) {

    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (view.getId()){

        }


        //不覆盖已有的点击事件
        return false;
    }

    @Override
    public CustomChatRowProvider onSetCustomChatRowProvider() {
        return null;
    }

}

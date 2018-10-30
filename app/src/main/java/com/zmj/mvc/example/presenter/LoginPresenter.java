package com.zmj.mvc.example.presenter;

import com.zmj.mvc.example.model.HttpCallbackListener;
import com.zmj.mvc.example.model.LoginModel;
import com.zmj.mvc.example.view.viewinterface.LoginUser;

/**
 * @author Zmj
 * @date 2018/10/30
 */
public class LoginPresenter {
    private LoginUser loginUser;
    private LoginModel loginModel;

    public LoginPresenter(LoginUser loginUser) {
        this.loginUser = loginUser;
        this.loginModel = new LoginModel();
    }

    public void login(HttpCallbackListener listener){
        loginModel.login(loginUser.getLoginName(),loginUser.getPassword(),listener);
    }
}

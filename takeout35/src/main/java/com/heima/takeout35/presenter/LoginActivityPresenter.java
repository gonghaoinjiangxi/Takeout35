package com.heima.takeout35.presenter;

import com.heima.takeout35.ui.activity.LoginActivity;

/**
 * Created by lidongzhi on 2017/4/13.
 */

public class LoginActivityPresenter extends BasePresenter{

    private LoginActivity mLoginActivity;

    public LoginActivityPresenter(LoginActivity loginActivity) {
        this.mLoginActivity = loginActivity;
    }

    public void loginByPhone(String phone, int type){
        //18912345566，用type区别到底普通账还是手机账号，type=-2表示是手机账号，-1表示是普通账号


    }

    @Override
    protected void parserJson(String json) {

    }
}

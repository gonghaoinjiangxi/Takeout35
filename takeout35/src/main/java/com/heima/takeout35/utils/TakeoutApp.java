package com.heima.takeout35.utils;

import android.app.Application;

import com.heima.takeout35.model.net.User;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by lidongzhi on 2017/4/13.
 */

public class TakeoutApp extends Application {

    //生命周期很长,栈内存释放不掉，原因是app这个类释放不掉
    public static User sUser;

    @Override
    public void onCreate() {
        super.onCreate();

        sUser = new User();
        sUser.setId(-1); //空用户，未登录

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}

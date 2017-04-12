package com.itheima.mvc;

/**
 * 业务类：目标 抽取1/2/3
 */

public class MainActivityPresenter {

    private MainActivity mMainActivity;

    public MainActivityPresenter(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public void login(String username, String password){
        //1.对javabean进行赋值
        final User user=new User();
        user.username=username;
        user.password=password;
        //2.开启子线程
        new Thread(){
            @Override
            public void run() {
                //3.执行业务逻辑
                UserLoginNet net=new UserLoginNet();

                if(net.sendUserLoginInfo(user)){
                    // 登陆成功
                    mMainActivity.success();
                }else{
                    //登陆失败
                    mMainActivity.failed();
                }

            }
        }.start();
    }
}

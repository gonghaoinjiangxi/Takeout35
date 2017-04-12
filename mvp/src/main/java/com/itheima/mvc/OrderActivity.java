package com.itheima.mvc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 订单
 */

public class OrderActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //查看订单必须要先登录
//        MainActivityPresenter mainActivityPresenter = new MainActivityPresenter(new MainActivity());
        //直接new Presenter需要保存和管理activity的引用

        //取出presenter,只需要管理presenter对象即可

    }
}

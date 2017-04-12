package com.itheima.mvc;

import dagger.Component;

/**
 * Created by lidongzhi on 2017/4/12.
 */
@Component(modules = MainActivityModule.class)
public interface MainActivityComponent {
    void in(MainActivity mainActivity);
}

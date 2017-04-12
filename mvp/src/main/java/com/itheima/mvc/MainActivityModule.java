package com.itheima.mvc;

import dagger.Module;
import dagger.Provides;

/**
 * 提供业务类对象
 */
@Module
public class MainActivityModule {

    MainActivity mMainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Provides
    MainActivityPresenter providerMainActivityPresenter(){
        return  new MainActivityPresenter(mMainActivity);
    }
}

package com.heima.takeout35.dagger2.module;

import com.heima.takeout35.presenter.HomeFragmentPresenter;
import com.heima.takeout35.ui.fragment.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lidongzhi on 2017/4/13.
 */
@Module
public class HomeFragmentModule {

    HomeFragment mHomeFragment;

    public HomeFragmentModule(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    @Provides
    HomeFragmentPresenter providerHomeFragmentPresenter(){
        return new HomeFragmentPresenter(mHomeFragment);
    }
}

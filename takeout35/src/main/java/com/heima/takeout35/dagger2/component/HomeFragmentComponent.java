package com.heima.takeout35.dagger2.component;

import com.heima.takeout35.dagger2.module.HomeFragmentModule;
import com.heima.takeout35.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by lidongzhi on 2017/4/13.
 */
@Component(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {
    void in(HomeFragment homeFragment);
}

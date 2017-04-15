package com.heima.takeout35.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lidongzhi on 2017/4/15.
 */

public class BusinessFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private String[] mTitles = new String[]{"商品", "评论", "商家"};

    public void setFragmentList(List<Fragment> fragmentList) {
        mFragmentList = fragmentList;
    }

    public BusinessFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragmentList!=null){
            return mFragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(mFragmentList!=null){
            return mFragmentList.size();
        }
        return 0;
    }
}

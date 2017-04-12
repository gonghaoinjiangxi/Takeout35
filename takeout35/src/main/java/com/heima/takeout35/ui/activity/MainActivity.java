package com.heima.takeout35.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.heima.takeout35.R;
import com.heima.takeout35.ui.fragment.HomeFragment;
import com.heima.takeout35.ui.fragment.MoreFragment;
import com.heima.takeout35.ui.fragment.OrderFragment;
import com.heima.takeout35.ui.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @InjectView(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomeSwitcherContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initFragments();
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, mFragmentList.get(1)).commit();
        initBottomBar();
        chageIndex(0);
    }

    private void initBottomBar() {
        int count = mMainBottomeSwitcherContainer.getChildCount();
        for(int i=0;i<count;i++){
            final View child = mMainBottomeSwitcherContainer.getChildAt(i);

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击了第三个
                    int index = mMainBottomeSwitcherContainer.indexOfChild(child);
                    chageIndex(index);
                }
            });
        }
    }

    private void chageIndex(int index) {
        int count = mMainBottomeSwitcherContainer.getChildCount();
        for(int i=0;i<count;i++) {
            final View child = mMainBottomeSwitcherContainer.getChildAt(i);
            //TODO:选中的禁用，其他的启用
            if(i == index){
                //禁用它以及他的孩子
//                child.setEnabled(false);
                setEnable(false, child);
            }else{
                //启用它以及他的孩子
//                child.setEnabled( true);
                setEnable(true, child);
            }
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, mFragmentList.get(index)).commit();
    }

    private void setEnable(boolean isEnable, View child) {
        child.setEnabled(isEnable);
        if(child instanceof ViewGroup){
            ViewGroup item = (ViewGroup) child;
            int count = item.getChildCount();
            for(int i=0;i<count;i++){
                View v = item.getChildAt(i);
                setEnable(isEnable, v);
            }
        }
    }

    private List<Fragment> mFragmentList = new ArrayList<>();
    private void initFragments() {
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new OrderFragment());
        mFragmentList.add(new UserFragment());
        mFragmentList.add(new MoreFragment());
    }
}

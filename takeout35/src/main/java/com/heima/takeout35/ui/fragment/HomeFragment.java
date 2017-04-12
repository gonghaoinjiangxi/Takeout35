package com.heima.takeout35.ui.fragment;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.ui.adapter.HomeRvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2017/4/12.
 */

public class HomeFragment extends Fragment {

    @InjectView(R.id.rv_home)
    android.support.v7.widget.RecyclerView mRvHome;
    @InjectView(R.id.home_tv_address)
    TextView mHomeTvAddress;
    @InjectView(R.id.ll_title_search)
    LinearLayout mLlTitleSearch;
    @InjectView(R.id.ll_title_container)
    LinearLayout mLlTitleContainer;
    private HomeRvAdapter mHomeRvAdapter;
    private ArgbEvaluator mEvaluator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.fragment_home, null);
        ButterKnife.inject(this, rootView);
        mEvaluator = new ArgbEvaluator();
        initRecycleView();
        return rootView;
    }

    private void initRecycleView() {
        mRvHome.setHasFixedSize(true); //固定itemview大小，防止重复测量刷新
        mRvHome.setLayoutManager(new LinearLayoutManager(getContext()));
        mHomeRvAdapter = new HomeRvAdapter(getContext());
        mRvHome.setAdapter(mHomeRvAdapter);
    }

    int sumY;
    float distance = 200.0f;  //假设的最大距离
    int startColor = 0x553190E8;
    int endColor = 0xFF3190E8;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //填充模拟数据
        testData();
        mHomeRvAdapter.setDatas(mNearby, mOther);
        //有数据后可以监听列表滚动事件
        mRvHome.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("home", "dy:" + dy);
                sumY += dy;

//                sumY/distance
                int color;
                if(sumY < 0){
                    color = startColor;
                }else if(sumY > distance){
                    color = endColor;
                }else{
                    //算16进制
                    color = (int) mEvaluator.evaluate(sumY/distance, startColor, endColor);
                }
                mLlTitleContainer.setBackgroundColor(color);
            }

        });
    }

    private List<String> mNearby = new ArrayList<>();
    private List<String> mOther = new ArrayList<>();
    private void testData() {
        for(int i=0;i<7;i++){
            mNearby.add("我是附近商家：" + i);
        }
        for(int i=0;i<25;i++){
            mOther.add("我是其他商家：" + i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

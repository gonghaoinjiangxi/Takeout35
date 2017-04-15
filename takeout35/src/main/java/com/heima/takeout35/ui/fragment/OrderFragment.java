package com.heima.takeout35.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.heima.takeout35.R;
import com.heima.takeout35.presenter.OrderFragmentPresenter;
import com.heima.takeout35.ui.adapter.OrderRvAdapter;
import com.heima.takeout35.utils.TakeoutApp;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2017/4/12.
 */

public class OrderFragment extends Fragment {

    @InjectView(R.id.rv_order_list)
    RecyclerView mRvOrderList;
    @InjectView(R.id.srl_order)
    SwipeRefreshLayout mSrlOrder;
    public OrderRvAdapter mOrderRvAdapter;
    private OrderFragmentPresenter mOrderFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.fragment_order, null);
        ButterKnife.inject(this, rootView);
        mOrderFragmentPresenter = new OrderFragmentPresenter(this);
        mRvOrderList.setHasFixedSize(true);
        mRvOrderList.setLayoutManager(new LinearLayoutManager(getContext()));
        mOrderRvAdapter = new OrderRvAdapter(getContext());
        mRvOrderList.setAdapter(mOrderRvAdapter);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int userId = TakeoutApp.sUser.getId();
        if(userId == -1){
            Toast.makeText(getContext(), "登录后才能查看订单信息", Toast.LENGTH_SHORT).show();
        }else{
            //去服务器取出订单数据
            mOrderFragmentPresenter.getOrderList(userId + "");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

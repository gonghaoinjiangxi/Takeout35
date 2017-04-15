package com.heima.takeout35.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heima.takeout35.R;
import com.heima.takeout35.presenter.GoodsFragmentPresenter;
import com.heima.takeout35.ui.activity.BusinessActivity;
import com.heima.takeout35.ui.adapter.GoodsAdapter;
import com.heima.takeout35.ui.adapter.GoodsTypeRvAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2017/4/15.
 */
public class GoodsFragment extends Fragment {

    @InjectView(R.id.rv_goods_type)
    public RecyclerView mRvGoodsType;
    @InjectView(R.id.slhlv)
    public  se.emilsjolander.stickylistheaders.StickyListHeadersListView mSlhlv;
    public GoodsTypeRvAdapter mGoodsTypeRvAdapter;
    public GoodsAdapter mGoodsAdapter;
    public GoodsFragmentPresenter mGoodsFragmentPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_goods, container, false);
        ButterKnife.inject(this, rootView);
        mGoodsFragmentPresenter = new GoodsFragmentPresenter(this);
        mRvGoodsType.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvGoodsType.setHasFixedSize(true);
        mGoodsTypeRvAdapter = new GoodsTypeRvAdapter(getContext(),this);
        mRvGoodsType.setAdapter(mGoodsTypeRvAdapter);
        //右侧设置adapter
        mGoodsAdapter = new GoodsAdapter(getContext());
        mSlhlv.setAdapter(mGoodsAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO:使用业务类请求数据
        mGoodsFragmentPresenter.getBusinessInfo((int) ((BusinessActivity) getActivity()).mSeller.getId());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

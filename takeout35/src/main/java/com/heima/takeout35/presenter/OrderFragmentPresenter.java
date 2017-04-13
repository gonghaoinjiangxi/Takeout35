package com.heima.takeout35.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.takeout35.model.net.Order;
import com.heima.takeout35.model.net.ResponseInfo;
import com.heima.takeout35.ui.fragment.OrderFragment;

import java.util.List;

import retrofit2.Call;

/**
 * 请求订单数据
 */

public class OrderFragmentPresenter extends BasePresenter{

    OrderFragment mOrderFragment;

    public OrderFragmentPresenter(OrderFragment orderFragment) {
        mOrderFragment = orderFragment;
    }

    public void getOrderList(String userId){
        Call<ResponseInfo> orderCall = mService.getOrderList(userId);
        orderCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String json) {
        //List<Order>
        Gson gson = new Gson();
        List<Order> orderList = gson.fromJson(json, new TypeToken<List<Order>>(){}.getType());
        mOrderFragment.mOrderRvAdapter.setOrderList(orderList);
    }
}

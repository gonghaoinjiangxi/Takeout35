package com.heima.takeout35.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.takeout35.model.net.ResponseInfo;
import com.heima.takeout35.model.net.Seller;
import com.heima.takeout35.ui.fragment.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

/**
 * 请求首页数据
 */

public class HomeFragmentPresenter extends BasePresenter {

    HomeFragment mHomeFragment;

    public HomeFragmentPresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }

    public void loadHomeInfo(){
        //3.获得一个请求对象,异步发出去
       Call<ResponseInfo> homeCall = mService.getHomeInfo();
       homeCall.enqueue(mCallback);
    }

    @Override
    protected void parserJson(String json) {
        Gson gson = new Gson();
        try {
            JSONObject jsonObject = new JSONObject(json);
            String nearby = jsonObject.getString("nearbySellerList");
            //使用匿名子类
            List<Seller> nearbyList = gson.fromJson(nearby, new TypeToken<List<Seller>>(){}.getType());

            String other = jsonObject.getString("otherSellerList");
            List<Seller> otherList = gson.fromJson(other, new TypeToken<List<Seller>>(){}.getType());

            //6.把数据丢给adapter去刷新
            mHomeFragment.mHomeRvAdapter.setDatas(nearbyList, otherList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

package com.heima.takeout35.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heima.takeout35.model.net.GoodsInfo;
import com.heima.takeout35.model.net.GoodsTypeInfo;
import com.heima.takeout35.model.net.ResponseInfo;
import com.heima.takeout35.ui.fragment.GoodsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * 请求商品详情页数据
 */

public class GoodsFragmentPresenter extends BasePresenter {

    private GoodsFragment mGoodsFragment;
    private List<GoodsInfo> mAllTypeGoodsList;

    public GoodsFragmentPresenter(GoodsFragment goodsFragment) {
        this.mGoodsFragment = goodsFragment;
    }

    public void getBusinessInfo(int sellerId){
       Call<ResponseInfo> businessCall = mService.getBusinessInfo(sellerId);
        businessCall.enqueue(mCallback);
    }


    @Override
    protected void parserJson(String json) {
        mAllTypeGoodsList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONObject jsonObj = new JSONObject(json);
            String typeListStr = jsonObj.getString("list");
            List<GoodsTypeInfo> goodsTypeInfoList = gson.fromJson(typeListStr, new TypeToken<List<GoodsTypeInfo>>(){}.getType());
            Log.e("business", "一共有" + goodsTypeInfoList.size() + "种商品类别");

            mGoodsFragment.mGoodsTypeRvAdapter.setGoodsTypeInfoList(goodsTypeInfoList);
            //右侧adapter刷新数据,
            //TODO:商品数据需要从每一个类别中取出，再拼装
            for(int i=0;i< goodsTypeInfoList.size();i++){
                GoodsTypeInfo goodsTypeInfo = goodsTypeInfoList.get(i);
                List<GoodsInfo> aTypeGoodsList = goodsTypeInfo.getList(); //一个类别的商品
                for(int j=0;j<aTypeGoodsList.size();j++){
                    GoodsInfo goodsInfo = aTypeGoodsList.get(j);
                    goodsInfo.setTypeId(goodsTypeInfo.getId());
                    goodsInfo.setTypeName(goodsTypeInfo.getName());
                }
                mAllTypeGoodsList.addAll(aTypeGoodsList);
            }

            mGoodsFragment.mGoodsAdapter.setGoodsInfoList(mAllTypeGoodsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据指定类别名称（粗粮主食）找到该类别下第一个商品的position(馒头）
     */
    public int getGoodsPositionByTypeId(int typeId){
        int position = -1;
        for(int j=0;j < mAllTypeGoodsList.size();j++){
            GoodsInfo goodsInfo = mAllTypeGoodsList.get(j);
            if(goodsInfo.getTypeId() == typeId){
                position = j;
                break;
            }
        }
        return position;
    }
}

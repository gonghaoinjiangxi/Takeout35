package com.heima.takeout35.presenter;

import android.util.Log;
import android.widget.AbsListView;

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
    private List<GoodsTypeInfo> mGoodsTypeInfoList;

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
            mGoodsTypeInfoList = gson.fromJson(typeListStr, new TypeToken<List<GoodsTypeInfo>>(){}.getType());
            Log.e("business", "一共有" + mGoodsTypeInfoList.size() + "种商品类别");

            mGoodsFragment.mGoodsTypeRvAdapter.setGoodsTypeInfoList(mGoodsTypeInfoList);
            //右侧adapter刷新数据,
            //TODO:商品数据需要从每一个类别中取出，再拼装
            for(int i = 0; i< mGoodsTypeInfoList.size(); i++){
                GoodsTypeInfo goodsTypeInfo = mGoodsTypeInfoList.get(i);
                List<GoodsInfo> aTypeGoodsList = goodsTypeInfo.getList(); //一个类别的商品
                for(int j=0;j<aTypeGoodsList.size();j++){
                    GoodsInfo goodsInfo = aTypeGoodsList.get(j);
                    goodsInfo.setTypeId(goodsTypeInfo.getId());
                    goodsInfo.setTypeName(goodsTypeInfo.getName());
                }
                mAllTypeGoodsList.addAll(aTypeGoodsList);
            }

            mGoodsFragment.mGoodsAdapter.setGoodsInfoList(mAllTypeGoodsList);

            //拿到所有商品后，监听右侧列表滚动事件
            mGoodsFragment.mSlhlv.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    //1.监听滚动事件，firstVisibleItem是指第一个可见的条目，

                    //2.切换的时机：新的类别顶掉旧的类别，变成firstVisibleItem
                    int oldIndex = mGoodsFragment.mGoodsTypeRvAdapter.selectIndex;
                    int oldTypeId = mGoodsTypeInfoList.get(oldIndex).getId();

                    int newTypeId = mAllTypeGoodsList.get(firstVisibleItem).getTypeId();
                    if(newTypeId != oldTypeId){
                        //3.切换到新的index,根据用心营养套餐的typeid算出来index
                        int newIndex = getTypePositionByTypeId(newTypeId);
                        mGoodsFragment.mGoodsTypeRvAdapter.selectIndex = newIndex;
                        mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用心营养套餐的typeId计算出position
     * @param typeId
     * @return
     */
    public int getTypePositionByTypeId(int typeId){
        int position =-1;
        for(int i=0;i<mGoodsTypeInfoList.size();i++){
            GoodsTypeInfo goodsTypeInfo = mGoodsTypeInfoList.get(i);
            if(goodsTypeInfo.getId() == typeId){
                position = i;
                break;
            }
        }
        return position;
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

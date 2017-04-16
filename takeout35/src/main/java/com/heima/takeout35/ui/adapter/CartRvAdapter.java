package com.heima.takeout35.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.net.GoodsInfo;
import com.heima.takeout35.model.net.GoodsTypeInfo;
import com.heima.takeout35.ui.activity.BusinessActivity;
import com.heima.takeout35.ui.fragment.GoodsFragment;
import com.heima.takeout35.utils.Constants;
import com.heima.takeout35.utils.TakeoutApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by lidongzhi on 2017/4/16.
 */

public class CartRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private final BusinessActivity mActivity;
    private final GoodsFragment mGoodsFragment;

    public CartRvAdapter(Context context) {
        mContext = context;
        mActivity = (BusinessActivity) mContext;
        mGoodsFragment = (GoodsFragment) mActivity.mFragmentList.get(0);

    }

    private List<GoodsInfo> mCartList = new ArrayList<>();

    public void setCartList(List<GoodsInfo> cartList) {
        mCartList = cartList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mCartList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mCartList!=null){
            return mCartList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.tv_name)
        TextView mTvName;
        @InjectView(R.id.tv_type_all_price)
        TextView mTvTypeAllPrice;
        @InjectView(R.id.ib_minus)
        ImageButton mIbMinus;
        @InjectView(R.id.tv_count)
        TextView mTvCount;
        @InjectView(R.id.ib_add)
        ImageButton mIbAdd;
        @InjectView(R.id.ll)
        LinearLayout mLl;
        private GoodsInfo mGoodsInfo;

        @OnClick({R.id.ib_add,R.id.ib_minus})
        public void Onclick(View view){
            boolean isAdd = false;
            switch (view.getId()){
                case R.id.ib_add:
                    isAdd = true;
                    doAddOperation();
                    break;
                case R.id.ib_minus:
                    doMinusOperation();
                    break;
            }

            //2.改变购物栏下方（数量和总价）
            ((BusinessActivity) mContext).updateCartUi(mGoodsFragment.mGoodsFragmentPresenter.getCartList());
            //3.刷新左侧列表（红点值）
            processRedDot(isAdd);
            //4.刷新右侧列表（数量）
            mGoodsFragment.mGoodsAdapter.notifyDataSetChanged();
        }

        private void doMinusOperation() {
            //1.改变购物车内部（数量、价格）
            int count = mGoodsInfo.getCount();
            count--;

            if(count == 0){
                //当馒头变成0个的时候，移除馒头条目
                mCartList.remove(mGoodsInfo);
                //当最后一类商品被移除（购物车为空的时候），关闭购物车
                if(mCartList.size() == 0){
                    //关闭购物车
                    mActivity.showOrCloseCart();
                }
                //删除缓存
                TakeoutApp.sInstance.deleteCacheSelectedInfo(mGoodsInfo.getId());
            }else{
                TakeoutApp.sInstance.updateCacheSelectedInfo(mGoodsInfo.getId(), Constants.MINUS);
            }
            mGoodsInfo.setCount(count); //已经在此行更改了数据
            notifyDataSetChanged();
        }

        private void doAddOperation() {
            //1.改变购物车内部（数量、价格）
                int count = mGoodsInfo.getCount();
                count++;
                mGoodsInfo.setCount(count); //已经在此行更改了数据
                notifyDataSetChanged();

            //5.购物车中数量至少为1，所以增加的时候都是更新
            TakeoutApp.sInstance.updateCacheSelectedInfo(mGoodsInfo.getId(), Constants.ADD);
        }

        private void processRedDot(boolean isAdd) {
            //点击增加或者减少馒头
            int typeId = mGoodsInfo.getTypeId(); //粗粮主食
            //2.根据粗粮主食id找到它的排序position
            int position = mGoodsFragment.mGoodsFragmentPresenter.getTypePositionByTypeId(typeId);
            //3.从左侧列表中找到第postion个GoodstypeInfo
            GoodsTypeInfo goodsTypeInfo = mGoodsFragment.mGoodsFragmentPresenter.mGoodsTypeInfoList.get(position);
            int redDotCount = goodsTypeInfo.getCount();
            if(isAdd){
                redDotCount++;
            }else{
                redDotCount --;
            }
            goodsTypeInfo.setCount(redDotCount);
            //4.刷新左侧列表
            mGoodsFragment.mGoodsTypeRvAdapter.notifyDataSetChanged();
        }



        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setData(GoodsInfo goodsInfo) {
            this.mGoodsInfo = goodsInfo;
            mTvName.setText(goodsInfo.getName());
            mTvTypeAllPrice.setText("￥" + goodsInfo.getCount() * goodsInfo.getNewPrice());
            mTvCount.setText(String.valueOf(goodsInfo.getCount()));
        }
    }
}

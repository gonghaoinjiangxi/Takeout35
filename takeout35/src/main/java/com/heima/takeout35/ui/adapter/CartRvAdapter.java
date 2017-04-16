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
import com.heima.takeout35.ui.activity.BusinessActivity;
import com.heima.takeout35.ui.fragment.GoodsFragment;

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
            switch (view.getId()){
                case R.id.ib_add:
                    doAddOperation();
                    break;
                case R.id.ib_minus:
                    doMinusOperation();
                    break;
            }
        }

        private void doAddOperation() {
            //1.改变购物车内部（数量、价格）
                int count = mGoodsInfo.getCount();
                count++;
                mGoodsInfo.setCount(count);
                notifyDataSetChanged();
            //2.改变购物栏下方（数量和总价）
            ((BusinessActivity) mContext).updateCartUi(mGoodsFragment.mGoodsFragmentPresenter.getCartList());
            //3.刷新左侧列表（红点值）

            //4.刷新右侧列表（数量）

        }

        private void doMinusOperation() {

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

package com.heima.takeout35.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.net.Order;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2017/4/13.
 */

public class OrderRvAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public OrderRvAdapter(Context context) {
        mContext = context;
    }

    private List<Order> mOrderList = new ArrayList<>();

    public void setOrderList(List<Order> orderList) {
        mOrderList = orderList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = View.inflate(mContext, R.layout.item_order_item, null);
        //false表示itemview在内存中，并没有被添加到其他的viewgroup中
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_order_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.setData(mOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        if(mOrderList!=null){
            return mOrderList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.iv_order_item_seller_logo)
        ImageView mIvOrderItemSellerLogo;
        @InjectView(R.id.tv_order_item_seller_name)
        TextView mTvOrderItemSellerName;
        @InjectView(R.id.tv_order_item_type)
        TextView mTvOrderItemType;
        @InjectView(R.id.tv_order_item_time)
        TextView mTvOrderItemTime;
        @InjectView(R.id.tv_order_item_foods)
        TextView mTvOrderItemFoods;
        @InjectView(R.id.tv_order_item_money)
        TextView mTvOrderItemMoney;
        @InjectView(R.id.tv_order_item_multi_function)
        TextView mTvOrderItemMultiFunction;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }

        public void setData(Order order) {
            mTvOrderItemSellerName.setText(order.getSeller().getName());
            mTvOrderItemType.setText(order.getType());
            //TODO:把10/20/30转成对应的订单状态的文本

        }
    }
}

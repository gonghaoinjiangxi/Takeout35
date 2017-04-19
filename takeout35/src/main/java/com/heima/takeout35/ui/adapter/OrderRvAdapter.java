package com.heima.takeout35.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.model.net.Order;
import com.heima.takeout35.ui.activity.OrderDetailActivity;
import com.heima.takeout35.utils.OrderObservable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lidongzhi on 2017/4/13.
 */

public class OrderRvAdapter extends RecyclerView.Adapter implements Observer {

    @Override
    public void update(Observable observable, Object data) {
        //看到黑板后信息的反应
        Log.e("jpush", "观察者收到新的消息。。。。");
        HashMap<String,String> map = (HashMap<String, String>) data;
        String pushOrderId = map.get("orderId");
        String pushType = map.get("type");

        int position = -1;  //-1表示所有都不匹配
        for(int i=0;i<mOrderList.size();i++){
            Order order = mOrderList.get(i);
            if(order.getId().equals(pushOrderId)){
                //匹配上,设置新的状态
                order.setType(pushType);
//                notifyDataSetChanged();
                //只刷新要修改的那一个
                position = i;
                notifyItemChanged(position);
            }
        }

    }

    private Context mContext;

    public OrderRvAdapter(Context context) {
        mContext = context;
        OrderObservable.getInstance().addObserver(this);
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
        private Order mOrder;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("orderId", mOrder.getId());
                    intent.putExtra("type", mOrder.getType());
                    mContext.startActivity(intent);
                }
            });
        }

        public void setData(Order order) {
            this.mOrder = order;
            mTvOrderItemSellerName.setText(order.getSeller().getName());
            mTvOrderItemType.setText(getOrderTypeInfo(order.getType()));
            //TODO:把10/20/30转成对应的订单状态的文本

        }

        private String getOrderTypeInfo(String type) {
            /**
             * 订单状态
             * 1 未支付 2 已提交订单 3 商家接单  4 配送中,等待送达 5已送达 6 取消的订单
             */
//            public static final String ORDERTYPE_UNPAYMENT = "10";
//            public static final String ORDERTYPE_SUBMIT = "20";
//            public static final String ORDERTYPE_RECEIVEORDER = "30";
//            public static final String ORDERTYPE_DISTRIBUTION = "40";
//            public static final String ORDERTYPE_SERVED = "50";
//            public static final String ORDERTYPE_CANCELLEDORDER = "60";

            String typeInfo = "";
            switch (type) {
                case OrderObservable.ORDERTYPE_UNPAYMENT:
                    typeInfo = "未支付";
                    break;
                case OrderObservable.ORDERTYPE_SUBMIT:
                    typeInfo = "已提交订单";
                    break;
                case OrderObservable.ORDERYPTE_RECEIVEORDER:
                    typeInfo = "商家接单";
                    break;
                case OrderObservable.ORDERTYPE_DISTRIBUTION:
                    typeInfo = "配送中";
                    break;
                case OrderObservable.ORDERTYPE_SERVED:
                    typeInfo = "已送达";
                    break;
                case OrderObservable.ORDERTYPE_CANCELLEDORDER:
                    typeInfo = "取消的订单";
                    break;
            }
            return typeInfo;
        }

    }
}

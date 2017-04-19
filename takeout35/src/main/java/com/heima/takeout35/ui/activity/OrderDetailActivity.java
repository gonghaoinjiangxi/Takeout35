package com.heima.takeout35.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heima.takeout35.R;
import com.heima.takeout35.utils.OrderObservable;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.heima.takeout35.utils.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL;
import static com.heima.takeout35.utils.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE;
import static com.heima.takeout35.utils.OrderObservable.ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL;

/**
 * Created by lidongzhi on 2017/4/13.
 */
public class OrderDetailActivity extends AppCompatActivity implements Observer {

    @InjectView(R.id.iv_order_detail_back)
    ImageView mIvOrderDetailBack;
    @InjectView(R.id.tv_seller_name)
    TextView mTvSellerName;
    @InjectView(R.id.tv_order_detail_time)
    TextView mTvOrderDetailTime;
    @InjectView(R.id.ll_order_detail_type_container)
    LinearLayout mLlOrderDetailTypeContainer;
    @InjectView(R.id.ll_order_detail_type_point_container)
    LinearLayout mLlOrderDetailTypePointContainer;
    private String mOrderId;
    private String mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);
        OrderObservable.getInstance().addObserver(this);
        processIntent();
    }

    private void processIntent() {
        if (getIntent() != null) {
            mOrderId = getIntent().getStringExtra("orderId");
            mType = getIntent().getStringExtra("type");

            int index = getIndex(mType);
            ((TextView) mLlOrderDetailTypeContainer.getChildAt(index)).setTextColor(Color.BLUE);
            ((ImageView) mLlOrderDetailTypePointContainer.getChildAt(index)).setImageResource(R.drawable.order_time_node_disabled);
        }
    }

    private int getIndex(String type) {
        int index = -1;
//        String typeInfo = "";
        switch (type) {
            case OrderObservable.ORDERTYPE_UNPAYMENT:
//                typeInfo = "未支付";
                break;
            case OrderObservable.ORDERTYPE_SUBMIT:
//                typeInfo = "已提交订单";
                index = 0;
                break;
            case OrderObservable.ORDERYPTE_RECEIVEORDER:
//                typeInfo = "商家接单";
                index = 1;
                break;
            case OrderObservable.ORDERTYPE_DISTRIBUTION:
            case ORDERTYPE_DISTRIBUTION_RIDER_GIVE_MEAL:
            case ORDERTYPE_DISTRIBUTION_RIDER_TAKE_MEAL:
            case ORDERTYPE_DISTRIBUTION_RIDER_RECEIVE:
//                typeInfo = "配送中";
                index = 2;
                break;
            case OrderObservable.ORDERTYPE_SERVED:
//                typeInfo = "已送达";
                index = 3;
                break;
            case OrderObservable.ORDERTYPE_CANCELLEDORDER:
//                typeInfo = "取消的订单";
                break;
        }
        return index;
    }

    @OnClick(R.id.iv_order_detail_back)
    public void onClick() {
        finish();
    }

    @Override
    public void update(Observable o, Object data) {

        HashMap<String, String> map = (HashMap<String, String>) data;
        String pushOrderId = map.get("orderId");
        String pushType = map.get("type");

        int newIndex = getIndex(pushType);

        if (pushOrderId.equals(mOrderId)) {
            //匹配上,选中新的状态
            ((TextView) mLlOrderDetailTypeContainer.getChildAt(newIndex)).setTextColor(Color.BLUE);
            ((ImageView) mLlOrderDetailTypePointContainer.getChildAt(newIndex)).setImageResource(R.drawable.order_time_node_disabled);

        }
    }
}

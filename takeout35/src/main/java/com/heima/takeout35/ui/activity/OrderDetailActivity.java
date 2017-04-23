package com.heima.takeout35.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.heima.takeout35.R;
import com.heima.takeout35.utils.OrderObservable;

import java.util.ArrayList;
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
    @InjectView(R.id.map)
    MapView mapView;
    private String mOrderId;
    private String mType;
    private AMap aMap;
    private Marker mSellerMarker;
    private Marker mBuyerMarker;
    private Marker mRiderMarker;

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.inject(this);
        OrderObservable.getInstance().addObserver(this);
        processIntent();

        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
//            setUpMap();
        }

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
        String lat = map.get("lat");
        String lng = map.get("lng");
        switch (Integer.parseInt(pushType)){
            case 30:
                //商家已接单
                mapView.setVisibility(View.VISIBLE);
                aMap.moveCamera(CameraUpdateFactory.zoomTo(16));

                //标注卖家位置 丰顺自选快餐 22.5788400000,113.9216330000
                MarkerOptions sellermarkerOptions = new MarkerOptions();
                sellermarkerOptions.title("丰顺自选快餐");
                sellermarkerOptions.position(new LatLng(22.5788400000,113.9216330000));
                sellermarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.order_seller_icon));
                mSellerMarker = aMap.addMarker(sellermarkerOptions);

                MarkerOptions buymarkerOptions = new MarkerOptions();
                buymarkerOptions.title("丰顺自选快餐");
                buymarkerOptions.position(new LatLng(22.5765800000,113.9237520000));
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.drawable.order_buyer_icon);
                buymarkerOptions.icon(BitmapDescriptorFactory.fromView(imageView));
                mBuyerMarker = aMap.addMarker(buymarkerOptions);

                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(22.5788400000,113.9216330000)));
                break;
            case 40:
                initRider();
                break;
            case 46:
            case 48:
                updateRider(Double.parseDouble(lat), Double.parseDouble(lng));
                break;
        }

        int newIndex = getIndex(pushType);

        if (pushOrderId.equals(mOrderId)) {
            //匹配上,选中新的状态
            ((TextView) mLlOrderDetailTypeContainer.getChildAt(newIndex)).setTextColor(Color.BLUE);
            ((ImageView) mLlOrderDetailTypePointContainer.getChildAt(newIndex)).setImageResource(R.drawable.order_time_node_disabled);

        }
    }

    private void updateRider(Double lat, Double lng) {
        //22.5774220000,113.922475000	产业园B
        mRiderMarker.hideInfoWindow();
        LatLng newPosition = new LatLng(lat,lng);
        mRiderMarker.setPosition(newPosition);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(newPosition));

        //画线
        mPoints.add(newPosition);
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.width(5);
        polylineOptions.color(Color.RED);
        polylineOptions.add(mPoints.get(mPoints.size() -1),mPoints.get(mPoints.size() -2));
        aMap.addPolyline(polylineOptions);
    }

    ArrayList<LatLng> mPoints = new ArrayList<>();
    private void initRider() {
        //标记骑手
        MarkerOptions riderOption = new MarkerOptions();
//        riderOption.title("我是百度骑士");
        LatLng initPoint = new LatLng(22.5766790000,113.9205490000);
        mPoints.add(initPoint);
        riderOption.position(initPoint);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.order_rider_icon);
        riderOption.icon(BitmapDescriptorFactory.fromView(imageView));
        mRiderMarker = aMap.addMarker(riderOption);
        mRiderMarker.setSnippet("我是百度骑士！");
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(22.5766790000,113.9205490000)));
        mRiderMarker.showInfoWindow();
    }
}

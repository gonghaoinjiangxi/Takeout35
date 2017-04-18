package com.heima.baidumap35;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;


public class BaseMapActivity extends AppCompatActivity implements BaiduMap.OnMapStatusChangeListener {

    protected MapView mMapView;
    protected BaiduMap mBaiduMap;
    LatLng zlswgy = new LatLng(22.581627, 113.929872);
//    LatLng zlswgy = new LatLng(22.581627, 113.929872);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_base);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mMapView.setLogoPosition(LogoPosition.logoPostionRightTop);
        mBaiduMap = mMapView.getMap();

//        mBaiduMap.getMaxZoomLevel();
//        mBaiduMap.getMinZoomLevel();
//        Toast.makeText(this, "最大：" + mBaiduMap.getMaxZoomLevel() + "，最小：" + mBaiduMap.getMinZoomLevel(), Toast.LENGTH_SHORT).show();
        //移动到指定位置，经纬度
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(zlswgy));
        //使用代码去放大
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));

        //添加状态监听
        mBaiduMap.setOnMapStatusChangeListener(this);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.controller, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.zoomin:
                zoomin();
                break;
            case R.id.zoomout:
                zoomout();
                break;
            case R.id.rotate:
                rotate();
                break;
            case R.id.translate:
                tranlate();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void tranlate() {
        //移动到指定位置，经纬度
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(zlswgy));
        //使用代码去放大
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(17));
    }

    private void rotate() {
        //先取出原来的旋转值
        MapStatus oldMapStatus = mBaiduMap.getMapStatus();

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.rotate(oldMapStatus.rotate + 90);  //rotate每次都逆时针或者顺时针旋转90，   rotateTo效果
        MapStatus mapStatus = builder.build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mapStatus));
    }

    private void zoomout() {
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut()); //一次性的减少一个单位
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomOut(), 3000); //以动画的方式缩小
    }

    private void zoomin() {
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn()); //一次性增加一个单位
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
        Log.e("map","当前级别为：" + mapStatus.zoom);
//        Toast.makeText(this, "当前级别为：" + mapStatus.zoom, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        Log.e("map","结束级别为：" + mapStatus.zoom);
//        Toast.makeText(this, "结束的级别为：" + mapStatus.zoom, Toast.LENGTH_SHORT).show();
    }
}

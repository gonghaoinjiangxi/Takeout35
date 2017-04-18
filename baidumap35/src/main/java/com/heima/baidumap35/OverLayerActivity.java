package com.heima.baidumap35;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by lidongzhi on 2017/4/18.
 */
public class OverLayerActivity extends BaseMapActivity implements BaiduMap.OnMarkerClickListener, BaiduMap.OnMapClickListener {

    private Overlay mMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("标注覆盖物");  //指的就是marker

        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMapClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.marker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.marker:
                marker();
                break;
            case R.id.unmarker:
                unmarker();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void unmarker() {
        mBaiduMap.hideInfoWindow();
//        mMarker.setVisible(false);//隐藏
        mMarker.remove();  //移除
        mBaiduMap.clear();  //移除所有覆盖物
    }

    private void marker() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(zlswgy);
        markerOptions.title("中粮商务公园3栋1308");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markc));
        markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);
        mMarker = mBaiduMap.addOverlay(markerOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //弹出文本“我是中粮商务公园" InfoWindow
        TextView textView = new TextView(this);
        textView.setText(marker.getTitle());
        textView.setTextColor(Color.RED);
        InfoWindow infoWindow = new InfoWindow(textView,zlswgy,-100);
        mBaiduMap.showInfoWindow(infoWindow);

        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        //点击整张地图
        unmarker();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }
}

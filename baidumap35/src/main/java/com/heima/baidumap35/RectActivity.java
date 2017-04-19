package com.heima.baidumap35;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.Overlay;

/**
 * Created by lidongzhi on 2017/4/18.
 */
public class RectActivity extends BaseMapActivity{

    private Overlay mOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("圆形覆盖物");

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
        mOverlay.remove();
    }

    private void marker() {
        GroundOverlayOptions groundOverlayOptions = new GroundOverlayOptions();
        groundOverlayOptions.image(BitmapDescriptorFactory.fromResource(R.mipmap.ground_overlay));
        //只需要知道两个点,两个点确定矩形的边界（四条边）
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        builder.include(zlswgy);
//        builder.include(hybfc);
//        groundOverlayOptions.positionFromBounds(builder.build());

        //第二种方式
        groundOverlayOptions.position(zlswgy);
        groundOverlayOptions.dimensions(500,1000);

        mOverlay = mBaiduMap.addOverlay(groundOverlayOptions);

    }
}

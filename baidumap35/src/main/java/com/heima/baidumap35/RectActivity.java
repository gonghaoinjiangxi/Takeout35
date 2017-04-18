package com.heima.baidumap35;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
        //只需要知道两个点,两个点确定矩形的边界（四条边）
//        groundOverlayOptions.positionFromBounds(new LatLngBounds());

        mOverlay = mBaiduMap.addOverlay(groundOverlayOptions);

    }
}

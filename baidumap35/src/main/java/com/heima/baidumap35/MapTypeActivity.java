package com.heima.baidumap35;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.baidu.mapapi.map.BaiduMap;

/**
 * Created by lidongzhi on 2017/4/18.
 */
public class MapTypeActivity extends BaseMapActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("图层展示");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.type_blank:
                blank();
                break;
            case R.id.type_normal:
                normal();
                break;
            case R.id.type_satelite:
                satelite();
                break;
            case R.id.type_traffic:
                showTraffic();
                break;
            case R.id.type_close_traffic:
                closeTraffic();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void closeTraffic() {
        mBaiduMap.setTrafficEnabled(false);
    }

    private void showTraffic() {
        mBaiduMap.setTrafficEnabled(true);
    }

    private void satelite() {
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
    }

    private void normal() {
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
    }

    private void blank() {
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
    }


}

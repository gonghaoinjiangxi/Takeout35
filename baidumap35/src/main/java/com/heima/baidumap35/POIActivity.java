package com.heima.baidumap35;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiBoundSearchOption;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.heima.baidumap35.demo.PoiOverlay;

import java.util.List;

/**
 * Created by lidongzhi on 2017/4/18.
 */
public class POIActivity extends BaseMapActivity implements BaiduMap.OnMarkerClickListener {
    private PoiSearch mPoiSearch;
    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener(){
        public void onGetPoiResult(PoiResult result){
            //获取POI检索结果
            if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                //详情检索失败
                // result.error请参考SearchResult.ERRORNO
                Toast.makeText(POIActivity.this, "搜索异常", Toast.LENGTH_SHORT).show();
            }
            else {
                //检索成功
                mPoiInfos = result.getAllPoi();
                if(mPoiInfos !=null) {
                    //添加标注
//                    markerResult(poiInfos);
                    mBaiduMap.clear();
                    //创建PoiOverlay
                    PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
                    //设置overlay可以处理标注点击事件
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    //设置PoiOverlay数据
                    overlay.setData(result);
                    //添加PoiOverlay到地图中
                    overlay.addToMap();
                    overlay.zoomToSpan(); //自适应缩放
                    return;
//                    Toast.makeText(POIActivity.this, "一共有：" + result.getAllPoi().size() + "个", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(POIActivity.this, "搜索结果为空", Toast.LENGTH_SHORT).show();
                }
            }
        }
        public void onGetPoiDetailResult(PoiDetailResult result){
            //获取Place详情页检索结果
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            //新版本增加了市内搜索
        }
    };
    private List<PoiInfo> mPoiInfos;

    private void markerResult(List<PoiInfo> poiInfos) {
        for(PoiInfo info : poiInfos){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_markg));
            markerOptions.position(info.location);
            markerOptions.title(info.name);
            mBaiduMap.addOverlay(markerOptions);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("兴趣点搜索");

        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

//        mBaiduMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.poi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.city:
                city();
                break;
            case R.id.area:
                area();
                break;
            case R.id.around:
                around();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void around() {

    }

    private void city() {
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("深圳")
                .keyword("电影院")
                .pageCapacity(10)
                .pageNum(1));
    }

    private void area() {
        PoiBoundSearchOption boundSearchOption = new PoiBoundSearchOption();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(zlswgy);
        builder.include(hybfc);
        builder.include(zsgy);
        boundSearchOption.bound(builder.build());
        boundSearchOption.keyword("超市");
        boundSearchOption.pageCapacity(10);
        boundSearchOption.pageNum(2);
        mPoiSearch.searchInBound(boundSearchOption);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
//        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    private class MyPoiOverlay extends PoiOverlay {
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }
        @Override
        public boolean onPoiClick(int index) {
            Toast.makeText(POIActivity.this, mPoiInfos.get(index).name, Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}

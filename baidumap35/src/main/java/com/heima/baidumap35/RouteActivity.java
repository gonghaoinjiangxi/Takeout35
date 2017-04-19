package com.heima.baidumap35;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.heima.baidumap35.demo.DrivingRouteOverlay;
import com.heima.baidumap35.demo.TransitRouteOverlay;

import java.util.List;

/**
 * Created by lidongzhi on 2017/4/18.
 */
public class RouteActivity extends BaseMapActivity{

    private RoutePlanSearch mSearch;

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            //
        }
        public void onGetTransitRouteResult(TransitRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(RouteActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                mRouteLineList = result.getRouteLines();
                TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }
        public void onGetDrivingRouteResult(DrivingRouteResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(RouteActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//                mRouteLineList = result.getRouteLines();
                DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }
    };
    private List<TransitRouteLine> mRouteLineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("线路规划");

        mSearch = RoutePlanSearch.newInstance();

        mSearch.setOnGetRoutePlanResultListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.route, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.walk:
                walk();
                break;
            case R.id.ride:
                ride();
                break;
            case R.id.drive:
                drive();
                break;
            case R.id.bus:
                bus();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void bus() {
        PlanNode stNode = PlanNode.withCityNameAndPlaceName("深圳", "世界之窗");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("深圳", "小梅沙");
        mSearch.transitSearch((new TransitRoutePlanOption())
                .policy(TransitRoutePlanOption.TransitPolicy.EBUS_TIME_FIRST)
                .from(stNode)
                .city("深圳")
                .to(enNode));
    }

    private void drive() {
        PlanNode stNode = PlanNode.withCityNameAndPlaceName("深圳", "兴东");
        PlanNode enNode = PlanNode.withCityNameAndPlaceName("深圳", "海上世界");
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_TIME_FIRST)
                .from(stNode)
                .to(enNode));
    }

    private void ride() {

    }

    private void walk() {

    }

    private class MyTransitRouteOverlay extends TransitRouteOverlay {

        public MyTransitRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onRouteNodeClick(int i) {
            Toast.makeText(RouteActivity.this, mRouteLineList.get(i).describeContents() + "" , Toast.LENGTH_SHORT).show();
            return super.onRouteNodeClick(i);
        }

        @Override
        public boolean onPolylineClick(Polyline polyline) {
            Toast.makeText(RouteActivity.this, polyline.getPoints().get(0).latitude +":" + polyline.getPoints().get(0).longitude, Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}

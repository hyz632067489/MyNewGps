package com.hyz.mynewgps.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.hyz.mynewgps.R;

public class MainActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private String lableName = "天堂软件园";

    //定位相关变量
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;


    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        this.context = this;
        //初始化控件
        initView();

        //初始化定位
        initLocation();
    }

    private void initLocation() {

        mLocationClient = new LocationClient(this);
        mLocationListener =  new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);


        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setScanSpan(10000);
        mLocationClient.setLocOption(option);

    }

    private void initView() {

        mMapView = (MapView) findViewById(R.id.baidu_mapView);
        mBaiduMap = mMapView.getMap();


       

        //缩小比例栏
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);


        //定义Maker坐标点
        LatLng point = new LatLng(29.493535 ,106.645762 );
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        //文字，在地图中也是一种覆盖物，开发者可利用相关的接口，快速实现在地图上书写文字的需求。实现方式如下：
        //定义文字所显示的坐标点
        LatLng llText = new LatLng(29.493535, 106.645762);
        //构建文字Option对象，用于在地图上添加文字
        OverlayOptions textOption = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(28)
                .fontColor(0xFFFF00FF)
                .text(lableName)
                .rotate(0)
                .position(llText);
        //在地图上添加该文字对象并显示
        mBaiduMap.addOverlay(textOption);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.id_map_common:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.id_map_site:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.id_map_traffic:
                if (mBaiduMap.isTrafficEnabled()) {
                    mBaiduMap.setTrafficEnabled(false);
                    item.setTitle("实时交通(false)");
                } else {
                    mBaiduMap.setTrafficEnabled(true);
                    item.setTitle("实时交通(on)");
                }
                break;
            case R.id.id_map_location:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyLocationListener implements BDLocationListener {

        private boolean isFirstIn = true;
        @Override
        public void onReceiveLocation(BDLocation location) {

            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            mBaiduMap.setMyLocationData(data);
            //定位小图标
//        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS,agr1,agr2);



            if(isFirstIn){

                LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
                MapStatusUpdate msu =  MapStatusUpdateFactory.newLatLng(latlng);

                mBaiduMap.animateMapStatus(msu);

                isFirstIn = false;

                Log.i("MainActivity","onReceiveLocation=========="+location.getLatitude()+"===="+location.getLongitude());
                Toast.makeText(context,"经纬度"+location.getAddrStr(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}

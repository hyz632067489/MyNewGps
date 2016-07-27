package com.hyz.mynewgps;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by ${hyz} on 2016/5/31.
 */
public class MyLocationListener implements BDLocationListener {

    private boolean isFirstIn = true;
    @Override
    public void onReceiveLocation(BDLocation location) {

        MyLocationData data = new MyLocationData.Builder()//
                .accuracy(location.getRadius())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        //定位小图标
//        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS,agr1,agr2);

            if(isFirstIn){

                LatLng latlng = new LatLng(location.getLatitude(),location.getLongitude());
//                MapStatusUpdate msu = new MapStatusUpdateFactory.newLatLng(latlng);


            }
    }
}

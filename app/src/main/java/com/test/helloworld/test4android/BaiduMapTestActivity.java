package com.test.helloworld.test4android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

public class BaiduMapTestActivity extends Activity implements LocationListener {

    private MapView mapV;
    //todo  百度map需要从map视图中获取一下地图
    private BaiduMap baiduMap;
    private MyLocationData myLocationData;

    private LocationManager manager;
    private String provider;

    private boolean firstLocate = true;

    private final String ak = "naSPU457vnZV3eqyQ62pABQG2Kcqsv08";

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo 此context使用的应用级context而非活动级
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.baidu_map);
        mapV = findViewById(R.id.baidu_map);
        baiduMap = mapV.getMap();
        baiduMap.setMyLocationEnabled(true);
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        List<String> providers = manager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            //todo  没有开启定位的提示要加上
            Toast.makeText(this, "没有开启网络或者定位", Toast.LENGTH_SHORT).show();
            return;
        }
        @SuppressLint("MissingPermission")
        Location location = manager.getLastKnownLocation(provider);
        if (location != null) {
            doSetLocation(location);
        }
        manager.requestLocationUpdates(provider, 5000, 1, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (baiduMap != null) {
            baiduMap.setMyLocationEnabled(false);
        }
        if (manager != null) {
            manager.removeUpdates(this);
        }
    }

    private void doSetLocation(Location location) {
        if (firstLocate) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(15f);
            baiduMap.animateMapStatus(update);
            firstLocate = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        myLocationData = builder.build();
        baiduMap.setMyLocationData(myLocationData);
    }

    @Override
    public void onLocationChanged(Location location) {
        doSetLocation(location);
        Log.e("baidu reLocation", location.getLatitude() + location.getLongitude() + "");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e("baidu status changed", "changed");

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.e("provider Enabled", "changed");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e("provider Disabled", "changed");

    }
}

package com.test.helloworld.test4android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.helloworld.test4android.network.HttpUtils;
import com.test.helloworld.test4android.network.RequestLinstener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MapTestActivity extends Activity implements LocationListener {

    private LocationManager manager;
    private String provider = null;

    private static final int LOCATION_PERMISSION_RESULT = 0;
    private static final int LOCATION_MESSAGE = 0;
    private TextView latlngTv;
    private TextView locationTv;

    //todo   使用了baidu的逆地理api请求地址
//    private final String geoUrl = "http://googleapis/geo/json?";
    private final String geoUrl = "http://api.map.baidu.com/reverse_geocoding/v3/?output=json";
    private final String ak = "naSPU457vnZV3eqyQ62pABQG2Kcqsv08";
    private String mcode = "6F:0E:F6:27:36:AB:EE:76:26:BA:61:2D:FB:F9:93:62:70:28:CA:47;";


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case LOCATION_MESSAGE:
                    locationTv.setText((String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        mcode = mcode + getPackageName();

        latlngTv = findViewById(R.id.latlng_tv);
        locationTv = findViewById(R.id.location_tv);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, LOCATION_PERMISSION_RESULT);
        } else {
            doLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (manager != null) {
            manager.removeUpdates(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void doLocation() {
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //todo 是获取所有可用的位置提供者，而不是获取所有
//        List<String> providers = manager.getAllProviders();
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
        Location location = manager.getLastKnownLocation(provider);
        //todo 必须在调取位置信息前，加上判断位置是否为空。因为位置总是获取不到
        if (location != null) {
            showLocation(location);
        }
        manager.requestLocationUpdates(provider, 5000, 1, this);
    }

    private void showLocation(Location location) {

        //todo altitude是海拔，latitude是纬度
//        double altitude = location.getAltitude();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        latlngTv.setText("纬度：" + latitude + "," + "经度：" + longitude);

        //todo baidu逆地理编码
        StringBuilder urlString = new StringBuilder();
        urlString.append(geoUrl);
        urlString.append("&ak=" + ak);
        urlString.append("&mcode=" + mcode);
        urlString.append("&location=" + latitude + "," + longitude);
        Log.e("baidu geourl", urlString.toString());
        HttpUtils.requestHttp(urlString.toString(), new RequestLinstener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    //todo google逆地理编码api需要银行卡，无法使用
//                    JSONArray array = object.getJSONArray("results");
//                    JSONObject formatObject = array.getJSONObject(0);
//                    String result = formatObject.getString("formatted_address");
                    //todo baidu逆地理编码api
                    JSONObject resultObject = object.getJSONObject("result");
                    String result = resultObject.getString("formatted_address");
                    Message message = new Message();
                    message.what = LOCATION_MESSAGE;
                    message.obj = result;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Exception e) {

            }
        });
//        locationTv.setText("具体位置："+);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_RESULT: {
                if (grantResults.length > 0) {
                    doLocation();
                }
                break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            showLocation(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

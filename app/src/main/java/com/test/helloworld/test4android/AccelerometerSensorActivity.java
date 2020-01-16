package com.test.helloworld.test4android;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.baidu.mapapi.map.Text;

public class AccelerometerSensorActivity extends Activity implements SensorEventListener {
    SensorManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        ((TextView) findViewById(R.id.acc_x_tv)).setText("加速度x：" + "" + x);
        ((TextView) findViewById(R.id.acc_y_tv)).setText("加速度y：" + "" + y);
        ((TextView) findViewById(R.id.acc_z_tv)).setText("加速度z：" + "" + z);
        if (Math.abs(x) > 15 || Math.abs(y) > 15 || Math.abs(z) > 15) {
            Log.e("x", "" + x);
            Log.e("y", "" + y);
            Log.e("z", "" + z);

            Toast.makeText(this, "摇一摇功能生效了一下！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

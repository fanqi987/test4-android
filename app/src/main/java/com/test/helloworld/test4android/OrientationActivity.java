package com.test.helloworld.test4android;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class OrientationActivity extends Activity implements SensorEventListener {


    TextView xTv;
    TextView yTv;
    TextView zTv;
    ImageView compassIv;
    SensorManager manager;

    float beforeDegree = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orientation);
        xTv = findViewById(R.id.or_x_tv);
        yTv = findViewById(R.id.or_y_tv);
        zTv = findViewById(R.id.or_z_tv);
        compassIv = findViewById(R.id.compass_bg_iv);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor1 = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        manager.registerListener(this, sensor1, SensorManager.SENSOR_DELAY_NORMAL);
        Sensor sensor2 = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        manager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterListener(this);
    }


    //todo 因为传感器获取数据是异步的，两个已经保存的传感器变量，不可使用临时变量
    float[] acc = new float[3];
    float[] mag = new float[3];

    @Override
    public void onSensorChanged(SensorEvent event) {
        //todo float数组在new时，需要确定一下数组长度

        float[] R = new float[9];
        float[] orientation = new float[3];

        //todo 这里书中使用的是int类型的传感器type，自己使用了String类型的
//        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        if (event.sensor.getStringType().equals(Sensor.STRING_TYPE_ACCELEROMETER)) {
            acc = event.values.clone();
        } else if (event.sensor.getStringType().equals(Sensor.STRING_TYPE_MAGNETIC_FIELD)) {
            mag = event.values.clone();
        }
        SensorManager.getRotationMatrix(R, null, acc, mag);
        SensorManager.getOrientation(R, orientation);

        zTv.setText("旋转角度z:" + Math.toDegrees(orientation[0]));
        xTv.setText("旋转角度x：" + Math.toDegrees(orientation[1]));
        yTv.setText("旋转角度y:" + Math.toDegrees(orientation[2]));

        float rotateDegree;
        rotateDegree = -(float) Math.toDegrees(orientation[0]);
        if (Math.abs(rotateDegree - beforeDegree) > 1) {
            RotateAnimation animation = new RotateAnimation(beforeDegree, rotateDegree,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            animation.setFillAfter(true);
            compassIv.startAnimation(animation);
            beforeDegree = rotateDegree;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

package com.ntut.mudanguideapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;

import com.ntut.mudanguideapp.camera.CameraHandler;

public class CameraActivity extends AppCompatActivity {
    private CameraHandler cameraHandler;
    private TextureView cameraPreview;

    private SensorManager sensorManager;

    float deltap=10;
    float deltam=-10;

    float[] gravity;
    float[] geomagnetic;
    float Rotation[] = new float[9];
    float[] degree = new float[3];
    float currentDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraHandler=new CameraHandler(this);
        cameraPreview=findViewById(R.id.camera_preview);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        cameraHandler.startPreview(cameraPreview);
        if(sensorManager!=null){
            Sensor magnetic=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            Sensor accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sel,magnetic,SensorManager.SENSOR_DELAY_GAME);
            sensorManager.registerListener(sel,accelerometer,SensorManager.SENSOR_DELAY_GAME);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        cameraHandler.stopPreview();
        sensorManager.unregisterListener(sel);
        super.onPause();
    }

    private void tagUpdate(){

    }

    private SensorEventListener sel=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                gravity = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagnetic = event.values;
            }
            if (gravity != null && geomagnetic != null) {

                SensorManager.getRotationMatrix(Rotation, null, gravity,
                        geomagnetic);
                SensorManager.getOrientation(Rotation, degree);

                currentDegree = (float) Math.toDegrees(degree[0]);
                tagUpdate();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}

package com.ntut.mudanguideapp.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

public class CompassHandler {
    private SensorManager sensorManager;

    private OrientationChangeListener listener;

    private double previousDegree=0;

    private double offset;

    public CompassHandler(Context c){
        sensorManager=(SensorManager) c.getSystemService(SENSOR_SERVICE);
        Log.i("CompassHandler","construct success");
    }

    public void setListener(OrientationChangeListener cocl){
        listener=cocl;
    }

    public void startCompass(int o){
        offset=o;
        if(sensorManager!=null){
            Sensor magnetic=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            Sensor accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sel,magnetic,SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(sel,accelerometer,SensorManager.SENSOR_DELAY_UI);
        }
        Log.i("CompassHandler","startCompass");
    }

    public void stopCompass(){
        sensorManager.unregisterListener(sel);
        Log.i("CompassHandler","stopCompass");
    }

    private SensorEventListener sel=new SensorEventListener() {
        float[] gravity;
        float[] geomagnetic;
        float Rotation[] = new float[9];
        float[] degree = new float[3];
        double currentDegree;
        double deltaDegree=0;
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                gravity = event.values;
            }
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                geomagnetic = event.values;
            }
            if (gravity != null && geomagnetic != null) {

                SensorManager.getRotationMatrix(Rotation, null, gravity, geomagnetic);
                SensorManager.getOrientation(Rotation, degree);
                currentDegree =Math.toDegrees(degree[0]);
                deltaDegree=Math.abs(previousDegree-currentDegree);
                previousDegree=currentDegree;
                if(deltaDegree>=1.5){
                    listener.onOrientationChange(outDegree(currentDegree));
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private double outDegree(double degree){
        degree-=offset;
        if(degree>=180) degree-=360;
        else if(degree<=-180) degree+=360;
        return degree;
    }
}
package com.ntut.mudanguideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.widget.TextView;

import com.ntut.mudanguideapp.camera.CameraHandler;
import com.ntut.mudanguideapp.sensor.CompassHandler;
import com.ntut.mudanguideapp.sensor.OrientationChangeListener;

public class CameraActivity extends AppCompatActivity {
    private CameraHandler cameraHandler;
    private TextureView cameraPreview;

    private CompassHandler compassHandler;

    private TextView textView;
    private TextView item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraHandler=new CameraHandler(this);
        cameraPreview=findViewById(R.id.camera_preview);
        textView=findViewById(R.id.textView2);
        item=findViewById(R.id.item);

        compassHandler=new CompassHandler(this);
        compassHandler.setListener(ocl);
    }

    @Override
    protected void onResume() {
        cameraHandler.startPreview(cameraPreview);
        compassHandler.startCompass(0);
        super.onResume();
    }

    @Override
    protected void onPause() {
        cameraHandler.stopPreview();
        compassHandler.stopCompass();
        super.onPause();
    }

    private OrientationChangeListener ocl=new OrientationChangeListener() {
        @Override
        public void onOrientationChange(double orientation) {
            textView.setText(Double.toString(orientation));
            checkOrientation(orientation);
        }
    };

    private void checkOrientation(double degree){
        if(degree<=-30 && degree >= -50){
            item.setText("kitchen");
            item.setVisibility(TextView.VISIBLE);
        }else if(degree<=-130 && degree >= -150){
            item.setText("TV");
            item.setVisibility(TextView.VISIBLE);
        }else{
            item.setVisibility(TextView.INVISIBLE);
        }
    }
}

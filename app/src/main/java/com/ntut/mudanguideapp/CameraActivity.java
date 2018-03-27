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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraHandler=new CameraHandler(this);
        cameraPreview=findViewById(R.id.camera_preview);
        textView=findViewById(R.id.textView2);

        compassHandler=new CompassHandler(this);
        compassHandler.setListener(ocl);
    }

    @Override
    protected void onResume() {
        cameraHandler.startPreview(cameraPreview);
        compassHandler.startCompass();
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
        }
    };
}

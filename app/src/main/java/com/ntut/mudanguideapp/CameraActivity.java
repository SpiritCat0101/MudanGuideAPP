package com.ntut.mudanguideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.TextureView;

import com.ntut.mudanguideapp.camera.CameraHandler;

public class CameraActivity extends AppCompatActivity {

    private CameraHandler cameraHandler;
    private TextureView cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        cameraHandler=new CameraHandler(this);
        cameraPreview=findViewById(R.id.camera_preview);
    }

    @Override
    protected void onResume() {
        cameraHandler.startPreview(cameraPreview);
        super.onResume();
    }

    @Override
    protected void onPause() {
        cameraHandler.stopPreview();
        super.onPause();
    }
}

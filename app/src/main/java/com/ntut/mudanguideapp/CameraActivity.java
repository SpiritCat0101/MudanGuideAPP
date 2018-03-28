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

    private double[] ArObject_dir={-40,-140};
    private String[] ArObject_name={"kitchen","TV"};

    private double delta=7;

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
        boolean inShow=false;
        for(int i=0;i<2;i++){
            if(degree<=ArObject_dir[i]+delta && degree>=ArObject_dir[i]-delta){
                item.setText(ArObject_name[i]);
                item.setVisibility(TextView.VISIBLE);
                inShow=true;
                break;
            }else{
                inShow=false;
            }
        }
        if(!inShow){
            item.setVisibility(TextView.INVISIBLE);
        }
    }
}
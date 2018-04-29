package com.ntut.mudanguideapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ntut.mudanguideapp.Database.InfoDatabase;

import java.io.IOException;

public class launch extends AppCompatActivity {
    private static final int  REQUEST = 1;

    private final String[] Permission={
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    boolean check_permission=true;

    private InfoDatabase infoDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        infoDatabase=new InfoDatabase(this);
        infoDatabase.versionSync();
    }

    @Override
    protected void onResume(){
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    getPermission();
                    if(check_permission){
                        Thread.sleep(2000);
                        startActivity(new Intent(launch.this,MainActivity.class));
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void getPermission(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            int locationPer= ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if(locationPer== PackageManager.PERMISSION_DENIED ){
                check_permission=false;
                ActivityCompat.requestPermissions(this,Permission,REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST:
                startActivity(new Intent(launch.this,MainActivity.class));
                Log.i("launch", "get permission, change page");
                break;
        }
    }
}
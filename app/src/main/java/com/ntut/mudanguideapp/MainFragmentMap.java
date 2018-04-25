package com.ntut.mudanguideapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;

public class MainFragmentMap extends PagerActive {
    private Context context;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    public MainFragmentMap(Context c, Activity a){
        super(c);
        context=c;

        mapFragment =(SupportMapFragment) ((FragmentActivity)a)
                .getSupportFragmentManager().findFragmentById(R.id.map);
    }

    public MainFragmentMap(Context context, AttributeSet attrs) { super(context, attrs); }

    @Override
    public void startView(){
        mapFragment.getMapAsync(omrc);
    }

    @Override
    public void stopView(){
        mMap.clear();
    }

    @Override
    public void onRefresh(Object obj){
    }

    private OnMapReadyCallback omrc=new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap=googleMap;
            UiSettings uiSettings=mMap.getUiSettings();
            uiSettings.setZoomControlsEnabled(true);
            uiSettings.setCompassEnabled(true);
            int locationPer= ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
            if(locationPer== PackageManager.PERMISSION_GRANTED ){
                mMap.setMyLocationEnabled(true);
            }
            mMap.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(22.174263,120.791608),(float) 12.5));
        }
    };
}

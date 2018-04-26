package com.ntut.mudanguideapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ntut.mudanguideapp.Database.InfoDatabase;

public class MainFragmentMap extends PagerActive {
    private Context context;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private InfoDatabase infoDatabase;

    public MainFragmentMap(Context c, Activity a){
        super(c);
        context=c;

        infoDatabase=new InfoDatabase(context);

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
                    .newLatLngZoom(new LatLng(22.174263,120.791608),(float) 11.5));

            Cursor cu;
            infoDatabase.OpenDB();

            cu=infoDatabase.getCursor(null,null);
            cu.moveToFirst();
            while(!cu.isAfterLast()){
                String name=cu.getString(1);
                double lat=cu.getDouble(3);
                double lng=cu.getDouble(4);
                if(lat==0 && lng==0){
                    cu.moveToNext();
                    continue;
                }
                LatLng location=new LatLng(lat,lng);

                mMap.addMarker(new MarkerOptions()
                        .title(name)
                        .position(location)
                );

                cu.moveToNext();
            }

            infoDatabase.CloseDB();
        }
    };
}
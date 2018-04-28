package com.ntut.mudanguideapp.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
/**
 * Created by Spirit on 2018/3/26.
 */

public class LocationHandler{

    private Context context;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    private LocationChangeListener locationChangeListener;

    public LocationHandler(Context c){
        this.context=c;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        if(checkPermission()){
            createLocationRequest();
        }else{
            Log.i("LocationHandler","ACCESS_FINE_LOCATION DENIED");
        }
        Log.i("LocationHandler","construct success");
    }

    public void startLocationUpdates(LocationChangeListener listener){
        locationChangeListener=listener;
        Log.i("LocationHandler","start update");
        try{
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,lcb,null);
        }catch (SecurityException e){
            //ignore
        }
    }

    public void stopLocationUpdates() {
        Log.i("LocationHandler","stop update");
        mFusedLocationClient.removeLocationUpdates(lcb);
    }

    private LocationCallback lcb=new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                Log.i("LocationHandler","locationResult == null");
            }else{
                Log.i("LocationHandler","location get");
                locationChangeListener.onLocationChange(locationResult.getLastLocation());
            }
        }
    };

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(10000)
            .setFastestInterval(1000);
        Log.i("LocationHandler","location request create success");
    }

    private boolean checkPermission(){
        boolean check_permission=true;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            int per= ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
            if(per== PackageManager.PERMISSION_DENIED){
                check_permission=false;
            }
        }
        return check_permission;
    }
}

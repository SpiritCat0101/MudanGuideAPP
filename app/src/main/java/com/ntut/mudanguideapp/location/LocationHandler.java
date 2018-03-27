package com.ntut.mudanguideapp.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Spirit on 2018/3/26.
 */

public class LocationHandler{

    private Context context;
    private Activity activity;

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;

    private Location currentLocation;

    private LocationChangeListener locationChangeListener;

    public LocationHandler(Context c,Activity a){
        this.context=c;
        this.activity=a;

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        if(checkPermission()){
            createLocationRequest();
            checkLocationSetting();
            getLastLocation();
        }else{
            Log.i("LocationHandler","ACCESS_FINE_LOCATION DENIED");
        }
        Log.i("LocationHandler","construct success");
    }

    public Location getCurrentLocation(){return currentLocation;}

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
                currentLocation=locationResult.getLastLocation();
                locationChangeListener.onLocationChange(currentLocation);
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

    private void checkLocationSetting(){
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    Log.i("LocationHandler","settings are not satisfied");
                    stopLocationUpdates();
                    Toast.makeText(context, "請開啟定位服務", Toast.LENGTH_LONG).show();
                    context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }
        });
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

    @SuppressWarnings("MissingPermission")
    private void getLastLocation(){
        Log.i("LocationHandler","trying get last location");
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(activity, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Log.i("LocationHandler","location get");
                            currentLocation = task.getResult();
                        }
                    }
                });
    }
}

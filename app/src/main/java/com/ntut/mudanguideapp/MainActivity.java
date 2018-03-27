package com.ntut.mudanguideapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ntut.mudanguideapp.location.LocationChangeListener;
import com.ntut.mudanguideapp.location.LocationHandler;

/* Create By SpiritCat and in charge */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long currentBackPressedTime = 0;
    private int BACK_PRESSED_INTERVAL = 2000;

    private DrawerLayout drawer;

    private LocationHandler locationHandler;
    private Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        locationHandler=new LocationHandler(this,this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        location=locationHandler.getCurrentLocation();
        locationHandler.startLocationUpdates(lcl);
    }

    @Override
    protected void onStop(){
        super.onStop();
        locationHandler.stopLocationUpdates();
    }

    @Override
    protected void onDestroy(){
        locationHandler.stopLocationUpdates();
        Process.killProcess(Process.myPid());
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (System.currentTimeMillis()- currentBackPressedTime > BACK_PRESSED_INTERVAL) {

            currentBackPressedTime = System.currentTimeMillis();

            Toast.makeText(this, "再按一次返回鍵退出程式", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.camera:
                startActivity(new Intent(this,CameraActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    //nav item select
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private LocationChangeListener lcl=new LocationChangeListener() {
        @Override
        public void onLocationChange(Location l) {
            location=l;
            Log.i("Main",Double.toString(location.getLatitude()));
        }
    };
}

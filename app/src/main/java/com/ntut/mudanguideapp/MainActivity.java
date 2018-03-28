package com.ntut.mudanguideapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Process;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ntut.mudanguideapp.location.LocationChangeListener;
import com.ntut.mudanguideapp.location.LocationHandler;

import java.util.ArrayList;
import java.util.List;

/* Create By SpiritCat and in charge */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private long currentBackPressedTime = 0;
    private int BACK_PRESSED_INTERVAL = 2000;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<ConstraintLayout> pageList;

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

        tabLayout=findViewById(R.id.main_tabs);
        viewPager=findViewById(R.id.main_pager);

        locationHandler=new LocationHandler(this,this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        location=locationHandler.getCurrentLocation();
        locationHandler.startLocationUpdates(lcl);
        setUpTab();
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

    private void setUpTab(){
        tabLayout.addTab(tabLayout.newTab().setText("Page one"));
        tabLayout.addTab(tabLayout.newTab().setText("Page two"));
        setUpPager();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private void setUpPager(){
        pageList=new ArrayList<>();
        pageList.add(new MainPagerOne(this));
        pageList.add(new MainPagerTwo(this));
        viewPager.setAdapter(new pagerAdapter());
    }

    private LocationChangeListener lcl=new LocationChangeListener() {
        @Override
        public void onLocationChange(Location l) {
            location=l;
            Log.i("Main",Double.toString(location.getLatitude()));
        }
    };

    private class pagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

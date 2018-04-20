package com.ntut.mudanguideapp;

import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
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
import android.widget.ViewFlipper;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ntut.mudanguideapp.Database.InfoDatabase;
import com.ntut.mudanguideapp.location.LocationChangeListener;
import com.ntut.mudanguideapp.location.LocationHandler;

import java.util.ArrayList;
import java.util.List;

/* Create By SpiritCat and in charge */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int BACK_PRESSED_INTERVAL = 2000;

    private InfoDatabase infoDatabase;

    private long currentBackPressedTime = 0;

    private List<PagerActive> pageList;
    private int currentPage=0;

    private ViewFlipper flipper;

    private DrawerLayout drawer;

    private MaterialSearchView searchView;

    private LocationHandler locationHandler;
    private Location previousLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        infoDatabase=new InfoDatabase(this);

        flipper=findViewById(R.id.main_flipper);
        setUpPageList();

        searchView=findViewById(R.id.search_bar);
        searchView.closeSearch();
        searchView.setOnQueryTextListener(oqtl);
        searchView.setOnSearchViewListener(svl);

        locationHandler=new LocationHandler(this,this);

        previousLocation=new Location("previousLocation");
        previousLocation.setLatitude(0);
        previousLocation.setLongitude(0);
    }

    @Override
    protected void onResume(){
        super.onResume();
        locationHandler.startLocationUpdates(lcl);
        pageList.get(currentPage).startView();
        flipper.setDisplayedChild(currentPage);
        Log.i("Main","on resume");
    }

    @Override
    protected void onStop(){
        super.onStop();
        locationHandler.stopLocationUpdates();
        pageList.get(currentPage).stopView();
        Log.i("Main","on stop");
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
        }else if(searchView.isSearchOpen()) {
            searchView.closeSearch();
        }else if (currentPage!=0) {
            changePage(0);
        }else if (System.currentTimeMillis()- currentBackPressedTime > BACK_PRESSED_INTERVAL) {

            currentBackPressedTime = System.currentTimeMillis();

            Toast.makeText(this, "再按一次返回鍵退出程式", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("NullPointerException")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item=menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    //nav item select
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.home:
                changePage(0);
                break;
            case R.id.map:
                changePage(1);
                break;
            case R.id.local:
                changePage(2);
                break;
            case R.id.sight:
                changePage(3);
                break;
            case R.id.like:
                changePage(4);
                break;
            case R.id.about:
                changePage(5);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateDistance(Location currentLocation){
        float distance=currentLocation.distanceTo(previousLocation);
        Log.i("main",String.valueOf(distance));
        if(distance>=500){
            infoDatabase.OpenDB();
            Cursor cu=infoDatabase.getCursor(null,null);
            cu.moveToFirst();
            while(!cu.isAfterLast()){
                Location target=new Location("target");
                target.setLatitude(cu.getDouble(3));
                target.setLongitude(cu.getDouble(4));
                float disTo=currentLocation.distanceTo(target);

                infoDatabase.updateDB(
                        cu.getInt(0),
                        cu.getString(1),
                        cu.getString(2),
                        cu.getDouble(3),
                        cu.getDouble(4),
                        cu.getInt(5),
                        cu.getString(6),
                        disTo
                );

                cu.moveToNext();
            }
            previousLocation=currentLocation;
            infoDatabase.CloseDB();
        }
    }

    private void setUpPageList(){
        pageList=new ArrayList<>();
        pageList.add(new MainFragmentHome(this,this));
        pageList.add(new MainFragmentMap(this,this));
        pageList.add(new MainFragmentLocal(this,this));
        pageList.add(new MainFragmentSight(this,this));
        pageList.add(new MainFragmentLike(this,this));
        pageList.add(new MainFragmentAbout(this,this));
        pageList.add(new MainFragmentSearchResult(this,this));
    }

    private void changePage(int page){
        pageList.get(currentPage).stopView();
        pageList.get(page).startView();
        flipper.setDisplayedChild(page);
        currentPage=page;
    }

    private LocationChangeListener lcl=new LocationChangeListener() {
        @Override
        public void onLocationChange(Location l) {
            updateDistance(l);
        }
    };

    private MaterialSearchView.OnQueryTextListener oqtl= new MaterialSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            if(currentPage==6){
                pageList.get(6).onRefresh(query);
                pageList.get(6).stopView();
            }
            Log.i("Main","query="+query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if(currentPage==6 && searchView.isSearchOpen()){
                pageList.get(6).onRefresh(newText);
            }
            Log.i("Main","newText="+newText);
            return false;
        }
    };

    private MaterialSearchView.SearchViewListener svl=new MaterialSearchView.SearchViewListener() {
        int previousPage;
        @Override
        public void onSearchViewShown() {
            previousPage=currentPage;
            changePage(6);
            Log.i("Main","SearchViewShown");
        }

        @Override
        public void onSearchViewClosed() {
            Log.i("Main","SearchViewClosed");
        }
    };
}
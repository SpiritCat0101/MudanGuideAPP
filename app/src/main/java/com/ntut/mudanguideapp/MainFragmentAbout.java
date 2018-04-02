package com.ntut.mudanguideapp;

import android.content.Context;
import android.location.Location;

public class MainFragmentAbout extends PagerActive {
    private Context context;

    public MainFragmentAbout(Context c){
        super(c);
        context=c;
    }

    @Override
    public void startView(){

    }

    @Override
    public void stopView(){

    }

    @Override
    public void onRefresh(Location location){
    }
}

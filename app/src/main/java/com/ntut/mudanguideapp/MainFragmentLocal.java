package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

public class MainFragmentLocal extends PagerActive {
    private Context context;
    private Activity activity;

    public MainFragmentLocal(Context c, Activity a){
        super(c);
        context=c;
        activity=a;
    }

    @Override
    public void startView(){

    }

    @Override
    public void stopView(){

    }

    @Override
    public void onRefresh(String str){

    }
}

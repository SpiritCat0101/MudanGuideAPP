package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.location.Location;

public class MainFragmentLike extends PagerActive{
    private Context context;
    private Activity activity;

    public MainFragmentLike(Context c, Activity a){
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
    public void onRefresh(Object obj){

    }
}

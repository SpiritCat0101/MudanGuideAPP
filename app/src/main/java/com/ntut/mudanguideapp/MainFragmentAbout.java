package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

public class MainFragmentAbout extends PagerActive {
    private Activity activity;

    public MainFragmentAbout(Context c, Activity a){
        super(c);
        activity=a;

        setUpPreferenceFrame();
    }

    public MainFragmentAbout (Context context, AttributeSet attrs) { super(context, attrs); }

    @Override
    public void startView(){

    }

    @Override
    public void stopView(){

    }

    @Override
    public void onRefresh(Object obj){
    }

    private void setUpPreferenceFrame(){
        PreferenceClass preferenceClass=new PreferenceClass();

        android.app.FragmentTransaction ft=activity.getFragmentManager().beginTransaction();
        ft.add(R.id.preference_frame,preferenceClass);
        ft.commit();
    }
}

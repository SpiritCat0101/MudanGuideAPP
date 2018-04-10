package com.ntut.mudanguideapp;

import android.content.Context;
import android.location.Location;
import android.support.constraint.ConstraintLayout;

public abstract class PagerView extends ConstraintLayout {
    public PagerView(Context context){super(context);}
    public  abstract void onRefresh(Object obj);
}
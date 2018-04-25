package com.ntut.mudanguideapp;

import android.content.Context;
import android.location.Location;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.ViewDebug;

public abstract class PagerActive extends ConstraintLayout {
   public PagerActive (Context context){super(context);}
   public PagerActive(Context context, AttributeSet attrs) { super(context, attrs); }
   public abstract void startView();
   public abstract void stopView();
   public abstract void onRefresh(Object obj);
}
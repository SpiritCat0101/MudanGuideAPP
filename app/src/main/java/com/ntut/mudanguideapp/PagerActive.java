package com.ntut.mudanguideapp;

import android.content.Context;
import android.location.Location;
import android.support.constraint.ConstraintLayout;

public abstract class PagerActive extends ConstraintLayout {
   public PagerActive (Context context){super(context);}
   public abstract void startView();
   public abstract void stopView();
   public abstract void onRefresh(String str);
}
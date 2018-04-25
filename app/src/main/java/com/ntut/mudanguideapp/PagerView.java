package com.ntut.mudanguideapp;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public abstract class PagerView extends ConstraintLayout {
    public PagerView(Context context){super(context);}
    public PagerView(Context context, AttributeSet attrs) { super(context, attrs); }
    public  abstract void onRefresh(Object obj);
}
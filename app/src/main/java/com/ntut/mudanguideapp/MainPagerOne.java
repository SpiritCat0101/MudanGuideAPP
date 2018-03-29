package com.ntut.mudanguideapp;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MainPagerOne extends PagerView {
    Context context;

    public MainPagerOne(Context c) {
        super(c);
        context=c;
        View view = LayoutInflater.from(c).inflate(R.layout.pager_main, null);
        TextView textView = view.findViewById(R.id.pager_text);
        textView.setText("Page one");
        addView(view);
    }

    @Override
    public void onRefresh(Location location){
    }
}
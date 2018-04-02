package com.ntut.mudanguideapp;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SightPager extends PagerView {
    private Context context;

    private String[] villageName={
            "牡丹村",
            "石門村",
            "東源村",
            "旭海村",
            "高士村",
            "四海村"
    };

    private TextView villageInfo;

    public SightPager(Context c,int page) {
        super(c);
        context=c;
        View view = LayoutInflater.from(c).inflate(R.layout.pager_sight, null);
        villageInfo=view.findViewById(R.id.village_info);
        villageInfo.setText(villageName[page]);
        addView(view);
    }

    @Override
    public void onRefresh(Location location){
    }
}

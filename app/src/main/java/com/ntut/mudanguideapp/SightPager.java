package com.ntut.mudanguideapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

public class SightPager extends PagerView {
    private Context context;

    private static final String[] villageCat={
            "SI",
            "TJ",
            "MAL",
            "MAC",
            "KU",
            "SE"
    };

    private RecyclerView listView;
    private RecyclerViewHandler handler;

    public SightPager(Context c,int page) {
        super(c);
        context=c;
        View view = LayoutInflater.from(c).inflate(R.layout.pager_sight, null);
        listView=view.findViewById(R.id.sightList);

        setUpList(page);
        addView(view);
    }

    public SightPager(Context context, AttributeSet attrs) { super(context, attrs); }

    @Override
    public void onRefresh(Object obj){
        handler.startShow();
    }

    private void setUpList(int page){
        String query="village = '"+villageCat[page]+"' ORDER BY distance ASC";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}
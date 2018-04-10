package com.ntut.mudanguideapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ntut.mudanguideapp.Database.InfoDatabase;
import com.ntut.mudanguideapp.RecyclerView.RecyclerUpdateListener;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewAdapter;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class SightPager extends PagerView {
    private Context context;

    private InfoDatabase infoDatabase;

    private static final String[] villageName={
            "牡丹村",
            "石門村",
            "東源村",
            "旭海村",
            "高士村",
            "四海村"
    };

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
        infoDatabase=new InfoDatabase(context);
        View view = LayoutInflater.from(c).inflate(R.layout.pager_sight, null);
        listView=view.findViewById(R.id.sightList);

        setUpList(page);
        addView(view);
    }

    @Override
    public void onRefresh(Object obj){
        handler.startShow();
    }

    private void setUpList(int page){
        String query="village = '"+villageCat[page]+"'";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}
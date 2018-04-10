package com.ntut.mudanguideapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ntut.mudanguideapp.Database.InfoDatabase;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewAdapter;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class LocalPager extends PagerView {
    private Context context;

    private String[] localCat={
            "H",
            "D",
            "C",
            "E"
    };

    private RecyclerView listView;
    private RecyclerViewHandler handler;

    LocalPager(Context c, int page){
        super(c);
        context=c;

        View view = LayoutInflater.from(c).inflate(R.layout.pager_local, null);
        listView=view.findViewById(R.id.localList);

        setUpList(page);
        addView(view);
    }

    @Override
    public void onRefresh(Object obj) {
        handler.startShow();
    }

    private void setUpList(int page){
        String query="village = '"+localCat[page]+"'";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}

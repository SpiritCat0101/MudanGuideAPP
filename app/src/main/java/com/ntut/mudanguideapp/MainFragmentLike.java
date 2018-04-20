package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.IDNA;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ntut.mudanguideapp.Database.InfoDatabase;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewAdapter;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class MainFragmentLike extends PagerActive{
    private Context context;
    private Activity activity;

    private RecyclerView listView;
    private RecyclerViewHandler handler;

    public MainFragmentLike(Context c, Activity a){
        super(c);
        context=c;
        activity=a;

        listView=activity.findViewById(R.id.likeList);

        setUpList();
    }

    @Override
    public void startView(){
        handler.startShow();
    }

    @Override
    public void stopView(){

    }

    @Override
    public void onRefresh(Object obj){

    }

    private void setUpList(){
        String query="isLike = 1 ORDER BY distance ASC";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}

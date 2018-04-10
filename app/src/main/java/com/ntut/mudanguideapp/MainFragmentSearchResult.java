package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ntut.mudanguideapp.Database.InfoDatabase;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewAdapter;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainFragmentSearchResult extends PagerActive {
    private Context context;
    private Activity activity;

    private boolean isShow;

    private InfoDatabase infoDatabase;

    private RecyclerView listView;
    private RecyclerViewHandler handler;

    public MainFragmentSearchResult(Context c, Activity a){
        super(c);
        context=c;
        activity=a;

        listView=activity.findViewById(R.id.searchList);

        infoDatabase=new InfoDatabase(context);
    }

    @Override
    public void startView(){
        isShow=true;
    }

    @Override
    public void stopView(){
        isShow=false;
    }

    @Override
    public void onRefresh(Object obj){
        if(isShow){
            String search=(String) obj;
            setUpList(search);
        }
    }

    private void setUpList(String search){
        String query="name LIKE '"+search+"%' or content LIKE '%"+search+"%'";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}

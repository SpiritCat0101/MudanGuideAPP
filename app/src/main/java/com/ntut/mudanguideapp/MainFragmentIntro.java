package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

public class MainFragmentIntro extends PagerActive {
    private Context context;

    private RecyclerView listView;
    private RecyclerViewHandler handler;

    public MainFragmentIntro(Context c, Activity a){
        super(c);
        context=c;

        listView=a.findViewById(R.id.introList);

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

    private void setUpList() {
        String query="village = 'INTRO'";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}

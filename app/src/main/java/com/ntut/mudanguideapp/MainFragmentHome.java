package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

public class MainFragmentHome extends PagerActive {
    private Context context;

    private RecyclerView listView;
    private RecyclerViewHandler handler;

    public MainFragmentHome(Context c, Activity a){
        super(c);
        context=c;

        listView=a.findViewById(R.id.mainList);
        setUpList();
    }

    public MainFragmentHome(Context context, AttributeSet attrs) { super(context, attrs); }

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
        String query="village = 'ALL'";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}

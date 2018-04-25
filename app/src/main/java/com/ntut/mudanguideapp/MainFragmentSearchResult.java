package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

public class MainFragmentSearchResult extends PagerActive {
    private Context context;

    private boolean isShow;

    private RecyclerView listView;

    public MainFragmentSearchResult(Context c, Activity a){
        super(c);
        context=c;

        listView=a.findViewById(R.id.searchList);
    }

    public MainFragmentSearchResult(Context context, AttributeSet attrs) { super(context, attrs); }

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
        String query="name LIKE '"+search+"%' or content LIKE '%"+search+"%' ORDER BY distance ASC";

        RecyclerViewHandler handler=new RecyclerViewHandler(context,listView,query);
        handler.startShow();
    }
}
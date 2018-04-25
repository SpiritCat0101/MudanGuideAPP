package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

public class MainFragmentLike extends PagerActive{
    private Context context;

    private RecyclerView listView;
    private RecyclerViewHandler handler;

    public MainFragmentLike(Context c, Activity a){
        super(c);
        context=c;

        listView=a.findViewById(R.id.likeList);

        setUpList();
    }

    public MainFragmentLike(Context context, AttributeSet attrs) { super(context, attrs); }

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
        String query="isLike = 1 ORDER BY _id ASC";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}

package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

public class MainFragmentHome extends PagerActive {
    private Context context;
    private MainActivity mainActivity;

    private RecyclerView listView;
    private RecyclerViewHandler handler;

    private LinearLayout local;
    private LinearLayout sight;
    private LinearLayout like;

    public MainFragmentHome(MainActivity m, Context c, Activity a){
        super(c);
        context=c;
        mainActivity=m;

        listView=a.findViewById(R.id.mainList);

        local=a.findViewById(R.id.home_local);
        sight=a.findViewById(R.id.home_sight);
        like=a.findViewById(R.id.home_like);
        setUpOnClick();
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

    private void setUpOnClick(){
        local.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changePage(2);
            }
        });

        sight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changePage(3);
            }
        });

        like.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changePage(4);
            }
        });
    }

    private void setUpList(){
        String query="village = 'ALL'";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}

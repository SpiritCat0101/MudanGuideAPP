package com.ntut.mudanguideapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ntut.mudanguideapp.RecyclerView.RecyclerViewHandler;

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

    public LocalPager(Context c, int page){
        super(c);
        context=c;

        View view = LayoutInflater.from(c).inflate(R.layout.pager_local, null);
        listView=view.findViewById(R.id.localList);

        setUpList(page);
        addView(view);
    }

    public LocalPager(Context context, AttributeSet attrs) { super(context, attrs); }

    @Override
    public void onRefresh(Object obj) {
        handler.startShow();
    }

    private void setUpList(int page){
        String query="village = '"+localCat[page]+"'";

        handler=new RecyclerViewHandler(context,listView,query);
    }
}

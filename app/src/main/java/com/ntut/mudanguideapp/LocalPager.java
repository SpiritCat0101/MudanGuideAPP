package com.ntut.mudanguideapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ntut.mudanguideapp.Database.InfoDatabase;
import com.ntut.mudanguideapp.RecyclerView.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class LocalPager extends PagerView {
    private Context context;

    private InfoDatabase infoDatabase;

    private String[] localCat={
            "H",
            "D",
            "C",
            "E"
    };

    private RecyclerView listView;
    private String[] from={"_id","name","isLike"};

    LocalPager(Context c, int page){
        super(c);
        context=c;
        infoDatabase=new InfoDatabase(context);
        View view = LayoutInflater.from(c).inflate(R.layout.pager_local, null);
        listView=view.findViewById(R.id.localList);

        setUpList(page);
        addView(view);
    }

    @Override
    public void onRefresh(Object obj) { }

    private void setUpList(int page){
        Cursor cu;
        String query="village = '"+localCat[page]+"'";

        infoDatabase.OpenDB();
        cu=infoDatabase.getCursor(query,null);
        cu.moveToFirst();

        ArrayList<HashMap<String,Object>> arrayList=new ArrayList<>();
        for(int i=0;i<cu.getCount();i++){
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put(from[0],cu.getString(1));
            hashMap.put(from[1],cu.getInt(5));
            arrayList.add(hashMap);
            cu.moveToNext();
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(context,arrayList,from);
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);
        infoDatabase.CloseDB();
    }
}

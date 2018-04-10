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

import java.util.ArrayList;
import java.util.HashMap;

public class MainFragmentLike extends PagerActive{
    private Context context;
    private Activity activity;

    private InfoDatabase infoDatabase;

    private RecyclerView listView;
    private String[] from={"_id","name","isLike"};

    public MainFragmentLike(Context c, Activity a){
        super(c);
        context=c;
        activity=a;

        infoDatabase=new InfoDatabase(context);

        listView=activity.findViewById(R.id.likeList);

        setUpList();
    }

    @Override
    public void startView(){

    }

    @Override
    public void stopView(){

    }

    @Override
    public void onRefresh(Object obj){

    }

    private void setUpList(){
        Cursor cu;
        String query="isLike = 1";

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

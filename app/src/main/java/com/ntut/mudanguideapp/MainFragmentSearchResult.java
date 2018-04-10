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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainFragmentSearchResult extends PagerActive {
    private Context context;
    private Activity activity;

    private boolean isShow;

    private InfoDatabase infoDatabase;

    private RecyclerView listView;
    private String[] from={"_id","name","isLike"};

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
        Cursor cu;
        String query="name LIKE '"+search+"%' or content LIKE '%"+search+"%'";

        infoDatabase.OpenDB();
        cu=infoDatabase.getCursor(query,null);
        cu.moveToFirst();

        ArrayList<HashMap<String,Object>> arrayList=new ArrayList<>();
        Log.i("SearchResult",String.valueOf(cu.getCount()));
        for(int i=0;i<cu.getCount();i++){
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put(from[0],cu.getString(1));
            hashMap.put(from[1],cu.getInt(5));
            arrayList.add(hashMap);
            cu.moveToNext();
        }
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(context, arrayList,from);
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);
        infoDatabase.CloseDB();
    }
}

package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ntut.mudanguideapp.Database.InfoDatabase;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainFragmentSearchResult extends PagerActive {
    private Context context;
    private Activity activity;

    private boolean isShow;

    private InfoDatabase infoDatabase;

    private ListView listView;
    private String[] from={"main","sub"};
    private int[] to={android.R.id.text1,android.R.id.text2};

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

        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        Log.i("SearchResult",String.valueOf(cu.getCount()));
        for(int i=0;i<cu.getCount();i++){
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("main",cu.getString(1));
            hashMap.put("sub",cu.getString(2));
            arrayList.add(hashMap);
            cu.moveToNext();
        }
        SimpleAdapter adapter=new SimpleAdapter(context,arrayList,android.R.layout.simple_list_item_2,from,to);
        listView.setAdapter(adapter);
        infoDatabase.CloseDB();
    }
}

package com.ntut.mudanguideapp;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ntut.mudanguideapp.Database.InfoDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SightPager extends PagerView {
    private Context context;

    private InfoDatabase infoDatabase;

    private static final String[] villageName={
            "牡丹村",
            "石門村",
            "東源村",
            "旭海村",
            "高士村",
            "四海村"
    };

    private static final String[] villageCat={
            "SI",
            "TJ",
            "MAL",
            "MAC",
            "KU",
            "SE"
    };

    private ListView listView;
    private String[] from={"main","sub"};
    private int[] to={android.R.id.text1,android.R.id.text2};

    public SightPager(Context c,int page) {
        super(c);
        context=c;
        infoDatabase=new InfoDatabase(context);
        View view = LayoutInflater.from(c).inflate(R.layout.pager_sight, null);
        listView=view.findViewById(R.id.sightList);
        setUpList(page);
        addView(view);
    }

    @Override
    public void onRefresh(Location location){
    }

    private void setUpList(int page){
        Cursor cu;
        String query="village = '"+villageCat[page]+"'";

        infoDatabase.OpenDB();
        cu=infoDatabase.getCursor(query,null);
        cu.moveToFirst();

        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
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
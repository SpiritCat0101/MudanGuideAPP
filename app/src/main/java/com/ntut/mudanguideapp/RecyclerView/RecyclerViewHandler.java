package com.ntut.mudanguideapp.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;

import com.ntut.mudanguideapp.Database.InfoDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewHandler {
    private Context context;

    private InfoDatabase infoDatabase;

    private RecyclerView recyclerView;
    private String query;

    private ArrayList<SparseArray<Object>> arrayList;
    private RecyclerViewAdapter adapter;

    public RecyclerViewHandler(Context c,RecyclerView listView, String where){
        context=c;
        recyclerView=listView;
        query=where;
        infoDatabase=new InfoDatabase(context);
        setUpList();
    }

    public void startShow(){
        updateList();
    }

    private void updateList(){
        Cursor cu;

        infoDatabase.OpenDB();
        cu=infoDatabase.getCursor(query,null);
        cu.moveToFirst();
        arrayList.clear();
        for(int i=0;i<cu.getCount();i++){
            SparseArray<Object> sparseArray=new SparseArray<>();
            sparseArray.append(0,cu.getInt(0));
            sparseArray.append(1,cu.getString(1));
            sparseArray.append(2,cu.getString(2));
            sparseArray.append(3,cu.getDouble(3));
            sparseArray.append(4,cu.getDouble(4));
            sparseArray.append(5,cu.getInt(5));
            sparseArray.append(6,cu.getString(6));
            arrayList.add(sparseArray);
            cu.moveToNext();
        }
        adapter.notifyDataSetChanged();
        infoDatabase.CloseDB();
        Log.i("RecyclerHandler","list update");
    }

    private void setUpList(){
        Cursor cu;

        infoDatabase.OpenDB();
        cu=infoDatabase.getCursor(query,null);
        cu.moveToFirst();
        arrayList=new ArrayList<>();
        for(int i=0;i<cu.getCount();i++){
            SparseArray<Object> hashMap=new SparseArray<>();
            hashMap.append(0,cu.getInt(0));
            hashMap.append(1,cu.getString(1));
            hashMap.append(2,cu.getString(2));
            hashMap.append(3,cu.getDouble(3));
            hashMap.append(4,cu.getDouble(4));
            hashMap.append(5,cu.getInt(5));
            hashMap.append(6,cu.getString(6));
            arrayList.add(hashMap);
            cu.moveToNext();
        }
        adapter = new RecyclerViewAdapter(context,arrayList,rul);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        Log.i("RecyclerHandler","list set");
        infoDatabase.CloseDB();
    }

    private RecyclerUpdateListener rul=new RecyclerUpdateListener() {
        @Override
        public void onUpdate() {
            updateList();
        }
    };
}
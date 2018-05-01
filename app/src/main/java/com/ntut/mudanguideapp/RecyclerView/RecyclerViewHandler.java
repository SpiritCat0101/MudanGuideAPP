package com.ntut.mudanguideapp.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;

import com.ntut.mudanguideapp.Database.InfoDatabase;

import java.util.ArrayList;

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
        while(!cu.isAfterLast()){
            SparseArray<Object> sparseArray=new SparseArray<>();
            sparseArray.append(0,cu.getInt(0));
            sparseArray.append(1,cu.getString(1));
            sparseArray.append(3,cu.getDouble(3));
            sparseArray.append(4,cu.getDouble(4));
            sparseArray.append(5,cu.getInt(5));
            sparseArray.append(8,cu.getString(8));
            sparseArray.append(9,cu.getString(9));
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
        while(!cu.isAfterLast()){
            SparseArray<Object> sparseArray=new SparseArray<>();
            sparseArray.append(0,cu.getInt(0));
            sparseArray.append(1,cu.getString(1));
            sparseArray.append(3,cu.getDouble(3));
            sparseArray.append(4,cu.getDouble(4));
            sparseArray.append(5,cu.getInt(5));
            sparseArray.append(8,cu.getString(8));
            sparseArray.append(9,cu.getString(9));
            arrayList.add(sparseArray);
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
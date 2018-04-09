package com.ntut.mudanguideapp;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    private RecyclerView listView;
    private String[] from={"name"};

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
            hashMap.put("name",cu.getString(1));
            arrayList.add(hashMap);
            cu.moveToNext();
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(arrayList);
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);
        infoDatabase.CloseDB();
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
        public ArrayList<HashMap<String,String>> myValues;

        public RecyclerViewAdapter (ArrayList<HashMap<String,String>> myValues){
            this.myValues= myValues;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_modual, parent, false);
            return new MyViewHolder(listItem);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.nameText.setText(myValues.get(position).get(from[0]));
        }

        @Override
        public int getItemCount() {
            return myValues.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView nameText;
            public MyViewHolder(View itemView) {
                super(itemView);
                nameText = itemView.findViewById(R.id.nameText);
            }
        }
    }
}
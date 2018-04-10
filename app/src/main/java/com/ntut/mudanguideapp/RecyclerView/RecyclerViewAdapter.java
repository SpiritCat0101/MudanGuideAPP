package com.ntut.mudanguideapp.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ntut.mudanguideapp.Database.InfoDatabase;
import com.ntut.mudanguideapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;

    private InfoDatabase infoDatabase;

    private RecyclerUpdateListener listener;

    private ArrayList<HashMap<String,Object>> myValues;
    private String[] from;

    public RecyclerViewAdapter (Context c,ArrayList<HashMap<String,Object>> myValues, String[] from){
        this.myValues= myValues;
        this.from=from;
        this.context=c;
        this.infoDatabase=new InfoDatabase(context);
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_modual, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        infoDatabase.OpenDB();
        holder.nameText.setText((String)myValues.get(position).get(from[0]));
        if((int)myValues.get(position).get(from[1]) == 1){
            holder.likeIcon.setImageResource(R.mipmap.ic_star_black_36dp);
        }
        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //infoDatabase.updateDB();
                listener.onUpdate();
            }
        });
        infoDatabase.CloseDB();
    }

    @Override
    public int getItemCount() {
        return myValues.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private ImageView likeIcon;
        MyViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            likeIcon = itemView.findViewById(R.id.likeImage);
        }
    }
}

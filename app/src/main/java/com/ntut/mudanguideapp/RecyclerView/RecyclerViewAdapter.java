package com.ntut.mudanguideapp.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
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

    private ArrayList<SparseArray<Object>> myValues;

    RecyclerViewAdapter (Context c,ArrayList<SparseArray<Object>> myValues,RecyclerUpdateListener l){
        this.myValues= myValues;
        this.context=c;
        this.infoDatabase=new InfoDatabase(context);
        this.listener=l;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_modual, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.MyViewHolder holder, final int pos) {
        final int _id=(int) myValues.get(pos).get(0);
        final String name=(String) myValues.get(pos).get(1);
        final String content=(String) myValues.get(pos).get(2);
        final double Lat=(double) myValues.get(pos).get(3);
        final double Lng=(double) myValues.get(pos).get(4);
        final int isLike=(int) myValues.get(pos).get(5);
        final String village=(String) myValues.get(pos).get(6);

        holder.nameText.setText(name);
        if(isLike == 1){
            holder.likeIcon.setImageResource(R.mipmap.ic_star_black_36dp);
        }else{
            holder.likeIcon.setImageResource(R.mipmap.ic_star_border_black_36dp);
        }
        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoDatabase.OpenDB();
                infoDatabase.updateDB(_id,name,content,Lat,Lng,(isLike+1)%2,village);
                infoDatabase.CloseDB();
                listener.onUpdate();
            }
        });
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

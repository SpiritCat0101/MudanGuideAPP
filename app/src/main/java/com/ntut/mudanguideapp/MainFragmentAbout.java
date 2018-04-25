package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainFragmentAbout extends PagerActive {
    private Context context;

    private ListView listView;
    private String[] from={"main","sub"};
    private int[] to={android.R.id.text1,android.R.id.text2};

    public MainFragmentAbout(Context c, Activity a){
        super(c);
        context=c;

        listView = a.findViewById(R.id.about_list);
        setUpList();
    }

    public MainFragmentAbout (Context context, AttributeSet attrs) { super(context, attrs); }

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
        String[] mainItem=getResources().getStringArray(R.array.author_main);
        String[] subItem=getResources().getStringArray(R.array.author_sub);

        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        for(int i=0;i<mainItem.length;i++){
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("main",mainItem[i]);
            hashMap.put("sub",subItem[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter adapter=new SimpleAdapter(context,arrayList,android.R.layout.simple_list_item_2,from,to);
        listView.setAdapter(adapter);
    }
}

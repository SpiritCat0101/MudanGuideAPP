package com.ntut.mudanguideapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainPagerTwo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pager_main, container, false);
        TextView textView = rootView.findViewById(R.id.pager_text);
        textView.setText("ViewFragment2");
        return rootView;
    }
}
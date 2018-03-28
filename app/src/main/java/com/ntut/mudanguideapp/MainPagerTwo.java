package com.ntut.mudanguideapp;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MainPagerTwo extends ConstraintLayout {
    public MainPagerTwo(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.pager_main, null);
        TextView textView = view.findViewById(R.id.pager_text);
        textView.setText("Page two");
        addView(view);
    }
}
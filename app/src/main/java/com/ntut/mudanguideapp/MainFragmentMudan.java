package com.ntut.mudanguideapp;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainFragmentMudan extends PagerActive{

    private Context context;
    private Activity activity;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<PagerView> pageList;
    private int currentTab=0;

    public MainFragmentMudan(Context c, Activity a){
        super(c);
        context=c;
        activity=a;
        tabLayout=activity.findViewById(R.id.main_tabs);
        viewPager=activity.findViewById(R.id.main_pager);
        setUpTab();
    }

    @Override
    public void startView(){
    }

    @Override
    public void stopView(){
    }

    @Override
    public void onRefresh(Location location){

    }

    private void setUpTab(){
        pageList=new ArrayList<>();
        pageList.add(new MainPagerOne(context));
        pageList.add(new MainPagerTwo(context));
        viewPager.setAdapter(new pagerAdapter());
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(opcl);
        Log.i("MainFragmentMudan","set up tab");
    }

    private ViewPager.OnPageChangeListener opcl=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            currentTab=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class pagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
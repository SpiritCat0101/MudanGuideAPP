package com.ntut.mudanguideapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class IntroActivity extends AppCompatActivity {
    private FrameLayout webContainer;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Toolbar toolbar = findViewById(R.id.intro_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));

        webContainer = findViewById(R.id.intro_web);
        webView = new WebView(this);
        webView.loadUrl("file:///android_asset/HTML/test.html");
        webContainer.addView(webView);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(webView != null){
            webContainer.removeAllViews();
            webView.clearCache(false);
            webView.destroy();
            webView=null;
            Log.i("IntroActivity","webView release");
        }

        Log.i("IntroActivity","destroy");
    }

    @SuppressWarnings("NullPointerException")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intro_menu, menu);
        MenuItem item=menu.findItem(R.id.action_like);
        item.setIcon(getDrawable(R.mipmap.ic_star_border_black_36dp));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
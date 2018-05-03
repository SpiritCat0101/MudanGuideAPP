package com.ntut.mudanguideapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ntut.mudanguideapp.Database.InfoDatabase;

public class IntroActivity extends AppCompatActivity {
    private FrameLayout webContainer;
    private WebView webView;
    private Intent intent;
    private MenuItem item;

    private int isLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        intent=getIntent();
        getExtra();

        Toolbar toolbar = findViewById(R.id.intro_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));

        TextView textView = findViewById(R.id.intro_title);
        textView.setText(intent.getStringExtra("name"));

        webContainer = findViewById(R.id.intro_web);
        webView = new WebView(this);
        //webView.loadUrl("file:///android_asset/HTML/"+intent.getStringExtra("html"));
        webView.loadUrl("file:///android_asset/HTML/MudanTownIntro.html");
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
        item=menu.findItem(R.id.action_like);
        iconChange();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_like:
                InfoDatabase infoDatabase = new InfoDatabase(this);
                infoDatabase.OpenDB();
                infoDatabase.updateIsLike(intent.getIntExtra("_id",0));
                isLike=(isLike+1)%2;
                iconChange();
                infoDatabase.CloseDB();
                break;
            case R.id.action_map:
                mapClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getExtra(){
        isLike=intent.getIntExtra("isLike",0);
    }

    public void mapClick(){
        String loc=String.valueOf(intent.getDoubleExtra("Lat",0))+","+String.valueOf(intent.getDoubleExtra("Lng",0));
        Uri gmmIntentUri = Uri.parse("geo:"+loc+"?z=14&q="+loc+"("+intent.getStringExtra("name")+")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }else{
            Toast.makeText(this, "Oops, some error happen, please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    public void iconChange(){
        if(isLike == 1){
            item.setIcon(getDrawable(R.mipmap.ic_star_black_36dp));
        }else{
            item.setIcon(getDrawable(R.mipmap.ic_star_border_black_36dp));
        }
    }
}
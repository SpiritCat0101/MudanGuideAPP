package com.ntut.mudanguideapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ntut.mudanguideapp.Database.InfoDatabase;

import java.io.IOException;

public class PreferenceClass extends PreferenceFragment {
    private InfoDatabase infoDatabase;

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Preference pref_DB;
        final Preference pref_official_link;
        context=getContext();

        addPreferencesFromResource(R.xml.preference);

        infoDatabase=new InfoDatabase(context);

        pref_DB=findPreference("pref_DB");
        pref_DB.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDialog();
                return false;
            }
        });

        pref_official_link=findPreference("pref_link_official");
        pref_official_link.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String url = "https://www.pthg.gov.tw/townmdt";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return false;
            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("清除資料庫");
        builder.setMessage("清除資料庫會讓所有資料丟失，確定嗎?\n清除後會重啟程式");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                infoDatabase.copyDB();
                System.exit(0);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }
}
package com.ntut.mudanguideapp.Database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseFileHandler {
    private Context context;

    private String DB_PATH;
    private String DB_NAME;

    DatabaseFileHandler(Context c,String name){
        context=c;
        DB_PATH=this.context.getFilesDir().getAbsolutePath() +"/";
        DB_NAME=name+".db";
    }

    void importDB(){
        if(!checkDbExists()) {  //若此database不在手機裡則嘗試由外部檔案匯入
            try {
                copy();
            } catch (IOException e) {
                Log.e("DatabaseFileHandler", "database import fail");
            }
        }
        else{
            Log.i("DatabaseFileHandler", "database already imported");
        }
    }

    SQLiteDatabase getDatabase(){
        return SQLiteDatabase.openDatabase(DB_PATH + DB_NAME
                , null, SQLiteDatabase.OPEN_READWRITE);
    }

    void copy() throws IOException {  //將外部DB匯入到手機裡
        AssetManager assetManager = this.context.getAssets();
        InputStream in = assetManager.open("Database/"+DB_NAME);
        OutputStream out = new FileOutputStream(DB_PATH+DB_NAME);
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
        in.close();
        out.flush();
        out.close();
        Log.i("DatabaseFileHandler", "database import success");
    }

    private Boolean checkDbExists(){  //檢查此database是否存在手機裡
        return new File(this.DB_PATH+DB_NAME).exists();
    }
}
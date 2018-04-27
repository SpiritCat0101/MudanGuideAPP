package com.ntut.mudanguideapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InfoDatabase {
    private String DB_PATH;
    private final String DB_NAME="SightInfo_Database.db";
    private final String Table_name="SightInfo";
    private String[] DB_menu={"_id","name","content","Lat","Lng","isLike","village","distance","previewImg","html"};
    private final Context context;
    private SQLiteDatabase myDataBase;

    public InfoDatabase(Context context){
        Log.i("InfoDatabase", "database create");
        this.context= context;
        this.DB_PATH=this.context.getFilesDir().getAbsolutePath() +"/";
        if(!checkDbExists()) {  //若此database不在手機裡則嘗試由外部檔案匯入
            try {
                this.CopyDB();
            } catch (IOException e) {
                Log.e("InfoDatabase", "database import fail");
            }
        }
        else{
            Log.i("InfoDatabase", "database already imported");
        }
    }

    private Boolean checkDbExists(){  //檢查此database是否存在手機裡
        return new File(this.DB_PATH+this.DB_NAME).exists();
    }

    public void CopyDB() throws IOException {  //將外部DB匯入到手機裡
        AssetManager assetManager = this.context.getAssets();
        InputStream in = assetManager.open("Database/"+this.DB_NAME);
        OutputStream out = new FileOutputStream(this.DB_PATH+this.DB_NAME);
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
        in.close();
        out.flush();
        out.close();
        Log.i("InfoDatabase", "database import success");
    }

    public void OpenDB(){
        if(myDataBase == null) {
            this.myDataBase = SQLiteDatabase.openDatabase(this.DB_PATH + this.DB_NAME
                    , null, SQLiteDatabase.OPEN_READWRITE);
            Log.i("InfoDatabase", "database open success");
        }
    }

    public void CloseDB() {
        if(myDataBase != null){
            myDataBase.close();
            myDataBase=null;
        }
        Log.i("InfoDatabase", "database close success");
    }

    public Cursor getCursor(String where, String[] whereChoise){
        final Cursor c=myDataBase.query(Table_name,DB_menu,where,whereChoise,null,null,null);
        Log.i("InfoDatabase","Cursor get");
        return c;
    }

    public void updateDB(int _id,String name
            ,String content
            ,double Lat
            ,double Lng
            ,int isLike
            ,String village
            ,float distance
            ,String previewImg
            ,String html){
        ContentValues cv=new ContentValues();

        cv.put(DB_menu[1],name);
        cv.put(DB_menu[2],content);
        cv.put(DB_menu[3],Lat);
        cv.put(DB_menu[4],Lng);
        cv.put(DB_menu[5],isLike);
        cv.put(DB_menu[6],village);
        cv.put(DB_menu[7],distance);
        cv.put(DB_menu[8],previewImg);
        cv.put(DB_menu[9],html);

        myDataBase.update(Table_name,cv,"_id = "+Integer.toString(_id),null);
        Log.i("InfoDatabase","update success");
    }
}

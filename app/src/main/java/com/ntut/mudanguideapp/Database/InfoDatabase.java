package com.ntut.mudanguideapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

public class InfoDatabase{
    /**
     *  Database version for check update
     *  Execute PRAGMA user_version = [version] in database to modify new version
     */
    private static final int DATABASE_VERSION = 1;

    private static final String DB_NAME="SightInfo_Database";
    private static final String Table_name="SightInfo";
    private String[] DB_menu={"_id","name","content","Lat","Lng","isLike","village","distance","previewImg","html"};
    private DatabaseFileHandler fileHandler;
    private SQLiteDatabase myDataBase;

    public InfoDatabase(Context context){
        Log.i("InfoDatabase", "database create");
        fileHandler=new DatabaseFileHandler(context,DB_NAME);
        fileHandler.importDB();
    }

    public void OpenDB(){
        if(myDataBase == null) {
            this.myDataBase = fileHandler.getDatabase();
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

    public void updateDB(int _id
            ,String name
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

    public boolean updateIsLike(int _id){
        Cursor cu = this.getCursor("_id = "+String.valueOf(_id),null);

        if(cu.getCount()==1){
            cu.moveToFirst();
            this.updateDB(
                    _id,
                    cu.getString(1),
                    cu.getString(2),
                    cu.getDouble(3),
                    cu.getDouble(4),
                    (cu.getInt(5)+1)%2,
                    cu.getString(6),
                    cu.getFloat(7),
                    cu.getString(8),
                    cu.getString(9)
            );
            cu.close();
            Log.i("InfoDatabase","isLike update");
            return true;
        }else{
            cu.close();
            Log.i("InfoDatabase","isLike update fail conflict id");
            return false;
        }
    }

    public void versionSync(){
        this.OpenDB();

        if(myDataBase.getVersion()!=DATABASE_VERSION){
            Log.i("InfoDatabase","Database is not up to date");
            copyDB();
        }else{
            Log.i("InfoDatabase","Database is up to date");
        }

        this.CloseDB();
    }

    public void copyDB(){
        try{
            fileHandler.copy();
            Log.i("InfoDatabase","copy success");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
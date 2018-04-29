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
    private static final int DATABASE_VERSION = 0;

    /**
     *  Database update method control flag
     *  1 for new data include, just insert new data, keep old user isLike data
     *  2 for data table structure modify, copy new database and cover the old one
     *  isLike data will lost !!!
     */
    private static final int UPDATE_TYPE=2;

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

    public void versionSync(){
        this.OpenDB();

        if(myDataBase.getVersion()!=DATABASE_VERSION){
            Log.i("InfoDatabase","Database is not up to date");
            switch (UPDATE_TYPE){
                case 1:
                    dataSync();
                    myDataBase.execSQL("PRAGMA user_version = "+String.valueOf(DATABASE_VERSION));
                    break;
                case 2:
                    copyDB();
                    break;
            }
        }else{
            Log.i("InfoDatabase","Database is up to date");
        }

        this.CloseDB();
    }

    public void copyDB(){
        try{
            fileHandler.copy();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void insertDB(String name
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

        myDataBase.insert(Table_name, null, cv);
        Log.i("InfoDatabase","insert success");
    }

    public void emptyDB(){
        myDataBase.execSQL("DELETE FROM "+Table_name);
        Log.i("Route_Database","empty success");
    }


    private void dataSync(){
        fileHandler.importTempDB();
        SQLiteDatabase tempDatabase=fileHandler.getTempDatabase();

        Cursor oldC=getCursor(null,null);
        Cursor newC=tempDatabase.query(Table_name,DB_menu,null,null,null,null,null);

        emptyDB();

        oldC.moveToFirst();
        newC.moveToFirst();

        while(!newC.isAfterLast()){
            insertDB(
                    newC.getString(1),
                    newC.getString(2),
                    newC.getDouble(3),
                    newC.getDouble(4),
                    oldC.getInt(5),
                    newC.getString(6),
                    newC.getFloat(7),
                    newC.getString(8),
                    newC.getString(9)
            );
            oldC.moveToNext();
            newC.moveToNext();
        }

        newC.close();
        tempDatabase.close();
        fileHandler.deleteTempDB();
    }
}
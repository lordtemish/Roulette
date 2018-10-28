package com.dynamica.lordtemich.roulette;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RouletteDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "roulettedb"; // the name of our database
    private static final int DB_VERSION = 1;
    public RouletteDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE position (_id INTEGER PRIMARY KEY AUTOINCREMENT, PONUMBER INTEGER);");
        db.execSQL("CREATE TABLE rozygr (_id INTEGER PRIMARY KEY AUTOINCREMENT,POSITIONS TEXT);");
        db.execSQL("CREATE TABLE token (_id INTEGER PRIMARY KEY AUTOINCREMENT,ID TEXT);");
        ContentValues cv=new ContentValues();
        ContentValues cv2=new ContentValues();
        ContentValues cv3=new ContentValues();
        cv.put("PONUMBER",7);
        cv2.put("POSITIONS","7");
        cv3.put("ID"," ");
        db.insert("position","",cv);
        db.insert("rozygr","",cv2);
        db.insert("token","",cv3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV,int newV){

    }
}

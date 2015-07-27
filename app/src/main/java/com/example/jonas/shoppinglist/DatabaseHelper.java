package com.example.jonas.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static String DATABASE_NAME = "shoppingItems.db";
    public static String TABLE_NAME = "shopping_data";
    public static String COL1 = "id";
    public static String COL2 = "item";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + COL1 + "INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        db.insert(TABLE_NAME, null, contentValues);
        //https://www.youtube.com/results?search_query=android+botngets voor het vervolg, kijken op 7:15
        return true;
    }
}

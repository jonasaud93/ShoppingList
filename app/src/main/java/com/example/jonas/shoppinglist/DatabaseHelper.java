package com.example.jonas.shoppinglist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;

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
        db.execSQL("create table " + TABLE_NAME + "(" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT)");
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
        long result = db.insert(TABLE_NAME, null, contentValues);

        if(result == -1)
            return false;

        return true;
    }

    public ArrayList<String> getAllItems(){
        ArrayList<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL2 + " FROM " + TABLE_NAME, null);

        while(cursor.moveToNext()){
            items.add(cursor.getString(cursor.getColumnIndex(COL2)));
        }
        android.util.Log.i("DatabaseHelper getAll()", "received " + items.size() + " records");
        return items;
    }

    public void clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}

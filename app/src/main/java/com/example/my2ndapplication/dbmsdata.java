package com.example.my2ndapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbmsdata extends SQLiteOpenHelper {


    public static final String RECORD = "RECORD";
    public static final String ID = "ID";
    public static final String NUMS = "NUMS";

    public dbmsdata(@Nullable Context context) {
        super(context, "record", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String rec= "CREATE TABLE " + RECORD + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NUMS + " DOUBLE)";
    db.execSQL(rec);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RECORD);
        onCreate(db);
    }
    public boolean add(putdata nos){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(NUMS,nos.getNo());
        long insert = db.insert(RECORD, null,cv);
        db.close();
        return insert!=-1;

    }
    public List<putdata> allno() {
        List<putdata> returnlist = new ArrayList<>();
        String query = "SELECT * FROM " + RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int a = cursor.getInt(0);
                double b = cursor.getDouble(1);
                putdata c = new putdata(a, b);
                returnlist.add(c);
            } while (cursor.moveToNext());
        } else {}
        cursor.close();
        db.close();
        return returnlist;
    }}


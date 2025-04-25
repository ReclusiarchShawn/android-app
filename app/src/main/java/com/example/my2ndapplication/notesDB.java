package com.example.my2ndapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class notesDB extends SQLiteOpenHelper {
    public static final String NOTES = "NOTES";
    public static final String ID1 = "ID1";
    public static final String WORDS = "WORDS";
    public static final String WORDS2 = "WORDS2";

    public notesDB(@Nullable Context context) {
        super(context, "dbnotes", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String note = "CREATE TABLE " + NOTES + "(" + ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + WORDS + " TEXT," + WORDS2 + " TEXT) ";
        db.execSQL(note);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTES);
        onCreate(db);
    }

    public long writenotes(noteClass notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORDS, notes.getTexts());
        cv.put(WORDS2, notes.getTexts2());
        long result = db.insert(NOTES, null, cv);
            db.close();
            return result;
    }

    public List<noteClass> showlist() {
        List<noteClass> list = new ArrayList<>();
        String query = "SELECT * FROM " + NOTES ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                noteClass vars = new noteClass();
                vars.setId1(cursor.getInt(0));
                vars.setTexts(cursor.getString(1));
                vars.setTexts2(cursor.getString(2));
                list.add(vars);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }


    public noteClass noteid(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(NOTES, new String[]{ID1, WORDS, WORDS2}, ID1 + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            noteClass vars = new noteClass();
            vars.setId1(cursor.getInt(0));
            vars.setTexts(cursor.getString(1));
            vars.setTexts2(cursor.getString(2));
            cursor.close();
            return vars;
        }
        return null;
    }

    public boolean update(noteClass note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put(WORDS, note.getTexts());
            cv.put(WORDS2, note.getTexts2());
            int change = db.update(NOTES, cv, ID1 + " = ?", new String[]{String.valueOf(note.getId1())}
            );
            return change > 0;
        } finally {
            db.close();
        }
    }
    public boolean deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(NOTES, ID1 + " = ?", new String[]{String.valueOf(id)}
        );
        db.close();
        return rowsDeleted > 0;
    }
}

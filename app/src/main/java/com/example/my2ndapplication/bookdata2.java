package com.example.my2ndapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class bookdata2 extends SQLiteOpenHelper {
    public static final String INGREDIENTS = "INGREDIENTS";
    public static final String ID_1 = "ID_1";
    public static final String RECIPE = "RECIPE";
    public static final String ADD_RECIPE = "ADD_RECIPE";

    public bookdata2(@Nullable Context context) {
        super(context, "ingredients", null, 2  );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + INGREDIENTS + "(" + ID_1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + RECIPE + " TEXT," + ADD_RECIPE + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS + " ");
        onCreate(db);
    }
    public boolean addrecipe(bookvar2 var){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(RECIPE,var.getIngredient());
        cv.put(ADD_RECIPE,var.getAddrecipe());
        long in=db.insert(INGREDIENTS,null,cv);
        if(in==-1){
            return false;
        }else { return true;
    }
}
    public List<bookvar2> showingredients(){
        List<bookvar2> a=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+ INGREDIENTS;
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(0);
                String recipe=cursor.getString(1);
                String add=cursor.getString(2);
                bookvar2 b=new bookvar2(id,recipe,add);
                a.add(b);
            }while (cursor.moveToNext());
        }cursor.close();
        db.close();
        return a;
    }
}

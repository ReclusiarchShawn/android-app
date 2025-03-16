package com.example.my2ndapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class bookdata extends SQLiteOpenHelper {
    public static final String COOKBOOK = "COOKBOOK";
    public static final String ID = "ID";
    public static final String FOOD_NAME = "FOOD_NAME";
    public static final String IMG_PATH = "IMG_PATH";
//    public static final String RECIPES = "recipes";
//    public static final String ID1 = "id";
//    public static final String NAME = "name";
//    public static final String DETAILS = "details";
//    public static final String INGREDIENT = "ingredient";
//    public static final String INGREDIENTS = INGREDIENT + "s";
//    public static final String FOOD_ID1 = "food_id";
//    public static final String FOOD_ID = FOOD_ID1;

    public bookdata(@Nullable Context context) {
        super(context, "cookbook", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + COOKBOOK + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FOOD_NAME + " TEXT, " + IMG_PATH + " TEXT)";
        db.execSQL(query);
//        String createRecipesTable = "CREATE TABLE " + RECIPES + " (" + ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + DETAILS + " TEXT)";
//        db.execSQL(createRecipesTable);
//        String createIngredientsTable = "CREATE TABLE " + INGREDIENTS + " (" + ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FOOD_ID + " INTEGER, " + INGREDIENT + " TEXT, FOREIGN KEY(" + FOOD_ID1 + ") REFERENCES " + RECIPES + "(" + ID1 + ") ON DELETE CASCADE)";
//        db.execSQL(createIngredientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COOKBOOK);
//        db.execSQL("DROP TABLE IF EXISTS " + RECIPES);
//        db.execSQL("DROP TABLE IF EXISTS " + INGREDIENTS);
        onCreate(db);
    }
    public boolean addfood(bookvar var){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(FOOD_NAME,var.getFoodname());
        cv.put(IMG_PATH,var.getImg_path());
        long foodInsert = db.insert(COOKBOOK, null, cv);

        if (foodInsert == -1) {
            return false;
        }else {return true;

//        ContentValues recipeValues = new ContentValues();
//        recipeValues.put(NAME, var.getFoodname());
//        recipeValues.put(DETAILS, recipeDetails);
//        long recipeInsert = db.insert(RECIPES, null, recipeValues);
//
//        db.close();
//        return recipeInsert != -1;
    }}
    public List<bookvar> viewfood(){
        SQLiteDatabase db=this.getReadableDatabase();
        List<bookvar> foodlist=new ArrayList<>();
        String query="SELECT * FROM " + COOKBOOK;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id= cursor.getInt(0);
                String name=cursor.getString(1);
                String img=cursor.getString(2);
                bookvar a=new bookvar(id,name,img);
                foodlist.add(a);
            }while (cursor.moveToNext());
        } cursor.close();
        db.close();
        return foodlist;
    }
    public boolean delete(bookvar delmod){
        SQLiteDatabase db=this.getWritableDatabase();

        int deletedRows=db.delete(COOKBOOK, ID + "=?", new String[]{String.valueOf(delmod.getId())});
        db.close();
        return deletedRows > 0;
    }
//    public String getRecipeDetails(int foodId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + COOKBOOK + " WHERE " + ID + " = ?", new String[]{String.valueOf(foodId)});
//
//        if (cursor.moveToFirst()) {
//            String details = "Food: " + cursor.getString(1) + "\nImage Path: " + cursor.getString(2);
//            cursor.close();
//            db.close();
//            return details;
//        }
//
//        return "Recipe not found";
//    }
//    public List<String> getIngredients(int foodId) {
//        List<String> ingredients = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT " + INGREDIENT + " FROM " + INGREDIENTS + " WHERE " + FOOD_ID + " = ?", new String[]{String.valueOf(foodId)});
//
//
//        if (cursor.moveToFirst()) {
//            do {
//                ingredients.add(cursor.getString(0));  // Get ingredient name
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return ingredients;
//    }
//    public int addRecipe(String name) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("name", name);
//        values.put("details", "Recipe details here");
//
//        long id = db.insert("recipes", null, values);
//        db.close();
//
//        return (int) id; // Return newly created recipe ID
//    }
//    public void addIngredient(int foodId, String ingredient) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("food_id", foodId);
//        values.put("ingredient", ingredient);
//
//        db.insert("ingredients", null, values);
//        db.close();
//    }
}

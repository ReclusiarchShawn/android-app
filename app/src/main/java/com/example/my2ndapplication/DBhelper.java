package com.example.my2ndapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MaryCookbook.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_RECIPES = "recipes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_INGREDIENTS = "ingredients";
    private static final String COLUMN_INSTRUCTIONS = "instructions";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_IMAGE + " TEXT,"
                + COLUMN_INGREDIENTS + " TEXT,"
                + COLUMN_INSTRUCTIONS + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    // Add a new recipe
    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, recipe.getName());
        cv.put(COLUMN_IMAGE, recipe.getImage());
        cv.put(COLUMN_INGREDIENTS, recipe.getIngredients());
        cv.put(COLUMN_INSTRUCTIONS, recipe.getInstructions());
        db.insert(TABLE_RECIPES, null, cv);
        db.close();
    }

    // Get all recipes
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setId(cursor.getInt(0));
                recipe.setName(cursor.getString(1));
                recipe.setImage(cursor.getString(2));
                recipe.setIngredients(cursor.getString(3));
                recipe.setInstructions(cursor.getString(4));
                recipes.add(recipe);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipes;
    }

    // Delete a recipe
    public void deleteRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public Recipe getRecipeById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_RECIPES,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_IMAGE, COLUMN_INGREDIENTS, COLUMN_INSTRUCTIONS},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor != null && cursor.moveToFirst()) {
            Recipe recipe = new Recipe();
            recipe.setId(cursor.getInt(0));
            recipe.setName(cursor.getString(1));
            recipe.setImage(cursor.getString(2));
            recipe.setIngredients(cursor.getString(3));
            recipe.setInstructions(cursor.getString(4));
            cursor.close();
            return recipe;
        }
        return null; // Recipe not found
    }

    public boolean upgrade(Recipe edit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        try {
            cv.put(COLUMN_NAME, edit.getName());
            cv.put(COLUMN_IMAGE, edit.getImage());
            cv.put(COLUMN_INGREDIENTS,edit.getIngredients());
            cv.put(COLUMN_INSTRUCTIONS,edit.getInstructions());
            int rowsAffected = db.update(TABLE_RECIPES, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(edit.getId())}
            );
            return rowsAffected > 0;
        } finally {
            db.close();
        }
    }
}
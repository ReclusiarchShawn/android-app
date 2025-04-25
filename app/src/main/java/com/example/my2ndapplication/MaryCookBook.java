package com.example.my2ndapplication;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MaryCookBook extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CustomList adapter;
    private DBhelper db;
    private List<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marycookbook);

        db = new DBhelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setContentDescription("Add new recipe");
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MaryCookBook.this, AddRecipe.class);
            startActivity(intent);
        });

        loadRecipes();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            refreshRecipes();
        }
    }
    private void refreshRecipes() {
        List<Recipe> newRecipes = db.getAllRecipes();
        recipes.clear();
        recipes.addAll(newRecipes);
        adapter.notifyDataSetChanged();
    }

    private void loadRecipes() {
        recipes = db.getAllRecipes();
        adapter = new CustomList(recipes, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes();
    }
}
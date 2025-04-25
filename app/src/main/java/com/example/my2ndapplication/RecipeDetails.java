
package com.example.my2ndapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.my2ndapplication.R;

public class RecipeDetails extends AppCompatActivity {
    private TextView tvName, tvIngredients, tvInstructions;
    private ImageView imgFood;
    private Button btnEdit;
    private DBhelper db;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        db = new DBhelper(this);
        recipeId = getIntent().getIntExtra("RECIPE_ID", -1);

        tvName = findViewById(R.id.tvName);
        tvIngredients = findViewById(R.id.tvIngredients);
        tvInstructions = findViewById(R.id.tvInstructions);
        imgFood = findViewById(R.id.imgFood);
        btnEdit = findViewById(R.id.btnEdit);

        loadRecipeDetails();

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddRecipe.class);
            intent.putExtra("RECIPE_ID", recipeId);
            startActivity(intent);
        });
    }

    private void loadRecipeDetails() {
        Recipe recipe = db.getRecipeById(recipeId);
        if (recipe != null) {
            tvName.setText(recipe.getName());
            tvIngredients.setText(recipe.getIngredients());
            tvInstructions.setText(recipe.getInstructions());
            Glide.with(this).load(recipe.getImage()).into(imgFood);
        }
    }
}
package com.example.my2ndapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Marycookbook3 extends AppCompatActivity {
ListView ingrediantlist;
TextView textView,textView2;
ArrayAdapter<String> adapter;
bookdata2 data2;
Button goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marycookbook3);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ingrediantlist = findViewById(R.id.ingredientslist);
        textView = findViewById(R.id.thefoodname);
        goback = findViewById(R.id.goback);
        goback.setOnClickListener(v -> {
            finish();
        });


        data2 = new bookdata2(Marycookbook3.this);
        List<bookvar2> ingredients = data2.showingredients();
        List<String> ingredientname = new ArrayList<>();
        for (bookvar2 item : ingredients) {
            ingredientname.add(item.getIngredient());
        }
        adapter = new ArrayAdapter<>(Marycookbook3.this, android.R.layout.simple_list_item_1, ingredientname);
        ingrediantlist.setAdapter(adapter);


        Intent x = getIntent();
        String recipeName = x.getStringExtra("food_name");
        int foodId = x.getIntExtra("food_id", -1);
        textView.setText(recipeName);


    }
    }

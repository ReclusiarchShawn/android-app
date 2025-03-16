package com.example.my2ndapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Marycookbook extends AppCompatActivity {
    Button breakfast,lunch,dinner;
    TextView coursebtn;
    FloatingActionButton floatbtn;
    bookdata datas;
    CustomAdapter show;
    ListView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marycookbook);
        Intent a = getIntent();
        String b = a.getStringExtra("name");
        ((TextView) findViewById(R.id.topview)).setText(b);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        view = findViewById(R.id.listings);
        breakfast = findViewById(R.id.breakid);
        lunch = findViewById(R.id.lunchid);
        dinner = findViewById(R.id.dinnerid);
        coursebtn = findViewById(R.id.btntext);
        floatbtn = findViewById(R.id.floatbtn);
        datas = new bookdata(Marycookbook.this);
        show = new CustomAdapter(Marycookbook.this, R.layout.cus_layout, datas.viewfood());
        view.setAdapter(show);


        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Marycookbook.this, Marycookbook2.class);
                startActivityForResult(a, 200);
            }
        });



        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = breakfast.getText().toString();
                coursebtn.setText(a);
                show = new CustomAdapter(Marycookbook.this, R.layout.cus_layout, datas.viewfood());
                view.setAdapter(show);

            }
        });
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = lunch.getText().toString();
                coursebtn.setText(a);
                show = new CustomAdapter(Marycookbook.this, R.layout.cus_layout, datas.viewfood());
                view.setAdapter(show);
            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = dinner.getText().toString();
                coursebtn.setText(a);
                show = new CustomAdapter(Marycookbook.this, R.layout.cus_layout, datas.viewfood());
                view.setAdapter(show);
            }
        });
    }
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            Log.d("ActivityResult", "Triggered with requestCode: " + requestCode + ", resultCode: " + resultCode);
            if (requestCode == 200 && resultCode == RESULT_OK) {

                show.clear();
                show.addAll(datas.viewfood());
                show.notifyDataSetChanged();
                Log.d("ActivityResult", "List updated successfully");
            }else {
                Log.d("ActivityResult", "onActivityResult() did not match requestCode 200");
            }
    }

}
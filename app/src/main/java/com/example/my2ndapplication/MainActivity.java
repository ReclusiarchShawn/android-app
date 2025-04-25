package com.example.my2ndapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Mtext(View view) {
        Intent btn=new Intent(this, MainActivity2.class);
        String a=((Button)view).getText().toString();
        btn.putExtra("name",a);
        startActivity(btn);
    }

    public void songsearch(View view){
        Intent a=new Intent(MainActivity.this, LyricsAPI.class);
        String b=((Button)view).getText().toString();
        a.putExtra("name3",b);
        startActivity(a);
    }
    public void cookbook(View view){
        Intent a=new Intent(MainActivity.this, MaryCookBook.class);
        startActivity(a);
    }

    public void notebook(View view) {
        Intent a=new Intent(this,Marynotes.class);
        startActivity(a);
    }
}
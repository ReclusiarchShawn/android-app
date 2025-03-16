package com.example.my2ndapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Marycookbook2 extends AppCompatActivity {
    EditText fname,writeingredients,ingredientname;
    Button submitbtn,recipebtn;
    ImageButton imgbtn;

    Uri selectedImageUri = null;
   // int selectedImageRes = R.drawable.baseline_fastfood_24;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marycookbook2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
recipebtn=findViewById(R.id.recipebtn);
writeingredients=findViewById(R.id.writeingredients);
ingredientname=findViewById(R.id.ingredientname);

        imgbtn = findViewById(R.id.addimgbtn);
        fname = findViewById(R.id.fwrite);
        submitbtn = findViewById(R.id.sunmitbtn);
        imgbtn.setImageResource(R.drawable.baseline_fastfood_24);
        imgbtn.setTag(R.drawable.baseline_fastfood_24);

        imgbtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);
        });
        recipebtn.setOnClickListener(v -> {

            bookvar2 var2=null;
            bookdata2 data=new bookdata2(Marycookbook2.this);
            try{
            var2=new bookvar2(-1,ingredientname.getText().toString(),writeingredients.getText().toString());
            }catch (Exception e){
                e.printStackTrace();
            }if(var2!=null){
                boolean click=data.addrecipe(var2);
                Toast.makeText(Marycookbook2.this, "success" + click, Toast.LENGTH_SHORT).show();
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SubmitButton", "Submit button clicked!");
                bookdata db = new bookdata(Marycookbook2.this);
                bookvar a = null;
                try {
                    String imagePath = selectedImageUri != null ? selectedImageUri.toString() : "";
                    a = new bookvar(-1, fname.getText().toString(), imagePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (a != null) {
                    boolean b = db.addfood(a);
                    Log.d("Database", "AddFood Success: " + b);
                    if (b) {
                        Toast.makeText(Marycookbook2.this, "Recipe Added!", Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finishAffinity();
                    }else {
                        Toast.makeText(Marycookbook2.this, "Error: Could not save recipe", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
                selectedImageUri = data.getData();
                imgbtn.setImageURI(selectedImageUri);

    }
}}
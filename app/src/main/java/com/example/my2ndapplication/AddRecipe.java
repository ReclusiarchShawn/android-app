package com.example.my2ndapplication;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class AddRecipe extends AppCompatActivity {
    private EditText etName, etIngredients, etInstructions;
    private ImageButton imgbtn;
    private Button btnSave;
    private DBhelper db;
    private String currentImagePath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        db = new DBhelper(this);
        etName = findViewById(R.id.etName);
        etIngredients = findViewById(R.id.etIngredients);
        etInstructions = findViewById(R.id.etInstructions);
        imgbtn = findViewById(R.id.imgbtn);
        btnSave = findViewById(R.id.btnSave);

        int id=getIntent().getIntExtra("RECIPE_ID",-1);
        if(id!=-1){
            Recipe a=db.getRecipeById(id);
            if(a!=null){
                etName.setText(a.getName());
                etIngredients.setText(a.getIngredients());
                etInstructions.setText(a.getInstructions());
            }
        }

        imgbtn.setOnClickListener(v -> openImageChooser());

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String ingredients = etIngredients.getText().toString();
            String instructions = etInstructions.getText().toString();
            if(id==-1){
            if (name.isEmpty()) {
                etName.setError("Recipe name required");
                return;
            }

            Recipe recipe = new Recipe(name, currentImagePath, ingredients, instructions);
            db.addRecipe(recipe);
            }else{
                Recipe edit=new Recipe(name,currentImagePath,ingredients,instructions);
                db.upgrade(edit);
            }finish();
        });
    }
        private void openImageChooser() {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 100);

        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==100 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
//            String imagePath = getPathFromUri(imageUri);
//            Log.d("Image Path", imagePath);
            try {
                currentImagePath = getPathFromUri(selectedImageUri);
                Glide.with(this).load(selectedImageUri).into(imgbtn);
            } catch (Exception e) {
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String getPathFromUri(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }
}

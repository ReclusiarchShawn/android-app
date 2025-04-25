package com.example.my2ndapplication;

import static com.example.my2ndapplication.notesDB.ID1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Marynotes2 extends AppCompatActivity {
    private Button submit,delete;
    private EditText et1,et2;
    private notesDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marynotes2);
        et1 = findViewById(R.id.notehead);
        et2 = findViewById(R.id.notetext);
        submit = findViewById(R.id.notesub);
        delete=findViewById(R.id.notedel);
        db = new notesDB(Marynotes2.this);
        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            noteClass b = db.noteid(id);
            if (b != null) {
                et1.setText(b.getTexts());
                et2.setText(b.getTexts2());
           }
        }

        submit.setOnClickListener(v -> {
            if (et1.length() > 0 && et2.length() > 0) {
                if (id == -1) {
                    noteClass a = new noteClass(id, et1.getText().toString(), et2.getText().toString());
                    Toast.makeText(this, "put", Toast.LENGTH_SHORT).show();
                    long insert=db.writenotes(a);
                    if(insert!=-1){
                        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    noteClass b = new noteClass(id, et1.getText().toString(), et2.getText().toString());
                    db.update(b);
                }
                setResult(RESULT_OK);
                finish();
            }
        });
        delete.setOnClickListener(v -> {
            new AlertDialog.Builder(this).setTitle("Delete Note").setMessage("Are you sure you want to delete this note?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        if (db.deleteNote(id)) {
                            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }else{
                            Toast.makeText(this,"cannot delete",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null).show();
        });
    }}
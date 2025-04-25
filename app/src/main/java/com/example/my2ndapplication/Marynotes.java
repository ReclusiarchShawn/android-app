package com.example.my2ndapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Marynotes extends AppCompatActivity {
    private FloatingActionButton fb;
    private RecyclerView rv;
    private notesDB db;
    private ArrayList<noteClass> list;
    private customlv cl;
    private SearchView sv;
    private static final int REQUEST_CODE_ADD_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marynotes);
        sv=findViewById(R.id.sv);
        rv=findViewById(R.id.lv);
        fb=findViewById(R.id.fb);
        db=new notesDB(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        cl = new customlv(list, this);
        rv.setAdapter(cl);

       refresh();
        search();

        fb.setContentDescription("add note");
        fb.setOnClickListener(v -> {
            Intent i=new Intent(this, Marynotes2.class);
            startActivityForResult(i,REQUEST_CODE_ADD_NOTE);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode==RESULT_OK) {
            refresh();
        }
    }
    private void search(){
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cl.filternote(newText);
                return true;
            }
        });
    }



    private void refresh() {
        if (db != null) {
            List<noteClass> fresh = db.showlist();
            if(fresh!=null){
                list.clear();
                list.addAll(fresh);
                cl.notifyDataSetChanged();
            if (cl != null) {
                cl.updateLists(fresh);
            }
            }
        }
    }


    @Override
    protected void onResume(){
        super.onResume();
        refresh();
    }
    @Override
    protected void onDestroy(){
        if (db != null) {
            db.close();
        }
        super.onDestroy();
    }

}
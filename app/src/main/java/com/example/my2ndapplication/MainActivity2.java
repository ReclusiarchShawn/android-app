package com.example.my2ndapplication;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity2 extends AppCompatActivity {
    public EditText a,b;
    TextView t1;
    double num1,num2;
    dbmsdata numbers;
    ArrayAdapter<putdata> show;
    ListView records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        Intent btn=getIntent();
        String a=btn.getStringExtra("name");
        ((TextView)findViewById(R.id.text1)).setText(a);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

records=findViewById(R.id.record);
numbers=new dbmsdata(MainActivity2.this);
show=new ArrayAdapter<putdata>(MainActivity2.this, android.R.layout.simple_list_item_1,numbers.allno());
records.setAdapter(show);
    }
    public boolean nums(){
        a=findViewById(R.id.message);
        b=findViewById(R.id.message2);
        t1=findViewById(R.id.result);
        if (a.getText().toString().isEmpty() || b.getText().toString().isEmpty()) {
            return false;}

        try {
            num1 =  Double.parseDouble(a.getText().toString());
            num2 =  Double.parseDouble(b.getText().toString());
            return true;
        }
        catch (NumberFormatException e){
        return false;}
    }
    public void sum(View view) {
        putdata rec;

        try {
            if (nums()) {
                double sum = num1 + num2;
                t1.setText(String.valueOf(sum));
                rec = new putdata(-1, sum);
                numbers.add(rec);
                Toast.makeText(MainActivity2.this, rec.toString(), Toast.LENGTH_SHORT).show();
                show.clear();
                show.addAll(numbers.allno());
                show.notifyDataSetChanged();
            } else {
                t1.setText("Error Please enter Required Values");
            }

        } catch (Exception e) {
            Toast.makeText(MainActivity2.this, "error", Toast.LENGTH_SHORT).show();
        }

    }
    public void multiply(View view) {
        putdata rec;
        try{if(nums()) {
            double multi = num1 * num2;
            t1.setText(String.valueOf(multi));
            rec = new putdata(-1,multi);
            numbers.add(rec);
            Toast.makeText(MainActivity2.this, "inserted", Toast.LENGTH_SHORT).show();
            show.clear();
            show.addAll(numbers.allno());
            show.notifyDataSetChanged();
        }
        else
        {
            t1.setText("Error Please enter Required Values");
        }
        }catch (Exception e){
            Toast.makeText(MainActivity2.this,"error",Toast.LENGTH_SHORT).show();
        }

    }
    public void minusbtn(View view) {
        putdata rec;

        try {
            if (nums()) {
                double minus = num1 - num2;
                t1.setText(String.valueOf(minus));
                rec = new putdata(-1, minus);
                numbers.add(rec);
                Toast.makeText(MainActivity2.this, rec.toString(), Toast.LENGTH_SHORT).show();
                show.clear();
                show.addAll(numbers.allno());
                show.notifyDataSetChanged();
            } else {
                t1.setText("Error Please enter Required Values");
            }

        } catch (Exception e) {
            Toast.makeText(MainActivity2.this, "error", Toast.LENGTH_SHORT).show();
        }

    }
    public void dividebtn(View view) {
        putdata rec;
        try{if(nums()) {
            double div = num1 / num2;
            t1.setText(String.valueOf(div));
            rec = new putdata(-1,div);
            numbers.add(rec);
            Toast.makeText(MainActivity2.this, "inserted", Toast.LENGTH_SHORT).show();
            show.clear();
            show.addAll(numbers.allno());
            show.notifyDataSetChanged();
        }
        else
        {
            t1.setText("Error Please enter Required Values");
        }
        }catch (Exception e){
            Toast.makeText(MainActivity2.this,"error",Toast.LENGTH_SHORT).show();
        }

    }
public void clearbtn(View view){
        String clear="";
        t1.setText(clear);
}
public void history(View view){

        try{
           numbers.deleteall();
           show.clear();
           show.notifyDataSetChanged();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
}}


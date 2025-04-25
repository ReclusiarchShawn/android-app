package com.example.my2ndapplication;

import androidx.recyclerview.widget.RecyclerView;

public class noteClass {
    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public String getTexts() {
        return texts;
    }

    public void setTexts(String texts) {
        this.texts = texts;
    }
    public String getTexts2(){
        return texts2;
    }
    public void setTexts2(String texts2){
        this.texts2=texts2;
    }

    public noteClass() {

    }

    public noteClass( int id1,String texts,String texts2) {
        this.id1=id1;
        this.texts = texts;
        this.texts2 = texts2;

    }

    private int id1;
    private String texts;
    private String texts2;



}

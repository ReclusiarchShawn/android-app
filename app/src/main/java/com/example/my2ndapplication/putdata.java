package com.example.my2ndapplication;

public class putdata {
   private int id;
   private double no;

public putdata(int id,double no){
    this.id=id;
    this.no=no;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNo() {
        return no;
    }

    public void setNo(double no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return String.valueOf(no);
    }
}

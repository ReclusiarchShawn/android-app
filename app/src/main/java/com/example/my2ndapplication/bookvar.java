package com.example.my2ndapplication;

public class bookvar {
    private int id;
    private String foodname;
    private String img_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public bookvar(int id, String foodname, String img_path) {
        this.id = id;
        this.foodname = foodname;
        this.img_path = img_path;
    }
}

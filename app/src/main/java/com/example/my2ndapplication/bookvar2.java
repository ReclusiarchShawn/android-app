package com.example.my2ndapplication;

public class bookvar2 {
    private int id1;
    private String ingredient;
    private String addrecipe;

    public bookvar2(int id1, String recipe, String addrecipe) {
        this.id1 = id1;
        this.ingredient = recipe;
        this.addrecipe = addrecipe;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getAddrecipe() {
        return addrecipe;
    }

    public void setAddrecipe(String addrecipe) {
        this.addrecipe = addrecipe;
    }

    @Override
    public String toString() {
        return "bookvar2{" +
                "id1=" + id1 +
                ", ingredient='" + ingredient + '\'' +
                ", addrecipe='" + addrecipe + '\'' +
                '}';
    }
}

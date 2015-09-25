package com.example.renan.testepls.entities;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by c1284141 on 25/09/2015.
 */
public class Recipe {

    private String title;
    private int imageRecipe;
    private List<String> ingredients;
    private String prepareMode;
    private String prepareTime;
    private int serves;
    private String recipeType;

    public Recipe(String title, int imageRecipe, String prepareTime, int serves){
        this.title = title;
        this.imageRecipe = imageRecipe;
        this.prepareTime = prepareTime;
        this.serves = serves;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPrepareMode() {
        return prepareMode;
    }

    public void setPrepareMode(String prepareMode) {
        this.prepareMode = prepareMode;
    }

    public String getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(String prepareTime) {
        this.prepareTime = prepareTime;
    }

    public int getServes() {
        return serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }

    public String getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType;
    }

    public int getImageRecipe() {
        return imageRecipe;
    }

    public void setImageRecipe(int imageRecipe) {
        this.imageRecipe = imageRecipe;
    }
}

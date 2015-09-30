package com.example.renan.testepls.entities;

import java.util.List;

/**
 * Created by c1284141 on 25/09/2015.
 */
public class Recipe {

    private long id;
    private String title;
    private int imageRecipe;
    private List<String> ingredients;
    private String prepareMode;
    private String prepareTime;
    private int serves;
    private int recipeType;
    private String observation;

    public Recipe(String title, int imageRecipe, String prepareMode, String prepareTime, int serves, int recipeType, String observation){
        this.title = title;
        this.imageRecipe = imageRecipe;
        this.prepareMode = prepareMode;
        this.prepareTime = prepareTime;
        this.serves = serves;
        this.recipeType = recipeType;
        this.observation = observation;
    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getRecipeType() {
        return recipeType;
    }

    public void setRecipeType(int recipeType) {
        this.recipeType = recipeType;
    }

    public int getImageRecipe() {
        return imageRecipe;
    }

    public void setImageRecipe(int imageRecipe) {
        this.imageRecipe = imageRecipe;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }


}

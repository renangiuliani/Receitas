package com.example.renan.testepls.entities;

/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeType {

    private int imageRecipeType;
    private String nameRecipeType;


    public RecipeType(int imageRecipeType, String nameRecipeType) {
        this.imageRecipeType = imageRecipeType;
        this.nameRecipeType = nameRecipeType;
    }

    public int getImageRecipeType(){
        return imageRecipeType;
    }

    public String getNameRecipeType(){
        return nameRecipeType;
    }
}

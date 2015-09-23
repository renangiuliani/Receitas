package com.example.renan.testepls.entities;

/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeType {

    private int idRecipeType;
    private String nameRecipeType;


    public RecipeType(int idRecipeType, String nameRecipeType) {
        this.idRecipeType = idRecipeType;
        this.nameRecipeType = nameRecipeType;
    }

    public int getIdRecipeType(){
        return idRecipeType;
    }

    public String getNameRecipeType(){
        return nameRecipeType;
    }
}

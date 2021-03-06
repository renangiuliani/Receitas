package com.example.renan.recipeapplication.entities;

import com.example.renan.recipeapplication.persistence.IngredientsRecipeRepository;

import java.util.List;

/**
 * Created by c1284141 on 24/09/2015.
 */
public class Ingredient {

    private Integer id;
    private String nameIngredient;
    private int recipeId;
    private int order;

    public Ingredient() {
        super();
    }

    public String getNameIngredient() {
        return nameIngredient;
    }

    public void setNameIngredient(String nameIngredient) {
        this.nameIngredient = nameIngredient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void save() {
        IngredientsRecipeRepository.getInstance().save(this);
    }

    public static void deleteByRecipe(int recipeId) {
        IngredientsRecipeRepository.getInstance().deleteByRecipe(recipeId);
    }

    public static List<Ingredient> getByRecipe(int recipeId) {
        return IngredientsRecipeRepository.getInstance().getByRecipe(recipeId);
    }
}

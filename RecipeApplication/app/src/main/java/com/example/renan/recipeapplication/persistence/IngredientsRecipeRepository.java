package com.example.renan.recipeapplication.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.renan.recipeapplication.Util.Util;
import com.example.renan.recipeapplication.entities.Recipe;
import com.example.renan.recipeapplication.entities.Ingredient;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Renan on 05/10/2015.
 */
public class IngredientsRecipeRepository {

    private static Integer sSequence = 0;
    private static Map<Integer, Recipe> sRepository = new LinkedHashMap<>();

    private static class Singleton {
        public static final IngredientsRecipeRepository INSTANCE = new IngredientsRecipeRepository();
    }

    private IngredientsRecipeRepository() {
        super();
    }

    public static IngredientsRecipeRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public void save(Ingredient ingredient) {
        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(IngredientsRecipeBD.TABLE, null, IngredientsRecipeBD.getContentValues(ingredient));
        db.close();
        helper.close();
    }

    public void deleteByRecipe(int recipeId){
        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(IngredientsRecipeBD.TABLE, "recipe_id = ?", new String[]{String.valueOf(recipeId)});
        db.close();
        helper.close();
    }

    public List<Ingredient> getByRecipe(int recipeId){
        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();

        String queryString = "recipe_id = " + String.valueOf(recipeId);

        Cursor cursor = db.query(IngredientsRecipeBD.TABLE, IngredientsRecipeBD.COLUNS, queryString, null, null, null, IngredientsRecipeBD.ORDER, null);
        List<Ingredient> ingredients = IngredientsRecipeBD.bindList(cursor);

        db.close();
        helper.close();

        return ingredients;
    }


}

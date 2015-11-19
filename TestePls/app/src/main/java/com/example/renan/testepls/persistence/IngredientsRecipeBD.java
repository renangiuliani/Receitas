package com.example.renan.testepls.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.renan.testepls.entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renan on 30/09/2015.
 */
public class IngredientsRecipeBD {

    public static final String TABLE = "ingredients_recipe";
    public static final String ID = "_id";
    public static final String ORDER = "orderRecipe";
    public static final String RECIPE_ID = "recipe_id";
    public static final String INGREDIENT = "ingredient";

    public static final String[] COLUNS = {ID, ORDER, RECIPE_ID, INGREDIENT};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(ORDER + " INTEGER, ");
        sql.append(RECIPE_ID + " INTEGER, ");
        sql.append(INGREDIENT + " TEXT ");
        sql.append("); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Ingredient ingredient) {
        ContentValues content = new ContentValues();
        content.put(ID, ingredient.getId());
        content.put(ORDER, ingredient.getOrder());
        content.put(RECIPE_ID, ingredient.getRecipeId());
        content.put(INGREDIENT, ingredient.getNameIngredient());
        return content;
    }

    public static Ingredient bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(cursor.getInt(cursor.getColumnIndex(ID)));
            ingredient.setOrder(cursor.getInt(cursor.getColumnIndex(ORDER)));
            ingredient.setNameIngredient(cursor.getString(cursor.getColumnIndex(INGREDIENT)));
            ingredient.setRecipeId(cursor.getInt(cursor.getColumnIndex(RECIPE_ID)));
            return ingredient;
        }
        return null;
    }

    public static List<Ingredient> bindList(Cursor cursor) {
        final List<Ingredient> ingredients = new ArrayList<Ingredient>();
        while (cursor.moveToNext()){
            ingredients.add(bind(cursor));
        }
        return ingredients;
    }

}

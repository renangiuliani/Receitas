package com.example.renan.recipeapplication.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.renan.recipeapplication.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1284141 on 01/10/2015.
 */
public class RecipeDB {

    public static final String TABLE = "recipe";
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String IMAGE = "image";
    public static final String PREPARE_MODE = "prepare_mode";
    public static final String PREPARE_TIME = "prepare_time";
    public static final String SERVES = "serves";
    public static final String RECIPE_TYPE = "recipe_type";
    public static final String OBSERVATION = "observation";
    public static final String FAVORITE = "favorite";
    public static final String PRICE = "price";
    public static final String DIFFICULTY = "difficulty";

    public static final String[] COLUNS = {TABLE +"."+ID, TITLE, IMAGE, PREPARE_MODE, PREPARE_TIME, SERVES, RECIPE_TYPE, OBSERVATION, FAVORITE, PRICE, DIFFICULTY};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(TITLE + " TEXT, ");
        sql.append(IMAGE + " BLOB, ");
        sql.append(PREPARE_MODE + " TEXT, ");
        sql.append(PREPARE_TIME + " TEXT, ");
        sql.append(SERVES + " INTEGER, ");
        sql.append(RECIPE_TYPE + " INTEGER, ");
        sql.append(OBSERVATION + " TEXT, ");
        sql.append(FAVORITE + " INTEGER, ");
        sql.append(PRICE + " REAL, ");
        sql.append(DIFFICULTY + " INTEGER ");
        sql.append("); ");
        return sql.toString();
    }

    public static ContentValues getContentValues(Recipe recipe) {
        ContentValues content = new ContentValues();
        content.put(ID, recipe.getId());
        content.put(TITLE, recipe.getTitle());
        content.put(IMAGE, recipe.getImageRecipe());
        content.put(PREPARE_MODE, recipe.getPrepareMode());
        content.put(PREPARE_TIME, recipe.getPrepareTime());
        content.put(SERVES, recipe.getServes());
        content.put(RECIPE_TYPE, recipe.getRecipeType());
        content.put(OBSERVATION, recipe.getObservation());
        content.put(FAVORITE, recipe.getFavorite());
        content.put(PRICE, recipe.getPrice());
        content.put(DIFFICULTY, recipe.getDifficulty());
        return content;
    }

    public static Recipe bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Recipe recipe = new Recipe();
            recipe.setId((cursor.getInt(cursor.getColumnIndex(ID))));
            recipe.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            recipe.setImageRecipe(cursor.getBlob(cursor.getColumnIndex(IMAGE)));
            recipe.setPrepareMode(cursor.getString(cursor.getColumnIndex(PREPARE_MODE)));
            recipe.setPrepareTime(cursor.getString(cursor.getColumnIndex(PREPARE_TIME)));
            recipe.setServes(cursor.getInt(cursor.getColumnIndex(SERVES)));
            recipe.setRecipeType(cursor.getInt(cursor.getColumnIndex(RECIPE_TYPE)));
            recipe.setObservation(cursor.getString(cursor.getColumnIndex(OBSERVATION)));
            recipe.setFavorite(cursor.getInt(cursor.getColumnIndex(FAVORITE)));
            recipe.setPrice(cursor.getFloat(cursor.getColumnIndex(PRICE)));
            recipe.setDifficulty(cursor.getInt(cursor.getColumnIndex(DIFFICULTY)));
            return recipe;
        }
        return null;
    }

    public static List<Recipe> bindList(Cursor cursor) {
        final List<Recipe> recipes = new ArrayList<Recipe>();
        while (cursor.moveToNext()) {
            recipes.add(bind(cursor));
        }
        return recipes;
    }

    public static Recipe bindRecipe(Cursor cursor){
        return bind(cursor);
    }
}

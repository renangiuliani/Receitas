package com.example.renan.testepls.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.renan.testepls.Util.Util;
import com.example.renan.testepls.entities.Recipe;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by c1284141 on 01/10/2015.
 */
public class RecipeRepository {

    private static Integer sSequence = 0;
    private static Map<Integer, Recipe> sRepository = new LinkedHashMap<>();

    private static class Singleton {
        public static final RecipeRepository INSTANCE = new RecipeRepository();
    }

    private RecipeRepository() {
        super();
    }

    public static RecipeRepository getInstance() {
        return Singleton.INSTANCE;
    }

    public long save(Recipe recipe) {
        long idRecipe;
        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (recipe.getId() == null) {
            idRecipe = db.insert(RecipeDB.TABLE, null, RecipeDB.getContentValues(recipe));
        }else{
            String where = RecipeDB.ID + " = ?";
            String[] args = {recipe.getId().toString()};
            db.update(RecipeDB.TABLE, RecipeDB.getContentValues(recipe), where, args);
            idRecipe = recipe.getId();
        }
        db.close();
        helper.close();

        return idRecipe;
    }

    public void delete(int id){
        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(RecipeDB.TABLE, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        helper.close();
    }

    public List<Recipe> getAll(int limit){

        String limitString = "";

        if(limit != -1) {
            limitString = String.valueOf(limit) + ", 10";
        }

        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(RecipeDB.TABLE, RecipeDB.COLUNS, null, null, null, null, RecipeDB.ID, limitString);
        List<Recipe> recipe = RecipeDB.bindList(cursor);
        db.close();
        helper.close();
        return recipe;
    }

    public List<Recipe> getByType(int limit, HashMap<String,String> query){

        String limitString = "";

        if(limit != -1) {
            limitString = String.valueOf(limit) + ", 10";
        }

        String queryString = "1 = 1";

        queryString += (query.get("codeType") != null) ? " AND recipe_type = '" + query.get("codeType") + "'" : "";

        queryString += (query.get("title") != null) ? " AND UPPER(title) like '%" + query.get("title") + "%'" : "";

        queryString += (query.get("favorite") != null) ? " AND favorite = '" + query.get("favorite") + "'" : "";

        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(RecipeDB.TABLE, RecipeDB.COLUNS, queryString, null, null, null, RecipeDB.ID, limitString);
        List<Recipe> recipe = RecipeDB.bindList(cursor);
        db.close();
        helper.close();
        return recipe;
    }

    public Recipe getById(int idRecipe){

        String queryString = "_id = '" + idRecipe + "'";

        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.query(RecipeDB.TABLE, RecipeDB.COLUNS, queryString, null, null, null, null, null);
        Recipe recipe = (Recipe) RecipeDB.bindRecipe(cursor);
        db.close();
        helper.close();

        return recipe;
    }

}

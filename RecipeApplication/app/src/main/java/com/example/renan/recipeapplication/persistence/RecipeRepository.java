package com.example.renan.recipeapplication.persistence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.renan.recipeapplication.Util.Util;
import com.example.renan.recipeapplication.entities.Recipe;

import java.util.ArrayList;
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
        } else {
            String where = RecipeDB.ID + " = ?";
            String[] args = {recipe.getId().toString()};
            db.update(RecipeDB.TABLE, RecipeDB.getContentValues(recipe), where, args);
            idRecipe = recipe.getId();
        }
        db.close();
        helper.close();

        return idRecipe;
    }

    public void delete(int id) {
        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(RecipeDB.TABLE, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        helper.close();
    }

    public List<Recipe> getAll(int limit) {

        String limitString = "";

        if (limit != -1) {
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

    public List<Recipe> getByAll(int limit, HashMap<String, String> query) {

        String limitString = "";
        String where = "1 = 1";
        List<String> args = new ArrayList<String>();
        Cursor cursor;
        DatabaseHelper helper = new DatabaseHelper(Util.CONTEXT);
        SQLiteDatabase db = helper.getReadableDatabase();

        if (limit != -1) {
            limitString = String.valueOf(limit) + ", 10";
        }

        if (query.get("codeType") != null) {
            where += " AND (recipe_type = ?)";
            args.add(query.get("codeType"));
        }

        if (query.get("title") != null) {
            where += " AND (UPPER(title) like ?)";
            args.add("%" + query.get("title") + "%");
        }

        if (query.get("favorite") != null) {
            where += " AND (favorite = ?)";
            args.add(query.get("favorite"));
        }

        if (query.get("difficulty") != null) {
            where += " AND (difficulty = ?)";
            args.add(query.get("difficulty"));
        }

        if (query.get("prepareTime") != null) {
            where += " AND (prepare_time = ?)";
            args.add(query.get("prepareTime"));
        }

        if (query.get("serves") != null) {
            where += " AND (serves = ?)";
            args.add(query.get("serves"));
        }

        if (query.get("price") != null) {
            where += " AND (price = ?)";
            args.add(query.get("price"));
        }


        if (query.get("ingredients") != null) {
            String[] ingredients = query.get("ingredients").split(",");
            for(String st : ingredients){
                where += " AND (ingredient like ?)";
                args.add("%" + st + "%");
            }

            SQLiteQueryBuilder _QB = new SQLiteQueryBuilder();

            _QB.setTables(RecipeDB.TABLE +
                    " LEFT OUTER JOIN " + IngredientsRecipeBD.TABLE + " ON " +
                    RecipeDB.TABLE + "." + RecipeDB.ID + " = " + IngredientsRecipeBD.TABLE + "." + IngredientsRecipeBD.RECIPE_ID);


            cursor = _QB.query(db, RecipeDB.COLUNS, where, args.toArray(new String[]{}), null, null, null);
        }else{
            cursor = db.query(RecipeDB.TABLE, RecipeDB.COLUNS, where, args.toArray(new String[]{}), null, null, RecipeDB.ID, limitString);
        }

        List<Recipe> recipe = RecipeDB.bindList(cursor);
        db.close();
        helper.close();
        return recipe;
    }

    public Recipe getById(int idRecipe) {

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

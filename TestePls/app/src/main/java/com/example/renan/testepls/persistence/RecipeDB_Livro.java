package com.example.renan.testepls.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.renan.testepls.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1284141 on 30/09/2015.
 */
public class RecipeDB_Livro extends SQLiteOpenHelper{

    private static final String TAG = "sql";
    public static final String NOME_BANCO = "recipe_sqlite";
    private static final int VERSAO_BANCO = 1;


    public RecipeDB_Livro(Context context){
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a Tabela recipe");
        db.execSQL("create table if not exists recipe (" +
                "_id integer primary key autoincrement, " +
                "title text, " +
                "recipe_type integer, " +
                "prepare_time text, " +
                "serves integer, " +
                "prepare_mode text," +
                "observation text, " +
                "image integer);");
        Log.d(TAG, "Tabela recipe criada com sucesso");

        Log.d(TAG, "Criando a Tabela ingredient_recipe");
        db.execSQL("create table if not exists ingredient_recipe (" +
                "_id integer primary key autoincrement, " +
                "id_recipe integer, " +
                "ingredient text;");
        Log.d(TAG, "Tabela ingredient_recipe criada com sucesso");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Caso mude a versão do banco de dados, escrever aqui
    }

    //Inserir/Atualizar receita
    public long save(Recipe recipe){
        long id = recipe.getId();

        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("title", recipe.getTitle());
            values.put("recipe_type", recipe.getRecipeType());
            values.put("prepare_time", recipe.getPrepareTime());
            values.put("serves", recipe.getServes());
            values.put("prepare_mode", recipe.getPrepareMode());
            values.put("observation", recipe.getObservation());
            values.put("image", recipe.getImageRecipe());
            if(id != 0){
                String _id = String.valueOf(recipe.getId());
                String[] whereArgs = new String[]{_id};
                int count = db.update("recipe", values, "_id=?", whereArgs);
                return count;
            }else {
                id = db.insert("recipe", "", values);
                return id;
            }
        }finally {
            db.close();
        }
    }

    //Deletar receita
    public int delete(Recipe recipe){
        SQLiteDatabase db = getWritableDatabase();

        try {
            int count = db.delete("recipe", "_id=?", new String[]{String.valueOf(recipe.getId())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;
        }finally {
            db.close();
        }
    }

    //Consultar lista das receitas
    public List<Recipe> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            Cursor c = db.query("recipe", null, null, null, null, null, null, null);
            return toList(c);
        }finally {
            db.close();
        }
    }

    //Consultar lista de receita por tipo
    public List<Recipe> findAllByType(int recipeType){
        SQLiteDatabase db = getWritableDatabase();
        try{
            Cursor c = db.query("recipe", null, "recipe_type= '" + recipeType + "'", null, null, null, null, null);
            return toList(c);
        }finally {
            db.close();
        }
    }

    //Lê o cursor e cria a lista de receitas
    private List<Recipe> toList(Cursor c) {
        List<Recipe> recipes = new ArrayList<Recipe>();
        if(c.moveToFirst()){
            do{
                Recipe recipe = new Recipe(
                        /*c.getString(c.getColumnIndex("title")),
                        c.getInt(c.getColumnIndex("image")),
                        c.getString(c.getColumnIndex("prepare_mode")),
                        c.getString(c.getColumnIndex("prepare_time")),
                        c.getInt(c.getColumnIndex("serves")),
                        c.getInt(c.getColumnIndex("recype_type")),
                        c.getString(c.getColumnIndex("observation"))*/
                        );
                recipes.add(recipe);
            }while (c.moveToNext());
        }
        return recipes;
    }

    //Executa SQL
    public  void execSQL(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql);
        }finally {
            db.close();
        }
    }

    //Executa SQL
    public  void execSQL(String sql, Object[] args){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql, args);
        }finally {
            db.close();
        }
    }


}

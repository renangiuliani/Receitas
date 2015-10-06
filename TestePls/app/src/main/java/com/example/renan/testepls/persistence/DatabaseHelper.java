package com.example.renan.testepls.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by c1284141 on 01/10/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "RECIPE_DB";
    private static int VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DatabaseHelper.BANCO_DADOS, null, DatabaseHelper.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecipeDB.createTable());
        db.execSQL(IngredientsRecipeBD.createTable());
        /*db.execSQL("INSERT INTO recipe (_id, title, image, prepare_mode, prepare_time, serves, recipe_type, observation) " +
                "VALUES (1, 'Teste Receita 1', "+ R.drawable.soup +", 'Modo Preparo 1', '01:00', 1, 1, 'Observação 1')");

        db.execSQL("INSERT INTO recipe (_id, title, image, prepare_mode, prepare_time, serves, recipe_type, observation) " +
                "VALUES (2, 'Teste Receita 2', "+ R.drawable.sandwich +", 'Modo Preparo 2', '02:00', 2, 2, 'Observação 2')");

        db.execSQL("INSERT INTO recipe (_id, title, image, prepare_mode, prepare_time, serves, recipe_type, observation) " +
                "VALUES (3, 'Teste Receita 3', "+ R.drawable.bird +", 'Modo Preparo 3', '03:00', 3, 3, 'Observação 3')");
*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}

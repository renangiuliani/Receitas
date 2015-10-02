package com.example.renan.testepls.persistence;

/**
 * Created by Renan on 30/09/2015.
 */
public class IngredientsRecipeBD {

    public static final String TABLE = "ingredients_recipe";
    public static final String ID = "_id";
    public static final String ID_RECIPE = "id_recipe";
    public static final String INGREDIENT = "ingredient";

    public static final String[] COLUNS = {ID, ID_RECIPE, INGREDIENT};

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE ");
        sql.append(TABLE);
        sql.append(" ( ");
        sql.append(ID + " INTEGER PRIMARY KEY, ");
        sql.append(ID_RECIPE + " INTEGER, ");
        sql.append(INGREDIENT + " TEXT, ");
        sql.append(" ); ");
        return sql.toString();
    }

}

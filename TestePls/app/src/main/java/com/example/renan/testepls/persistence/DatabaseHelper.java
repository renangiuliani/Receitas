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


//        //Recipe

//        for(int x = 1; x < 22; x++) {
//
//
//            db.execSQL("INSERT INTO recipe (_id, title, prepare_mode, prepare_time, serves, recipe_type, observation, favorite, price, difficulty) " +
//                    "VALUES ("+x+", 'Teste Receita "+x+"', 'Primeiro eu queria cumprimentar os internautas. -Oi Internautas! Depois dizer que o meio ambiente é sem dúvida nenhuma uma ameaça ao desenvolvimento sustentável. E isso significa que é uma ameaça pro futuro do nosso planeta e dos nossos países. O desemprego beira 20%, ou seja, 1 em cada 4 portugueses.\n" +
//                    "\n" +
//                    "No meu xinélo da humildade eu gostaria muito de ver o Neymar e o Ganso. Por que eu acho que.... 11 entre 10 brasileiros gostariam. Você veja, eu já vi, parei de ver. Voltei a ver, e acho que o Neymar e o Ganso têm essa capacidade de fazer a gente olhar.', " +
//                    "'01:00', 1, "+((x % 10) <= 0 ? x : x % 10)+", " +
//                    "'Silvio Santos Ipsum É fácil ou não éam? Ma quem quer dinheiroam? Eu não queria perguntar isso publicamente, ma vou perguntar. Carla, você tem o ensino fundamentauam? Ha hai. Bem boladoam, bem boladoam. Bem gozadoam. Ma! Ao adquirir o carnê do Baú, você estará concorrendo a um prêmio de cem mil reaisam. Ma não existem mulher feiam, existem mulher que não conhece os produtos Jequitiamm. É dinheiro ou não éam? É por sua conta e riscoamm? Ma vejam só, vejam só. É bom ou não éam?\n" +
//                    "\n" +
//                    " Mah você não consegue né Moisés? Você não consegueam. Ma você, topa ou não topamm. Mah é a porta da esperançaam. Ma você está certo dissoam? Eu não queria perguntar isso publicamente, ma vou perguntar. Carla, você tem o ensino fundamentauam? Mah roda a roduamm. Qual é a musicamm? Ma não existem mulher feiam, existem mulher que não conhece os produtos Jequitiamm. O arriscam tuduam, valendo um milhão de reaisuam. O Raul Gil é gayam! ... Maa O Ah Ae! Ih Ih! O Raul Gil é gayamm! Mah é a porta da esperançaam.'," +
//                    +((x % 2) == 0 ? 1 : 0)+", '5.0', '3.0')");
//
//        }
//
//        Drawable drawable2 = R.drawable.test2;
//
//        Bitmap bitmap2 = ((BitmapDrawable) drawable1).getBitmap();
//        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] bitmapdata2 = stream.toByteArray();
//
//        db.execSQL("INSERT INTO recipe (_id, title, image, prepare_mode, prepare_time, serves, recipe_type, observation, favorite) " +
//                "VALUES (2, 'Teste Receita 2', " + bitmapdata2 + ", 'Modo Preparo 2', '02:00', 2, 2, 'Observação 2', 1)");
//
//        Drawable drawable3 = R.drawable.test3;
//
//        Bitmap bitmap3 = ((BitmapDrawable) drawable3).getBitmap();
//        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] bitmapdata3 = stream.toByteArray();
//
//        db.execSQL("INSERT INTO recipe (_id, title, image, prepare_mode, prepare_time, serves, recipe_type, observation, favorite, difficulty, price) " +
//                "VALUES (3, 'Teste Receita 3', "+ bitmapdata3 +", 'Modo Preparo 3', '03:00', 3, 3, 'Observação 3', 1, '3.0', '11.22')");
//
//        //Ingredients
//        db.execSQL("INSERT INTO ingredients_recipe (_id, recipe_id, ingredient) " +
//                "VALUES (1, 1, '1 Pão meio assado')");
//
//        db.execSQL("INSERT INTO ingredients_recipe (_id, recipe_id, ingredient) " +
//                "VALUES (2, 1, '2 ovos com gema DURA!')");
//
//        db.execSQL("INSERT INTO ingredients_recipe (_id, recipe_id, ingredient) " +
//                "VALUES (3, 1, '3 litros de água podre de tamarindo')");
//
//        db.execSQL("INSERT INTO ingredients_recipe (_id, recipe_id, ingredient) " +
//                "VALUES (4, 1, '4 pedaços de chocolate inteiro amargo')");
//
//        db.execSQL("INSERT INTO ingredients_recipe (_id, recipe_id, ingredient) " +
//                "VALUES (5, 2, 'Ingrediente 3')");
//
//        db.execSQL("INSERT INTO ingredients_recipe (_id, recipe_id, ingredient) " +
//                "VALUES (6, 2, 'Ingrediente 4')");
//
//        db.execSQL("INSERT INTO ingredients_recipe (_id, recipe_id, ingredient) " +
//                "VALUES (7, 3, 'Ingrediente 5')");
//
//        db.execSQL("INSERT INTO ingredients_recipe (_id, recipe_id, ingredient) " +
//                "VALUES (8, 3, 'Ingrediente 6')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}

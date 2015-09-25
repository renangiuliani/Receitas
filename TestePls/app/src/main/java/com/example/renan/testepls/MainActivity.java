package com.example.renan.testepls;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.renan.testepls.adapter.RecipeTypeAdapter;
import com.example.renan.testepls.entities.RecipeType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<RecipeType> recipeTypes;
    private RecipeTypeAdapter recipeTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createAndPopulateRecipeTypeArray();

        bindElements();
    }

    private void bindElements() {
        recipeTypeAdapter = new RecipeTypeAdapter(this, recipeTypes);
        recyclerView = (RecyclerView) findViewById(R.id.rv_recipe_type);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeTypeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createAndPopulateRecipeTypeArray(){
        recipeTypes = new ArrayList<RecipeType>();
        recipeTypes.add(new RecipeType(R.drawable.meat, "Carnes"));
        recipeTypes.add(new RecipeType(R.drawable.bird, "Aves"));
        recipeTypes.add(new RecipeType(R.drawable.fish, "Peixes e Frutos do Mar"));
        recipeTypes.add(new RecipeType(R.drawable.salad, "Saladas"));
        recipeTypes.add(new RecipeType(R.drawable.sauce, "Molhos e Acompanhamentos"));
        recipeTypes.add(new RecipeType(R.drawable.soup, "Sopas"));
        recipeTypes.add(new RecipeType(R.drawable.pasta, "Massas"));
        recipeTypes.add(new RecipeType(R.drawable.drink, "Bebidas"));
        recipeTypes.add(new RecipeType(R.drawable.candy, "Doces e Sobremesas"));
        recipeTypes.add(new RecipeType(R.drawable.sandwich, "Lanches"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

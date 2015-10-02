package com.example.renan.testepls.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.renan.testepls.R;
import com.example.renan.testepls.adapter.RecipeTypeAdapter;
import com.example.renan.testepls.entities.EnumRecipeType;
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
        recipeTypes.add(new RecipeType(R.drawable.meat, EnumRecipeType.MEAT));
        recipeTypes.add(new RecipeType(R.drawable.bird, EnumRecipeType.BIRD));
        recipeTypes.add(new RecipeType(R.drawable.fish, EnumRecipeType.FISH));
        recipeTypes.add(new RecipeType(R.drawable.salad, EnumRecipeType.SALAD));
        recipeTypes.add(new RecipeType(R.drawable.sauce, EnumRecipeType.SAUCE));
        recipeTypes.add(new RecipeType(R.drawable.soup, EnumRecipeType.SOUP));
        recipeTypes.add(new RecipeType(R.drawable.pasta, EnumRecipeType.PASTA));
        recipeTypes.add(new RecipeType(R.drawable.drink, EnumRecipeType.DRINK));
        recipeTypes.add(new RecipeType(R.drawable.candy, EnumRecipeType.CANDY));
        recipeTypes.add(new RecipeType(R.drawable.sandwich, EnumRecipeType.SANDWICH));
    }
}

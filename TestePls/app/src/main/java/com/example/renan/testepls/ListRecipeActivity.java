package com.example.renan.testepls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.testepls.adapter.IngredientAdapter;
import com.example.renan.testepls.adapter.ListRecipeAdapter;
import com.example.renan.testepls.entities.Recipe;
import com.example.renan.testepls.entities.RecipeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renan on 21/09/2015.
 */
public class ListRecipeActivity extends AppCompatActivity {

    private String recipeType;
    private FloatingActionButton fbAddRecipe;
    private TextView tvTbName;
    private ListRecipeAdapter listRecipeAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Recipe> recipes;
    private  ImageView menuItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_recipe);
        setSupportActionBar(toolbar);
        recipeType = getIntent().getStringExtra("recipeType");

        createAndPopulateRecipeTypeArray();

        bindElements();

        if(recipeType != null){
            tvTbName.setText(recipeType);
        }

    }

    private void createAndPopulateRecipeTypeArray(){
        recipes = new ArrayList<Recipe>();
        recipes.add(new Recipe("Receita da vovó",R.drawable.meat, "1:30", 7));
        recipes.add(new Recipe("Mingal de milho assado e frito",R.drawable.soup, "2:00", 3));
        recipes.add(new Recipe("Bolacha Recheada com leite em pó sólido",R.drawable.candy, "0:30", 1));
        recipes.add(new Recipe("Peito de Frango ossado",R.drawable.bird, "1:00", 7));
        recipes.add(new Recipe("Molho de alho com dente de leão",R.drawable.sauce, "10:00", 35));

    }

    private void bindElements() {
        fbAddRecipe = (FloatingActionButton) findViewById(R.id.fb_add_recipe);
        tvTbName = (TextView) findViewById(R.id.tv_tb_name);

        listRecipeAdapter = new ListRecipeAdapter(this, recipes);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_ingredient);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(listRecipeAdapter);

        bindEvents();
    }

    private void bindEvents() {
        fbAddRecipe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListRecipeActivity.this, RecipeActivity.class);
                intent.putExtra("recipeType", recipeType);
                startActivity(intent);
            }
        });

    }
}

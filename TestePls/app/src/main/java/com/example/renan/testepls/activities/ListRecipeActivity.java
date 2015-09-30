package com.example.renan.testepls.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.testepls.R;
import com.example.renan.testepls.adapter.ListRecipeAdapter;
import com.example.renan.testepls.entities.Recipe;

import java.util.ArrayList;

/**
 * Created by Renan on 21/09/2015.
 */
public class ListRecipeActivity extends AppCompatActivity {

    private String recipeType;
    private FloatingActionButton fbAddRecipe;
    private TextView tvName;
    private EditText etSearch;
    private ListRecipeAdapter listRecipeAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Recipe> recipes;
    private ImageView ivSearch, ivBack, ivImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipeType = getIntent().getStringExtra("recipeType");

        createAndPopulateRecipeTypeArray();

        bindElements();

        if (recipeType != null) {
            setTitle(recipeType);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearhFilter());

        return true;
    }

    private class SearhFilter implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(ListRecipeActivity.this, "Filtrar: " + query, Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            ArrayList<Recipe> listAux = new ArrayList<Recipe>();
            for (Recipe r : recipes) {
                if (r.getTitle().toUpperCase().contains(newText.toUpperCase())) {
                    listAux.add(r);
                }
            }
            listRecipeAdapter.setList(listAux);
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createAndPopulateRecipeTypeArray() {
        recipes = new ArrayList<Recipe>();
        recipes.add(new Recipe("Receita da vovó", R.drawable.meat, "Modo Preparo 1", "01:30", 7, 1, "Observação 1"));
        recipes.add(new Recipe("Mingal de milho assado e frito", R.drawable.soup, "Modo Preparo 2", "02:00", 3, 2, "Observação 2"));
        recipes.add(new Recipe("Bolacha Recheada com leite em pó sólido", R.drawable.candy, "Modo Preparo 3", "0:30", 1, 3, "Observação 3"));
        recipes.add(new Recipe("Peito de Frango ossado", R.drawable.bird, "Modo Preparo 4", "1:00", 7, 4, "Observação 4"));
        recipes.add(new Recipe("Molho de alho com dente de leão", R.drawable.sauce, "Modo Preparo 5", "10:00", 35, 5, "Observação 5"));
        recipes.add(new Recipe("Água com açúcar calmante", R.drawable.drink, "Modo Preparo 6", "00:05", 1, 6, "Observação 6"));
        recipes.add(new Recipe("Água com açúcar calmante", R.drawable.meat, "Modo Preparo 7", "00:05", 1, 7, "Observação 7"));
        recipes.add(new Recipe("Água com açúcar calmante", R.drawable.sandwich, "Modo Preparo 8", "00:05", 1, 8, "Observação 8"));
    }

    private void bindElements() {
        fbAddRecipe = (FloatingActionButton) findViewById(R.id.fb_save_recipe);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivImage = (ImageView) findViewById(R.id.iv_image);
        tvName = (TextView) findViewById(R.id.tv_name);
        etSearch = (EditText) findViewById(R.id.et_search);
        ivSearch = (ImageView) findViewById(R.id.iv_search);

        listRecipeAdapter = new ListRecipeAdapter(this, recipes);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_ingredient);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(listRecipeAdapter);

        bindEvents();
    }

    private void bindEvents() {
        fbAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListRecipeActivity.this, RecipeActivity.class);
                intent.putExtra("recipeType", recipeType);
                startActivity(intent);
            }
        });

    }
}

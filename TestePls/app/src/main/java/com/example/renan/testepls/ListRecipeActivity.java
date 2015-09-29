package com.example.renan.testepls;

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

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_recipe);
        //setSupportActionBar(toolbar);
        recipeType = getIntent().getStringExtra("recipeType");

        createAndPopulateRecipeTypeArray();

        bindElements();

        if (recipeType != null) {
            //tvName.setText(recipeType);
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
            int cont = 0;
            for (Recipe r : recipes) {
                if (r.getTitle().contains(newText)) {
                    listAux.add(r);
                    cont++;
                }
            }
            listRecipeAdapter = new ListRecipeAdapter(ListRecipeActivity.this, listAux);
            recyclerView.setAdapter(listRecipeAdapter);
            listRecipeAdapter.notifyDataSetChanged();
            //Toast.makeText(ListRecipeActivity.this, "Buscar: " + newText + ". Total: " + String.valueOf(cont), Toast.LENGTH_SHORT).show();
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
        recipes.add(new Recipe("Receita da vovó", R.drawable.meat, "1:30", 7));
        recipes.add(new Recipe("Mingal de milho assado e frito", R.drawable.soup, "2:00", 3));
        recipes.add(new Recipe("Bolacha Recheada com leite em pó sólido", R.drawable.candy, "0:30", 1));
        recipes.add(new Recipe("Peito de Frango ossado", R.drawable.bird, "1:00", 7));
        recipes.add(new Recipe("Molho de alho com dente de leão", R.drawable.sauce, "10:00", 35));
        recipes.add(new Recipe("Água com açúcar calmante", R.drawable.drink, "00:05", 1));
        recipes.add(new Recipe("Água com açúcar calmante", R.drawable.meat, "00:05", 1));
        recipes.add(new Recipe("Água com açúcar calmante", R.drawable.sandwich, "00:05", 1));

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

// Ações Botões ToolBar
// ivSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ivImage.setVisibility(View.INVISIBLE);
//                tvName.setVisibility(View.INVISIBLE);
//                etSearch.setVisibility(View.VISIBLE);
//                ivSearch.setVisibility(View.INVISIBLE);
//                hideShowKeyboard(ListRecipeActivity.this, v);
//                etSearch.requestFocus();
//                etSearch.setText("");
//            }
//        });
//
//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tvName.getVisibility() == View.INVISIBLE) {
//                    ivImage.setVisibility(View.VISIBLE);
//                    tvName.setVisibility(View.VISIBLE);
//                    etSearch.setVisibility(View.INVISIBLE);
//                    ivSearch.setVisibility(View.VISIBLE);
//                } else {
//                    Toast.makeText(ListRecipeActivity.this, "Voltar", Toast.LENGTH_SHORT).show();
//                    onBackPressed();
//                }
//            }
//        });
//
//        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                        ivImage.setVisibility(View.VISIBLE);
//                        tvName.setVisibility(View.VISIBLE);
//                        etSearch.setVisibility(View.INVISIBLE);
//                        ivSearch.setVisibility(View.VISIBLE);
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//        etSearch.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//                if (etSearch.getText().toString().trim().equals("")) {
//                    Toast.makeText(ListRecipeActivity.this, "Filtrar Tudo!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ListRecipeActivity.this, "Filtrar: " + etSearch.getText(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        /*This method is called to notify you that, within s, the count characters beginning at start are about to be replaced by new text with length after. It is an error to attempt to make changes to s from this callback.*/
//            }
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//        });
//
//        etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (false == hasFocus) {
//                    hideShowKeyboard(ListRecipeActivity.this, v);
//                }
//            }
//        });


    }
}

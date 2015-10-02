package com.example.renan.testepls.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
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
import com.example.renan.testepls.entities.RecipeType;

import java.util.List;

/**
 * Created by Renan on 21/09/2015.
 */
public class ListRecipeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private FloatingActionButton fbAddRecipe;
    private TextView tvName;
    private EditText etSearch;
    private static ListRecipeAdapter listRecipeAdapter;
    private RecyclerView recyclerView;
    private static List<Recipe> recipes;
    private ImageView ivSearch, ivBack, ivImage;
    private RecipeType recipeType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Bundle extras = getIntent().getExtras();

        //recipeType = getIntent().getStringExtra("recipeType");

        bindElements();

        if(extras != null){
            recipeType = extras.getParcelable("recipeType");
            setTitle(recipeType.getEnumRecipeType().getName());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateItens();
        listRecipeAdapter.notifyDataSetChanged();
    }

    public static void updateItens(){
        Recipe recipe = new Recipe();
        recipes  =  recipe.getAll();
        listRecipeAdapter.setList(recipes);
        listRecipeAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearhFilter());

        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        final Recipe selectedItem = listRecipeAdapter.getSelectedItem();
        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(ListRecipeActivity.this, "Editar Receita", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListRecipeActivity.this, RecipeActivity.class);
                intent.putExtra("edit", selectedItem);
                intent.putExtra("recipeType", recipeType);
                ListRecipeActivity.this.startActivity(intent);
                return false;
            case R.id.action_remove:
                selectedItem.delete(selectedItem.getId());
                ListRecipeActivity.updateItens();
                Toast.makeText(ListRecipeActivity.this, "Receita removida com sucesso!", Toast.LENGTH_SHORT).show();
                return false;
            case R.id.action_change_photo:
                Toast.makeText(ListRecipeActivity.this, "Trocar Foto", Toast.LENGTH_SHORT).show();
                return false;
        }
        return false;
    }

    private class SearhFilter implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(ListRecipeActivity.this, "Filtrar: " + query, Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            /*ArrayList<Recipe> listAux = new ArrayList<Recipe>();
            for (Recipe r : recipes) {
                if (r.getTitle().toUpperCase().contains(newText.toUpperCase())) {
                    listAux.add(r);
                }
            }
            listRecipeAdapter.setList(listAux);*/
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

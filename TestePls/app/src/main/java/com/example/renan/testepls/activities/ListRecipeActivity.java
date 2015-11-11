package com.example.renan.testepls.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.renan.testepls.R;
import com.example.renan.testepls.Util.DrawerMenuUtil;
import com.example.renan.testepls.adapter.ListRecipeAdapter;
import com.example.renan.testepls.adapter.RecipeTypeAdapter;
import com.example.renan.testepls.entities.EnumRecipeType;
import com.example.renan.testepls.entities.Recipe;
import com.example.renan.testepls.entities.RecipeType;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Renan on 21/09/2015.
 */
public class ListRecipeActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private FloatingActionButton fbAddRecipe;
    private static ListRecipeAdapter listRecipeAdapter;
    private static RecyclerView rvListRecipe;
    private static List<Recipe> recipes;
    public static int filterRecipeType;
    private static Recipe recipe;
    private static List<Recipe> listAdd;
    private static String textSearch;
    private static TextView tvNoResult;
    ArrayList<RecipeType> recipeTypes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bindElements();

        filterRecipeType = 0;

        recipe = new Recipe();

        recipes = recipe.getAll();
        listRecipeAdapter.setList(recipes);

        Drawer result = new DrawerMenuUtil(this, toolbar).create().build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recipe = new Recipe();
        recipes = recipe.getAll();
        listRecipeAdapter.setList(recipes);
        listRecipeAdapter.notifyDataSetChanged();
    }

    public static void updateItens(int limit) {
        HashMap<String, String> hashMapCodeType = new HashMap<String, String>();

        if(filterRecipeType == 12){
            hashMapCodeType.put("favorite", "1");
        }else if(filterRecipeType > 0 && filterRecipeType < 11){
            hashMapCodeType.put("codeType", String.valueOf(filterRecipeType));
        }

        if(!("").equals(textSearch)){
            hashMapCodeType.put("title", textSearch);
        }

        listAdd = recipe.getByType(limit, hashMapCodeType);

        if(limit == 0){
            recipes = listAdd;
            listRecipeAdapter.setList(recipes);
        }else {
            recipes.addAll(listAdd);
        }

        if(recipes.size() == 0){
            rvListRecipe.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
        }else{
            tvNoResult.setVisibility(View.GONE);
            rvListRecipe.setVisibility(View.VISIBLE);
        }

    }

//    public static void deleteItem(int position) {
//        recipes.remove(position);
//        listRecipeAdapter.notifyDataSetChanged();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchFilter());

        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
//        final Recipe selectedItem = listRecipeAdapter.getSelectedItem();
//        switch (item.getItemId()) {
//            case R.id.action_edit:
//                Intent intent = new Intent(ListRecipeActivity.this, RecipeActivity.class);
//                intent.putExtra("edit", selectedItem);
//                intent.putExtra("recipeType", recipeType);
//                ListRecipeActivity.this.startActivity(intent);
//                return false;
//            case R.id.action_remove:
//                new AlertDialog.Builder(ListRecipeActivity.this)
//                        .setTitle(R.string.recipe_remove)
//                        .setMessage(R.string.question_recipe_remove)
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                selectedItem.delete(selectedItem.getId());
//                                ListRecipeActivity.deleteItem(listRecipeAdapter.mPosition);
//
//                                Toast.makeText(ListRecipeActivity.this, R.string.remove_successful, Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                // do nothing
//                            }
//                        })
//                        .setIcon(R.drawable.ic_alert_warning)
//                        .show();
//
//                return false;
//            case R.id.action_change_photo:
//                Toast.makeText(ListRecipeActivity.this, "Trocar Foto", Toast.LENGTH_SHORT).show();
//                return false;
//        }
        return false;
    }

    private class SearchFilter implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.toString().equals("")){
                textSearch = "";
            }else{
                textSearch = newText.toUpperCase();
            }

            updateItens(0);

//            HashMap<String, String> hashMapTitle = new HashMap<String, String>();
//            hashMapTitle.put("codeType", String.valueOf(recipeType.getEnumRecipeType().getCode()));
//            hashMapTitle.put("title", newText.toUpperCase());
//            recipes = recipe.getByType(0, hashMapTitle);
//            listRecipeAdapter.setList(recipes);

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

        listRecipeAdapter = new ListRecipeAdapter(this, recipes);
        rvListRecipe = (RecyclerView) findViewById(R.id.rv_list_ingredient);
        rvListRecipe.setHasFixedSize(true);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        rvListRecipe.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == listRecipeAdapter.getItemCount() - 1) {
                    updateItens(listRecipeAdapter.getItemCount() - 1);
                }

            }
        });

        tvNoResult = (TextView) findViewById(R.id.tv_no_result);

        rvListRecipe.setLayoutManager(gridLayoutManager);
        rvListRecipe.setAdapter(listRecipeAdapter);

        bindEvents();
    }

    private void bindEvents() {
        fbAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAndPopulateRecipeTypeArray();

                final Dialog dialog = new Dialog(ListRecipeActivity.this);

                View view = getLayoutInflater().inflate(R.layout.dialog_recipe_type, null);

                RecyclerView rvRecipeType = (RecyclerView) view.findViewById(R.id.rv_recipe_type);
                rvRecipeType.setHasFixedSize(true);
                final GridLayoutManager gridLayoutManager = new GridLayoutManager(ListRecipeActivity.this, 2);

                rvRecipeType.setLayoutManager(gridLayoutManager);

                RecipeTypeAdapter recipeTypeAdapter = new RecipeTypeAdapter(ListRecipeActivity.this, recipeTypes, dialog);

                rvRecipeType.setAdapter(recipeTypeAdapter);

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(view);

                DisplayMetrics metrics = getResources().getDisplayMetrics();

                dialog.getWindow().setLayout((3 * metrics.widthPixels / 4), (6 * metrics.heightPixels / 10));

                dialog.show();
            }
        });

    }

    private void createAndPopulateRecipeTypeArray() {

        recipeTypes = new ArrayList<RecipeType>();
        recipeTypes.add(new RecipeType(R.drawable.meat, EnumRecipeType.MEAT));
        recipeTypes.add(new RecipeType(R.drawable.bird, EnumRecipeType.BIRD));
        recipeTypes.add(new RecipeType(R.drawable.fish, EnumRecipeType.FISH));
        recipeTypes.add(new RecipeType(R.drawable.pasta, EnumRecipeType.PASTA));
        recipeTypes.add(new RecipeType(R.drawable.salad, EnumRecipeType.SALAD));
        recipeTypes.add(new RecipeType(R.drawable.soup, EnumRecipeType.SOUP));
        recipeTypes.add(new RecipeType(R.drawable.bread, EnumRecipeType.BREAD));
        recipeTypes.add(new RecipeType(R.drawable.candy, EnumRecipeType.CANDY));
        recipeTypes.add(new RecipeType(R.drawable.drink, EnumRecipeType.DRINK));
        recipeTypes.add(new RecipeType(R.drawable.sauce, EnumRecipeType.SAUCE));
    }

}

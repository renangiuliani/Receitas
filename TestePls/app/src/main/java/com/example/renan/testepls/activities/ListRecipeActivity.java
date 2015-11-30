package com.example.renan.testepls.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renan.testepls.R;
import com.example.renan.testepls.Util.DrawerMenuUtil;
import com.example.renan.testepls.Util.MyLinearLayoutManager;
import com.example.renan.testepls.Util.NumericUtil;
import com.example.renan.testepls.adapter.IngredientAdapter;
import com.example.renan.testepls.adapter.ListRecipeAdapter;
import com.example.renan.testepls.adapter.RecipeTypeAdapter;
import com.example.renan.testepls.entities.EnumRecipeType;
import com.example.renan.testepls.entities.Ingredient;
import com.example.renan.testepls.entities.Recipe;
import com.example.renan.testepls.entities.RecipeType;
import com.example.renan.testepls.helper.SimpleItemTouchHelperCallback;
import com.mikepenz.materialdrawer.Drawer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Renan on 21/09/2015.
 */
public class ListRecipeActivity extends AppCompatActivity {

    private FloatingActionButton fbAddRecipe;
    private static ListRecipeAdapter listRecipeAdapter;
    private static RecyclerView rvListRecipe, rvIngredients;
    private static List<Recipe> recipes;
    public static int filterRecipeType, difficulty = 0;
    private static Recipe recipe;
    private static List<Recipe> listAdd;
    private static List<Ingredient> ingredients, ingredientsBk;
    private static String textSearch;
    private static TextView tvNoResult;
    private Drawer result;
    ArrayList<RecipeType> recipeTypes;
    private IngredientAdapter ingredientAdapter;
    private List<ImageView> listStar;
    private static String stPrepareTime, stServes, stPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bindElements();

        filterRecipeType = 0;

        recipe = new Recipe();
        listStar = new ArrayList<>();
        ingredients = new ArrayList<>();
        ingredientsBk = new ArrayList<>();

        recipes = recipe.getAll(0);
        listRecipeAdapter.setList(recipes);

        result = new DrawerMenuUtil(this, toolbar).create().build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recipe = new Recipe();
        updateItens(0);
        listRecipeAdapter.setList(recipes);
        listRecipeAdapter.notifyDataSetChanged();

        if (recipes.size() == 0) {
            rvListRecipe.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
        } else {
            tvNoResult.setVisibility(View.GONE);
            rvListRecipe.setVisibility(View.VISIBLE);
        }

        updateTotalRecipes(result);
    }

    private void updateTotalRecipes(Drawer result) {
        result.updateBadge(String.valueOf(recipe.getAll(-1).size()), 0);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("favorite", String.valueOf(1))).size()), 1);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.MEAT.getCode()))).size()), 3);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.BIRD.getCode()))).size()), 4);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.FISH.getCode()))).size()), 5);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.PASTA.getCode()))).size()), 6);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.SALAD.getCode()))).size()), 7);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.SOUP.getCode()))).size()), 8);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.BREAD.getCode()))).size()), 9);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.CANDY.getCode()))).size()), 10);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.DRINK.getCode()))).size()), 11);
        result.updateBadge(String.valueOf(recipe.getByAll(-1, setValueHashMap("codeType", String.valueOf(EnumRecipeType.SAUCE.getCode()))).size()), 12);
    }

    public static void updateItens(int limit) {
        HashMap<String, String> hashMapQuery = new HashMap<String, String>();

        if (filterRecipeType == 12) {
            hashMapQuery.put("favorite", "1");
        } else if (filterRecipeType > 0 && filterRecipeType < 11) {
            hashMapQuery.put("codeType", String.valueOf(filterRecipeType));
        }

        if (!("").equals(textSearch) && textSearch != null) {
            hashMapQuery.put("title", textSearch);
        }

        if (difficulty != 0) {
            hashMapQuery.put("difficulty", String.valueOf(difficulty));
        }

        if (!("").equals(stPrepareTime) && stPrepareTime != null) {
            hashMapQuery.put("prepareTime", stPrepareTime);
        }

        if (!("").equals(stServes) && stServes != null) {
            hashMapQuery.put("serves", stServes);
        }

        if (!("").equals(stPrice) && stPrice != null) {
            hashMapQuery.put("price", stPrice.replaceAll("[.]", "").replaceAll("[,]", "."));
        }

        if (ingredients.size() != 0) {
            String stIngredients = "";
            for (int x = 0; x < ingredients.size(); x++) {
                if ((x + 1) == ingredients.size()) {
                    stIngredients += ingredients.get(x).getNameIngredient();
                } else {
                    stIngredients += ingredients.get(x).getNameIngredient() + ",";
                }
            }
            hashMapQuery.put("ingredients", stIngredients);
        }

        listAdd = recipe.getByAll(limit, hashMapQuery);

        if (limit == 0) {
            recipes = listAdd;
        } else {
            recipes.addAll(listAdd);
        }
        listRecipeAdapter.setList(recipes);

        if (recipes.size() == 0) {
            rvListRecipe.setVisibility(View.GONE);
            tvNoResult.setVisibility(View.VISIBLE);
        } else {
            tvNoResult.setVisibility(View.GONE);
            rvListRecipe.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchFilter());

        return true;
    }

    private class SearchFilter implements SearchView.OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText.toString().equals("")) {
                textSearch = "";
            } else {
                textSearch = newText.toUpperCase();
            }

            updateItens(0);

            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_advanced_filter:

                ingredientsBk.clear();
                for(Ingredient i : ingredients){
                    ingredientsBk.add(i);
                }

                final Dialog dialog = new Dialog(ListRecipeActivity.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.custom_dialog_advanced_filter);

                bindElementsDialog(dialog);

                for (int x = 0; x < listStar.size(); x++) {
                    final int finalX = x;
                    listStar.get(x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fillStars((finalX + 1), false);
                        }
                    });
                }

                fillStars(difficulty, true);

                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindElementsDialog(final Dialog dialog) {
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients.clear();
                for(Ingredient i : ingredientsBk){
                    ingredients.add(i);
                }

                dialog.dismiss();
            }
        });

        final EditText etPrepareTime = (EditText) dialog.findViewById(R.id.et_d_prepare_time);
        etPrepareTime.setText(stPrepareTime);

        final EditText etServes = (EditText) dialog.findViewById(R.id.et_d_serves);
        etServes.setText(stServes);

        final EditText etPrice = (EditText) dialog.findViewById(R.id.et_d_price);
        etPrice.setText(stPrice);
        etPrice.addTextChangedListener(new NumericUtil.MonetaryMask(etPrice, false));

        final EditText etIngredientName = (EditText) dialog.findViewById(R.id.et_d_name_ingredient);
        etIngredientName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_d_add_ingredient), null);

        etIngredientName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etIngredientName.getRight() - etIngredientName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!etIngredientName.getText().toString().trim().equals("")) {
                            Ingredient ingredientAdd = new Ingredient();
                            ingredientAdd.setNameIngredient(etIngredientName.getText().toString().trim());
                            if (ingredientAdapter.addItem(ingredientAdd)) {
                                etIngredientName.setText("");
                                etIngredientName.setError(null);
                            } else {
                                etIngredientName.setError(getString(R.string.existing_ingredient));
                            }
                        } else {
                            etIngredientName.setText("");
                        }
                    }
                }
                return false;
            }
        });

        etIngredientName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && !etIngredientName.getText().toString().trim().equals("")) {
                        Ingredient ingredientAdd = new Ingredient();
                        ingredientAdd.setNameIngredient(etIngredientName.getText().toString().trim());
                        if (ingredientAdapter.addItem(ingredientAdd)) {
                            etIngredientName.setText("");
                            etIngredientName.setError(null);
                        } else {
                            etIngredientName.setError(getString(R.string.existing_ingredient));
                        }
                    } else if (etIngredientName.getText().toString().trim().equals("")) {
                        etIngredientName.setText("");
                    }
                    return true;
                }
                return false;
            }
        });

        TextView tvClean = (TextView) dialog.findViewById(R.id.tv_d_clean);
        tvClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPrepareTime.setText("");
                etServes.setText("");
                etPrice.setText("");
                etIngredientName.setText("");
                fillStars(0, false);
                difficulty = 0;
                ingredientAdapter.setList(new ArrayList<Ingredient>());
                ingredients = new ArrayList<>();
                stPrepareTime = "";
                stServes = "";
                stPrice = "";
            }
        });

        TextView tvFilter = (TextView) dialog.findViewById(R.id.tv_d_filter);
        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stPrepareTime = etPrepareTime.getText().toString();
                stServes = etServes.getText().toString();
                stPrice = etPrice.getText().toString();
                updateItens(0);
                dialog.dismiss();
            }
        });

        listStar = new ArrayList<>();

        listStar.add((ImageView) dialog.findViewById(R.id.iv_star_1));
        listStar.add((ImageView) dialog.findViewById(R.id.iv_star_2));
        listStar.add((ImageView) dialog.findViewById(R.id.iv_star_3));
        listStar.add((ImageView) dialog.findViewById(R.id.iv_star_4));
        listStar.add((ImageView) dialog.findViewById(R.id.iv_star_5));

        ingredientAdapter = new IngredientAdapter(this, ingredients, false);
        rvIngredients = (RecyclerView) dialog.findViewById(R.id.rv_d_list_ingredient);
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIngredients.setAdapter(ingredientAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(ingredientAdapter, false, true);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvIngredients);
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
                if (newState == 0) {
                    fbAddRecipe.setVisibility(View.VISIBLE);
                } else {
                    fbAddRecipe.setVisibility(View.GONE);
                }
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

    private HashMap<String, String> setValueHashMap(String key, String value) {
        HashMap<String, String> hashMapCodeType = new HashMap<String, String>();

        hashMapCodeType.put(key, value);

        return hashMapCodeType;
    }

    public void fillStars(int number, Boolean start) {

        if ((number == difficulty) && !start) {
            number = 0;
        }

        difficulty = number;

        for (int x = 0; x < listStar.size(); x++) {
            if (x < number) {
                listStar.get(x).setImageDrawable(getResources().getDrawable(R.drawable.ic_star_on));
                listStar.get(x).setAlpha((float) 1);
            } else {
                listStar.get(x).setImageDrawable(getResources().getDrawable(R.drawable.ic_star_off));
                listStar.get(x).setAlpha((float) 0.5);
            }
        }
    }

}

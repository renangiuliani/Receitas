package com.example.renan.testepls;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.renan.testepls.adapter.IngredientAdapter;
import com.example.renan.testepls.entities.Ingredient;
import com.example.renan.testepls.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renan on 22/09/2015.
 */
public class RecipeActivity extends AppCompatActivity{

    private String recipeType;
    private EditText ingredientName, prepareTime, serves;
    private IngredientAdapter ingredientAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeType = getIntent().getStringExtra("recipeType");
        if(recipeType != null){
            RecipeActivity.this.setTitle("Registrar " + recipeType);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindElements();
    }

    private List<Ingredient> createAndPopulateIngredientArray() {
        List<Ingredient> ingredient = new ArrayList<Ingredient>();
        ingredient.add(new Ingredient("1 ovo"));
        ingredient.add(new Ingredient("2 galinhas picadinhas"));
        ingredient.add(new Ingredient("1/2 litro água que tubarão não nada"));
        ingredient.add(new Ingredient("1 litro de leite de macho"));
        ingredient.add(new Ingredient("45 pentelhos"));

        return ingredient;
    }

    private void bindElements() {
        ingredientName = (EditText) findViewById(R.id.et_name_ingredient);
        ingredientName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_add_ingredient), null);

        prepareTime = (EditText) findViewById(R.id.et_prepare_time);
        prepareTime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_prepare_time), null);

        serves = (EditText) findViewById(R.id.et_serves);
        serves.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_serves), null);

        ingredientAdapter = new IngredientAdapter(this, createAndPopulateIngredientArray());
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_ingredient);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ingredientAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(ingredientAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);


        bindEvents();
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


    private void bindEvents() {
        ingredientName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (ingredientName.getRight() - ingredientName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (!ingredientName.getText().toString().trim().equals("")) {
                            Ingredient ingredient = new Ingredient(ingredientName.getText().toString().trim());
                            ingredientAdapter.addItem(ingredient);
                            ingredientName.setText("");
                        } else {
                            ingredientName.setText("");
                        }
                    }
                }
                return false;
            }
        });

    }

}

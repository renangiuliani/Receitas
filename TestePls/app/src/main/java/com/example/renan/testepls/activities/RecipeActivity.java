package com.example.renan.testepls.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.testepls.R;
import com.example.renan.testepls.Util.MyLinearLayoutManager;
import com.example.renan.testepls.Util.Util;
import com.example.renan.testepls.adapter.IngredientAdapter;
import com.example.renan.testepls.entities.Ingredient;
import com.example.renan.testepls.entities.Recipe;
import com.example.renan.testepls.helper.SimpleItemTouchHelperCallback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Renan on 22/09/2015.
 */
public class RecipeActivity extends AppCompatActivity{

    private String recipeType;
    private EditText ingredientName, prepareTime, serves, titleRecipe, prepareMode, observation;
    private IngredientAdapter ingredientAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fbSave;
    private Recipe recipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        bindElements();

        final Bundle extras = getIntent().getExtras();

        if(extras != null) {

            if (extras.getString("recipeType") == null) {
                final Recipe recipe = getIntent().getParcelableExtra("edit");

                this.setTitle("Registrar " + recipe.getRecipeType());
                titleRecipe.setText(recipe.getTitle());
                prepareTime.setText(recipe.getPrepareTime());
                serves.setText(String.valueOf(recipe.getServes()));
                prepareMode.setText(recipe.getPrepareMode());
                observation.setText(recipe.getObservation());
            } else {
                this.setTitle("Registrar " + extras.getString("recipeType"));
                recipe = new Recipe();
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    private void bindElements() {

        titleRecipe = (EditText) findViewById(R.id.et_title_recipe);
        prepareMode = (EditText) findViewById(R.id.et_prepare_mode);

        ingredientName = (EditText) findViewById(R.id.et_name_ingredient);
        ingredientName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_add_ingredient), null);

        prepareTime = (EditText) findViewById(R.id.et_prepare_time);
        prepareTime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_prepare_time), null);

        serves = (EditText) findViewById(R.id.et_serves);
        serves.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_serves), null);

        observation = (EditText) findViewById(R.id.et_observation);

        fbSave = (FloatingActionButton) findViewById(R.id.fb_save_recipe);

        ingredientAdapter = new IngredientAdapter(this, createAndPopulateIngredientArray());
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_ingredient);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(ingredientAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(ingredientAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);


        bindEvents();
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
                        }
                        ingredientName.setText("");
                        ingredientName.setError(null);
                    }
                }
                return false;
            }
        });

        ingredientName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        Ingredient ingredient = new Ingredient(ingredientName.getText().toString().trim());
                        ingredientAdapter.addItem(ingredient);
                        ingredientName.setText("");
                        ingredientName.setError(null);
                    }
                    return true;
                }
                return false;
            }
        });

        fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveServiceOrder();
            }
        });

    }

    private void saveServiceOrder() {
        final Calendar serviceOrderCalendar = Calendar.getInstance(Util.LOCALE_PT_BR);

        boolean isValid = this.verifyMandatoryFields(titleRecipe, prepareMode, prepareTime, serves);

        isValid = isValid & verifyListEmpty(recyclerView);

        isValid = isValid & this.verifyPrepareTime(serviceOrderCalendar);

        if (isValid) {
            recipe.setTitle(titleRecipe.getText().toString());
            recipe.setImageRecipe(R.drawable.soup);
            recipe.setPrepareMode(prepareMode.getText().toString());
            recipe.setPrepareTime(prepareTime.getText().toString());
            recipe.setServes(Integer.valueOf(serves.getText().toString()));
            recipe.setRecipeType(2);
            recipe.setObservation(observation.getText().toString());
            recipe.save();
            Toast.makeText(RecipeActivity.this, "Receita salva com sucesso!", Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            Toast.makeText(this, "Algo errado", Toast.LENGTH_SHORT).show();
        }
    }



    private boolean verifyMandatoryFields(EditText... fields) {
        boolean isValid = true;
        for (EditText field : fields) {
            field.setError(null);
            if (TextUtils.isEmpty(field.getText().toString().trim())) {
                field.setError(getString(R.string.msg_mandatory));
                if (isValid) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    private boolean verifyListEmpty(RecyclerView recyclerView) {
        boolean isValid = true;

        if (recyclerView.getChildCount() == 0) {
            ingredientName.setError(getString(R.string.ingredient_empty));
            isValid = false;
        }

        return isValid;
    }

    private boolean verifyPrepareTime(Calendar serviceOrderCalendar) {
        final String timeText = prepareTime.getText().toString().trim();
        if (!TextUtils.isEmpty(timeText)) {
            try {
                final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Util.LOCALE_PT_BR);
                timeFormat.setLenient(false);
                timeFormat.parse(timeText);
                if (serviceOrderCalendar != null) {
                    final String[] timeTextArray = timeText.split("[:]");
                    serviceOrderCalendar.set(Calendar.HOUR, Integer.valueOf(timeTextArray[0]));
                    serviceOrderCalendar.set(Calendar.MINUTE, Integer.valueOf(timeTextArray[1]));
                }
            } catch (ParseException parseException) {
                prepareTime.setError(this.getString(R.string.msg_invalid_time));
                return false;
            }

            if(!timeText.substring(2,3).equals(":") || timeText.length() != 5){
                prepareTime.setError(this.getString(R.string.msg_invalid_time));
                return false;
            }
        }
        return true;
    }

}

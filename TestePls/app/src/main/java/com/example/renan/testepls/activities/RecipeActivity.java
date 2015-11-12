package com.example.renan.testepls.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.testepls.R;
import com.example.renan.testepls.Util.MyLinearLayoutManager;
import com.example.renan.testepls.Util.Util;
import com.example.renan.testepls.adapter.IngredientAdapter;
import com.example.renan.testepls.entities.Ingredient;
import com.example.renan.testepls.entities.Recipe;
import com.example.renan.testepls.entities.RecipeType;
import com.example.renan.testepls.helper.SimpleItemTouchHelperCallback;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Renan on 22/09/2015.
 */
public class RecipeActivity extends AppCompatActivity {

    private RecipeType recipeType;
    private EditText ingredientName, prepareTime, serves, titleRecipe, prepareMode, observation;
    private IngredientAdapter ingredientAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fbSave;
    private Recipe recipe;
    private List<Ingredient> ingredients;
    private Ingredient ingredientSave;
    private ImageView photoRecipe;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe);

        ingredients = new ArrayList<>();

        ingredientSave = new Ingredient();

        bindElements();

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {

            recipeType = extras.getParcelable("recipeType");
            setTitle("Registrar " + recipeType.getEnumRecipeType().getName());

            if (extras.getParcelable("edit") != null) {
                recipe = extras.getParcelable("edit");

                photoRecipe.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getImageRecipe(), 0, recipe.getImageRecipe().length));

                titleRecipe.setText(recipe.getTitle());
                prepareTime.setText(recipe.getPrepareTime());
                serves.setText(String.valueOf(recipe.getServes()));
                prepareMode.setText(recipe.getPrepareMode());
                observation.setText(recipe.getObservation());

                List<Ingredient> ingredients = ingredientSave.getByRecipe(recipe.getId());

                ingredientAdapter.setList(ingredients);
            } else {
                recipe = new Recipe();
                recipe.setRecipeType(recipeType.getEnumRecipeType().getCode());
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                Bitmap img = (Bitmap) bundle.get("data");
                photoRecipe.setImageBitmap(img);
            }

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

        ingredientAdapter = new IngredientAdapter(this, ingredients, false);
        recyclerView = (RecyclerView) findViewById(R.id.rv_list_ingredient);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(ingredientAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(ingredientAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        photoRecipe = (ImageView) findViewById(R.id.iv_photo);

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
                            Ingredient ingredientAdd = new Ingredient();
                            ingredientAdd.setNameIngredient(ingredientName.getText().toString().trim());
                            if (ingredientAdapter.addItem(ingredientAdd)) {
                                ingredientName.setText("");
                                ingredientName.setError(null);
                            } else {
                                ingredientName.setError(getString(R.string.existing_ingredient));
                            }
                        } else {
                            ingredientName.setText("");
                        }
                    }
                }
                return false;
            }
        });

        ingredientName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && !ingredientName.getText().toString().trim().equals("")) {
                        Ingredient ingredientAdd = new Ingredient();
                        ingredientAdd.setNameIngredient(ingredientName.getText().toString().trim());
                        if (ingredientAdapter.addItem(ingredientAdd)) {
                            ingredientName.setText("");
                            ingredientName.setError(null);
                        } else {
                            ingredientName.setError(getString(R.string.existing_ingredient));
                        }
                    } else if (ingredientName.getText().toString().trim().equals("")) {
                        ingredientName.setText("");
                    }
                    return true;
                }
                return false;
            }
        });

        prepareTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && ("00:00").equals(prepareTime.getText().toString())) {
                    prepareTime.setText("");
                }
            }
        });

        serves.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && ("0").equals(serves.getText().toString())) {
                    serves.setText("");
                }
            }
        });

        fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });

        photoRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamera, 0);
            }
        });

    }

    private void saveRecipe() {
        final Calendar recipeCalendar = Calendar.getInstance(Util.LOCALE_PT_BR);

        boolean isValid;

        isValid = verifyListEmpty(recyclerView);

        isValid = isValid & this.verifyPrepareTime(recipeCalendar);

        isValid = isValid & this.verifyMandatoryFields(prepareMode, serves, prepareTime, titleRecipe);

        if (isValid) {

            new AlertDialog.Builder(RecipeActivity.this)
                    .setTitle(getString(R.string.recipe_save))
                    .setMessage(R.string.question_save_recipe)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            recipe.setTitle(titleRecipe.getText().toString());
                            Bitmap photoSave = ((BitmapDrawable) photoRecipe.getDrawable()).getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            photoSave.compress(Bitmap.CompressFormat.PNG, 0, bos);

                            recipe.setImageRecipe(bos.toByteArray());
//                            switch (recipeType.getEnumRecipeType().getCode()) {
//                                case 1:
//                                    recipe.setImageRecipe(R.drawable.meat);
//                                    break;
//                                case 2:
//                                    recipe.setImageRecipe(R.drawable.bird);
//                                    break;
//                                case 3:
//                                    recipe.setImageRecipe(R.drawable.fish);
//                                    break;
//                                case 4:
//                                    recipe.setImageRecipe(R.drawable.salad);
//                                    break;
//                                case 5:
//                                    recipe.setImageRecipe(R.drawable.sauce);
//                                    break;
//                                case 6:
//                                    recipe.setImageRecipe(R.drawable.soup);
//                                    break;
//                                case 7:
//                                    recipe.setImageRecipe(R.drawable.pasta);
//                                    break;
//                                case 8:
//                                    recipe.setImageRecipe(R.drawable.drink);
//                                    break;
//                                case 9:
//                                    recipe.setImageRecipe(R.drawable.candy);
//                                    break;
//                                case 10:
//                                    recipe.setImageRecipe(R.drawable.bread);
//                                    break;
//                            }

                            recipe.setPrepareMode(prepareMode.getText().toString());
                            recipe.setPrepareTime(prepareTime.getText().toString());
                            recipe.setServes(Integer.valueOf(serves.getText().toString()));
                            recipe.setRecipeType(recipeType.getEnumRecipeType().getCode());
                            recipe.setObservation(observation.getText().toString());
                            recipe.setId((int)recipe.save());

                            saveIngredients();

                            Toast.makeText(RecipeActivity.this, R.string.save_successful, Toast.LENGTH_SHORT).show();
                            RecipeActivity.this.finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(R.drawable.ic_dialog_save)
                    .show();
        } else {
            Toast.makeText(this, R.string.save_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveIngredients() {
        ingredientSave.deleteByRecipe(recipe.getId());

        for (Ingredient i : ingredientAdapter.itens) {
            i.setRecipeId(recipe.getId());
            i.save();
        }
    }

    private boolean verifyMandatoryFields(EditText... fields) {
        boolean isValid = true;
        for (EditText field : fields) {
            if (TextUtils.isEmpty(field.getText().toString().trim())) {
                field.setError(getString(R.string.msg_mandatory));
                field.requestFocus();
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
            ingredientName.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    private boolean verifyPrepareTime(Calendar recipeCalendar) {
        final String timeText = prepareTime.getText().toString().trim();
        if (!TextUtils.isEmpty(timeText)) {
            try {
                final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Util.LOCALE_PT_BR);
                timeFormat.setLenient(false);
                timeFormat.parse(timeText);
                if (recipeCalendar != null) {
                    final String[] timeTextArray = timeText.split("[:]");
                    recipeCalendar.set(Calendar.HOUR, Integer.valueOf(timeTextArray[0]));
                    recipeCalendar.set(Calendar.MINUTE, Integer.valueOf(timeTextArray[1]));
                }
            } catch (ParseException parseException) {
                prepareTime.setError(this.getString(R.string.msg_invalid_time));
                prepareTime.requestFocus();
                return false;
            }

            if (!timeText.substring(2, 3).equals(":") || timeText.length() != 5) {
                prepareTime.setError(this.getString(R.string.msg_invalid_time));
                prepareTime.requestFocus();
                return false;
            }
        }
        return true;
    }
}

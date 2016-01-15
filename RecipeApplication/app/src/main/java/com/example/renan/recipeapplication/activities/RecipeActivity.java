package com.example.renan.recipeapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.recipeapplication.R;
import com.example.renan.recipeapplication.Util.MyLinearLayoutManager;
import com.example.renan.recipeapplication.Util.NumericUtil;
import com.example.renan.recipeapplication.Util.Util;
import com.example.renan.recipeapplication.adapter.IngredientAdapter;
import com.example.renan.recipeapplication.entities.EnumRecipeType;
import com.example.renan.recipeapplication.entities.Ingredient;
import com.example.renan.recipeapplication.entities.Recipe;
import com.example.renan.recipeapplication.helper.SimpleItemTouchHelperCallback;

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

    private EnumRecipeType enumRecipeType;
    private EditText etIngredientName, etPrepareTime, etServes, etTitleRecipe, etPrepareMode, etObservation, etPrice;
    private IngredientAdapter ingredientAdapter;
    private RecyclerView rvIngredients;
    private Recipe recipe;
    private List<Ingredient> ingredients;
    private Ingredient ingredientSave;
    private ImageView ivImageRecipe;
    private List<ImageView> listStar;
    private int difficulty = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe);

        ingredients = new ArrayList<>();
        listStar = new ArrayList<>();
        ingredientSave = new Ingredient();

        bindElements();

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {

            enumRecipeType = (EnumRecipeType) extras.getSerializable("recipeType");
            setTitle("Registrar " + enumRecipeType.getName());

            fillStars(1);

            if (extras.getParcelable("edit") != null) {
                recipe = extras.getParcelable("edit");

                //ivImageRecipe.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getImageRecipe(), 0, recipe.getImageRecipe().length));

                etTitleRecipe.setText(recipe.getTitle());
                etPrepareTime.setText(recipe.getPrepareTime());
                etServes.setText(String.valueOf(recipe.getServes()));
                etPrepareMode.setText(recipe.getPrepareMode());
                etObservation.setText(recipe.getObservation());

                String stPrice = NumericUtil.parseMoneyToBr(Double.valueOf(recipe.getPrice()));

                etPrice.setText((recipe.getPrice() == 0 ? "" : stPrice));

                fillStars(recipe.getDifficulty());

                List<Ingredient> ingredients = ingredientSave.getByRecipe(recipe.getId());

                ingredientAdapter.setList(ingredients);
            } else {
                recipe = new Recipe();
                recipe.setRecipeType(enumRecipeType.getCode());
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap img = (Bitmap) bundle.get("data");
                ivImageRecipe.setImageBitmap(img);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem m1 = menu.add(0, 0, 0, "Salvar");
        m1.setIcon(R.drawable.ic_save_recipe);
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                onBackPressed();
                return true;
            case 0:
                saveRecipe();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindElements() {

        etTitleRecipe = (EditText) findViewById(R.id.et_title_recipe);
        etPrepareMode = (EditText) findViewById(R.id.et_prepare_mode);

        etIngredientName = (EditText) findViewById(R.id.et_name_ingredient);
        etIngredientName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_add_ingredient), null);

        etPrepareTime = (EditText) findViewById(R.id.et_prepare_time);
        etPrepareTime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_prepare_time), null);

        etServes = (EditText) findViewById(R.id.et_serves);
        etServes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_serves), null);

        etObservation = (EditText) findViewById(R.id.et_observation);

        ingredientAdapter = new IngredientAdapter(this, ingredients, false);
        rvIngredients = (RecyclerView) findViewById(R.id.rv_list_ingredient);
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIngredients.setAdapter(ingredientAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(ingredientAdapter, true, true);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvIngredients);

        ivImageRecipe = (ImageView) findViewById(R.id.iv_image_recipe);

        etPrice = (EditText) findViewById(R.id.et_price);

        listStar.add((ImageView) findViewById(R.id.iv_star_1));
        listStar.add((ImageView) findViewById(R.id.iv_star_2));
        listStar.add((ImageView) findViewById(R.id.iv_star_3));
        listStar.add((ImageView) findViewById(R.id.iv_star_4));
        listStar.add((ImageView) findViewById(R.id.iv_star_5));

        bindEvents();
    }

    private void bindEvents() {
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

        etPrepareTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && ("00:00").equals(etPrepareTime.getText().toString())) {
                    etPrepareTime.setText("");
                }
            }
        });

        etServes.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && ("0").equals(etServes.getText().toString())) {
                    etServes.setText("");
                }
            }
        });


        ivImageRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamera, 0);
            }
        });

        etPrice.addTextChangedListener(new NumericUtil.MonetaryMask(etPrice, true));

        for(int x = 0; x < listStar.size(); x++){
            final int finalX = x;
            listStar.get(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fillStars((finalX + 1));
                }
            });
        }
    }

    private void saveRecipe() {
        final Calendar recipeCalendar = Calendar.getInstance(Util.LOCALE_PT_BR);

        boolean isValid;

        isValid = verifyListEmpty(rvIngredients);

        isValid = isValid & this.verifyPrepareTime(recipeCalendar);

        isValid = isValid & this.verifyMandatoryFields(etPrepareMode, etServes, etPrepareTime, etTitleRecipe);

        if (isValid) {

            new AlertDialog.Builder(RecipeActivity.this)
                    .setTitle(getString(R.string.recipe_save))
                    .setMessage(R.string.question_save_recipe)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            recipe.setTitle(etTitleRecipe.getText().toString());
                            Bitmap imageSave = ((BitmapDrawable) ivImageRecipe.getDrawable()).getBitmap();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            imageSave.compress(Bitmap.CompressFormat.PNG, 0, bos);

                            recipe.setImageRecipe(bos.toByteArray());

                            recipe.setPrepareMode(etPrepareMode.getText().toString());
                            recipe.setPrepareTime(etPrepareTime.getText().toString());
                            recipe.setServes(Integer.valueOf(etServes.getText().toString()));
                            recipe.setRecipeType(enumRecipeType.getCode());
                            recipe.setObservation(etObservation.getText().toString());
                            String stPrice = etPrice.getText().toString().replaceAll("[R$.]", "").replaceAll("[,]", ".");
                            recipe.setPrice(("").equals(stPrice) ? 0 : Float.valueOf(stPrice));
                            recipe.setDifficulty(difficulty);
                            recipe.setId((int) recipe.save());

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

        for (int i = 0; i < ingredientAdapter.itens.size(); i++) {
            Ingredient ingredientAux = new Ingredient();
            ingredientAux.setOrder(i);
            ingredientAux.setRecipeId(recipe.getId());
            ingredientAux.setNameIngredient(ingredientAdapter.itens.get(i).getNameIngredient());
            ingredientAux.save();
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
            etIngredientName.setError(getString(R.string.ingredient_empty));
            etIngredientName.requestFocus();
            isValid = false;
        }

        return isValid;
    }

    private boolean verifyPrepareTime(Calendar recipeCalendar) {
        final String timeText = etPrepareTime.getText().toString().trim();
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
                etPrepareTime.setError(this.getString(R.string.msg_invalid_time));
                etPrepareTime.requestFocus();
                return false;
            }

            if (!timeText.substring(2, 3).equals(":") || timeText.length() != 5) {
                etPrepareTime.setError(this.getString(R.string.msg_invalid_time));
                etPrepareTime.requestFocus();
                return false;
            }
        }
        return true;
    }

    public void fillStars(int number){

        difficulty = number;

        for(int x = 0; x < listStar.size(); x++){
            listStar.get(x).setImageResource(enumRecipeType.getImageBlackWhite());
            listStar.get(x).setAlpha((float) 0.7);
            if(x < number) {
                listStar.get(x).setImageResource(enumRecipeType.getImage());
                listStar.get(x).setAlpha((float) 1);
            }
        }
    }
}

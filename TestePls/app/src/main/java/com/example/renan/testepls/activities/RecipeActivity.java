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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Renan on 22/09/2015.
 */
public class RecipeActivity extends AppCompatActivity {

    private RecipeType recipeType;
    private EditText etIngredientName, etPrepareTime, etServes, etTitleRecipe, etPrepareMode, etObservation, etPrice;
    private IngredientAdapter ingredientAdapter;
    private RecyclerView rvIngredients;
    private FloatingActionButton fbSave;
    private Recipe recipe;
    private List<Ingredient> ingredients;
    private Ingredient ingredientSave;
    private ImageView ivImageRecipe;
    private RatingBar rbDifficulty;
    private String numero;


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

                ivImageRecipe.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getImageRecipe(), 0, recipe.getImageRecipe().length));

                etTitleRecipe.setText(recipe.getTitle());
                etPrepareTime.setText(recipe.getPrepareTime());
                etServes.setText(String.valueOf(recipe.getServes()));
                etPrepareMode.setText(recipe.getPrepareMode());
                etObservation.setText(recipe.getObservation());

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
                ivImageRecipe.setImageBitmap(img);
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

        etTitleRecipe = (EditText) findViewById(R.id.et_title_recipe);
        etPrepareMode = (EditText) findViewById(R.id.et_prepare_mode);

        etIngredientName = (EditText) findViewById(R.id.et_name_ingredient);
        etIngredientName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_add_ingredient), null);

        etPrepareTime = (EditText) findViewById(R.id.et_prepare_time);
        etPrepareTime.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_prepare_time), null);

        etServes = (EditText) findViewById(R.id.et_serves);
        etServes.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_serves), null);

        etObservation = (EditText) findViewById(R.id.et_observation);

        fbSave = (FloatingActionButton) findViewById(R.id.fb_save_recipe);

        ingredientAdapter = new IngredientAdapter(this, ingredients, false);
        rvIngredients = (RecyclerView) findViewById(R.id.rv_list_ingredient);
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvIngredients.setAdapter(ingredientAdapter);

        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(ingredientAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvIngredients);

        ivImageRecipe = (ImageView) findViewById(R.id.iv_image_recipe);

        rbDifficulty = (RatingBar) findViewById(R.id.rb_difficulty);

        etPrice = (EditText) findViewById(R.id.et_price);

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

        fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });

        ivImageRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamera, 0);
            }
        });

        rbDifficulty.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(RecipeActivity.this, String.valueOf(rbDifficulty.getRating()), Toast.LENGTH_SHORT).show();
            }
        });

        etPrice.addTextChangedListener(new TextWatcher() {

            private boolean isUpdating = false;
            // Pega a formatacao do sistema, se for brasil R$ se EUA US$
            private NumberFormat nf = NumberFormat.getCurrencyInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before,int after) {
                s.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                s.toString();
                // Não utilizamos
            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat decimalFormat = (DecimalFormat) (NumberFormat.getNumberInstance(Locale.US));
                decimalFormat.applyPattern("#.00");
                numero = decimalFormat.format(!s.equals("") ? 0.00 : Float.valueOf(s.toString()));
            }
        });

        etPrice.setText(numero);
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

                            recipe.setPrepareMode(etPrepareMode.getText().toString());
                            recipe.setPrepareTime(etPrepareTime.getText().toString());
                            recipe.setServes(Integer.valueOf(etServes.getText().toString()));
                            recipe.setRecipeType(recipeType.getEnumRecipeType().getCode());
                            recipe.setObservation(etObservation.getText().toString());
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
}

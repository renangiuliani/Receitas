package com.example.renan.testepls.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
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

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private File fileImage;
    public static final int MEDIA_TYPE_IMAGE = 1;


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

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
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

        ingredientAdapter = new IngredientAdapter(this, ingredients);
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

        fbSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveRecipe();
            }
        });

        photoRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecipeActivity.this, "Tirar Foto", Toast.LENGTH_SHORT).show();
                // create Intent to take a picture and return control to the calling application
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                final String filename = getResources().getString(R.string.app_name);

                fileImage = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        filename + "_" + System.currentTimeMillis() + ".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileImage); // set the image file name

                // start the image capture Intent
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    private void saveRecipe() {
        final Calendar recipeCalendar = Calendar.getInstance(Util.LOCALE_PT_BR);

        boolean isValid = this.verifyMandatoryFields(titleRecipe, prepareMode, prepareTime, serves);

        isValid = isValid & verifyListEmpty(recyclerView);

        isValid = isValid & this.verifyPrepareTime(recipeCalendar);

        if (isValid) {

            new AlertDialog.Builder(RecipeActivity.this)
                    .setTitle(getString(R.string.recipe_save))
                    .setMessage(R.string.question_save_recipe)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            recipe.setTitle(titleRecipe.getText().toString());
                            //recipe.setImageRecipe(R.drawable.without_photo);
            switch (recipeType.getEnumRecipeType().getCode()){

                case 1:
                    recipe.setImageRecipe(R.drawable.meat);
                    break;
                case 2:
                    recipe.setImageRecipe(R.drawable.bird);
                    break;
                case 3:
                    recipe.setImageRecipe(R.drawable.fish);
                    break;
                case 4:
                    recipe.setImageRecipe(R.drawable.salad);
                    break;
                case 5:
                    recipe.setImageRecipe(R.drawable.sauce);
                    break;
                case 6:
                    recipe.setImageRecipe(R.drawable.soup);
                    break;
                case 7:
                    recipe.setImageRecipe(R.drawable.pasta);
                    break;
                case 8:
                    recipe.setImageRecipe(R.drawable.drink);
                    break;
                case 9:
                    recipe.setImageRecipe(R.drawable.candy);
                    break;
                case 10:
                    recipe.setImageRecipe(R.drawable.sandwich);
                    break;
            }

                            recipe.setPrepareMode(prepareMode.getText().toString());
                            recipe.setPrepareTime(prepareTime.getText().toString());
                            recipe.setServes(Integer.valueOf(serves.getText().toString()));
                            recipe.setRecipeType(recipeType.getEnumRecipeType().getCode());
                            recipe.setObservation(observation.getText().toString());
                            recipe.setId((int) recipe.save());

                            saveIngredients();

                            Toast.makeText(RecipeActivity.this, R.string.save_successful, Toast.LENGTH_SHORT).show();
                            RecipeActivity.this.finish();
                            Intent intent = new Intent(RecipeActivity.this, ListRecipeActivity.class);

                            intent.putExtra("recipeType", recipeType);

                            startActivity(intent);
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
                return false;
            }

            if (!timeText.substring(2, 3).equals(":") || timeText.length() != 5) {
                prepareTime.setError(this.getString(R.string.msg_invalid_time));
                return false;
            }
        }
        return true;
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

}

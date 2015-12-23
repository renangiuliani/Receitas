package com.example.renan.recipeapplication.activities;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.recipeapplication.R;
import com.example.renan.recipeapplication.Util.MyLinearLayoutManager;
import com.example.renan.recipeapplication.Util.NumericUtil;
import com.example.renan.recipeapplication.adapter.IngredientAdapter;
import com.example.renan.recipeapplication.entities.EnumRecipeType;
import com.example.renan.recipeapplication.entities.Ingredient;
import com.example.renan.recipeapplication.entities.Recipe;

import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1284141 on 30/10/2015.
 */
public class VisualizeRecipeActivity extends AppCompatActivity {

    private Recipe recipe, recipeAux;
    private Integer idRecipe;
    private ImageView ivImageRecipe, ivShare, ivBack, ivEdit, ivGradient, ivDelete;
    private FloatingActionButton ivFavorite;
    private TextView tvTitle, tvPrepareTime, tvServes, tvPrepareMode, tvObservation, tvTitleObservation, tvPrice;
    private RecyclerView rvListIngredient;
    private EnumRecipeType enumRecipeType;
    private IngredientAdapter ingredientAdapter;
    private List<Ingredient> ingredients;
    private Ingredient ingredientAux;
    private View lnObservation;
    private List<ImageView> listStar;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_visualize);
        toolbar.setContentInsetsAbsolute(0, 0);

        setSupportActionBar(toolbar);

        ingredients = new ArrayList<>();
        listStar = new ArrayList<>();

        bindElements();

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            idRecipe = extras.getInt("recipe");
            fillElements();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        fillElements();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap img = (Bitmap) bundle.get("data");
                ivImageRecipe.setImageBitmap(img);

                Bitmap imageSave = ((BitmapDrawable) ivImageRecipe.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                imageSave.compress(Bitmap.CompressFormat.PNG, 0, bos);

                recipe.setImageRecipe(bos.toByteArray());

                recipe.save();
                Toast.makeText(VisualizeRecipeActivity.this, "Imagem da receita atualizada!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void bindElements() {

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvPrepareTime = (TextView) findViewById(R.id.tv_prepare_time);
        tvServes = (TextView) findViewById(R.id.tv_serves);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivFavorite = (FloatingActionButton) findViewById(R.id.iv_favorite);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivImageRecipe = (ImageView) findViewById(R.id.iv_image_recipe);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);
        tvPrepareMode = (TextView) findViewById(R.id.tv_prepare_mode);
        tvObservation = (TextView) findViewById(R.id.tv_observation);
        tvTitleObservation = (TextView) findViewById(R.id.tv_title_observation);

        ingredientAdapter = new IngredientAdapter(this, ingredients, true);
        rvListIngredient = (RecyclerView) findViewById((R.id.rv_list_ingredient));
        rvListIngredient.setHasFixedSize(true);
        rvListIngredient.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvListIngredient.setAdapter(ingredientAdapter);

        ivGradient = (ImageView) findViewById(R.id.iv_gradient);

        tvPrice = (TextView) findViewById(R.id.tv_price);

        listStar.add((ImageView) findViewById(R.id.iv_star_1));
        listStar.add((ImageView) findViewById(R.id.iv_star_2));
        listStar.add((ImageView) findViewById(R.id.iv_star_3));
        listStar.add((ImageView) findViewById(R.id.iv_star_4));
        listStar.add((ImageView) findViewById(R.id.iv_star_5));

        ivDelete = (ImageView) findViewById(R.id.iv_delete);

        lnObservation = (View) findViewById(R.id.ln_observation);

        bindEvents();

    }

    private void bindEvents() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(VisualizeRecipeActivity.this, R.anim.image_click));
                onBackPressed();
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualizeRecipeActivity.this, RecipeActivity.class);
                intent.putExtra("edit", recipe);
                intent.putExtra("recipeType", enumRecipeType);
                VisualizeRecipeActivity.this.startActivity(intent);
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the text message with a string
                final Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, prepareTextShare(recipe));
                sendIntent.setType(HTTP.PLAIN_TEXT_TYPE);

                // Create intent to show the chooser dialog
                final Intent chooser = Intent.createChooser(sendIntent, getString(R.string.share));

                // Verify the original intent will resolve to at least one activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recipe.getFavorite() == 1) {
                    recipe.setFavorite(0);
                    ivFavorite.setImageResource(R.drawable.ic_favorite_off2);
                    Toast.makeText(VisualizeRecipeActivity.this, "Receita adicionada aos seus favoritos!", Toast.LENGTH_SHORT).show();
                } else {
                    recipe.setFavorite(1);
                    ivFavorite.setImageResource(R.drawable.ic_favorite_on);
                    Toast.makeText(VisualizeRecipeActivity.this, "Receita removida dos seus favoritos!", Toast.LENGTH_SHORT).show();
                }
                recipe.save();
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(VisualizeRecipeActivity.this)
                        .setTitle(R.string.recipe_remove)
                        .setMessage(R.string.question_recipe_remove)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteRecipe(recipe.getId());
                                Toast.makeText(VisualizeRecipeActivity.this, R.string.remove_successful, Toast.LENGTH_SHORT).show();
                                VisualizeRecipeActivity.this.finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_alert_warning)
                        .show();
            }
        });

        ivGradient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamera, 0);
            }
        });

    }

    public void fillElements() {
        recipe = recipeAux.getById(idRecipe);

        tvTitle.setText(recipe.getTitle());
        tvPrepareTime.setText(recipe.getPrepareTime());
        tvServes.setText(String.valueOf(recipe.getServes()));
        if(recipe.getImageRecipe() != null) {
            ivImageRecipe.setImageBitmap(BitmapFactory.decodeByteArray(recipe.getImageRecipe(), 0, recipe.getImageRecipe().length));
        }else{
            ivImageRecipe.setImageResource(R.drawable.without_photo);
        }
        tvPrepareMode.setText(recipe.getPrepareMode());

        tvObservation.setText(recipe.getObservation());
        if (!("").equals(recipe.getObservation())) {
        } else {
            tvTitleObservation.setVisibility(View.GONE);
            tvObservation.setVisibility(View.GONE);
            lnObservation.setVisibility(View.GONE);
        }

        if (recipe.getFavorite() == 1) {
            ivFavorite.setImageResource(R.drawable.ic_favorite_on);
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorite_off2);
        }

        String stPrice = NumericUtil.parseMoneyToBr(Double.valueOf(recipe.getPrice())).replaceAll("[R$]", "");

        tvPrice.setText("R$ " + (recipe.getPrice() == 0 ? "-" : stPrice));

        ingredients = ingredientAux.getByRecipe(idRecipe);
        ingredientAdapter.setList(ingredients);

        enumRecipeType = EnumRecipeType.getEnumByCode(recipe.getRecipeType());

        fillStars(recipe.getDifficulty());
    }

    public void deleteRecipe(int idRecipe) {
        recipe.delete(idRecipe);
        ingredientAux.deleteByRecipe(idRecipe);
    }

    public String prepareTextShare(Recipe recipe) {
        String textShare = "";

        textShare += getString(R.string.title) + ": " + recipe.getTitle();
        textShare += "\n \n" + getString(R.string.prepare_time) + ": " + recipe.getPrepareTime();
        textShare += "\n" + getString(R.string.serves) + ": " + recipe.getServes();
        textShare += "\n" + getString(R.string.difficulty) + ": " + (int) recipe.getDifficulty();
        textShare += "\n" + getString(R.string.price) + ": " + recipe.getPrice();

        textShare += "\n \n" + getString(R.string.list_ingredient) + ": ";

        for (Ingredient i : ingredients) {
            textShare += "\n" + i.getNameIngredient();
        }

        textShare += "\n \n" + getString(R.string.prepare_mode) + ": \n" + recipe.getPrepareMode();

        if (!("").equals(recipe.getObservation())) {
            textShare += "\n \n" + getString(R.string.observation) + ": \n" + recipe.getObservation();
        }

        return textShare;
    }

    public void fillStars(int number){

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

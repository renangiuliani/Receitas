package com.example.renan.testepls.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.testepls.R;
import com.example.renan.testepls.Util.MyLinearLayoutManager;
import com.example.renan.testepls.adapter.IngredientAdapter;
import com.example.renan.testepls.entities.EnumRecipeType;
import com.example.renan.testepls.entities.Ingredient;
import com.example.renan.testepls.entities.Recipe;
import com.example.renan.testepls.entities.RecipeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c1284141 on 30/10/2015.
 */
public class VisualizeRecipeActivity extends AppCompatActivity {

    private Recipe recipe, recipeAux;
    private Integer idRecipe;
    private ImageView ivPhotoRecipe, ivShare, ivBack, ivEdit, ivPrepareTime;
    private FloatingActionButton ivFavorite;
    private TextView tvTitle, tvPrepareTime, tvServes, tvPrepareMode, tvObservation, tvTitleObservation;
    private RecyclerView rvListIngredient;
    private boolean favorite = false;
    private RecipeType recipeType;
    private IngredientAdapter ingredientAdapter;
    private List<Ingredient> ingredients;
    private Ingredient ingredientAux;
    private RelativeLayout rlMain;

    private int teste = 0;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_visualize);
        toolbar.setContentInsetsAbsolute(0, 0);

        setSupportActionBar(toolbar);

        ingredients = new ArrayList<>();

        bindElements();

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            idRecipe = extras.getInt("recipe");

            recipe = recipeAux.getById(idRecipe);


            tvTitle.setText(recipe.getTitle());
            tvPrepareTime.setText(recipe.getPrepareTime());
            tvServes.setText(String.valueOf(recipe.getServes()));
            ivPhotoRecipe.setImageResource(recipe.getImageRecipe());
            tvPrepareMode.setText(recipe.getPrepareMode());

            tvObservation.setText(recipe.getObservation());
            if (!("").equals(recipe.getObservation())) {
            } else {
                tvTitleObservation.setVisibility(View.GONE);
                tvObservation.setVisibility(View.GONE);
            }

            if (recipe.getFavorite() == 1) {
                ivFavorite.setImageResource(R.drawable.ic_favorite_on);
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_off2);
            }

            ingredients = ingredientAux.getByRecipe(idRecipe);
            ingredientAdapter.setList(ingredients);

            recipeType = new RecipeType(recipe.getImageRecipe(), EnumRecipeType.getEnumByCode(recipe.getRecipeType()));
        }

    }

    private void bindElements() {

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvPrepareTime = (TextView) findViewById(R.id.tv_prepare_time);
        tvServes = (TextView) findViewById(R.id.tv_serves);
        ivShare = (ImageView) findViewById(R.id.iv_share);
        ivFavorite = (FloatingActionButton) findViewById(R.id.iv_favorite);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivPhotoRecipe = (ImageView) findViewById(R.id.iv_photo);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);
        tvPrepareMode = (TextView) findViewById(R.id.tv_prepare_mode);
        tvObservation = (TextView) findViewById(R.id.tv_observation);
        tvTitleObservation = (TextView) findViewById(R.id.tv_title_observation);

        ingredientAdapter = new IngredientAdapter(this, ingredients, true);
        rvListIngredient = (RecyclerView) findViewById((R.id.rv_list_ingredient));
        rvListIngredient.setHasFixedSize(true);
        rvListIngredient.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvListIngredient.setAdapter(ingredientAdapter);

        ivPrepareTime = (ImageView) findViewById(R.id.iv_prepare_time);
        rlMain = (RelativeLayout) findViewById(R.id.rl_main);

        bindEvents();

    }

    private void bindEvents() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualizeRecipeActivity.this, RecipeActivity.class);
                intent.putExtra("edit", recipe);
                intent.putExtra("recipeType", recipeType);
                VisualizeRecipeActivity.this.startActivity(intent);
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VisualizeRecipeActivity.this, "Share", Toast.LENGTH_SHORT).show();
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

        ivPrepareTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (teste == 0) {
//                    teste = 1;
//                    rlMain.setBackground(getResources().getDrawable(R.drawable.background3));
//                } else if (teste == 1) {
//                    teste = 2;
//                    rlMain.setBackground(getResources().getDrawable(R.drawable.background33));
//                } else if (teste == 2) {
//                    teste = 3;
//                    rlMain.setBackground(getResources().getDrawable(R.drawable.background3));
//                } else if (teste == 3) {
//                    teste = 4;
//                    rlMain.setBackground(getResources().getDrawable(R.drawable.background33));
//                } else if (teste == 4) {
//                    teste = 5;
//                    rlMain.setBackground(getResources().getDrawable(R.drawable.background3));
//                } else if (teste == 5) {
//                    teste = 6;
//                    rlMain.setBackground(getResources().getDrawable(R.drawable.background33));
//                } else {
//                    teste = 0;
//                    rlMain.setBackground(getResources().getDrawable(R.drawable.background3));
//                }
            }
        });
    }
}

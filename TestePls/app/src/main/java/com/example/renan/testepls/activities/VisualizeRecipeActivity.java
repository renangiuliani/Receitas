package com.example.renan.testepls.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.testepls.R;
import com.example.renan.testepls.entities.Recipe;
import com.example.renan.testepls.entities.RecipeType;

/**
 * Created by c1284141 on 30/10/2015.
 */
public class VisualizeRecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private ImageView ivPhotoRecipe, ivShare, ivBack, ivEdit;
    private FloatingActionButton ivFavorite;
    private TextView tvTitle, tvPrepareTime, tvServes;
    private boolean favorite = false;
    private RecipeType recipeType;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_visualize);
        toolbar.setContentInsetsAbsolute(0, 0);

        setSupportActionBar(toolbar);

        bindElements();

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            recipe = extras.getParcelable("recipe");
            tvTitle.setText(recipe.getTitle());
            tvPrepareTime.setText(recipe.getPrepareTime());
            tvServes.setText(String.valueOf(recipe.getServes()));
            ivPhotoRecipe.setImageResource(recipe.getImageRecipe());

            recipeType = extras.getParcelable("recipeType");
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
                if (favorite) {
                    favorite = false;
                    ivFavorite.setImageResource(R.drawable.ic_favorite_off2);
                } else {
                    favorite = true;
                    ivFavorite.setImageResource(R.drawable.ic_favorite_on);
                }
                Toast.makeText(VisualizeRecipeActivity.this, "Favorite", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

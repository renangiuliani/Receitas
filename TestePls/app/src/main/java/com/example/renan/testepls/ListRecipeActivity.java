package com.example.renan.testepls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Renan on 21/09/2015.
 */
public class ListRecipeActivity extends AppCompatActivity {

    private String recipeType;
    private FloatingActionButton fbAddRecipe;
    private ImageView ivTbPicture;
    private TextView tvTbName;
    private String picture;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_recipe);
        setSupportActionBar(toolbar);
        recipeType = getIntent().getStringExtra("recipeType");

        //Bitmap bitmap = savedInstanceState.getParcelable("picture");

        bindElements();

        if(recipeType != null){
            tvTbName.setText(recipeType);
            //ivTbPicture.setImageDrawable(bitmap);
        }

    }

    private void bindElements() {
        fbAddRecipe = (FloatingActionButton) findViewById(R.id.fb_add_recipe);
        ivTbPicture = (ImageView) findViewById(R.id.iv_tb_picture);
        tvTbName = (TextView) findViewById(R.id.tv_tb_name);
        bindEvents();
    }

    private void bindEvents() {
        fbAddRecipe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListRecipeActivity.this, RecipeActivity.class);
                intent.putExtra("recipeType", recipeType);
                startActivity(intent);
            }
        });
    }
}

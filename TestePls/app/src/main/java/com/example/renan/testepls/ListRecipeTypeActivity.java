package com.example.renan.testepls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Renan on 21/09/2015.
 */
public class ListRecipeTypeActivity extends AppCompatActivity {

    private String recipeType;
    private FloatingActionButton fbAddRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recipe_type);
        recipeType = getIntent().getStringExtra("recipeType");

        if(recipeType != null){
            Toast.makeText(ListRecipeTypeActivity.this, "Foiii: " + recipeType, Toast.LENGTH_LONG).show();
        }

        bindElements();

        ListRecipeTypeActivity.this.setTitle("Lista de algo");


    }

    private void bindElements() {
        fbAddRecipe = (FloatingActionButton) findViewById(R.id.fb_add_recipe);

        bindEvents();
    }

    private void bindEvents() {
        fbAddRecipe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Activity) ListRecipeTypeActivity.this, RecipeActivity.class);
                startActivity(intent);
            }
        });
    }
}

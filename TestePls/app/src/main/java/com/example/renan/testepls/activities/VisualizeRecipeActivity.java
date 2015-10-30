package com.example.renan.testepls.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.renan.testepls.R;
import com.example.renan.testepls.entities.Recipe;

/**
 * Created by c1284141 on 30/10/2015.
 */
public class VisualizeRecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize_recipe);

        final Bundle extras = getIntent().getExtras();

        if (extras != null) {
            recipe = extras.getParcelable("recipe");
            setTitle(recipe.getTitle());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}

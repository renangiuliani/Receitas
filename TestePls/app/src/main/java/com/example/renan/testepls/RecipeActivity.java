package com.example.renan.testepls;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Renan on 22/09/2015.
 */
public class RecipeActivity extends AppCompatActivity{

    private String recipeType;
    private EditText titleRecipe, nameIngrediente;
    private TextView listIngredients;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeType = getIntent().getStringExtra("recipeType");

        if(recipeType != null){
            RecipeActivity.this.setTitle("Registrar " + recipeType);
        }

        bindElements();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_recipe, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.save:
//                Toast.makeText(RecipeActivity.this, "LILONES", Toast.LENGTH_SHORT).show();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void bindElements() {
        titleRecipe = (EditText) findViewById(R.id.title_recipe);
        nameIngrediente = (EditText) findViewById(R.id.name_ingredient);
        nameIngrediente.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_add_ingredient), null);
        listIngredients = (TextView) findViewById(R.id.list_ingredients);

        bindEvents();
    }

    private void bindEvents() {
        nameIngrediente.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (nameIngrediente.getRight() - nameIngrediente.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if(!nameIngrediente.getText().toString().trim().equals("")) {
                            if (listIngredients.getText().toString().equals("")) {
                                listIngredients.setText(nameIngrediente.getText().toString().trim());
                            }else {
                                listIngredients.setText(listIngredients.getText() + "\n" + nameIngrediente.getText().toString().trim());
                            }
                            nameIngrediente.setText("");
                        }else{
                            nameIngrediente.setText("");
                        }
                    }
                }
                return false;
            }
        });

    }

}

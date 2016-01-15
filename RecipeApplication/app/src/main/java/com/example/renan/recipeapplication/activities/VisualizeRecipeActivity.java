package com.example.renan.recipeapplication.activities;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.recipeapplication.R;
import com.example.renan.recipeapplication.Util.MyLinearLayoutManager;
import com.example.renan.recipeapplication.Util.NumericUtil;
import com.example.renan.recipeapplication.adapter.AdditionalTimerAdapter;
import com.example.renan.recipeapplication.adapter.IngredientAdapter;
import com.example.renan.recipeapplication.entities.AdditionalTimer;
import com.example.renan.recipeapplication.entities.EnumRecipeType;
import com.example.renan.recipeapplication.entities.Ingredient;
import com.example.renan.recipeapplication.entities.Recipe;

import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by c1284141 on 30/10/2015.
 */
public class VisualizeRecipeActivity extends AppCompatActivity {

    private Recipe recipe, recipeAux;
    private Integer idRecipe;
    private ImageView ivImageRecipe, ivShare, ivBack, ivEdit, ivGradient, ivDelete, ivStartStop;
    private FloatingActionButton ivFavorite;
    private TextView tvTitle, tvPrepareTime, tvServes, tvPrepareMode, tvObservation, tvTitleObservation, tvPrice, tvTimer;
    private RecyclerView rvListIngredient, rvListTimer;
    private EnumRecipeType enumRecipeType;
    private IngredientAdapter ingredientAdapter;
    private AdditionalTimerAdapter additionalTimerAdapter;
    private List<Ingredient> ingredients;
    private Ingredient ingredientAux;
    private View lnObservation;
    private List<ImageView> listStar;
    private Timer timer;
    private TimerTask timerTask;
    private Boolean activeTimer = false;
    final Handler handler = new Handler();

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

        ivStartStop = (ImageView) findViewById(R.id.iv_start_stop);
        tvTimer = (TextView) findViewById(R.id.et_timer);

        List<AdditionalTimer> listTimer = new ArrayList<>();
        AdditionalTimer additionalTimer1 = new AdditionalTimer();
        additionalTimer1.setTimerStr("00:00:30");
        additionalTimer1.setDescription("Coco no forno");
        additionalTimer1.setActiveTimer(false);
        listTimer.add(additionalTimer1);
        AdditionalTimer additionalTimer2 = new AdditionalTimer();
        additionalTimer2.setTimerStr("00:01:15");
        additionalTimer2.setDescription("Pentelhos cozinhando");
        additionalTimer2.setActiveTimer(false);
        listTimer.add(additionalTimer2);

        additionalTimerAdapter = new AdditionalTimerAdapter(this, listTimer);
        rvListTimer = (RecyclerView) findViewById((R.id.rv_list_timer));
        rvListTimer.setHasFixedSize(true);
        rvListTimer.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvListTimer.setAdapter(additionalTimerAdapter);

        bindEvents();

    }

    private void bindEvents() {
        final int duration = 150;
        final int scale = 70;
        final float alpha = (float) 0.1;

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView ivAnimBack = (ImageView) findViewById(R.id.iv_anim_back);
                ivAnimBack.animate().setDuration(duration).scaleX(scale).scaleY(scale).alpha(alpha).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ivAnimBack.setAlpha((float) 0);
                        ivAnimBack.setScaleX(1);
                        ivAnimBack.setScaleY(1);
                        onBackPressed();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView ivAnimEdit = (ImageView) findViewById(R.id.iv_anim_edit);
                ivAnimEdit.animate().setDuration(duration).scaleX(scale).scaleY(scale).alpha(alpha).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ivAnimEdit.setAlpha((float) 0);
                        ivAnimEdit.setScaleX(1);
                        ivAnimEdit.setScaleY(1);
                        Intent intent = new Intent(VisualizeRecipeActivity.this, RecipeActivity.class);
                        intent.putExtra("edit", recipe);
                        intent.putExtra("recipeType", enumRecipeType);
                        VisualizeRecipeActivity.this.startActivity(intent);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }
        });

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImageView ivAnimShare = (ImageView) findViewById(R.id.iv_anim_share);
                ivAnimShare.animate().setDuration(duration).scaleX(scale).scaleY(scale).alpha(alpha).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ivAnimShare.setAlpha((float) 0);
                        ivAnimShare.setScaleX(1);
                        ivAnimShare.setScaleY(1);
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

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
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
                final ImageView ivAnimDelete = (ImageView) findViewById(R.id.iv_anim_delete);
                ivAnimDelete.animate().setDuration(duration).scaleX(scale).scaleY(scale).alpha(alpha).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ivAnimDelete.setAlpha((float) 0);
                        ivAnimDelete.setScaleX(1);
                        ivAnimDelete.setScaleY(1);
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

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).start();
            }
        });

        ivGradient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamera, 0);
            }
        });

        ivStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!activeTimer){
                    startTimer();
                    ivStartStop.setImageResource(R.drawable.ic_pause);
                }else{
                    stopTimerTask();
                    ivStartStop.setImageResource(R.drawable.ic_play);
                }
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

    public void startTimer() {

        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 1000, 1000); //

        activeTimer = true;
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
            activeTimer = false;
        }
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        int hour, min, sec;
                        if(("XX:XX:XX").equals(tvTimer.getText())) {
                            hour = Integer.parseInt("00:00:20".substring(0, 2).replaceAll(":", ""));
                            min = Integer.parseInt("00:00:20".substring(3, 5).replaceAll(":", ""));
                            sec = Integer.parseInt("00:00:20".substring(6, 8).replaceAll(":", ""));
                        }else{
                            hour = Integer.parseInt(tvTimer.getText().toString().substring(0, 2).replaceAll(":", ""));
                            min = Integer.parseInt(tvTimer.getText().toString().substring(3, 5).replaceAll(":", ""));
                            sec = Integer.parseInt(tvTimer.getText().toString().substring(6, 8).replaceAll(":", ""));
                        }
                        DecimalFormat df = new DecimalFormat("##");

                        String dx = df.format(hour);

                        sec = sec - 1;
                        if(sec == -1){
                            min = min -1;
                            sec = 60;
                        }
                        if(min == -1){
                            hour = hour -1;
                            min = 60;
                        }
                        tvTimer.setText(String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec));
                        if(sec == 0 && min == 0 && hour == 0){
                            Toast.makeText(getApplicationContext(), "Acabou", Toast.LENGTH_SHORT).show();
                            stopTimerTask();
                            tvTimer.setText("00:00:20");
                        }
                    }
                });

            }

        };

    }

}

package com.example.renan.testepls.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.renan.testepls.ListRecipeActivity;
import com.example.renan.testepls.R;
import com.example.renan.testepls.RecipeTypeViewHolder;
import com.example.renan.testepls.entities.RecipeType;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeTypeAdapter extends RecyclerView.Adapter<RecipeTypeViewHolder>{

    private Context context;
    private ArrayList<RecipeType> itens;

    public RecipeTypeAdapter(Context context, ArrayList<RecipeType> itens){
        this.context = context;
        this.itens = itens;
    }

    @Override
    public RecipeTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_type, parent, false);
        return new RecipeTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeTypeViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(itens.get(position).getNameRecipeType());
        viewHolder.ivPicture.setImageResource(itens.get(position).getIdRecipeType());

        viewHolder.itemView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ListRecipeActivity.class);

                        intent.putExtra("recipeType", viewHolder.tvName.getText().toString());

//                        Bitmap bmp = BitmapFactory.decodeResource(v.getContext().getResources(), viewHolder.ivPicture);
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                        byte[] b = baos.toByteArray();
//
//                        intent.putExtra("picture",b);

                        v.getContext().startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

}

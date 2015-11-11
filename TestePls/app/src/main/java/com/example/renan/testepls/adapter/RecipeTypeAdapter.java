package com.example.renan.testepls.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.renan.testepls.R;
import com.example.renan.testepls.activities.RecipeActivity;
import com.example.renan.testepls.entities.RecipeType;

import java.util.ArrayList;

/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeTypeAdapter extends RecyclerView.Adapter<RecipeTypeAdapter.RecipeTypeViewHolder> {

    private Context context;
    private ArrayList<RecipeType> itens;
    private Dialog dialog;

    public RecipeTypeAdapter(Context context, ArrayList<RecipeType> itens, Dialog dialog) {
        this.context = context;
        this.itens = itens;
        this.dialog = dialog;
    }

    @Override
    public RecipeTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_type, parent, false);
        return new RecipeTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeTypeViewHolder viewHolder, final int position) {
        viewHolder.ivPicture.setImageResource(itens.get(position).getImageRecipeType());

        viewHolder.itemView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, RecipeActivity.class);

                        intent.putExtra("recipeType", itens.get(position));

                        v.getContext().startActivity(intent);

                        dialog.cancel();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    static class RecipeTypeViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPicture;

        public RecipeTypeViewHolder(final View itemView) {
            super(itemView);
            ivPicture = (ImageView) itemView.findViewById(R.id.iv_recipe_type_picture);
        }
    }

}

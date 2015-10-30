package com.example.renan.testepls.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renan.testepls.R;
import com.example.renan.testepls.activities.ListRecipeActivity;
import com.example.renan.testepls.entities.RecipeType;

import java.util.ArrayList;

/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeTypeAdapter extends RecyclerView.Adapter<RecipeTypeAdapter.RecipeTypeViewHolder> {

    private Context context;
    private ArrayList<RecipeType> itens;

    public RecipeTypeAdapter(Context context, ArrayList<RecipeType> itens) {
        this.context = context;
        this.itens = itens;
    }

    @Override
    public RecipeTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe_type, parent, false);
        return new RecipeTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeTypeViewHolder viewHolder, final int position) {
        viewHolder.tvName.setText(itens.get(position).getEnumRecipeType().getName());
        viewHolder.ivPicture.setImageResource(itens.get(position).getImageRecipeType());

        viewHolder.itemView
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ListRecipeActivity.class);

                        intent.putExtra("recipeType", itens.get(position));

                        v.getContext().startActivity(intent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    static class RecipeTypeViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public ImageView ivPicture;

        public RecipeTypeViewHolder(final View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_recipe_type_name);
            ivPicture = (ImageView) itemView.findViewById(R.id.iv_recipe_type_picture);
        }
    }

}

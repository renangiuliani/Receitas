package com.example.renan.testepls.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.renan.testepls.R;
import com.example.renan.testepls.RecipeTypeViewHolder;
import com.example.renan.testepls.entities.RecipeType;

import java.util.ArrayList;

/**
 * Created by Renan on 17/09/2015.
 */
public class RecipeTypeAdapter extends RecyclerView.Adapter<RecipeTypeViewHolder> implements View.OnClickListener{

    private Context context;
    private ArrayList<RecipeType> itens;

    public RecipeTypeAdapter(Context context, ArrayList<RecipeType> itens){
        this.context = context;
        this.itens = itens;
    }

    @Override
    public RecipeTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_type_item, parent, false);
        RecipeTypeViewHolder viewHolder = new RecipeTypeViewHolder(context, view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeTypeViewHolder viewHolder, int position) {
        RecipeType recipeType = itens.get(position);
        viewHolder.tvName.setText(recipeType.getNameRecipeType());
        viewHolder.ivPicture.setImageResource(recipeType.getIdRecipeType());

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, "CLICKOU", Toast.LENGTH_LONG).show();
    }
}

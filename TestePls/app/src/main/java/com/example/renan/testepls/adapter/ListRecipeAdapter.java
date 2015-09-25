package com.example.renan.testepls.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renan.testepls.R;
import com.example.renan.testepls.entities.Recipe;

import java.util.List;

/**
 * Created by c1284141 on 25/09/2015.
 */
public class ListRecipeAdapter extends RecyclerView.Adapter<ListRecipeAdapter.ListRecipeViewHolder>{

    private Context context;
    private List<Recipe> itens;

    public ListRecipeAdapter(Context context, List<Recipe> itens){
        this.context = context;
        this.itens = itens;
    }

    @Override
    public ListRecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_recipe, parent, false);
        return new ListRecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListRecipeViewHolder viewHolder, int position) {
        viewHolder.ivRecipe.setImageResource(itens.get(position).getImageRecipe());
        if(itens.get(position).getTitle().length() > 30){
            viewHolder.tvTitle.setText(itens.get(position).getTitle().subSequence(0, 27) + "...");
        }else{
            viewHolder.tvTitle.setText(itens.get(position).getTitle());
        }
        viewHolder.tvPrepareTime.setText(itens.get(position).getPrepareTime());
        viewHolder.tvServes.setText(String.valueOf(itens.get(position).getServes()));
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public class ListRecipeViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivRecipe;
        public TextView tvTitle, tvPrepareTime, tvServes;

        public ListRecipeViewHolder(View itemView) {
            super(itemView);
            ivRecipe = (ImageView) itemView.findViewById(R.id.iv_recipe);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvPrepareTime = (TextView) itemView.findViewById(R.id.tv_prepare_time);
            tvServes = (TextView) itemView.findViewById(R.id.tv_serves);
        }
    }
}

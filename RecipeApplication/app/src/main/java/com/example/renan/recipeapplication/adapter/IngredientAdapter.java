package com.example.renan.recipeapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renan.recipeapplication.R;
import com.example.renan.recipeapplication.entities.Ingredient;
import com.example.renan.recipeapplication.helper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by c1284141 on 24/09/2015.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> implements ItemTouchHelperAdapter{

    private Context context;
    private Boolean whiteItens;
    public List<Ingredient> itens;

    public IngredientAdapter(Context context, List<Ingredient> itens, boolean whiteItens){
        this.context = context;
        this.itens = itens;
        this.whiteItens = whiteItens;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(itens.get(position).getNameIngredient());

        if(whiteItens){
            viewHolder.tvName.setTextColor(context.getResources().getColor(R.color.text_icons));
            viewHolder.ivMarker.setImageResource(R.drawable.ic_marker_white);
        }
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public boolean addItem(Ingredient item){
        boolean isValid = true;
        for(Ingredient i : itens){
            if(i.getNameIngredient().equals(item.getNameIngredient())){
                isValid = false;
            }
        }
        if(isValid) {
            itens.add(item);
            notifyDataSetChanged();
        }

        return isValid;
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itens, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itens, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void onItemDismiss(int position) {
        itens.remove(position);
        notifyDataSetChanged();
    }


    class IngredientViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public ImageView ivMarker;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_ingredient);
            ivMarker = (ImageView) itemView.findViewById(R.id.iv_marker);
        }
    }

    public void setList(List<Ingredient> itens){
        this.itens = itens;
        notifyDataSetChanged();
    }
}

package com.example.renan.testepls.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.renan.testepls.R;
import com.example.renan.testepls.entities.Ingredient;
import com.example.renan.testepls.helper.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by c1284141 on 24/09/2015.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> implements ItemTouchHelperAdapter{

    private Context context;
    private List<Ingredient> itens;

    public IngredientAdapter(Context context, List<Ingredient> itens){
        this.context = context;
        this.itens = itens;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder viewHolder, int position) {
        viewHolder.tvName.setText(itens.get(position).getNameIngredient());
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public void addItem(Ingredient item){

        itens.add(item);
        notifyItemInserted(itens.size() - 1);

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
        notifyItemRemoved(position);
    }


    class IngredientViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_ingredient);
        }
    }
}
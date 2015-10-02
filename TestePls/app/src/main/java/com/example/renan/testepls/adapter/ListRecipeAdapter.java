package com.example.renan.testepls.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renan.testepls.R;
import com.example.renan.testepls.activities.ListRecipeActivity;
import com.example.renan.testepls.activities.RecipeActivity;
import com.example.renan.testepls.entities.Recipe;

import java.util.List;

/**
 * Created by c1284141 on 25/09/2015.
 */
public class ListRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Recipe> itens;
    private int mPosition;

    private final static int VIEW_TYPE_ITEM = 1, VIEW_TYPE_LAST = 2;

    public ListRecipeAdapter(Context context, List<Recipe> itens){
        this.context = context;
        this.itens = itens;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        RecyclerView.ViewHolder viewHolder = null;

        if(viewType==VIEW_TYPE_ITEM){
            view = LayoutInflater.from(context).inflate(R.layout.item_list_recipe, parent, false);
            viewHolder = new ListRecipeViewHolder(view);
        }else if(viewType==VIEW_TYPE_LAST){
            view = LayoutInflater.from(context).inflate(R.layout.item_last_list_recipe, parent, false);
            viewHolder = new LastViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_ITEM){
            mPosition = holder.getLayoutPosition();
            final ListRecipeViewHolder viewHolder = (ListRecipeViewHolder) holder;

            viewHolder.ivRecipe.setImageResource(itens.get(position).getImageRecipe());

            if(itens.get(position).getTitle().length() > 30){
                viewHolder.tvTitle.setText(itens.get(position).getTitle().subSequence(0, 27) + "...");
            }else{
                viewHolder.tvTitle.setText(itens.get(position).getTitle());
            }

            viewHolder.tvPrepareTime.setText(String.valueOf(itens.get(position).getPrepareTime()));
            viewHolder.tvServes.setText(String.valueOf(itens.get(position).getServes()));

            viewHolder.ivMenu.setOnClickListener(new PopUpContextMenu());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == itens.size()) ? VIEW_TYPE_LAST : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return itens.size()+1;
    }

    public void setList(List<Recipe> itens){
        this.itens = itens;
        notifyDataSetChanged();
    }

    private class PopUpContextMenu implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            final PopupMenu popup = new PopupMenu(context, v);

            popup.inflate(R.menu.menu_list_recipe_item);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    final Recipe selectedItem = ListRecipeAdapter.this.getSelectedItem();

                    switch (item.getItemId()) {
                        case R.id.action_edit:
                            Toast.makeText(context, "Editar Receita", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, RecipeActivity.class);
                            intent.putExtra("edit", selectedItem);
                            context.startActivity(intent);
                            return  false;
                        case R.id.action_remove:
                            selectedItem.delete(selectedItem.getId());
                            ListRecipeActivity.updateItens();
                            Toast.makeText(context, "Receita removida com sucesso!", Toast.LENGTH_SHORT).show();
                            return  false;
                        case R.id.action_change_photo:
                            Toast.makeText(context, "Trocar Foto", Toast.LENGTH_SHORT).show();
                            return  false;
                    }
                    return  false;
                }
            });

            popup.show();
        }
    }

    public static class ListRecipeViewHolder extends RecyclerView.ViewHolder{

        public ImageView ivRecipe, ivMenu;
        public TextView tvTitle, tvPrepareTime, tvServes;

        public ListRecipeViewHolder(View itemView) {
            super(itemView);
            ivRecipe = (ImageView) itemView.findViewById(R.id.iv_recipe);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvPrepareTime = (TextView) itemView.findViewById(R.id.tv_prepare_time);
            tvServes = (TextView) itemView.findViewById(R.id.tv_serves);
            ivMenu = (ImageView) itemView.findViewById(R.id.iv_menu);
        }
    }

    public static class LastViewHolder extends RecyclerView.ViewHolder {

        public LastViewHolder(View itemView) {
            super(itemView);
        }
    }

    public Recipe getSelectedItem() {
        return itens.get(mPosition);
    }
}

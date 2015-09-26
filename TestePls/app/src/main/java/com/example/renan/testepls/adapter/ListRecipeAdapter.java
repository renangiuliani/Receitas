package com.example.renan.testepls.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.PopupMenu.OnMenuItemClickListener;
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
    public void onBindViewHolder(final ListRecipeViewHolder viewHolder, int position) {
        viewHolder.ivRecipe.setImageResource(itens.get(position).getImageRecipe());
        if(itens.get(position).getTitle().length() > 30){
            viewHolder.tvTitle.setText(itens.get(position).getTitle().subSequence(0, 27) + "...");
        }else{
            viewHolder.tvTitle.setText(itens.get(position).getTitle());
        }
        viewHolder.tvPrepareTime.setText(itens.get(position).getPrepareTime());
        viewHolder.tvServes.setText(String.valueOf(itens.get(position).getServes()));

        viewHolder.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, viewHolder.ivMenu);

                popup.inflate(R.menu.menu_list_recipe_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                Toast.makeText(context, "Editar Receita", Toast.LENGTH_SHORT).show();
                            case R.id.action_remove:
                                Toast.makeText(context, "Remover Receita", Toast.LENGTH_SHORT).show();
                            case R.id.action_change_photo:
                                Toast.makeText(context, "Trocar Foto", Toast.LENGTH_SHORT).show();
                        }
                        return  true;
                    }
                });

                popup.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public class ListRecipeViewHolder extends RecyclerView.ViewHolder{

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
}

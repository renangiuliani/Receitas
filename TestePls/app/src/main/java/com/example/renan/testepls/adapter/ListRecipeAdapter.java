package com.example.renan.testepls.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.renan.testepls.R;
import com.example.renan.testepls.activities.VisualizeRecipeActivity;
import com.example.renan.testepls.entities.Recipe;

import java.util.List;

/**
 * Created by c1284141 on 25/09/2015.
 */
public class ListRecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Recipe> itens;
    public int mPosition;

    private final static int VIEW_TYPE_ITEM = 1, VIEW_TYPE_LAST = 2;
    private Recipe mRecipe;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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

            viewHolder.ivMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosition = viewHolder.getLayoutPosition();
                    final PopupMenu popup = new PopupMenu(context, v);
                    popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) context);
                    popup.inflate(R.menu.menu_list_recipe_item);

                    popup.show();
                }
            });

            viewHolder.ivRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, VisualizeRecipeActivity.class);
                    intent.putExtra("recipe", itens.get(position));
                    context.startActivity(intent);

                }
            });
        }
    }

    private Recipe getRecipe(int position) {
        return itens.get(position);
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
